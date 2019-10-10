package org.ido.syntax.vo;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.ParserException;

public class ImmutableVO extends VO {

	private final Object _value;
	public ImmutableVO(String src, ITypeDescriptor<?> typeDescriptor) throws ParserException {
		super(src, typeDescriptor);
		
		_value = typeDescriptor.parseVo(src);
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
