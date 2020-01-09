package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class Division extends Operator {
	
	public Division() {
		super(OperatorPriority.Multiplicative, "/");
	}
}
