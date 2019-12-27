package org.ido.syntax.operator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.OperatorPriority;
import org.ido.syntax.SyntaxException;
import org.junit.jupiter.api.Test;

public class OperatorTest {

	private class OperatorImpl extends Operator{
				
		public OperatorImpl(OperatorPriority priority, String lexeme) {
			super(priority, lexeme);
		}
		
		public OperatorImpl(String lexeme) {
			this(OperatorPriority.Lowest, lexeme);
		}

		@Override
		public ITypeDescriptor<?> detectResultType(List<ITypeDescriptor<?>> operands) throws SyntaxException {
			return null;
		}
	}
	
	@Test
	public void testOperator_String() {
		Operator o =  new OperatorImpl("+");
		
		assertEquals(o.getClass().getCanonicalName(), o.getLexemeId());
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
		
		assertEquals(o.getClass().getCanonicalName(), o.getLexemeId());
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
		
		assertEquals(o.getClass().getCanonicalName(), o.getLexemeId());
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
