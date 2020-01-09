package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class LessThan extends Operator {
	
	public LessThan() {
		super(OperatorPriority.Relational, "<");
	}

}
