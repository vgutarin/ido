package org.ido.syntax.operator;

import static org.junit.Assert.*;

import org.junit.Test;

public class OperatorTest {

	private class OperatorImpl extends Operator{
		public OperatorImpl(String lexeme) {
			super(lexeme);
		}
		
		public OperatorImpl(String lexeme, boolean isLeftOperandExpected) {
			super(lexeme, isLeftOperandExpected);
		}
	}
	
	@Test
	public void testOperator_String() {
		Operator o =  new OperatorImpl("+");
		
		assertEquals("operator.OperatorImpl", o.getLexemeId());
		assertTrue(o.isLeftOperandExpected());
		assertTrue(o.isStringRepresentationStartsWith("+"));
		assertTrue(o.isStringRepresentationValid("+"));
		assertFalse(o.isStringRepresentationStartsWith(" +"));
		assertFalse(o.isStringRepresentationValid(" +"));
		assertFalse(o.isStringRepresentationStartsWith("+ "));
		assertFalse(o.isStringRepresentationValid("+ "));
		assertFalse(o.isStringRepresentationStartsWith("-"));
		assertFalse(o.isStringRepresentationValid("-"));
	}
	
	@Test
	public void testOperator_String_True() {
		Operator o =  new OperatorImpl("*", true);
		
		assertEquals("operator.OperatorImpl", o.getLexemeId());
		assertTrue(o.isLeftOperandExpected());
		assertTrue(o.isStringRepresentationStartsWith("*"));
		assertTrue(o.isStringRepresentationValid("*"));
		assertFalse(o.isStringRepresentationStartsWith(" *"));
		assertFalse(o.isStringRepresentationValid(" *"));
		assertFalse(o.isStringRepresentationStartsWith("* "));
		assertFalse(o.isStringRepresentationValid("* "));
		assertFalse(o.isStringRepresentationStartsWith("+"));
		assertFalse(o.isStringRepresentationValid("+"));
	}
	
	@Test
	public void testOperator_String_False() {
		Operator o =  new OperatorImpl("++", false);
		
		assertEquals("operator.OperatorImpl", o.getLexemeId());
		assertFalse(o.isLeftOperandExpected());
		assertTrue(o.isStringRepresentationStartsWith("+"));
		assertFalse(o.isStringRepresentationValid("+"));
		assertTrue(o.isStringRepresentationValid("++"));
		assertFalse(o.isStringRepresentationStartsWith(" +"));
		assertFalse(o.isStringRepresentationValid(" ++"));
		assertFalse(o.isStringRepresentationStartsWith("+ "));
		assertFalse(o.isStringRepresentationValid("++ "));
		assertFalse(o.isStringRepresentationStartsWith("*"));
		assertFalse(o.isStringRepresentationValid("*"));
	}

}
