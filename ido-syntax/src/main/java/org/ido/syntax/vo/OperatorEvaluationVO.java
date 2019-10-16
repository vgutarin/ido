package org.ido.syntax.vo;

import java.util.ArrayList;
import java.util.List;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVO;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;

public class OperatorEvaluationVO extends VO {

	
	// TODO detect immutable
	
	private final List<IVO> _operands;
	private final IOperator _operator;
	public OperatorEvaluationVO(String src, IOperator operator, List<IVO> operands) throws ParserException {
		super(src, _typeDescriptor(operands));
		//TODO check not nulls
		_operands = new ArrayList<IVO>(operands);
		_operator = operator;
	}

	@Override
	public Object getValue() throws SyntaxException {
		return getTypeDescriptor().apply(_operator, _operands);
	}

	@Override
	public boolean isMutable() {
		return true;
	}
	
	private static ITypeDescriptor<?> _typeDescriptor(List<IVO> operands) {
		// TODO cast types
		// TODO validate operator is applicable ()
		return operands.get(0).getTypeDescriptor();
	}

}
