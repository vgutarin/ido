package org.ido.syntax;

import java.util.List;

public interface ITypeDescriptor<T> extends ILexeme {
	boolean isCompartible(ITypeDescriptor<?> type);
	T castToValue(IVo value) throws SyntaxException;
	boolean isStringRepresentationValid(String src);
	Object parseValue(String src) throws ParserException;
	OperatorLogic findLogic(IOperator operator, List<ITypeDescriptor<?>> operands) throws SyntaxException;
}
