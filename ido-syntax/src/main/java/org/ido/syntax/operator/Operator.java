package org.ido.syntax.operator;

import java.util.List;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.NotSupportedOperatorException;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.OperatorPriority;
import org.ido.syntax.SyntaxException;

public abstract class Operator implements IOperator {

	private final String _lexeme;
	private final OperatorPriority _priority;
	
	protected Operator(OperatorPriority priority, String lexeme) {
		_lexeme = lexeme;
		_priority = priority;
	}

	@Override
	public OperatorPriority getPriority() {
		return _priority;
	}
	
	@Override
	public final String getLexemeId() {
		return getClass().getCanonicalName();
	}

	@Override
	public boolean isStringRepresentationStartsWith(String src) {
		return _lexeme.startsWith(src);
	}
	
	@Override
	public boolean isStringRepresentationValid(String src) {
		return  _lexeme.equals(src);
	}
	
	@Override
	public int leftOperandsCount() {
		return 1;
	}
	
	@Override
	public int rightOperandsCount() {
		return 1;
	}
	
	@Override
	public OperatorLogic getLogic(List<ITypeDescriptor<?>> operands) throws SyntaxException {
		for (ITypeDescriptor<?> ot : operands) {
			OperatorLogic result = ot.findLogic(this, operands);
			if (null != result) return result;
		}
		
		throw new NotSupportedOperatorException(
				"Operator %s is not applicable to given arguments: %s",
				getLexemeId(),
				SyntaxException.toCsv(operands)
		);
	}
	
}
