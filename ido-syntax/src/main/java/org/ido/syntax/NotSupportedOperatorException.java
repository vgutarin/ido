package org.ido.syntax;

public class NotSupportedOperatorException extends SyntaxException {

	private static final long serialVersionUID = 20191012L;

	public NotSupportedOperatorException() {
	}

	public NotSupportedOperatorException(String arg0) {
		super(arg0);
	}

	public NotSupportedOperatorException(Throwable arg0) {
		super(arg0);
	}

	public NotSupportedOperatorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotSupportedOperatorException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	public NotSupportedOperatorException(String format, Object... objects) {
		super(format, objects);
	}
}
