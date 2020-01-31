package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVoComponent;
import org.ido.syntax.ParserException;

public abstract class Vo implements IVoComponent {
	protected final ExpressionComponent  component;
	private final ITypeDescriptor<?> _typeDescriptor;
	
	protected Vo(ExpressionComponent component, ITypeDescriptor<?> typeDescriptor) throws ParserException {
		this.component = component;
		if (null == component) throw new ParserException("Component must be not null"); 
		_typeDescriptor = typeDescriptor;
		
	}

	@Override
	public ITypeDescriptor<?> getTypeDescriptor() {
		return _typeDescriptor;
	}

	@Override
	public ExpressionComponent getComponentDesc() {
		return component;
	}
}
