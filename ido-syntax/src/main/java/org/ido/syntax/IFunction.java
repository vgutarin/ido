package org.ido.syntax;

import java.util.List;

import org.ido.syntax.vo.HoldVoValue;

public interface IFunction extends ILexeme {
	String getName();
	
	/**
	 * Method must return result type if function is applicable to incoming arguments types descriptors.
	 * Expectations are method will check both arguments types and arguments count and arguments order.
	 * Result of this method is expected to be deterministic.
	 * <p>
	 * If result type is not deterministic or arguments count|types do not match expectations - null must be returned  
	 * 
	 * @param arguments 
	 * @return ITypeDescriptor<?> or null
	 */
	
	ITypeDescriptor<?> isApplicable(List<ITypeDescriptor<?>> arguments);
	HoldVoValue apply(List<HoldVoValue> arguments) throws SyntaxException;
	
	/**
	 * Method must return true if IFunction returns the same value for the same input arguments(i.e. min(0,1) is always 0) and false otherwise (i.e rand() or shuffle(...)) 
	 * 
	 * @return
	 */
	boolean isDeterministic();
}
