package org.ido.syntax.type;

import org.ido.syntax.ITypeDescriptor;

public abstract class TypeDescriptor<T> implements ITypeDescriptor<T> {

	@Override
	public final String getLexemeId() {
		return getClass().getCanonicalName();
	}
}
