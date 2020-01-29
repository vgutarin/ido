package org.ido.syntax;

import java.util.ArrayList;
import java.util.List;

public class ParserBuilder {
	
	private final List<ITypeDescriptor<?>> _types;
	private final List<IOperator> _operators;
	private final List<IFunction> _functions;
	private IVariablesProvider _variablesProvider;
	
	public ParserBuilder() {
		_types = new ArrayList<ITypeDescriptor<?>>();
		_operators = new ArrayList<IOperator>();
		_functions = new ArrayList<IFunction>(); 
	}
	
	public ParserBuilder withTypes(ITypeDescriptor<?> ...descriptors) {
		for(ITypeDescriptor<?> d : descriptors) {
			_types.add(d);
		}
		return this;
	}
	
	public ParserBuilder withOperators(IOperator ...operators) {
		for(IOperator o : operators) {
			_operators.add(o);
		}
		return this;
	}
	
	public ParserBuilder withFunctions(IFunction ...functions) {
		for(IFunction f : functions) {
			_functions.add(f);
		}
		return this;
	}
	
	public ParserBuilder withVariablesProvider(IVariablesProvider variablesProvider) throws ParserException {
		if (null != _variablesProvider) {
			throw new ParserException("Variables provider is already registered");
		}		
		_variablesProvider = variablesProvider;
		return this;
	}
	
	public Parser build() throws SyntaxException
	{
		return new Parser(_types, _operators, _functions); //_variablesProvider
	}
}
