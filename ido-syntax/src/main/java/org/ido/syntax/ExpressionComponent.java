package org.ido.syntax;

public class ExpressionComponent {

	public final String str;
	public final Position src;
	public final int startIdx, length;
	public final ILexeme lexeme;

	public final ITypeDescriptor<?> typeDescriptor;
	public final IOperator operator;

	public ExpressionComponent(Position src, int startIdx, int length, ILexeme lexeme) {
		this.src = src;
		str = this.src.str.substring(startIdx, startIdx + length).trim();
		this.startIdx = startIdx;
		this.length = length;
		this.lexeme = lexeme;
		typeDescriptor = this.lexeme instanceof ITypeDescriptor<?> ? (ITypeDescriptor<?>) this.lexeme : null;
		operator = this.lexeme instanceof IOperator ? (IOperator) this.lexeme : null;
	}

}
