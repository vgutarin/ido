package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;

public abstract class Vo implements IVo {
	private final ExpressionComponent  _component;
	private final ITypeDescriptor<?> _typeDescriptor;
	
	protected Vo(ExpressionComponent component, ITypeDescriptor<?> typeDescriptor) {
		//TODO validate typeDescriptor is not null
		_component = component;
		_typeDescriptor = typeDescriptor;
	}
	protected Vo(ExpressionComponent component) {
		this(component, component.typeDescriptor);
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
