package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.IVo;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;

public class VariableComponentVo extends Vo {
	
	private IVo _vo;
	public VariableComponentVo(ExpressionComponent component) throws ParserException {
		super(component, component.variable.getVo().getTypeDescriptor());
		if (null == component.variable) throw new ParserException("component.variable must be not null"); 
		_vo = component.variable.getVo();
	}

	@Override
	public Object getValue() throws SyntaxException {
		return _vo.getValue();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

}
