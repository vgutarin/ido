package org.ido.syntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.ido.syntax.vo.ImmutableVO;

public class Parser {

	private final List<ITypeDescriptor<?>> _types;
	private final List<IFunction> _functions;

	private class Scope<T> {
		public final String src;
		public final int startPosition;
		public int currentPosition;
		public T expectedObject;
		public final IFunction leftFunction;
		public final IVO leftArgument;
		
		public Scope(String src, int startPosition, IVO leftArgument, IFunction leftFunction) {
			this.src = src;
			this.startPosition = currentPosition = startPosition;
			this.leftFunction = leftFunction;
			this.leftArgument = leftArgument;
		}
		
		public Scope(String src, int startPosition, IFunction leftFunction) {
			this(src, startPosition, null, leftFunction);
		}
		
		public Scope(String src, int startPosition, IVO leftArgument) {
			this(src, startPosition, leftArgument, null);
		}
		
		public Scope(String src, int startPosition) {
			this(src, startPosition, null, null);
		}
		
		public Scope(String src) {
			this(src, 0);
		}
		
		@SafeVarargs
		public final <LT extends ILexeme> void removeNotApplicable(List<LT>... lexemes)
		{
			
			List<List<LT>> ll = Arrays.asList(lexemes);
			
			int startIdx = currentPosition;

			for (; currentPosition < src.length(); ++currentPosition) {
				final String stringToAnalyze = src.substring(startIdx, currentPosition+1);
				if ("".equals(stringToAnalyze.trim())) {
					//skip leading white symbols;
					++startIdx;
					continue;
				}

				if (
						ll.stream().anyMatch(
								l -> l.stream().anyMatch(
										t -> t.isStringRepresentationStartsWith(stringToAnalyze)
								)
						)
				) {
					
					ll.stream().forEach(
							l -> l.removeIf(t -> !t.isStringRepresentationStartsWith(stringToAnalyze))
					);
					continue;
				}
				
				if (startIdx == currentPosition) {
					ll.forEach(List::clear);
				}
				
				return;
			}
		}
	}

	public Parser(List<ITypeDescriptor<?>> types, List<IFunction> functions) {

		// TODO make sure typeId`s are unique
		_types = new ArrayList<ITypeDescriptor<?>>(types);
		_functions = new ArrayList<IFunction>(functions);
	}

	private List<IVO> _parseRightArgument(Scope<?> scope, IFunction function) throws ParserException {
		if (!function.isArgumentsClosingCharacterExpected()) {
			Scope voScope = new Scope<IVO>(scope.src, scope.currentPosition, function);
		}
		return null;
	}

	
//	private IFunction _parseFunction(Scope<IFunction> scope, List<IFunction> applicableFunctions) throws ParserException {
//
//		if (null == applicableFunctions) {
//			applicableFunctions = new ArrayList<IFunction>(_functions);
//			scope.removeNotApplicable(applicableFunctions);
//		}
//		
//		if(null == scope.leftArgument)
//			applicableFunctions.removeIf(f -> !f.isLeftArgumentExpected());
//		else
//			applicableFunctions.removeIf(f -> f.isLeftArgumentExpected());
//		
//			
//		final String candidateRawSrc = scope.src.substring(scope.startPosition, scope.currentPosition);
//		final String candidateSrc = candidateRawSrc.trim();
//		
//		if ("".equals(candidateSrc))
//			return null;
//
//		if (applicableTypes.size() > 0 && applicableFunctions.size() > 0) {
//			throw new ParserException(
//					"Ambiguous source code (%d types and %d functions are applicable simultaneously) near position: %d. String: %s%s%s",
//					applicableTypes.size(), 
//					applicableFunctions.size(), 
//					scope.currentPosition, 
//					scope.startPosition > 0 ? "..." : "",
//					candidateRawSrc, 
//					scope.currentPosition < scope.src.length() - 1 ? "..." : "");
//		}
//		
//		//--currentPosition;
//
//		if (1 == applicableTypes.size()) {
//			ITypeDescriptor<?> td = applicableTypes.get(0);
//			
//			if (!td.isStringRepresentationValid(candidateSrc)) {
//				throw new ParserException(
//						"Type %s is not able to parse source near position: %d. String: %s%s%s",
//						td.getTypeId(),
//						scope.currentPosition, 
//						scope.startPosition > 0 ? "..." : "",
//						candidateRawSrc, 
//						scope.currentPosition < scope.src.length() - 1 ? "..." : "");
//			}
//			
//			IVO vo = new ImmutableVO(candidateSrc, td);
//			if ("".equals(scope.src.substring(scope.currentPosition).trim())) return vo;
//			
//			
//		}
//
//		// try to analyze functions to find best fit
//
//		return null;
//	}
	
	public IVO parse(Scope<IVO> scope) throws ParserException {

		List<ILexeme> applicableTypes = new ArrayList<ILexeme>(_types);
		applicableTypes.addAll(_functions);

		scope.removeNotApplicable(applicableTypes);
		
		final String candidateRawSrc = scope.src.substring(scope.startPosition, scope.currentPosition);
		final String candidateSrc = candidateRawSrc.trim();
		
		if ("".equals(candidateSrc))
			return null;

		if (1 == applicableTypes.size() && (applicableTypes.get(0) instanceof ITypeDescriptor<?>)) {
			ITypeDescriptor<?> td = (ITypeDescriptor<?>) applicableTypes.get(0);
			
			if (!td.isStringRepresentationValid(candidateSrc)) {
				throw new ParserException(
						"Type %s is not able to parse source near position: %d. String: %s%s%s",
						td.getTypeId(),
						scope.currentPosition, 
						scope.startPosition > 0 ? "..." : "",
						candidateRawSrc, 
						scope.currentPosition < scope.src.length() - 1 ? "..." : "");
			}
			
			IVO vo = new ImmutableVO(candidateSrc, td);
			if ("".equals(scope.src.substring(scope.currentPosition).trim())) return vo;
		}
		
		if (applicableTypes.size() > 1 && applicableTypes.stream().anyMatch(l -> !(l instanceof IFunction))) {
			throw new ParserException(
					"Ambiguous source code (%d lexemes are applicable simultaneously) near position: %d. String: %s%s%s",
					applicableTypes.size(), 
					scope.currentPosition, 
					scope.startPosition > 0 ? "..." : "",
					candidateRawSrc, 
					scope.currentPosition < scope.src.length() - 1 ? "..." : "");
		}
		
		// try to analyze functions to find best fit
		return null;
	}

	public IVO parse(String src) throws ParserException {
		return parse(new Scope<IVO>(src));
	}
}
