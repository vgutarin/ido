package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class Addition extends NumericOperator {
	
	public Addition() {
		super(OperatorPriority.Additive, "+");
	}

}
