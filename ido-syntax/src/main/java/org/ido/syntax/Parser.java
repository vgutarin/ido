package org.ido.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ido.syntax.operator.ParenthesesClose;
import org.ido.syntax.operator.ParenthesesOpen;
import org.ido.syntax.vo.ImmutableVO;
import org.ido.syntax.vo.OperatorEvaluationVO;
import org.ido.syntax.vo.VOPriorityScope;

public class Parser {

	private final List<ITypeDescriptor<?>> _types;
	private final List<IOperator> _operators;

	public Parser(List<ITypeDescriptor<?>> types, List<IOperator> functions) {

		// TODO make sure typeId`s are unique
		_types = new ArrayList<ITypeDescriptor<?>>(types);
		_operators = new ArrayList<IOperator>(functions);
	}

	private <LT extends ILexeme> void _removeNotApplicable(Source src, List<LT> lexemes) {
		int startIdx =  src.currentPosition;

		for (; src.currentPosition < src.str.length(); ++src.currentPosition) {
			final String stringToAnalyze = src.str.substring(startIdx, src.currentPosition + 1);
			if ("".equals(stringToAnalyze.trim())) {
				// skip leading white symbols;
				++startIdx;
				continue;
			}

			if (lexemes.stream().anyMatch(t -> t.isStringRepresentationStartsWith(stringToAnalyze))) {
				lexemes.removeIf(t -> !t.isStringRepresentationStartsWith(stringToAnalyze));
				continue;
			}

			if (startIdx == src.currentPosition) {
				lexemes.clear();
			}

			return;
		}
	}
	
	private ExpressionComponent _parseComponent(Source src, final int leftOperandsCount) throws ParserException {
		List<ILexeme> lexemes = new ArrayList<ILexeme>(_types);
		lexemes.addAll(_operators.stream().filter(o-> o.leftOperandsCount() == leftOperandsCount).collect(Collectors.toList()));
		final int startPosition = src.currentPosition;
		_removeNotApplicable(src, lexemes);

		final String candidateRawSrc = src.str.substring(startPosition, src.currentPosition);
		final String candidateSrc = candidateRawSrc.trim();

		if (lexemes.isEmpty())
			throw new ParserException("Cannot find any lexeme on position: %s.",
					src.getCurrentPositionDescription());

		if (1 < lexemes.size())
			throw new ParserException(
					"Ambiguous source code (%s lexemes are applicable simultaneously) near position: %s",
					String.join(", ", lexemes.stream().map(l -> l.getLexemeId()).collect(Collectors.toList())),
					src.getCurrentPositionDescription());

		final ExpressionComponent ec = new ExpressionComponent(src, startPosition, src.currentPosition - startPosition, lexemes.get(0));
		if (null != ec.typeDescriptor) {
			if (!ec.typeDescriptor.isStringRepresentationValid(candidateSrc)) {
				throw new ParserException("Type %s is not able to parse source near position: %s.", ec.lexeme.getLexemeId(),
						src.getCurrentPositionDescription());
			}
			return ec;
		}
		
		if (null != ec.operator) {
			if (!ec.operator.isStringRepresentationValid(candidateSrc)) {
				throw new ParserException("Operator %s is not able to parse source near position: %s.", ec.lexeme.getLexemeId(),
						src.getCurrentPositionDescription());
			}
			
			if(ec.operator instanceof ParenthesesClose) {
				if(0 > --src.parentnessesOpenCount) {
					throw new ParserException("Closing parentheses %s is not expected near position: %s.", ec.lexeme.getLexemeId(),
							src.getCurrentPositionDescription());
				}
			}else if(ec.operator instanceof ParenthesesOpen) {
				++src.parentnessesOpenCount;
			}
			
			return ec;
		} 
		
		throw new ParserException("Unknown lexeme %s is not able to be processed near position: %s.", ec.lexeme.getLexemeId(),
				src.getCurrentPositionDescription());
	}

	public IVO parse(String src) throws ParserException {
		Source source = new Source(src);
		
		List<IVO> operands = new ArrayList<IVO>();
		do{
			final ExpressionComponent c = _parseComponent(source, operands.size());
			if(null != c.operator) {
				Source os = source.copyForward();
				int nextOperatorLeftOperandsCount = 0;
				do{
					ExpressionComponent oc = _parseComponent(os, nextOperatorLeftOperandsCount);
					if (null != oc.operator) {
						nextOperatorLeftOperandsCount = 0 == oc.operator.rightOperandsCount() /*instanceof ParenthesesClose*/ ? 1 : 0;		
						if (
							0 == os.parentnessesOpenCount
							&&
							oc.operator.getPriority().value <= c.operator.getPriority().value
						) {
	
							if(
								oc.operator.getPriority().value == c.operator.getPriority().value
								//&& operands.isEmpty()
								&& 0 == c.operator.leftOperandsCount()
								&& 0 == oc.operator.leftOperandsCount()
							) {
								// sequence of unary operators like " - - - 12345"
							} else {
								if (!(oc.operator instanceof ParenthesesClose)) os.currentPosition = os.startPosition;
								break;
							}
						}
					} else {
						if(0 == nextOperatorLeftOperandsCount) ++nextOperatorLeftOperandsCount;
					}
					os = os.copyForward();
				} while (!"".equals(os.str.substring(os.currentPosition).trim()));
				
				
				final String operandCandidate = src.substring(c.startIdx + c.length, os.startPosition);
				IVO ivo = parse(operandCandidate);
				
				if (c.operator instanceof ParenthesesOpen) {
					ivo = new VOPriorityScope(
							new ExpressionComponent(
									source,
									c.src.startPosition,
									os.currentPosition - c.src.startPosition,
									c.operator),
							ivo
					);
				} else {					
					final int startOperatorIdx = Math.min(c.startIdx, operands.stream().mapToInt(o->o.getComponentDesc().startIdx).min().orElse(Integer.MAX_VALUE));
					int endOperatorIdx = Math.max(c.startIdx + c.length, os.startPosition);
					operands.add(ivo);
					ivo = new OperatorEvaluationVO(
							new ExpressionComponent(c.src, startOperatorIdx, endOperatorIdx, c.lexeme),
							operands
						);
					operands.clear();
				}
				operands.add(ivo);
				source = os.copyForward();
			} else {
				if (null != c.typeDescriptor){
					operands.add(new ImmutableVO(c));
					source = source.copyForward();
				}
			}
		}while(!"".equals(source.str.substring(source.currentPosition).trim()));
		
		if(operands.size() != 1)
			throw new ParserException("Exactly 1 operand is expected as result or syntax expression. Real count is %d", operands.size());
		
		return operands.get(0);
	}
}
