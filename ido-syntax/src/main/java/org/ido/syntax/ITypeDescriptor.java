package org.ido.syntax;

public interface ITypeDescriptor<T> extends ILexeme {
	String getTypeId();
	boolean isCompartible(ITypeDescriptor<?> type);
	T cast(ITypeDescriptor<?> type, Object value);
	boolean isStringRepresentationValid(String src);
	Object parseVo(String src) throws ParserException;
}
