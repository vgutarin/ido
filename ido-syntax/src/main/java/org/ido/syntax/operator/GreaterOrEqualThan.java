package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class GreaterOrEqualThan extends Operator {
	
	public GreaterOrEqualThan() {
		super(OperatorPriority.Relational, ">=");
	}

}
