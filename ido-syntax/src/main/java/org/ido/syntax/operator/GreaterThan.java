package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class GreaterThan extends Operator {
	
	public GreaterThan() {
		super(OperatorPriority.Relational, ">");
	}

}
