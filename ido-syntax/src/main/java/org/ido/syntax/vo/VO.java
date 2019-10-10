package org.ido.syntax.vo;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVO;

public abstract class VO implements IVO {

	private final ITypeDescriptor<?> _typeDescriptor;
	private final String _src;
	public VO(String src, ITypeDescriptor<?> typeDescriptor) {
		_src = src;
		_typeDescriptor = typeDescriptor;
	}

	@Override
	public ITypeDescriptor<?> getTypeDescriptor() {
		return _typeDescriptor;
	}

	@Override
	public String getSrc() {
		return _src;
	}
}
