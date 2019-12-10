package org.ido.syntax.type;

import java.util.List;
import java.util.regex.Pattern;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;
import org.ido.syntax.NotSupportedOperatorException;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.operator.Addition;
import org.ido.syntax.operator.Division;
import org.ido.syntax.operator.Multiplication;
import org.ido.syntax.operator.Remainder;
import org.ido.syntax.operator.Subtraction;
import org.ido.syntax.operator.UnaryMinus;
import org.ido.syntax.operator.UnaryPlus;

public class LongTypeDescriptor extends TypeDescriptor<Long> {

	private final Pattern _pattern = Pattern.compile("\\d+");

	@Override
	public boolean isCompartible(ITypeDescriptor<?> type) {
		return type instanceof LongTypeDescriptor;
	}

	@Override
	public Long castToValue(IVo value) throws SyntaxException {
		return (Long) value.getValue();
	}

	@Override
	public boolean isStringRepresentationStartsWith(String src) {
		return isStringRepresentationValid(src);
	}

	@Override
	public boolean isStringRepresentationValid(String src) {
		return _pattern.matcher(src).matches();
	}

	@Override
	public Object parseValue(String src) throws ParserException {
		try {
			return Long.parseLong(src.trim());
		} catch (Throwable e) {
			throw new ParserException("Could not parse Long from src: %s", src);
		}
	}
	
	@Override
	public Long apply(IOperator operator, List<IVo> operands) throws SyntaxException {
		
		Long firstOperand = castToValue(operands.get(0));
		
		if (operator instanceof UnaryMinus) {
			return - firstOperand;
		}
		
		if (operator instanceof UnaryPlus) {
			return firstOperand;
		}
		
		if (2 != operands.size())
			throw new NotSupportedOperatorException("Type %s does not support operator %s", getLexemeId(), operator.getLexemeId());
		
		Long secondOperand = castToValue(operands.get(1));
		
		if (operator instanceof Addition) {
			return firstOperand + secondOperand;
		}
		
		if (operator instanceof Subtraction) {
			return firstOperand - secondOperand;
		}
		
		if (operator instanceof Multiplication) {
			return firstOperand * secondOperand;
		}
		
		if (operator instanceof Division) {
			return firstOperand / secondOperand;
		}
		
		if (operator instanceof Remainder) {
			return firstOperand % secondOperand;
		}
		
		throw new NotSupportedOperatorException("Type %s does not support operator %s", getLexemeId(), operator.getLexemeId());
	}

}
