package org.ido.syntax;

import java.util.ArrayList;
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
		
		public Scope(String src, int startPosition) {
			this.src = src;
			this.startPosition = currentPosition = startPosition;
			leftFunction = null;
			leftArgument = null;
		}
		
		public Scope(String src) {
			this(src, 0);
		}
		
		public void removeNotApplicable(List<ITypeDescriptor<?>> applicableTypes, List<IFunction> applicableFunctions)
		{
			int startIdx = currentPosition = startPosition;

			for (; currentPosition < src.length(); ++currentPosition) {
				final String stringToAnalyze = src.substring(startIdx, currentPosition+1);
				if ("".equals(stringToAnalyze.trim())) {
					//skip leading white symbols;
					++startIdx;
					continue;
				}

				if (
						applicableTypes.stream().anyMatch(t -> t.isStringRepresentationStartsWith(stringToAnalyze))
						||
						applicableFunctions.stream().anyMatch(t -> t.isStringRepresentationStartsWith(stringToAnalyze))
				) {
					applicableFunctions.removeIf(t -> !t.isStringRepresentationStartsWith(stringToAnalyze));
					applicableTypes.removeIf(t -> !t.isStringRepresentationStartsWith(stringToAnalyze));
					continue;
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

	public IVO parse(Scope<IVO> scope) throws ParserException {
//		if (null != scope.function) {
//			// - function may add some rules like use "," delimiter to specify params etc.
//		}

		List<ITypeDescriptor<?>> applicableTypes = new ArrayList<ITypeDescriptor<?>>(_types);
		List<IFunction> applicableFunctions = new ArrayList<IFunction>(_functions);
//		if(null == scope.leftArgument || null != scope.function) {
//			applicableTypes.addAll(_types);
//		}
//		
//		if(null != leftArgument && null == function) {
//			applicableFunctions = _functions.stream().filter(f->f.isLeftArgumentExpected()).collect(Collectors.toList());
//		}
		
		scope.removeNotApplicable(applicableTypes, applicableFunctions);
		
		final String candidateRawSrc = scope.src.substring(scope.startPosition, scope.currentPosition);
		final String candidateSrc = candidateRawSrc.trim();
		
		if ("".equals(candidateSrc))
			return null;

		if (applicableTypes.size() > 0 && applicableFunctions.size() > 0) {
			throw new ParserException(
					"Ambiguous source code (%d types and %d functions are applicable simultaneously) near position: %d. String: %s%s%s",
					applicableTypes.size(), 
					applicableFunctions.size(), 
					scope.currentPosition, 
					scope.startPosition > 0 ? "..." : "",
					candidateRawSrc, 
					scope.currentPosition < scope.src.length() - 1 ? "..." : "");
		}
		
		//--currentPosition;

		if (1 == applicableTypes.size()) {
			ITypeDescriptor<?> td = applicableTypes.get(0);
			
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
			return null;
		}

		// try to analyze functions to find best fit

		return null;
	}

	public IVO parse(String src) throws ParserException {
		return parse(new Scope<IVO>(src));
	}
}
