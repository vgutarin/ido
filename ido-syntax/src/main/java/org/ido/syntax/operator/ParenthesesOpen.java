package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class ParenthesesOpen extends Operator {
	
	public ParenthesesOpen() {
		super(OperatorPriority.Highest, "(");
	}
	
	@Override
	public int leftOperandsCount() {
		return 0;
	}
	
	@Override
	public int rightOperandsCount() {
		return 1;
	}
}
