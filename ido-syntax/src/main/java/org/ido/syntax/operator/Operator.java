package org.ido.syntax.operator;

import org.ido.syntax.IOperator;
import org.ido.syntax.OperatorPriority;

public abstract class Operator implements IOperator {

	private final String _lexeme, _lexemeId;
	private final OperatorPriority _priority;
	
	protected Operator(OperatorPriority priority, String lexeme) {
		_lexeme = lexeme;
		_lexemeId = "operator." + getClass().getSimpleName();
		_priority = priority;
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
}
