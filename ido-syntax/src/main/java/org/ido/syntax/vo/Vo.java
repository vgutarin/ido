package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVoComponent;
import org.ido.syntax.ParserException;

public abstract class Vo implements IVoComponent {
	private final ExpressionComponent  _component;
	private final ITypeDescriptor<?> _typeDescriptor;
	
	protected Vo(ExpressionComponent component, ITypeDescriptor<?> typeDescriptor) throws ParserException {
		_component = component;
		if (null == _component) throw new ParserException("Component must be not null"); 
		_typeDescriptor = typeDescriptor;
		
	}
	protected Vo(ExpressionComponent component) throws ParserException {
		this(component, component.literal);
	}

	@Override
	public ITypeDescriptor<?> getTypeDescriptor() {
		return _typeDescriptor;
	}

	@Override
	public ExpressionComponent getComponentDesc() {
		return _component;
	}
}
