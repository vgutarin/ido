package org.ido.syntax;

public interface IVariablesProvider {
	boolean hasAnyStartsWith(String str);
	IVariable findByName(String name);
}