package org.ido.syntax.operator;

import org.ido.syntax.OperatorPriority;

public class Remainder extends Operator {
	
	public Remainder() {
		super(OperatorPriority.Multiplicative, "%");
	}
}
