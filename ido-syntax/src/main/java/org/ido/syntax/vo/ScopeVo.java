package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;
import org.ido.syntax.SyntaxException;

public class ScopeVo implements IVo {
	private final ExpressionComponent  _component;
	private final IVo _vo;
	
	public ScopeVo(ExpressionComponent component, IVo vo) {
		_component = component;
		_vo = vo;
	}

	@Override
	public ITypeDescriptor<?> getTypeDescriptor() {
		return _vo.getTypeDescriptor();
	}

	@Override
	public ExpressionComponent getComponentDesc() {
		return _component;
	}	

	@Override
	public Object getValue() throws SyntaxException {
		return _vo.getValue();
	}
	
	@Override
	public boolean isMutable() {
		return _vo.isMutable();
	}
}
