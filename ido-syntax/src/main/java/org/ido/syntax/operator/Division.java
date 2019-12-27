package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class Division extends NumericOperator {
	
	public Division() {
		super(OperatorPriority.Multiplicative, "/");
	}
}
