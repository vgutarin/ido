package org.ido.syntax.type;

import java.util.Comparator;
import java.util.List;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.SyntaxException;

public abstract class TypeDescriptor<T> implements ITypeDescriptor<T> {

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
		
		Comparator<T> comparator = findComparator();
		if (null == comparator)
			return null;
		
		return null;
	}
	
}
