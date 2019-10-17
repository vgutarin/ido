package org.ido.syntax;

public enum OperatorPriority {
	Highest(Integer.MAX_VALUE),
	Primary(1000), // x++, x--
	Unary(950), // +x, -x, !x, ++x, --x, ^x
	Multiplicative(900), // x * y, x / y, x % y
	Additive(850), // x + y, x – y
	Relational(800),// x < y, x > y, x <= y, x >= y
	Equality(750), //x == y, x != y	
	ConditionalAnd(700),//x && y	
	ConditionalOr(650), //x || y
//	//Assignment x = y, x += y, x -= y, x *= y, x /= y, x %= y	
	Lowest(Integer.MIN_VALUE)
	;
	public final int value;

	private OperatorPriority(int v) {
		value = v;
	}
}
