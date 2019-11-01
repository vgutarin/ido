package org.ido.syntax;

class Scope implements ILexeme {

	public final ILexeme closeLexeme;
	public final char openChar, closeChar;
	
	private final String _openLexemeId, _closeLexemeId;
	
	public Scope(char open, char close) throws SyntaxException {
		if (open == close) {
			throw new SyntaxException("Could not create instance of %s with equal open %s and close %s chars",
					getClass().getCanonicalName(),
					open,
					close
			);
		}
		openChar = open;
		closeChar = close;
		_openLexemeId = String.format("%s %s...%s Open", getClass().getCanonicalName(), open, close);
		_closeLexemeId = String.format("%s %s...%s Close", getClass().getCanonicalName(), open, close);
		closeLexeme = new ILexeme() {
			
			@Override
			public boolean isStringRepresentationStartsWith(String src) {
				return 1 == src.length() && closeChar == src.charAt(0);
			}
			
			@Override
			public String getLexemeId() {
				return _closeLexemeId;
			}
		};
	}
	
	@Override
	public String getLexemeId() {
		return _openLexemeId;
	}

	@Override
	public boolean isStringRepresentationStartsWith(String src) {
		return 1 == src.length() && openChar == src.charAt(0);
	}
	
	public String strip(String src) throws ParserException {
		src = src.trim();
		if (src.isEmpty() || openChar != src.charAt(0))
			throw new ParserException("Scope %s Cannot strip string %s", getLexemeId(), src);
		if (closeChar != src.charAt(src.length() - 1))
			throw new ParserException("Scope %s Cannot strip string %s", getLexemeId(), src);
		return src.substring(1, src.length() - 1);
	}
}
