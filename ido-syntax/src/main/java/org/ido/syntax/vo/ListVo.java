package org.ido.syntax.vo;

import java.util.ArrayList;
import java.util.List;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.IVo;
import org.ido.syntax.ParserException;

public class ListVo extends Vo {

	private final List<IVo> _value;
	public final boolean _isMutable;
	public ListVo(ExpressionComponent component, List<IVo> values) throws ParserException {
		super(component);
		_value = java.util.Collections.unmodifiableList(new ArrayList<IVo>(values));
		_isMutable = _value.stream().anyMatch(v -> v.isMutable());
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public boolean isMutable() {
		return _isMutable;
	}

}
