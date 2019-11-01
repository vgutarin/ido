package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.ParserException;

public class ImmutableVo extends Vo {

	private final Object _value;
	public ImmutableVo(ExpressionComponent component) throws ParserException {
		super(component);
		
		_value = getTypeDescriptor().parseVo(component.str);
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

}
