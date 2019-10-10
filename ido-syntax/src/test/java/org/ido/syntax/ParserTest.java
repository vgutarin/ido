package org.ido.syntax;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.ido.syntax.type.LongTypeDescriptor;
import org.junit.Test;

public class ParserTest {

	@Test
	public void testLongTypeDescriptor() throws ParserException {
		final ITypeDescriptor<?> td = new LongTypeDescriptor();
		final Parser p = new Parser(
				Arrays.asList(td),
				new ArrayList<IFunction>()
		);
		
		IVO vo = p.parse(" 123 ");
		assertNotNull(vo);
		assertEquals(new Long(123), vo.getValue());
		assertSame(td, vo.getTypeDescriptor());
		assertEquals("123", vo.getSrc());
	}

}
