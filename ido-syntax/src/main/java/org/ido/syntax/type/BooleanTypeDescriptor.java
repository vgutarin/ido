package org.ido.syntax.type;

import java.util.List;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;

public class BooleanTypeDescriptor extends TypeDescriptor<Boolean> {

	private final static String TRUE = "true", FALSE = "false";

	@Override
	public boolean isCompartible(ITypeDescriptor<?> type) {
		return type instanceof BooleanTypeDescriptor;
	}

	@Override
	public Boolean castToValue(IVo value) throws SyntaxException {
		return (Boolean) value.getValue();
	}

	@Override
	public boolean isStringRepresentationStartsWith(String src) {
		return TRUE.startsWith(src) || FALSE.startsWith(src);
	}

	@Override
	public boolean isStringRepresentationValid(String src) {
		return TRUE.equals(src) || FALSE.equals(src);
	}

	@Override
	public Object parseValue(String src) throws ParserException {

		src = src.trim();
		if (TRUE.equals(src))
			return Boolean.TRUE;
		if (FALSE.equals(src))
			return Boolean.FALSE;

		throw new ParserException("Could not parse Boolean from src: %s", src);
	}

	@Override
	public OperatorLogic findOperatorLogic(IOperator operator, List<ITypeDescriptor<?>> operands) throws SyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

}
