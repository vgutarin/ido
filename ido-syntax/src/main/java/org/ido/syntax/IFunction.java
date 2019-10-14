package org.ido.syntax;

public interface IFunction extends ILexeme {
	boolean isLeftArgumentExpected();
	boolean isArgumentsClosingCharacterExpected();
	char getArgumentsDelimiter();
	char getArgumentsClosingCharacter();
}
