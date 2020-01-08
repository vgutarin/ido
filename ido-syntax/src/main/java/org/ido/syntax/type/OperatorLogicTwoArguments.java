package org.ido.syntax.type;

import java.util.List;
import java.util.stream.Collectors;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.vo.HoldVoValue;

abstract class OperatorLogicTwoArguments<T, R> extends OperatorLogic {
	
	private ITypeDescriptor<T> _argumentType;
	
	public OperatorLogicTwoArguments(ITypeDescriptor<T> argumentType, ITypeDescriptor<?> resultType) {
		super(resultType);
		_argumentType = argumentType;
	}

	@Override
	protected HoldVoValue logic(List<HoldVoValue> operands)  throws SyntaxException {
		
		if (2 != operands.size()) {
			throw new SyntaxException("Tw operands is expected. Actual count is: %d", operands.size());
		}
		
		if (operands.stream().allMatch(o-> _argumentType != o.getTypeDescriptor())) {
			throw new SyntaxException(
					"%s operand type is expected. Actual types are: %s", 
					_argumentType.getLexemeId(), 
					SyntaxException.toCsv(
							operands.stream().map(o->o.getTypeDescriptor()).collect(Collectors.toList())
					)
			);
		} 
		
		return new HoldVoValue(resultType, logic(operands.get(0), operands.get(1)));
	}
	
	protected abstract R logic(HoldVoValue o1, HoldVoValue o2) throws SyntaxException;
}
