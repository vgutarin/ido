package org.ido.syntax;

import java.util.List;

public interface ITypeDescriptor<T> extends ILexeme {
	boolean isCompartible(ITypeDescriptor<?> type);
	T castToValue(IVo value) throws SyntaxException;
	boolean isStringRepresentationValid(String src);
	Object parseValue(String src) throws ParserException;
	T apply(IOperator operator, List<IVo> operands) throws SyntaxException;
}
