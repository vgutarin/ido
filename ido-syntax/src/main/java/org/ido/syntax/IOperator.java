package org.ido.syntax;

import java.util.List;

public interface IOperator extends ILexeme {
	boolean isStringRepresentationValid(String src);
	OperatorPriority getPriority();
	int leftOperandsCount();
	int rightOperandsCount();
	OperatorLogic getLogic(List<ITypeDescriptor<?>> operands) throws SyntaxException;
}