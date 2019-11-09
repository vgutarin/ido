package org.ido.syntax.function;

import java.util.List;
import java.util.stream.Collectors;

import org.ido.syntax.IFunction;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.vo.HoldVoValue;

public abstract class Function implements IFunction{
	private final String _name;

	protected Function(String name) throws SyntaxException {
		_name = null == name ? "" : name.trim();
		if ("".equals(_name))
			throw new SyntaxException("Function name cannot be empty");
	}

	@Override
	public final String getLexemeId() {
		return getClass().getCanonicalName();
	}

	@Override
	public final boolean isStringRepresentationStartsWith(String src) {
		return _name.startsWith(src);
	}

	@Override
	public final String getName() {
		return _name;
	}
	
	public void assertIsApplicable(List<HoldVoValue> arguments)  throws SyntaxException {
		List<ITypeDescriptor<?>> targs = arguments.stream().map(a -> a.getTypeDescriptor()).collect(Collectors.toList());
		if (null == isApplicable(targs)) {
			throw new SyntaxException(
					"Function %s is not applicable to arguments (%s)",
					SyntaxException.toCsv(targs));
		}
	}
	
}
