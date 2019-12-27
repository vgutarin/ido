package org.ido.syntax.operator;

import java.util.List;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.OperatorPriority;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.type.LongTypeDescriptor;

public class NumericOperator extends Operator {
	
	protected NumericOperator(OperatorPriority priority, String lexeme) {
		super(priority, lexeme);
	}

	@Override
	public ITypeDescriptor<?> detectResultType(List<ITypeDescriptor<?>> operands)  throws SyntaxException {
		if (operands.isEmpty())
			throw new SyntaxException("%s cannot be applied to empty operand list", getClass().getName());
		for(ITypeDescriptor<?> operand : operands)
		{
			if (operand.getClass() != LongTypeDescriptor.class)
				throw new SyntaxException("%s cannot be applied to %s type", getClass().getName(), operand.getLexemeId());
		}
		return operands.get(0);
	}

}
