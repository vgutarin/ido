package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class Multiplication extends Operator {
	
	public Multiplication() {
		super(OperatorPriority.Multiplicative, "*");
	}
}
