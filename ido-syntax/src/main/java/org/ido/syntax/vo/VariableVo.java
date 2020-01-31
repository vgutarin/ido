package org.ido.syntax.vo;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;
import org.ido.syntax.SyntaxException;

public class VariableVo<T> implements IVo {

	private final ITypeDescriptor<T> _typeDescriptor;
	private T _value;

	public VariableVo(ITypeDescriptor<T> typeDescriptor) {
		_typeDescriptor = typeDescriptor;
	}

	@Override
	public final ITypeDescriptor<?> getTypeDescriptor() {
		return _typeDescriptor;
	}

	@Override
	public final Object getValue() throws SyntaxException {
		return _value;
	}

	public final void setValue(T v) {
		_value = v;
	}
}
