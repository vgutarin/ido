package org.ido.syntax;

/**
 * 
 * @author Vitaliy Gutarin
 */
public interface IVoComponent extends IVo {
	ExpressionComponent getComponentDesc();
	boolean isMutable();
}
