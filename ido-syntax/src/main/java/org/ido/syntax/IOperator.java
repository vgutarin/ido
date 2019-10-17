package org.ido.syntax;

public interface IOperator extends ILexeme {
	boolean isLeftOperandExpected();
	boolean isStringRepresentationValid(String src);
	OperatorPriority getPriority();
}
