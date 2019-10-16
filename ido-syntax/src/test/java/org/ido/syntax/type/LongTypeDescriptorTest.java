package org.ido.syntax.type;

import static org.junit.Assert.*;

import org.ido.syntax.ParserException;
import org.junit.Test;

public class LongTypeDescriptorTest {

	LongTypeDescriptor ltd = new LongTypeDescriptor(); 
	
	@Test
	public void testGetId() {
		assertEquals("Long", ltd.getLexemeId());
	}
	
	@Test
	public void testIsStringRepresentationStartsWith() {
		assertFalse(ltd.isStringRepresentationStartsWith(""));
		assertFalse(ltd.isStringRepresentationStartsWith(" "));
		assertFalse(ltd.isStringRepresentationStartsWith(" \t\n"));
		assertFalse(ltd.isStringRepresentationStartsWith(" \t\n12"));
		assertFalse(ltd.isStringRepresentationStartsWith("12 "));
		assertTrue(ltd.isStringRepresentationStartsWith("1"));
		assertTrue(ltd.isStringRepresentationStartsWith("12"));
	}

	@Test
	public void testIsStringRepresentationValid() {
		assertFalse(ltd.isStringRepresentationValid(""));
		assertFalse(ltd.isStringRepresentationValid(" "));
		assertFalse(ltd.isStringRepresentationValid(" \t\n"));
		assertFalse(ltd.isStringRepresentationValid(" \t\n12"));
		assertFalse(ltd.isStringRepresentationValid(" \t\n12 \t\n"));
		assertTrue(ltd.isStringRepresentationValid("12"));
	}

	@Test
	public void testParseVo() throws ParserException {
		assertEquals(new Long(123), ltd.parseVo("123"));
		assertEquals(new Long(456), ltd.parseVo("456"));
	}
	
	@Test(expected = ParserException.class)
	public void testParseVo_Exception() throws ParserException {
		ltd.parseVo("123d");
	}
}
