package org.ido.syntax.vo;

import java.util.ArrayList;
import java.util.List;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;
import org.ido.syntax.NotSupportedOperatorException;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;

public class OperatorEvaluationVo extends Vo {

	
	// TODO detect immutable
	
	private final List<IVo> _operands;
	private final IOperator _operator;
	public OperatorEvaluationVo(ExpressionComponent component, List<IVo> operands) throws ParserException {
		super(component, _typeDescriptor(operands));
		//TODO check not nulls
		_operands = new ArrayList<IVo>(operands);
		_operator = component.operator;
	}

	@Override
	public Object getValue() throws SyntaxException {
		//TODO deal with null values. (If any operand is null - result is null)
		if (
				_operands.size() != _operator.rightOperandsCount() +  _operator.leftOperandsCount()
				||
				_operands.isEmpty()
		) {
				throw new NotSupportedOperatorException(
						"Cannot apply operator %s using %d operands. Expected operands count is: %d",
						_operator.getLexemeId(), _operands.size(), _operator.rightOperandsCount() + _operator.leftOperandsCount());
		}
		return getTypeDescriptor().apply(_operator, _operands);
	}

	@Override
	public boolean isMutable() {
		return true;
	}
	
	private static ITypeDescriptor<?> _typeDescriptor(List<IVo> operands) {
		// TODO cast types
		// TODO validate operator is applicable ()
		return operands.get(0).getTypeDescriptor();
	}

}
