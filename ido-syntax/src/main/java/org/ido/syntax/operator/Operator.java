package org.ido.syntax.operator;

import org.ido.syntax.IOperator;

public abstract class Operator implements IOperator {

	final String _lexeme, _lexemeId;
	final boolean _isLeftOperandExpected;
	
	public Operator(String lexeme, boolean isLeftOperandExpected) {
		_lexeme = lexeme;
		_isLeftOperandExpected = isLeftOperandExpected;
		_lexemeId = "operator." + getClass().getSimpleName();
	}
	
	protected Operator(String lexeme) {
		this(lexeme, true);
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
