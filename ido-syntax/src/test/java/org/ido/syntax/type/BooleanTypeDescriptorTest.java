package org.ido.syntax.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.ido.syntax.ParserException;
import org.junit.jupiter.api.Test;

class BooleanTypeDescriptorTest {

	private static final BooleanTypeDescriptor _btd = BooleanTypeDescriptor.instance;
	
	@Test
	void testIsCompartible() {
		assertFalse(_btd.isCompartible(LongTypeDescriptor.instance));
		assertFalse(_btd.isCompartible(null));
		assertTrue(_btd.isCompartible(_btd));
		assertTrue(_btd.isCompartible(BooleanTypeDescriptor.instance));
	}

	//TODO
//	@Test
//	void testCastToValue() {
//		fail("Not yet implemented");
//	}

	@Test
	void testIsStringRepresentationStartsWith() {
		
		assertTrue(_btd.isStringRepresentationStartsWith("t"));
		assertTrue(_btd.isStringRepresentationStartsWith("tr"));
		assertTrue(_btd.isStringRepresentationStartsWith("tru"));
		assertTrue(_btd.isStringRepresentationStartsWith("true"));
		
		assertTrue(_btd.isStringRepresentationStartsWith("f"));
		assertTrue(_btd.isStringRepresentationStartsWith("fa"));
		assertTrue(_btd.isStringRepresentationStartsWith("fal"));
		assertTrue(_btd.isStringRepresentationStartsWith("fals"));
		assertTrue(_btd.isStringRepresentationStartsWith("false"));
		
		assertFalse(_btd.isStringRepresentationStartsWith("e"));
		assertFalse(_btd.isStringRepresentationStartsWith("tre"));
		assertFalse(_btd.isStringRepresentationStartsWith("true1"));
		
		assertFalse(_btd.isStringRepresentationStartsWith("af"));
		assertFalse(_btd.isStringRepresentationStartsWith("fla"));
		assertFalse(_btd.isStringRepresentationStartsWith("false1"));
	}

	@Test
	void testIsStringRepresentationValid() {

		assertTrue(_btd.isStringRepresentationValid("true"));
		assertTrue(_btd.isStringRepresentationValid("false"));
		
		assertFalse(_btd.isStringRepresentationValid("tru"));
		assertFalse(_btd.isStringRepresentationValid("fals"));
		assertFalse(_btd.isStringRepresentationValid("true1"));
		assertFalse(_btd.isStringRepresentationValid("false1"));
	}

	@Test
	void testParseValue() throws ParserException {
		assertEquals(new Boolean(true), _btd.parseValue("true"));
		assertEquals(new Boolean(false), _btd.parseValue("false"));
	}
	
	@Test
	public void testParseValue_Exception() throws ParserException {
		assertThrows(
			ParserException.class,
			() -> _btd.parseValue("true1"));
		
		assertThrows(
				ParserException.class,
				() -> _btd.parseValue("false1"));
		
	}
	
	//TODO
//	@Test
//	void testApply() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetLexemeId() {
		assertEquals("org.ido.syntax.type.BooleanTypeDescriptor", _btd.getLexemeId());
	}

}
