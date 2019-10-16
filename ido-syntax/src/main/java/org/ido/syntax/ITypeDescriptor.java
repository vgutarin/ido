package org.ido.syntax;

import java.util.List;

public interface ITypeDescriptor<T> extends ILexeme {
	boolean isCompartible(ITypeDescriptor<?> type);
	T castValue(IVO value) throws SyntaxException;
	boolean isStringRepresentationValid(String src);
	Object parseVo(String src) throws ParserException;
	T apply(IOperator operator, List<IVO> operands) throws SyntaxException;
}
