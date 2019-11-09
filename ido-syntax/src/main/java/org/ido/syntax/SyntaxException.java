package org.ido.syntax;

import java.util.Collection;
import java.util.stream.Collectors;

public class SyntaxException extends Exception {

	private static final long serialVersionUID = 2019-10-12L;

	public SyntaxException() {
	}

	public SyntaxException(String arg0) {
		super(arg0);
	}

	public SyntaxException(Throwable arg0) {
		super(arg0);
	}

	public SyntaxException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SyntaxException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	public SyntaxException(String format, Object... objects) {
		super(String.format(format, objects));
	}
	
	public static <T extends ILexeme> String toCsv(Collection<T> lexemes) {
		return String.join(", ", lexemes.stream().map(l -> l.getLexemeId()).collect(Collectors.toList()));
	}
}
