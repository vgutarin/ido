package org.ido.syntax.variable;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVariable;
import org.ido.syntax.IVo;
import org.ido.syntax.vo.VariableVo;

public class Variable<T> implements IVariable {
	
	private final VariableVo<T> _vo;
	private final String _name;
	
	public Variable(ITypeDescriptor<T> typeDescriptor, String name) {
		_vo = new VariableVo<T>(typeDescriptor);
		_name = name.trim();		
	}
	
	@Override
	public String getLexemeId() {
		return String.format("(%s) %s", getClass().getCanonicalName(), _name);
	}
	
	@Override
	public boolean isStringRepresentationStartsWith(String src) {
		return _name.startsWith(src);
	}
	
	@Override
	public IVo getVo() {
		return _vo;
	}
	
	@Override
	public String getName() {
		return _name;
	}
	
	public void setValue(T v) {
		_vo.setValue(v);
	}
	
}
