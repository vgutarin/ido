package org.ido.syntax;

class Position {

	public final String str;
	public final int start;
	public int current;

	private Position(String str, int startPosition) {
		this.str = str;
		this.start = current = startPosition;
	}

	public Position(String src) {
		this(src, 0);
	}

	public Position copyForward() {
		Position s = new Position(str, current);
		return s;
	}

	public String toString() {
		int substrLength = 10;

		int startIdx = current - substrLength;

		if (startIdx < 0)
			startIdx = 0;
		if (startIdx + substrLength > str.length())
			substrLength = str.length() - startIdx;

		return String.format("%d. String: %s%s%s", current, startIdx > 0 ? "..." : "",
				str.substring(startIdx, startIdx + substrLength),
				startIdx + substrLength < str.length() - 1 ? "..." : "");
	}
	
	public void skipWhiteCharacters() {
		while(str.length() > current && Character.isWhitespace(str.charAt(current))) ++current;
	}
}
