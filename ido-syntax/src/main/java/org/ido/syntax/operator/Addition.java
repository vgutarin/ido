package org.ido.syntax.operator;

import org.ido.syntax.IOperator;

public class Addition implements IOperator {

	@Override
	public String getLexemeId() {
		return "Addition";
	}
	
	public Addition() {
		
	}

	@Override
	public boolean isStringRepresentationStartsWith(String src) {
		return isStringRepresentationValid(src);
	}

	@Override
	public boolean isLeftArgumentExpected() {
		return true;
	}

	@Override
	public boolean isStringRepresentationValid(String src) {
		return  "+".equals(src);
	}
}
