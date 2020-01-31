package org.ido.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.ido.syntax.function.Min;
import org.ido.syntax.operator.Addition;
import org.ido.syntax.operator.Division;
import org.ido.syntax.operator.Multiplication;
import org.ido.syntax.operator.Subtraction;
import org.ido.syntax.operator.UnaryMinus;
import org.ido.syntax.type.LongTypeDescriptor;
import org.ido.syntax.variable.LongTypeVariable;
import org.ido.syntax.variable.VariablesHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ParserBasedOnLongTypeVariablesTest {

	final ITypeDescriptor<?> td = LongTypeDescriptor.instance;
	
	final LongTypeVariable x, y;
	final IVariablesProvider vars;
	
	protected ParserBuilder builder;
	
	public ParserBasedOnLongTypeVariablesTest() throws SyntaxException {
		x = new LongTypeVariable("x");
		y = new LongTypeVariable("y");
		
		vars = new VariablesHolder(x,y);
	}
	
	@BeforeEach
	void setUp() throws SyntaxException {
		builder = new ParserBuilder()
					.withTypes(td)
					.withOperators(
							new Addition(),
							new Subtraction(),
							new Multiplication(),
							new UnaryMinus(),
							new Division())
					.withFunctions(new Min())
					.withVariablesProvider(vars);
	}
	
	@Test
	public void testLongVariableDescriptor() throws SyntaxException {
		
		final Parser p = builder.build();
		for (long i = 1; i < 5 ; ++i) {
			x.setValue(i);
			IVoComponent vo = p.parse(" x ");
			assertNotNull(vo);
			assertEquals(new Long(i), vo.getValue());
			assertSame(x.getVo().getTypeDescriptor(), vo.getTypeDescriptor());
			assertEquals("x", vo.getComponentDesc().str);
		}
	}
	
	@Test
	public void testLongVariableAddition() throws SyntaxException {
		
		final Parser p = builder.build();
		for (long i = 1; i < 5 ; ++i) {
			x.setValue(i);
			y.setValue(i + 2);
			IVoComponent vo = p.parse(" x + y ");
			assertNotNull(vo);
			assertEquals(new Long(i + (i + 2)), vo.getValue());
			assertSame(x.getVo().getTypeDescriptor(), vo.getTypeDescriptor());
			assertEquals("x + y", vo.getComponentDesc().str);
		}
	}
	
	@Test
	public void testLongVariableMultiplication() throws SyntaxException {
		
		final Parser p = builder.build();
		for (long i = 1; i < 5 ; ++i) {
			x.setValue(i);
			y.setValue(i + 2);
			IVoComponent vo = p.parse(" x + y * 2 ");
			assertNotNull(vo);
			assertEquals(new Long(i + (i + 2) * 2), vo.getValue());
			assertSame(x.getVo().getTypeDescriptor(), vo.getTypeDescriptor());
			assertEquals("x + y * 2", vo.getComponentDesc().str);
		}
	}
	
	@Test
	public void testLongVariableUnaryMinus() throws SyntaxException {
		
		final Parser p = builder.build();
		for (long i = 1; i < 5 ; ++i) {
			x.setValue(i);
			y.setValue(i + 2);
			IVoComponent vo = p.parse(" -( -x + y * 2) ");
			assertNotNull(vo);
			assertEquals(new Long(- (-i + (i + 2) * 2)), vo.getValue());
			assertSame(x.getVo().getTypeDescriptor(), vo.getTypeDescriptor());
			assertEquals("-( -x + y * 2)", vo.getComponentDesc().str);
		}
	}
	
	@Test
	public void testLongVariableMinFunction() throws SyntaxException {
		
		final Parser p = builder.build();
		for (long i = 1; i < 5 ; ++i) {
			x.setValue(i + 2);
			y.setValue(i);
			IVoComponent vo = p.parse(" min( -(x - y * 2), 0)");
			assertNotNull(vo);
			assertEquals(new Long(Math.min(-((i + 2) - i * 2), 0)), vo.getValue());
			assertSame(x.getVo().getTypeDescriptor(), vo.getTypeDescriptor());
			assertEquals("min( -(x - y * 2), 0)", vo.getComponentDesc().str);
		}
	}
	
}
