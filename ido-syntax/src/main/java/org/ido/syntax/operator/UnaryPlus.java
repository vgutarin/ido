package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class UnaryPlus extends NumericOperator {
	
	public UnaryPlus() {
		super(OperatorPriority.Unary, "+");
	}
	
	@Override
	public int leftOperandsCount() {
		return 0;
	}
}
