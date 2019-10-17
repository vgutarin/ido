package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVO;

public abstract class VO implements IVO {
	private final ExpressionComponent  _component;
	private final ITypeDescriptor<?> _typeDescriptor;
	
	protected VO(ExpressionComponent component, ITypeDescriptor<?> typeDescriptor) {
		//TODO validate typeDescriptor is not null
		_component = component;
		_typeDescriptor = typeDescriptor;
	}
	protected VO(ExpressionComponent component) {
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
