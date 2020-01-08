package org.ido.syntax.vo;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;
import org.ido.syntax.SyntaxException;

/**
 * Class is supposed to hold IVO value. Is useful to prefer multiple evaluation of the same IVO.getValue(). I.e. we may would like to hold function arguments values at function execution time.
 * 
 * @author Vitaliy Gutarin
 *
 */
public class HoldVoValue implements IVo {

	private final ITypeDescriptor<?> _typeDescriptor;
	private final Object _value; 
	
	public HoldVoValue(ITypeDescriptor<?> typeDescriptor, Object value) throws SyntaxException {
		_value = value;
		_typeDescriptor = typeDescriptor;
	}
	
	public HoldVoValue(IVo vo) throws SyntaxException {
		this(vo.getTypeDescriptor(), vo.getValue());
	}

	@Override
	public ITypeDescriptor<?> getTypeDescriptor() {
		return _typeDescriptor;
	}

	@Override
	public Object getValue() throws SyntaxException {
		return _value;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%s", _typeDescriptor.getLexemeId(), _value);
	}
}
