package org.ido.syntax.variable;

import org.ido.syntax.type.LongTypeDescriptor;

public class LongTypeVariable extends Variable<Long> {
	
	public LongTypeVariable(String name) {
		super(LongTypeDescriptor.instance, name);
	}

}
