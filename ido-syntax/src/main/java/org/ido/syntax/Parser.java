package org.ido.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ido.syntax.vo.ImmutableVO;
import org.ido.syntax.vo.OperatorEvaluationVO;

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
	
	private ExpressionComponent _parseComponent(Source src) throws ParserException {
		List<ILexeme> lexemes = new ArrayList<ILexeme>(_types);
		lexemes.addAll(_operators);
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
			return ec;
		} 
		
		throw new ParserException("Unknown lexeme %s is not able to be processed near position: %s.", ec.lexeme.getLexemeId(),
				src.getCurrentPositionDescription());
	}

	public IVO parse(String src) throws ParserException {
		Source scope = new Source(src);
		
		List<IVO> operands = new ArrayList<IVO>();
		do{
			final ExpressionComponent c = _parseComponent(scope);
			if(null != c.operator) {
				Source os = scope.copyForward();
				do{
					ExpressionComponent oc = _parseComponent(os);
					if (
						null != oc.operator
						&& 
						oc.operator.getPriority().value <= c.operator.getPriority().value
					){
						os.currentPosition = os.startPosition;
						break;
					}
					os = os.copyForward();
				}while(!"".equals(os.str.substring(os.currentPosition).trim()));
				
				int startOperatorIdx = Math.min(c.startIdx, operands.stream().mapToInt(o->o.getComponentDesc().startIdx).min().orElse(Integer.MAX_VALUE));
				
				operands.add(parse(src.substring(c.startIdx + c.length, os.startPosition)));
				
				int endOperatorIdx = Math.max(c.startIdx + c.length, os.startPosition);
				
				IVO oevo = new OperatorEvaluationVO(
						new ExpressionComponent(c.src, startOperatorIdx, endOperatorIdx, c.lexeme),
						operands
					);
				operands.clear();
				operands.add(oevo);
				scope = os.copyForward();
			} else {
				if (null != c.typeDescriptor){
					operands.add(new ImmutableVO(c));
					scope = scope.copyForward();
				}
			}
		}while(!"".equals(scope.str.substring(scope.currentPosition).trim()));
		
		if(operands.size() != 1)
			throw new ParserException("Exactly 1 operand is expected as result or syntax expression. Real count is %d", operands.size());
		
		return operands.get(0);
	}
}
