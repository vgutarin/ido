package org.ido.syntax;

/**
 * 
 * @author Vitaliy Gutarin
 * 
 * Requiremtns
 * 		1. Must provide Result type (INT, Decimal, String, ) - 
 */
public interface IVo {
	ITypeDescriptor<?> getTypeDescriptor();
	Object getValue() throws SyntaxException;
}
