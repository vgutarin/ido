package org.ido.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ido.syntax.vo.FunctionEvaluationVo;
import org.ido.syntax.vo.ImmutableVo;
import org.ido.syntax.vo.OperatorEvaluationVo;
import org.ido.syntax.vo.ScopeVo;

public class Parser {

	private final List<ILexeme> _types;
	private final List<IOperator> _operators;
	private final List<IFunction> _functions;

	public Parser(List<ITypeDescriptor<?>> types, List<IOperator> operators, List<IFunction> functions) throws SyntaxException {

		// TODO make sure typeId`s are unique
		_types = new ArrayList<ILexeme>(types);
		Scope parentheses = new Scope('(', ')');
		_types.add(parentheses);
		_types.add(parentheses.closeLexeme);
		_operators = new ArrayList<IOperator>(operators);
		_functions = new ArrayList<IFunction>(functions);
	}
	public Parser(List<ITypeDescriptor<?>> types, List<IOperator> operators) throws SyntaxException {
		this(types, operators, new ArrayList<IFunction>());
	}
	private <LT extends ILexeme> void _removeNotApplicable(Position src, List<LT> lexemes) {
		src.skipWhiteCharacters();
		final int firstNotWhiteIdx =  src.current;

		for (; src.current < src.str.length(); ++src.current) {
			final String stringToAnalyze = src.str.substring(firstNotWhiteIdx, src.current + 1);

			if (lexemes.stream().anyMatch(t -> t.isStringRepresentationStartsWith(stringToAnalyze))) {
				lexemes.removeIf(t -> !t.isStringRepresentationStartsWith(stringToAnalyze));
				continue;
			}

			break;
		}
		
		if (firstNotWhiteIdx == src.current) {
			lexemes.clear();
		}
	}
	
	private List<IVo> _parseFunctionArguments(Position src) throws ParserException {
		
		src.skipWhiteCharacters();
		if (src.str.length() <= src.current || '(' != src.str.charAt(src.current))
			throw new ParserException("Cannot parse source near position %s", src);
		
		++src.current;
		src.skipWhiteCharacters();
		int startVoIdx = src.current;
		List<IVo> result = new ArrayList<IVo>();
		
		ExpressionComponent ecs = null;
		while (src.str.length() > src.current && ')' != src.str.charAt(src.current)) {
			ecs = _parseComponent(src, null == ecs || null != ecs.operator ? 0 : 1);
			src.skipWhiteCharacters();
			
			if (src.str.length() > src.current && ',' == src.str.charAt(src.current)) {
				result.add(parse(src.str.substring(startVoIdx, src.current)));
				++src.current;
				src.skipWhiteCharacters();
				startVoIdx = src.current;
				ecs = null;
			}
		}
		
		if(startVoIdx != src.current)
			result.add(parse(src.str.substring(startVoIdx, src.current)));
		
		if (src.str.length() <= src.current || ')' != src.str.charAt(src.current))
			throw new ParserException("Cannot parse source near position %s", src);
		++src.current;
		
		return result;
	}
	
