package org.ido.syntax.variable;

import java.util.HashMap;

import org.ido.syntax.IVariable;
import org.ido.syntax.IVariablesProvider;
import org.ido.syntax.SyntaxException;

public class VariablesHolder implements IVariablesProvider {

	public final HashMap<String, IVariable> _map;
	
	public VariablesHolder(IVariable ...variables) throws SyntaxException {
		_map = new HashMap<String, IVariable>();
		if(null != variables) {
			for (IVariable v : variables) { 
				add(v);
			}
		}
	}

	public synchronized void add(IVariable v) throws SyntaxException {
		if (_map.containsKey(v.getName())) {
			throw 
				new SyntaxException(
					"Could not use variable name \"%s\" to register lexeme \"%s\". Name is already occupied by lexeme \"%s\"",
					v.getName(),
					v.getLexemeId(),
					_map.get(v.getName()).getLexemeId()
				);
		}
		
		_map.put(v.getName(), v);
	}
	
	@Override
	public synchronized boolean hasAnyStartsWith(String str) {
		return _map.keySet().stream().anyMatch(k->k.startsWith(str));
	}

	@Override
	public synchronized IVariable findByName(String name) {
		return _map.get(name);
	}

}
