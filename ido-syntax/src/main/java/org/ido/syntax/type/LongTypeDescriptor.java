package org.ido.syntax.type;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.operator.Addition;
import org.ido.syntax.operator.Division;
import org.ido.syntax.operator.Multiplication;
import org.ido.syntax.operator.Remainder;
import org.ido.syntax.operator.Subtraction;
import org.ido.syntax.operator.UnaryMinus;
import org.ido.syntax.operator.UnaryPlus;
import org.ido.syntax.vo.HoldVoValue;

public class LongTypeDescriptor extends TypeDescriptor<Long> implements Comparator<Long> {

	public final static LongTypeDescriptor instance = new LongTypeDescriptor();
	
	private final Pattern _pattern = Pattern.compile("\\d+");
	
	private final OperatorLogic
		 _unaryMinus,
		 _unaryPlus,
		 _addition,
		 _subtraction,
		 _multiplication,
		 _division,
		 _remainder;
	
	private LongTypeDescriptor() {
		_unaryMinus = new OperatorLogicOneArgument<Long, Long>(this, this) {
			
			@Override
			protected Long logic(HoldVoValue v) throws SyntaxException {
				return -castToValue(v);
			}
			
		};
		
		_unaryPlus = new OperatorLogicOneArgument<Long, Long>(this, this) {
			
			@Override
			protected Long logic(HoldVoValue v) throws SyntaxException {
				return castToValue(v);
			}
			
		};
		
		_addition = new OperatorLogicTwoArguments<Long, Long>(this, this) {
			
			@Override
			protected Long logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return castToValue(v1) + castToValue(v2);
			}
			
		};
		
		_subtraction = new OperatorLogicTwoArguments<Long, Long>(this, this) {
			
			@Override
			protected Long logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return castToValue(v1) - castToValue(v2);
			}
			
		};
		
		 _multiplication = new OperatorLogicTwoArguments<Long, Long>(this, this) {
				
			@Override
			protected Long logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return castToValue(v1) * castToValue(v2);
			}
		};
		
		 _division = new OperatorLogicTwoArguments<Long, Long>(this, this) {
				
			@Override
			protected Long logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return castToValue(v1) / castToValue(v2);
			}
		};
		
		 _remainder = new OperatorLogicTwoArguments<Long, Long>(this, this) {
				
			@Override
			protected Long logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return castToValue(v1) % castToValue(v2);
			}
		};
	}

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
	public OperatorLogic findOperatorLogic(IOperator operator, List<ITypeDescriptor<?>> operands) throws SyntaxException {
		if (operands.stream().anyMatch(t -> !(t instanceof LongTypeDescriptor))) return null;
		if (1 == operands.size())
		{
			if (operator instanceof UnaryMinus) {
				return _unaryMinus;
			}
			
			if (operator instanceof UnaryPlus) {
				return _unaryPlus;
			}
		}
		
		if (2 != operands.size())
			return null;
		
		
		if (operator instanceof Addition) {
			return _addition;
		}
		
		if (operator instanceof Subtraction) {
			return _subtraction;
		}
		
		if (operator instanceof Multiplication) {
			return _multiplication;
		}
		
		if (operator instanceof Division) {
			return _division;
		}
		
		if (operator instanceof Remainder) {
			return _remainder;
		}
		
		return null;
	}

	@Override
	public int compare(Long o1, Long o2) {
		return Long.compare(o1, o2);
	}

}
