package org.ido.syntax.vo;

import java.util.ArrayList;
import java.util.List;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.IVoComponent;
import org.ido.syntax.ParserException;

public class ListVo extends Vo {

	private final List<IVoComponent> _value;
	public final boolean _isMutable;
	public ListVo(ExpressionComponent component, List<IVoComponent> values) throws ParserException {
		super(component, null);
		_value = java.util.Collections.unmodifiableList(new ArrayList<IVoComponent>(values));
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
