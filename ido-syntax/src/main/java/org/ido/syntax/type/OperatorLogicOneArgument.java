package org.ido.syntax.type;

import java.util.List;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.vo.HoldVoValue;

abstract class OperatorLogicOneArgument<T, R> extends OperatorLogic {
	
	private ITypeDescriptor<T> _argumentType;
	
	public OperatorLogicOneArgument(ITypeDescriptor<T> argumentType, ITypeDescriptor<?> resultType) {
		super(resultType);
		_argumentType = argumentType;
	}

	@Override
	protected HoldVoValue logic(List<HoldVoValue> operands)  throws SyntaxException {
		
		if (1 != operands.size()) {
			throw new SyntaxException("One operand is expected. Actual count is: %d", operands.size());
		}
		
		if (_argumentType != operands.get(0).getTypeDescriptor()) {
			throw new SyntaxException("%s operand type is expected. Actual type is: %s", _argumentType.getLexemeId(), operands.get(0).getTypeDescriptor().getLexemeId());
		} 
		
		return new HoldVoValue(resultType, logic(operands.get(0)));
	}
	
	protected abstract R logic(HoldVoValue v) throws SyntaxException;
}
