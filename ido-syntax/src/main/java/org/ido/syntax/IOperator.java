package org.ido.syntax;

public interface IOperator extends ILexeme {
	boolean isStringRepresentationValid(String src);
	OperatorPriority getPriority();
	int leftOperandsCount();
	int rightOperandsCount();
}
