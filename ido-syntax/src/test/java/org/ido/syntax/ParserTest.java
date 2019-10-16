package org.ido.syntax;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.ido.syntax.operator.Addition;
import org.ido.syntax.type.LongTypeDescriptor;
import org.junit.Test;

public class ParserTest {

	@Test
	public void testLongTypeDescriptor() throws SyntaxException {
		final ITypeDescriptor<?> td = new LongTypeDescriptor();
		final Parser p = new Parser(
				Arrays.asList(td),
				new ArrayList<IOperator>()
		);
		
		IVO vo = p.parse(" 123 ");
		assertNotNull(vo);
		assertEquals(new Long(123), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("123", vo.getSrc());
	}
	
	@Test
	public void testLongsAddition() throws SyntaxException {
		final ITypeDescriptor<?> td = new LongTypeDescriptor();
		final Parser p = new Parser(
				Arrays.asList(td),
				Arrays.asList((IOperator)( new Addition() ))
		);
		
		IVO vo = p.parse(" 123 + 56");
		assertNotNull(vo);
		assertEquals(new Long(179), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("123 + 56", vo.getSrc());
	}
}
