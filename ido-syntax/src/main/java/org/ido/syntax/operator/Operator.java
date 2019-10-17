package org.ido.syntax.operator;

import org.ido.syntax.IOperator;
import org.ido.syntax.OperatorPriority;

public abstract class Operator implements IOperator {

	private final String _lexeme, _lexemeId;
	private final boolean _isLeftOperandExpected;
	private final OperatorPriority _priority;
	
	protected Operator(OperatorPriority priority, String lexeme, boolean isLeftOperandExpected) {
		_lexeme = lexeme;
		_isLeftOperandExpected = isLeftOperandExpected;
		_lexemeId = "operator." + getClass().getSimpleName();
		_priority = priority;
	}
	
	protected Operator(OperatorPriority priority, String lexeme) {
		this(priority, lexeme, true);
	}

	@Override
	public OperatorPriority getPriority() {
		return _priority;
	}
	
	@Override
	public String getLexemeId() {
		return _lexemeId;
	}

	@Override
	public boolean isStringRepresentationStartsWith(String src) {
		return _lexeme.startsWith(src);
	}

	@Override
	public boolean isLeftOperandExpected() {
		return _isLeftOperandExpected;
	}
	
	@Override
	public boolean isStringRepresentationValid(String src) {
		return  _lexeme.equals(src);
	}
}
