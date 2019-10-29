package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class ParenthesesClose extends Operator {
	
	public ParenthesesClose() {
		super(OperatorPriority.Highest, ")");
	}
	
	@Override
	public int leftOperandsCount() {
		return 1;
	}
	
	@Override
	public int rightOperandsCount() {
		return 0;
	}
}
