package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class Subtraction extends NumericOperator {
	
	public Subtraction() {
		super(OperatorPriority.Additive, "-");
	}

}
