package org.ido.syntax;

public class ParserException extends SyntaxException {

	private static final long serialVersionUID = 2019-10-12L;

	public ParserException() {
	}

	public ParserException(String arg0) {
		super(arg0);
	}

	public ParserException(Throwable arg0) {
		super(arg0);
	}

	public ParserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParserException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	public ParserException(String format, Object... objects) {
		super(format, objects);
	}
}
