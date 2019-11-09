package org.ido.syntax.function;

import java.util.List;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.type.LongTypeDescriptor;
import org.ido.syntax.vo.HoldVoValue;

public class Min extends Function {

	public Min() throws SyntaxException {
		super("min");
	}

	@Override
	public ITypeDescriptor<?> isApplicable(List<ITypeDescriptor<?>> arguments) {
		//TODO support NumericVos
		if (!arguments.isEmpty() && arguments.stream().allMatch(a -> null != a && a instanceof LongTypeDescriptor)) {
			return arguments.get(0);
		}
		
		return null;
	}

	@Override
	public HoldVoValue apply(List<HoldVoValue> arguments) throws SyntaxException {
		assertIsApplicable(arguments);
		//TODO support NumericVos
		//TODO deal with null values. (If any argument is null - result is null)
		HoldVoValue rs = null;
		for(HoldVoValue c : arguments) {
			if (null == rs) {
				rs = c;
			} else if (0 > Long.compare((Long) c.getValue(), (Long) rs.getValue())) {
				rs = c;
			}
		}
		
		return rs;
	}
	
	@Override
	public boolean isDeterministic() {
		return true;
	}

}
