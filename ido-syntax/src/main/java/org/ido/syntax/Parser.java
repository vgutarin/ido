package org.ido.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ido.syntax.vo.ImmutableVO;
import org.ido.syntax.vo.OperatorEvaluationVO;

public class Parser {

	private final List<ITypeDescriptor<?>> _types;
	private final List<IOperator> _operators;

	private class Scope {
		
		public final String src;
		public final int startPosition;
		public int currentPosition;
		public final IOperator operator;
		public final IVO leftOperand;
		
		private Scope(String src, int startPosition, IVO leftOperand, IOperator operator) {
			this.src = src;
			this.startPosition = currentPosition = startPosition;
			this.operator = operator;
			this.leftOperand = leftOperand;
		}
		
		public Scope(String src) {
			this(src, 0, null, null);
		}
		
		public Scope copyForward(){
			return new Scope(src, currentPosition, leftOperand, operator);
		}
		
		public Scope copyForward(IVO leftOperand){
			return new Scope(src, currentPosition, leftOperand, operator);
		}
		
		public Scope copyForward(IOperator operator){
			return new Scope(src, currentPosition, leftOperand, operator);
		}
		
		public <LT extends ILexeme> void removeNotApplicable(List<LT> lexemes)
		{			
			int startIdx = currentPosition;

			for (; currentPosition < src.length(); ++currentPosition) {
				final String stringToAnalyze = src.substring(startIdx, currentPosition+1);
				if ("".equals(stringToAnalyze.trim())) {
					//skip leading white symbols;
					++startIdx;
					continue;
				}

				if (
					lexemes.stream().anyMatch(
							t -> t.isStringRepresentationStartsWith(stringToAnalyze)
					)
				) {
					lexemes.removeIf(t -> !t.isStringRepresentationStartsWith(stringToAnalyze));
					continue;
				}
				
				if (startIdx == currentPosition) {
					lexemes.clear();
				}
				
				return;
			}
		}
		
		public String getCurrentPositionDescription(int substrLength ) {
			int startIdx = currentPosition;
			if(substrLength < 0){
				substrLength = -substrLength;
				startIdx -= substrLength;
			}
			return String.format("%d. String: %s%s%s",
					currentPosition, 
					startIdx > 0 ? "..." : "",
					src.substring(startIdx, substrLength), 
					startIdx + substrLength < src.length() - 1 ? "..." : "");
		}
	}
	
	public Parser(List<ITypeDescriptor<?>> types, List<IOperator> functions) {

		// TODO make sure typeId`s are unique
		_types = new ArrayList<ITypeDescriptor<?>>(types);
		_operators = new ArrayList<IOperator>(functions);
	}

//	private List<IVO> _parseFunctionRighttArgument(Scope<?> scope, IOperator function) throws ParserException {
//		if (!function.isArgumentsClosingCharacterExpected()) {
//			Scope voScope = new Scope<IVO>(scope.src, scope.currentPosition, function);
//		}
//		return null;
//	}

