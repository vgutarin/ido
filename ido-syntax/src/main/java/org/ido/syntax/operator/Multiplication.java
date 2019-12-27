package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class Multiplication extends NumericOperator {
	
	public Multiplication() {
		super(OperatorPriority.Multiplicative, "*");
	}
}
