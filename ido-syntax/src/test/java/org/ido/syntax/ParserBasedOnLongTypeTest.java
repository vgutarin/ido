package org.ido.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.ido.syntax.function.Min;
import org.ido.syntax.operator.Addition;
import org.ido.syntax.operator.Division;
import org.ido.syntax.operator.GreaterOrEqualThan;
import org.ido.syntax.operator.GreaterThan;
import org.ido.syntax.operator.LessOrEqualThan;
import org.ido.syntax.operator.LessThan;
import org.ido.syntax.operator.Multiplication;
import org.ido.syntax.operator.Remainder;
import org.ido.syntax.operator.Subtraction;
import org.ido.syntax.operator.UnaryMinus;
import org.ido.syntax.operator.UnaryPlus;
import org.ido.syntax.type.BooleanTypeDescriptor;
import org.ido.syntax.type.LongTypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ParserBasedOnLongTypeTest {
	
	final ITypeDescriptor<?> td = LongTypeDescriptor.instance;
	protected ParserBuilder builder;
	
	@BeforeEach
	void setUp() {
		builder = new ParserBuilder().withTypes(td);
	}
	
	@Test
	public void testLongTypeDescriptor() throws SyntaxException {
		
		final Parser p = builder.build();
		
		IVoComponent vo = p.parse(" 123 ");
		assertNotNull(vo);
		assertEquals(new Long(123), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("123", vo.getComponentDesc().str);
	}
	
	@Test
	public void testLongsAddition() throws SyntaxException {
		
		final Parser p = 
				builder
					.withOperators( new Addition() )
				.build();
		
		IVoComponent vo = p.parse(" 123 + 56");
		assertNotNull(vo);
		assertEquals(new Long(179), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("123 + 56", vo.getComponentDesc().str);
	}
	
	@Test
	public void testLongsSubtraction() throws SyntaxException {
		
		final Parser p = 
				builder
					.withOperators( new Subtraction() )
				.build();
		
		IVoComponent vo = p.parse(" 179 - 56");
		assertNotNull(vo);
		assertEquals(new Long(123), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("179 - 56", vo.getComponentDesc().str);
	}
	
	@Test
	public void testLongsAdditionAndSubtraction() throws SyntaxException {
		
		final Parser p = 
				builder
					.withOperators(
						new Addition(),
						new Subtraction()
					)
				.build();
		
		IVoComponent vo = p.parse("50 + 179 - 56");
		assertNotNull(vo);
		assertEquals(new Long(173), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("50 + 179 - 56", vo.getComponentDesc().str);
	}
	
	@Test
	public void testLongsMultiplication() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Multiplication(),
						new Subtraction()
					)
				.build();
		
		IVoComponent vo = p.parse(" 179 *56");
		assertNotNull(vo);
		assertEquals(new Long(10024), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("179 *56", vo.getComponentDesc().str);
	}
	
	@Test
	public void testLongsAdditionAndMultiplication() throws SyntaxException {
		
		final Parser p = builder
		.withOperators(
				new Addition(),
				new Subtraction(),
				new Multiplication()
			)
		.build();
		
		IVoComponent vo = p.parse("50 + 179 *56");
		assertNotNull(vo);
		assertEquals(new Long(10074), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("50 + 179 *56", vo.getComponentDesc().str);
		assertEquals(vo.getValue(), p.parse("179*56+50").getValue());
		assertEquals(new Long(10079), p.parse("50 + 179 *56 +5" ).getValue());
	}
	
	@Test
	public void testLongsDivision() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Multiplication(),
						new Subtraction(),
						new Division()
					)
				.build();
		
		IVoComponent vo = p.parse(" 179 /56");
		assertNotNull(vo);
		assertEquals(new Long(3), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("179 /56", vo.getComponentDesc().str);
	}
	
	@Test
	public void testLongsAdditionAndDivision() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division()
					)
				.build();
		
		IVoComponent vo = p.parse("50 + 179 /56");
		assertNotNull(vo);
		assertEquals(new Long(53), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("50 + 179 /56", vo.getComponentDesc().str);
		assertEquals(vo.getValue(), p.parse("179/56+50").getValue());
		assertEquals(new Long(58), p.parse("50 + 179 /56 +5" ).getValue());
		assertEquals(new Long(233), p.parse("50 - 1 + 56/56 *179 +5" ).getValue());
		assertEquals(new Long(168), p.parse("179 /56 * 56" ).getValue());
		
	}
	
	@Test
	public void testLongsRemainder() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder()
					)
				.build();
		
		IVoComponent vo = p.parse(" 179 %56");
		assertNotNull(vo);
		assertEquals(new Long(11), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("179 %56", vo.getComponentDesc().str);
	}
	
	@Test
	public void testLongsAdditionAndRemainder() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder()
					)
				.build();
		
		IVoComponent vo = p.parse("50 + 179 %56");
		assertNotNull(vo);
		assertEquals(new Long(61), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("50 + 179 %56", vo.getComponentDesc().str);
		assertEquals(vo.getValue(), p.parse("179%56+50").getValue());
		assertEquals(new Long(66), p.parse("50 + 179 %56 +5" ).getValue());
		assertEquals(new Long(55), p.parse("50 - 1 + 56/56 %179 +5" ).getValue());
		assertEquals(new Long(112), p.parse(" 56%179 + 56" ).getValue());
		
	}
	
	@Test
	public void testLongUnaryMinus() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus()
					)
				.build();
		
		IVoComponent vo = p.parse(" - 179");
		assertNotNull(vo);
		assertEquals(new Long(-179), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("- 179", vo.getComponentDesc().str);
		assertEquals(new Long(179), p.parse(" - - 179").getValue());
		assertEquals(new Long(-179), p.parse(" - - - 179").getValue());
	}
	
	@Test
	public void testLongsUnaryMinusInTheExpressions() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus()
					)
				.build();
		
		IVoComponent vo = p.parse(" - -50 + 179 %56");
		assertNotNull(vo);
		assertEquals(new Long(61), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("- -50 + 179 %56", vo.getComponentDesc().str);
		assertEquals(vo.getValue(), p.parse("179%56+50").getValue());
		assertEquals(new Long(56), p.parse("50 + 179 %56 + -5" ).getValue());
		assertEquals(new Long(57), p.parse("50 - - 1 + 56/56 %179 +5" ).getValue());
		assertEquals(new Long(0), p.parse("56 + -56%-179" ).getValue());
		assertEquals(new Long(0), p.parse("56 -56%-179" ).getValue());
		assertEquals(new Long(0), p.parse("56%-179 - 56" ).getValue());
		assertEquals(new Long(112), p.parse("56%-179 - - 56" ).getValue());
		assertEquals(new Long(112), p.parse("56%-179 + - - 56" ).getValue());
	}
	
	@Test
	public void testLongUnaryPlus() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus()
					)
				.build();
		
		IVoComponent vo = p.parse(" +- 179");
		assertNotNull(vo);
		assertEquals(new Long(-179), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("+- 179", vo.getComponentDesc().str);
		assertEquals(new Long(179), p.parse("+ - - 179").getValue());
		assertEquals(new Long(-179), p.parse(" - - + - +179").getValue());
	}
	
	@Test
	public void testLongsUnaryPlusInTheExpressions() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus()
					)
				.build();
		
		IVoComponent vo = p.parse(" - -+50 + +179 %56");
		assertNotNull(vo);
		assertEquals(new Long(61), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("- -+50 + +179 %56", vo.getComponentDesc().str);
		assertEquals(vo.getValue(), p.parse("179%+56 + +50").getValue());
		assertEquals(new Long(56), p.parse("50 + 179 %56 + -5" ).getValue());
		assertEquals(new Long(57), p.parse("50 - - +1 + 56/56 %179 +5" ).getValue());
		assertEquals(new Long(0), p.parse("56 + + -56%-179" ).getValue());
		assertEquals(new Long(0), p.parse("56 + -56%-179" ).getValue());
		assertEquals(new Long(0), p.parse("56%-+179 - 56" ).getValue());
		assertEquals(new Long(112), p.parse("56%-179 - + - 56" ).getValue());
		assertEquals(new Long(112), p.parse("56%-179 + + - - 56" ).getValue());
	}
	
	@Test
	public void testLongParentnesses() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus()
					)
				.build();
		
		IVoComponent vo = p.parse(" (+- 179)");
		assertNotNull(vo);
		assertEquals(new Long(-179), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("(+- 179)", vo.getComponentDesc().str);
		assertEquals(new Long(179), p.parse("+ - (- 179)").getValue());
		assertEquals(new Long(-179), p.parse(" - - + (- +179)").getValue());
	}
	
	@Test
	public void testLongsParentnessesInTheExpressions() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus()
					)
				.build();
		
		IVoComponent vo = p.parse(" - -(+50 + +179) %56");
		assertNotNull(vo);
		assertEquals(new Long(5), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("- -(+50 + +179) %56", vo.getComponentDesc().str);
		assertEquals(new Long(73), p.parse("179%+(56 + +50)").getValue());
		assertEquals(new Long(56), p.parse("(50 + 179 %56 + -5)" ).getValue());
		assertEquals(new Long(56), p.parse("50 - - +(1 + 56)/56 %179 +5" ).getValue());
		assertEquals(new Long(1), p.parse("(56 + 1 + -56)%-179" ).getValue());
		assertEquals(new Long(0), p.parse("(56 + -56)%-179" ).getValue());
		assertEquals(new Long(56), p.parse("56%-+(179 - 56)" ).getValue());
		assertEquals(new Long(56), p.parse("56%-(179 - + - 56)" ).getValue());
		assertEquals(new Long(56), p.parse("56%(-179 + + - - 56)" ).getValue());
		assertEquals(new Long(320), p.parse("(177-179) * 10 * ((3 + 5) * -(3-1))" ).getValue());
	}
	
	@Test
	public void testLongsMinFunction() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus()
					)
					.withFunctions(new Min())
				.build();
		
		IVoComponent vo = p.parse(" min(-1, - -(+50 + +179) %56)");
		assertNotNull(vo);
		assertEquals(new Long(-1), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("min(-1, - -(+50 + +179) %56)", vo.getComponentDesc().str);
		assertEquals(new Long(1), p.parse("min(1,22, 179%+(56 + +50))").getValue());
		assertEquals(new Long(-100), p.parse("min(1,22, 179%+(56 + +50), -100)").getValue());
		
		assertEquals(new Long(-1000), p.parse("min(1,22, 179%+(56 + +50), -100, min(3, -1000,50))").getValue());
	}
	
	@Test
	public void testLongsGreaterThanFunction() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus(),
						new GreaterThan()
					)
					.withFunctions(new Min())
				.build();
		
		IVoComponent vo = p.parse("0 > min(-1, - -(+50 + +179) %56)");
		assertNotNull(vo);
		assertSame(BooleanTypeDescriptor.instance, vo.getTypeDescriptor());
		assertEquals(Boolean.TRUE, vo.getValue());
		
		assertEquals("0 > min(-1, - -(+50 + +179) %56)", vo.getComponentDesc().str);
		
		assertEquals(Boolean.FALSE, p.parse("1 > min(1,22, 179%+(56 + +50))").getValue());
		assertEquals(Boolean.FALSE, p.parse("1 > 1").getValue());
		assertEquals(Boolean.TRUE, p.parse("-99 > min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.TRUE, p.parse("-99 > -100").getValue());
		assertEquals(Boolean.FALSE, p.parse("-101 > min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.FALSE, p.parse("-101 > -100").getValue());
		
	}
	
	@Test
	public void testLongsGreaterOrEqualThanFunction() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus(),
						new GreaterThan(),
						new GreaterOrEqualThan()
					)
					.withFunctions(new Min())
				.build();
		
		IVoComponent vo = p.parse("0 >= min(-1, - -(+50 + +179) %56)");
		assertNotNull(vo);
		assertSame(BooleanTypeDescriptor.instance, vo.getTypeDescriptor());
		assertEquals(Boolean.TRUE, vo.getValue());
		
		assertEquals("0 >= min(-1, - -(+50 + +179) %56)", vo.getComponentDesc().str);
		
		assertEquals(Boolean.TRUE, p.parse("1 >= min(1,22, 179%+(56 + +50))").getValue());
		assertEquals(Boolean.TRUE, p.parse("1 >= 1").getValue());
		assertEquals(Boolean.TRUE, p.parse("-99 >= min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.TRUE, p.parse("-99 >= -100").getValue());
		assertEquals(Boolean.TRUE, p.parse("-100 >= min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.TRUE, p.parse("-100 >= -100").getValue());
		assertEquals(Boolean.FALSE, p.parse("-101 >= min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.FALSE, p.parse("-101 >= -100").getValue());
		
	}
	
	@Test
	public void testLongsLessThanFunction() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus(),
						new GreaterThan(),
						new GreaterOrEqualThan(),
						new LessThan()
					)
					.withFunctions(new Min())
				.build();
		
		IVoComponent vo = p.parse("0 < min(-1, - -(+50 + +179) %56)");
		assertNotNull(vo);
		assertSame(BooleanTypeDescriptor.instance, vo.getTypeDescriptor());
		assertEquals(Boolean.FALSE, vo.getValue());
		
		assertEquals("0 < min(-1, - -(+50 + +179) %56)", vo.getComponentDesc().str);
		
		assertEquals(Boolean.FALSE, p.parse("1 < min(1,22, 179%+(56 + +50))").getValue());
		assertEquals(Boolean.FALSE, p.parse("1 < 1").getValue());
		assertEquals(Boolean.FALSE, p.parse("-99 < min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.FALSE, p.parse("-99 < -100").getValue());
		assertEquals(Boolean.FALSE, p.parse("-100 < min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.FALSE, p.parse("-100 < -100").getValue());
		assertEquals(Boolean.TRUE, p.parse("-101 < min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.TRUE, p.parse("-101 < -100").getValue());
		
	}
	
	@Test
	public void testLongsLessOrEqualThanFunction() throws SyntaxException {
		
		final Parser p = builder
					.withOperators(
						new Addition(),
						new Subtraction(),
						new Multiplication(),
						new Division(),
						new Remainder(),
						new UnaryMinus(),
						new UnaryPlus(),
						new GreaterThan(),
						new GreaterOrEqualThan(),
						new LessThan(),
						new LessOrEqualThan()
					)
					.withFunctions(new Min())
				.build();
		
		IVoComponent vo = p.parse("0 <= min(-1, - -(+50 + +179) %56)");
		assertNotNull(vo);
		assertSame(BooleanTypeDescriptor.instance, vo.getTypeDescriptor());
		assertEquals(Boolean.FALSE, vo.getValue());
		
		assertEquals("0 <= min(-1, - -(+50 + +179) %56)", vo.getComponentDesc().str);
		
		assertEquals(Boolean.TRUE, p.parse("1 <= min(1,22, 179%+(56 + +50))").getValue());
		assertEquals(Boolean.TRUE, p.parse("1 <= 1").getValue());
		assertEquals(Boolean.FALSE, p.parse("-99 <= min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.FALSE, p.parse("-99 <= -100").getValue());
		assertEquals(Boolean.TRUE, p.parse("-100 <= min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.TRUE, p.parse("-100 <= -100").getValue());
		assertEquals(Boolean.TRUE, p.parse("-101 <= min(1,22, 179%+(56 + +50), -100)").getValue());
		assertEquals(Boolean.TRUE, p.parse("-101 <= -100").getValue());
		
	}
}
