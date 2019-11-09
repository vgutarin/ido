package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
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

	private final IVo _vo;
	private final Object _value; 
	public HoldVoValue(IVo vo) throws SyntaxException {
		_value = vo.getValue();
		_vo = vo;
	}

	@Override
	public ITypeDescriptor<?> getTypeDescriptor() {
		return _vo.getTypeDescriptor();
	}

	@Override
	public ExpressionComponent getComponentDesc() {
		return _vo.getComponentDesc();
	}	

	@Override
	public Object getValue() throws SyntaxException {
		return _value;
	}
	
	@Override
	public boolean isMutable() {
		return _vo.isMutable();
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%s", _vo.getTypeDescriptor().getLexemeId(), _value);
	}
}
