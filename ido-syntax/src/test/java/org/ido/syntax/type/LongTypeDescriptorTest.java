package org.ido.syntax.type;

import static org.junit.jupiter.api.Assertions.*;

import org.ido.syntax.ParserException;
import org.junit.jupiter.api.Test;

public class LongTypeDescriptorTest {

	LongTypeDescriptor ltd = LongTypeDescriptor.instance; 
	
	@Test
	public void testGetId() {
		assertEquals(ltd.getClass().getCanonicalName(), ltd.getLexemeId());
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
	public void testParseValue() throws ParserException {
		assertEquals(new Long(123), ltd.parseValue("123"));
		assertEquals(new Long(456), ltd.parseValue("456"));
	}
	
	@Test
	public void testParseValue_Exception() throws ParserException {
		assertThrows(
			ParserException.class,
			() -> ltd.parseValue("123d"));
		
	}
}
