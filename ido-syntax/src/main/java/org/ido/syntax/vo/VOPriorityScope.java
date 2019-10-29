package org.ido.syntax.vo;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVO;
import org.ido.syntax.SyntaxException;

public class VOPriorityScope implements IVO {
	private final ExpressionComponent  _component;
	private final IVO _vo;
	
	public VOPriorityScope(ExpressionComponent component, IVO vo) {
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