	private ExpressionComponent _parseComponent(Position src, final int leftOperandsCount) throws ParserException {
		List<ILexeme> lexemes = new ArrayList<ILexeme>(_types);
		lexemes.addAll(_functions);
		lexemes.addAll(_operators.stream().filter(o-> o.leftOperandsCount() == leftOperandsCount).collect(Collectors.toList()));
		final int startPosition = src.current;
		_removeNotApplicable(src, lexemes);

		final String candidateRawSrc = src.str.substring(startPosition, src.current);
		final String candidateSrc = candidateRawSrc.trim();

		if (lexemes.isEmpty())
			throw new ParserException("Cannot find any lexeme on position: %s.",
					src.toString());

		if (1 < lexemes.size())
			throw new ParserException(
					"Ambiguous source code (%s lexemes are applicable simultaneously) near position: %s",
					SyntaxException.toCsv(lexemes),
					src.toString());

		final ExpressionComponent ec = new ExpressionComponent(src, startPosition, src.current - startPosition, lexemes.get(0));
		if (null != ec.typeDescriptor) {
			if (!ec.typeDescriptor.isStringRepresentationValid(candidateSrc)) {
				throw new ParserException("Type %s is not able to parse source near position: %s.", ec.lexeme.getLexemeId(),
						src.toString());
			}
			return ec;
		}
		
		if (null != ec.operator) {
			if (!ec.operator.isStringRepresentationValid(candidateSrc)) {
				throw new ParserException("Operator %s is not able to parse source near position: %s.", ec.lexeme.getLexemeId(),
						src.toString());
			}
			
			return ec;
		} 
		
		if (null != ec.scope) {
			ExpressionComponent ecs = _parseComponent(src, 0);
			while(ecs.lexeme != ec.scope.closeLexeme) {
				ecs = _parseComponent(src, null == ecs.operator ? 1 : 0);
			}
			
			return new ExpressionComponent(src, startPosition, src.current - startPosition, ec.scope); 
		}
		
		if (null != ec.function) {
			List<IVo> fargvs = _parseFunctionArguments(src);
			ExpressionComponent ecf  = new ExpressionComponent(ec.src, ec.startIdx, src.current - ec.startIdx, ec.lexeme);
			ecf.functionArgs = fargvs;
			return ecf;
		}
		
		return ec;
	}

	private Position _parseOperator(final Position source, final ExpressionComponent c, List<IVo> operands)  throws ParserException {
		Position os = source.copyForward();
		int nextOperatorLeftOperandsCount = 0;
		do {
			ExpressionComponent oc = _parseComponent(os, nextOperatorLeftOperandsCount);
			if (null != oc.operator) {
				nextOperatorLeftOperandsCount = 0 == oc.operator.rightOperandsCount() /*instanceof ParenthesesClose*/ ? 1 : 0;		
				if (
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
						os.current = os.start;
						break;
					}
				}
			} else {
				if (0 == nextOperatorLeftOperandsCount) ++nextOperatorLeftOperandsCount;
			}
			os = os.copyForward();
		} while (!"".equals(os.str.substring(os.current).trim()));
		
		
		final String operandCandidate = source.str.substring(c.startIdx + c.length, os.start);
		IVo ivo = parse(operandCandidate);
					
		final int startOperatorIdx = Math.min(c.startIdx, operands.stream().mapToInt(o->o.getComponentDesc().startIdx).min().orElse(Integer.MAX_VALUE));
		int endOperatorIdx = Math.max(c.startIdx + c.length, os.start);
		operands.add(ivo);
		ivo = new OperatorEvaluationVo(
				new ExpressionComponent(c.src, startOperatorIdx, endOperatorIdx, c.lexeme),
				operands
			);
		operands.clear();
		operands.add(ivo);
		
		return os;
	}
	
	public IVo parse(String src) throws ParserException {
		Position position = new Position(src);
		
		List<IVo> operands = new ArrayList<IVo>();
		do {
			final ExpressionComponent c = _parseComponent(position, operands.size());
			if (null != c.operator) {
				position = _parseOperator(position, c, operands);
			} else if (null != c.typeDescriptor) {
				operands.add(new ImmutableVo(c));
				position = position.copyForward();
			} else if (null != c.scope) {
				operands.add(
						new ScopeVo(
								c, 
								parse(c.scope.strip(c.str))
						)
				);
				position = position.copyForward();
			}else if (null != c.function) {
				operands.add(new FunctionEvaluationVo(c, c.functionArgs));
				position = position.copyForward();
			} else {
				throw new ParserException("Unexpected lexeme %s near position %s", c.lexeme.getLexemeId(), position.toString());
			}
			
		} while (!"".equals(position.str.substring(position.current).trim()));
		
		if (operands.size() != 1)
			throw new ParserException("Exactly 1 operand is expected as result or syntax expression. Real count is %d", operands.size());
		
		return operands.get(0);
	}
}
