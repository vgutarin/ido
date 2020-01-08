package org.ido.syntax.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ido.syntax.ExpressionComponent;
import org.ido.syntax.IFunction;
import org.ido.syntax.IVo;
import org.ido.syntax.IVoComponent;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;

public class FunctionEvaluationVo extends Vo {
	// TODO detect immutable
	
	private final List<IVo> _arguments;
	private final IFunction _function;
	
	public FunctionEvaluationVo(ExpressionComponent component, List<IVoComponent> arguments) throws ParserException {
		super(component, component.function.isApplicable(arguments.stream().map(a->a.getTypeDescriptor()).collect(Collectors.toList())));
		//TODO check not nulls
		_arguments = new ArrayList<IVo>(arguments);
		_function = component.function;
	}

	@Override
	public Object getValue() throws SyntaxException {
		List<HoldVoValue> holdValues = new ArrayList<HoldVoValue>();
		for(IVo ivo : _arguments) {
			holdValues.add(new HoldVoValue(ivo));
		}
		return _function.apply(holdValues).getValue();
	}

	@Override
	public boolean isMutable() {
		return true;
	}
}
