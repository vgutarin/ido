package org.ido.syntax;

public class Source {

	public final String str;
	public final int startPosition;
	public int currentPosition;

	private Source(String str, int startPosition) {
		this.str = str;
		this.startPosition = currentPosition = startPosition;
	}

	public Source(String src) {
		this(src, 0);
	}

	public Source copyForward() {
		return new Source(str, currentPosition);
	}

	public String getCurrentPositionDescription() {
		int substrLength = 10;

		int startIdx = currentPosition - substrLength;

		if (startIdx < 0)
			startIdx = 0;
		if (startIdx + substrLength > str.length())
			substrLength = str.length() - startIdx;

		return String.format("%d. String: %s%s%s", currentPosition, startIdx > 0 ? "..." : "",
				str.substring(startIdx, startIdx + substrLength),
				startIdx + substrLength < str.length() - 1 ? "..." : "");
	}
}
