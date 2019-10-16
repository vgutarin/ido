package org.ido.syntax;

/**
 * 
 * @author Vitaliy Gutarin
 * 
 * Requiremtns
 * 		1. Must provide Result type (INT, Decimal, String, ) - 
 */
public interface IVO {
	ITypeDescriptor<?> getTypeDescriptor();
	Object getValue() throws SyntaxException;
	String getSrc();
	boolean isMutable();
}
