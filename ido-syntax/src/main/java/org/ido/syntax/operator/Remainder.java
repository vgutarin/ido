package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class Remainder extends NumericOperator {
	
	public Remainder() {
		super(OperatorPriority.Multiplicative, "%");
	}
}
