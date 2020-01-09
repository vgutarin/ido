package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class UnaryMinus extends Operator {
	
	public UnaryMinus() {
		super(OperatorPriority.Unary, "-");
	}
	
	@Override
	public int leftOperandsCount() {
		return 0;
	}

}
