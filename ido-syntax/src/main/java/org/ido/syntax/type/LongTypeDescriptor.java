package org.ido.syntax.type;

import java.util.List;
import java.util.regex.Pattern;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVO;
import org.ido.syntax.NotSupportedOperatorException;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.operator.Addition;
import org.ido.syntax.operator.Multiplication;
import org.ido.syntax.operator.Subtraction;

public class LongTypeDescriptor implements ITypeDescriptor<Long> {

	private final Pattern _pattern = Pattern.compile("\\d+");

	public String getLexemeId() {
		return "Long";
	}

	public boolean isCompartible(ITypeDescriptor<?> type) {
		return type instanceof LongTypeDescriptor;
	}

	@Override
	public Long castValue(IVO value) throws SyntaxException {
		return (Long) value.getValue();
	}

	public boolean isStringRepresentationStartsWith(String src) {
		return isStringRepresentationValid(src);
	}

	public boolean isStringRepresentationValid(String src) {
		return _pattern.matcher(src).matches();
	}

	@Override
	public Object parseVo(String src) throws ParserException {
		try {
			return Long.parseLong(src.trim());
		} catch (Throwable e) {
			throw new ParserException("Could not parse Long from src: %s", src);
		}
	}

	
	@Override
	public Long apply(IOperator operator, List<IVO> operands) throws SyntaxException {
		
		
		if (2 != operands.size()) {
			throw new NotSupportedOperatorException(
					"Type %s cannot apply operator %s using &d operands. Expected operands count is 2",
					getLexemeId(), operator.getLexemeId(), operands.size());
		}
		Long firstOperand = castValue(operands.get(0));
		Long secondOperand = castValue(operands.get(1));
		
		if (operator instanceof Addition) {
			return firstOperand + secondOperand;
		}
		
		if (operator instanceof Subtraction) {
			return firstOperand - secondOperand;
		}
		
		if (operator instanceof Multiplication) {
			return firstOperand * secondOperand;
		}
		
		throw new NotSupportedOperatorException("Type %s does not support operator %s", getLexemeId(), operator.getLexemeId());
	}

}
