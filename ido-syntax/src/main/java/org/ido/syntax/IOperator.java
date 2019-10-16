package org.ido.syntax;

public interface IOperator extends ILexeme {
	boolean isLeftArgumentExpected();
	boolean isStringRepresentationValid(String src);
}
