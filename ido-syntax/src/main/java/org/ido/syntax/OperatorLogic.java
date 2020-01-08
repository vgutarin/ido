package org.ido.syntax;

import java.util.List;

import org.ido.syntax.vo.HoldVoValue;

public abstract class OperatorLogic {

	public final ITypeDescriptor<?> resultType;
	
	public OperatorLogic(ITypeDescriptor<?> resultType) {
		this.resultType = resultType;
	}
	
	public final HoldVoValue apply(List<HoldVoValue> operands) throws SyntaxException
	{
		HoldVoValue result = logic(operands);
		if (result.getTypeDescriptor() != resultType) 
			throw new SyntaxException("Could not apply Operatorlogic. Expected result type does not match actual ");
		return result;
	}
	
	protected abstract HoldVoValue logic(List<HoldVoValue> operands) throws SyntaxException;
}
