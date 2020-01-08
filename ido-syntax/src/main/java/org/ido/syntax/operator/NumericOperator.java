package org.ido.syntax.operator;

import java.util.List;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.NotSupportedOperatorException;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.OperatorPriority;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.type.LongTypeDescriptor;

public class NumericOperator extends Operator {
	
	protected NumericOperator(OperatorPriority priority, String lexeme) {
		super(priority, lexeme);
	}

	public ITypeDescriptor<?> _getMainType(List<ITypeDescriptor<?>> operands)  throws SyntaxException {
		if (operands.isEmpty())
			throw new SyntaxException("%s cannot be applied to empty operand list", getClass().getName());
		for(ITypeDescriptor<?> operand : operands)
		{
			if (operand.getClass() != LongTypeDescriptor.class)
				throw new SyntaxException("%s cannot be applied to %s type", getClass().getName(), operand.getLexemeId());
		}
		return operands.get(0);
	}

	@Override
	public OperatorLogic getLogic(List<ITypeDescriptor<?>> operands) throws SyntaxException {
		OperatorLogic result = _getMainType(operands).findLogic(this, operands);
		if (null != result) return result;
		
		throw new NotSupportedOperatorException(
				"Operator %s is not applicable to given arguments: %s",
				getLexemeId(),
				SyntaxException.toCsv(operands)
		);
	}

}
