package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class LessOrEqualThan extends Operator {
	
	public LessOrEqualThan() {
		super(OperatorPriority.Relational, "<=");
	}

}
