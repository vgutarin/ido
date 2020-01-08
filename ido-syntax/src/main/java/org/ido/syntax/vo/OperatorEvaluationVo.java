package org.ido.syntax.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.IOperator;
import org.ido.syntax.IVo;
import org.ido.syntax.IVoComponent;
import org.ido.syntax.NotSupportedOperatorException;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;

public class OperatorEvaluationVo extends Vo {

	// TODO detect immutable
	
	private final List<IVo> _operands;
	private final IOperator _operator;
	private final OperatorLogic _logic;
	
	protected OperatorEvaluationVo(ExpressionComponent component, OperatorLogic logic,  List<IVoComponent> operands) throws ParserException
	{
		super(component, logic.resultType);
		
		_operands = new ArrayList<IVo>(operands);
		_operator = component.operator;
		_logic = logic;
	}
	
	public OperatorEvaluationVo(ExpressionComponent component, List<IVoComponent> operands) throws SyntaxException {
		
		this(
				component,
				component.operator.getLogic(operands.stream().map(i->i.getTypeDescriptor()).collect(Collectors.toList())),
				operands
		);
	}

	@Override
	public Object getValue() throws SyntaxException {
		//TODO deal with null values. (If any operand is null - result is null)
		if (
				_operands.size() != _operator.rightOperandsCount() + _operator.leftOperandsCount()
				||
				_operands.isEmpty()
		) {
				throw new NotSupportedOperatorException(
						"Cannot apply operator %s using %d operands. Expected operands count is: %d",
						_operator.getLexemeId(), _operands.size(), _operator.rightOperandsCount() + _operator.leftOperandsCount());
		}

		List<HoldVoValue> holdValues = new ArrayList<HoldVoValue>();
		for(IVo ivo : _operands) {
			holdValues.add(new HoldVoValue(ivo));
		}
		
		return _logic.apply(holdValues).getValue();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

}
