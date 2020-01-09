package org.ido.syntax.type;

import java.util.Comparator;
import java.util.List;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.operator.GreaterOrEqualThan;
import org.ido.syntax.operator.GreaterThan;
import org.ido.syntax.operator.LessOrEqualThan;
import org.ido.syntax.operator.LessThan;
import org.ido.syntax.vo.HoldVoValue;

public abstract class TypeDescriptor<T> implements ITypeDescriptor<T> {
	
	private final OperatorLogic 
		_greaterThan,
		_greaterGreaterOrEqualThan,
		_lessThan,
		_lessOrEqualThan;
	
	protected TypeDescriptor() {
		Comparator<T> comparator = findComparator();
		
		_greaterThan = null == comparator ? null : new OperatorLogicTwoArguments<T, Boolean>(this, BooleanTypeDescriptor.instance) {
			
			@Override
			protected Boolean logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return 0 < comparator.compare(castToValue(v1), castToValue(v2));
			}
			
		};
		
		_greaterGreaterOrEqualThan = null == comparator ? null : new OperatorLogicTwoArguments<T, Boolean>(this, BooleanTypeDescriptor.instance) {
			
			@Override
			protected Boolean logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return -1 < comparator.compare(castToValue(v1), castToValue(v2));
			}
			
		};
		
		_lessThan = null == comparator ? null : new OperatorLogicTwoArguments<T, Boolean>(this, BooleanTypeDescriptor.instance) {
			
			@Override
			protected Boolean logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return 0 > comparator.compare(castToValue(v1), castToValue(v2));
			}
			
		};
		
		_lessOrEqualThan = null == comparator ? null : new OperatorLogicTwoArguments<T, Boolean>(this, BooleanTypeDescriptor.instance) {
			
			@Override
			protected Boolean logic(HoldVoValue v1, HoldVoValue v2) throws SyntaxException {
				return 1 > comparator.compare(castToValue(v1), castToValue(v2));
			}
			
		};
	}
	
	@Override
	public final String getLexemeId() {
		return getClass().getCanonicalName();
	}
	
	protected abstract OperatorLogic findOperatorLogic(IOperator operator, List<ITypeDescriptor<?>> operands) throws SyntaxException;
	
	protected Comparator<T> findComparator() {
		return null;
	}
	
	@Override
	public final OperatorLogic findLogic(IOperator operator, List<ITypeDescriptor<?>> operands) throws SyntaxException {
		OperatorLogic result = findOperatorLogic(operator, operands); 
		
		if (null != result)
			return result;
			
		if (2 != operands.size() || operands.stream().allMatch(o->this != o))
			return null;
		
		if (operator instanceof GreaterThan)
			return _greaterThan;
		
		if (operator instanceof GreaterOrEqualThan)
			return _greaterGreaterOrEqualThan;
		
		if (operator instanceof LessThan)
			return _lessThan;
		
		if (operator instanceof LessOrEqualThan)
			return _lessOrEqualThan;
		
		return null;
	}
	
}
