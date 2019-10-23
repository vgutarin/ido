package org.ido.syntax.operator;

import static org.junit.Assert.*;

import org.ido.syntax.OperatorPriority;
import org.junit.Test;

public class OperatorTest {

	private class OperatorImpl extends Operator{
				
		public OperatorImpl(OperatorPriority priority, String lexeme) {
			super(priority, lexeme);
		}
		
		public OperatorImpl(String lexeme) {
			this(OperatorPriority.Lowest, lexeme);
		}
	}
	
	@Test
	public void testOperator_String() {
		Operator o =  new OperatorImpl("+");
		
		assertEquals("operator.OperatorImpl", o.getLexemeId());
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
		Operator o =  new OperatorImpl("*");
		
		assertEquals("operator.OperatorImpl", o.getLexemeId());
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
		Operator o =  new OperatorImpl("++");
		
		assertEquals("operator.OperatorImpl", o.getLexemeId());
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

	@Test
	public void testOperator_Priority() {
		for(OperatorPriority p : OperatorPriority.values()) {
			assertSame(p, new OperatorImpl(p, "any").getPriority());
		}
	}
}