	private OperatorEvaluationVO _parseOperatorEvaluationVO(Scope scope, IOperator operator) throws ParserException {

		if (null == operator) {
			List<IOperator> operators = new ArrayList<IOperator>(_operators);
			
			if(null == scope.leftOperand)
				operators.removeIf(f -> f.isLeftOperandExpected());
			else
				operators.removeIf(f -> !f.isLeftOperandExpected());
			
			Scope s = scope.copyForward();
			
			s.removeNotApplicable(operators);
					
			if(operators.isEmpty()) {
				throw new ParserException(
						"Could not find applicable operator near position: %s",
						String.join(",", operators.stream().map(o->o.getLexemeId()).collect(Collectors.toList())),
						scope.getCurrentPositionDescription(1)
					);
			}
			
			if(operators.size() > 1) {
				throw new ParserException(
						"Ambiguous operators (%s are applicable simultaneously) near position: %s",
						String.join(",", operators.stream().map(o->o.getLexemeId()).collect(Collectors.toList())),
						scope.getCurrentPositionDescription(s.currentPosition - scope.currentPosition)
					);
			}
			
			operator = operators.get(0);
			scope.currentPosition = s.currentPosition;
			
		}

		if(null == scope.leftOperand && operator.isLeftOperandExpected())
			throw new ParserException(
					"left operand is missed for operator %s near position: %s",
					operator.getLexemeId(),
					scope.getCurrentPositionDescription(0)
				);

		final List<IVO> operands = new ArrayList<IVO>();
		
		if(null != scope.leftOperand) {
			if(!operator.isLeftOperandExpected())
				throw new ParserException(
						"left operand is not expected for operator %s near position: %s",
						operator.getLexemeId(),
						scope.getCurrentPositionDescription(0)
					);
			operands.add(scope.leftOperand);
		}

		
		Scope s = scope.copyForward(operator); 
		IVO rightOperand = parse(s);
		
		if (null == rightOperand)
			throw new ParserException(
					"Righ operand has not been found for operator %s near position: %s",
					operator.getLexemeId(),
					scope.getCurrentPositionDescription(-1)
				);
		
		operands.add(rightOperand);
		
		return new OperatorEvaluationVO(
				(null != scope.leftOperand ? scope.leftOperand.getSrc() : "") + scope.src.substring(scope.startPosition, s.currentPosition),
				operator,
				operands
			);
	}
	
	public IVO parse(Scope scope) throws ParserException {

		List<ILexeme> lexemes = new ArrayList<ILexeme>(_types);
		lexemes.addAll(_operators);

		scope.removeNotApplicable(lexemes);
		
		final String candidateRawSrc = scope.src.substring(scope.startPosition, scope.currentPosition);
		final String candidateSrc = candidateRawSrc.trim();
		
		if ("".equals(candidateSrc))
			return null;

		if (1 == lexemes.size()) {
			
			if (lexemes.get(0) instanceof ITypeDescriptor<?>) {
				ITypeDescriptor<?> td = (ITypeDescriptor<?>) lexemes.get(0);
				
				if (!td.isStringRepresentationValid(candidateSrc)) {
					throw new ParserException(
							"Type %s is not able to parse source near position: %s.",
							td.getLexemeId(),
							scope.getCurrentPositionDescription(candidateRawSrc.length())
					);
				}
				
				IVO vo = new ImmutableVO(candidateSrc, td);
				if ("".equals(scope.src.substring(scope.currentPosition).trim())) return vo;
				
				if (null != scope.leftOperand)
					throw new ParserException(
							"Not empty left argument %s near position: %s",
							scope.leftOperand.getSrc(), 
							scope.getCurrentPositionDescription(candidateRawSrc.length())
						);
				
				return _parseOperatorEvaluationVO(scope.copyForward(vo), null);
			}
			
//			if (lexemes.get(0) instanceof IOperator) {
//				IOperator o = (IOperator) lexemes.get(0);
//				
//				if (!o.isStringRepresentationValid(candidateSrc)) {
//					throw new ParserException(
//							"Operator %s is not able to parse source near position: %s.",
//							td.getLexemeId(),
//							scope.getCurrentPositionDescription(candidateRawSrc.length())
//					);
//				}
//				
//				IVO vo = new ImmutableVO(candidateSrc, td);
//				if ("".equals(scope.src.substring(scope.currentPosition).trim())) return vo;
//				
//				if (null != scope.leftOperand) {
//					throw new ParserException(
//							"Not empty left argument %s near position: %s",
//							scope.leftOperand.getSrc(), 
//							scope.getCurrentPositionDescription(candidateRawSrc.length())
//						);
//			}
		}
		
//		if (lexemes.size() > 1 && lexemes.stream().anyMatch(l -> !(l instanceof IOperator))) {
//			throw new ParserException(
//					"Ambiguous source code (%d lexemes are applicable simultaneously) near position: %s",
//					lexemes.size(), 
//					scope.getCurrentPositionDescription(candidateRawSrc.length())
//				);
//		}
		
		// try to analyze functions to find best fit
		return null;
	}

	public IVO parse(String src) throws ParserException {
		return parse(new Scope(src));
	}
}
