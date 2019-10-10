package org.ido.syntax.type;

import java.util.regex.Pattern;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.ParserException;

public class LongTypeDescriptor implements ITypeDescriptor<Long> {

	private final Pattern _pattern = Pattern.compile("\\d+");

	public String getTypeId() {
		return "Long";
	}

	public boolean isCompartible(ITypeDescriptor<?> type) {
		return false;
	}

	public Long cast(ITypeDescriptor<?> type, Object value) {
		return null;
	}

	public boolean isStringRepresentationStartsWith(String src) {
		return isStringRepresentationValid(src);
	}

	public boolean isStringRepresentationValid(String src) {
		return _pattern.matcher(src).matches();
	}

	@Override
	public Object parseVo(String src) throws ParserException {
		try
		{
			return Long.parseLong(src.trim());
		}
		catch(Throwable e)
		{
			throw new ParserException("Could not parse Long from src: %s", src);
		}
	}

}
