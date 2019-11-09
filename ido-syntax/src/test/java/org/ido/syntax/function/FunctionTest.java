package org.ido.syntax.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.vo.HoldVoValue;
import org.junit.jupiter.api.Test;

class FunctionTest {

	class FunctionImpl extends Function {

		protected FunctionImpl(String name) throws SyntaxException {
			super(name);
		}

		@Override
		public ITypeDescriptor<?> isApplicable(List<ITypeDescriptor<?>> arguments) {
			return null;
		}

		@Override
		public boolean isDeterministic() {
			return false;
		}

		@Override
		public HoldVoValue apply(List<HoldVoValue> arguments) throws SyntaxException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Test
	void testFunction() {
		assertThrows(SyntaxException.class, () -> new FunctionImpl(null));
		assertThrows(SyntaxException.class, () -> new FunctionImpl(""));
		assertThrows(SyntaxException.class, () -> new FunctionImpl(""));
	}
	
	@Test
	void testGetLexemeId() throws SyntaxException {
		assertEquals(FunctionImpl.class.getCanonicalName(), new FunctionImpl("a").getLexemeId());
	}

	@Test
	void testIsStringRepresentationStartsWith() throws SyntaxException {
		Function f = new FunctionImpl("any");
		assertTrue(f.isStringRepresentationStartsWith("a"));
		assertTrue(f.isStringRepresentationStartsWith("an"));
		assertTrue(f.isStringRepresentationStartsWith("any"));
		assertFalse(f.isStringRepresentationStartsWith("any1"));
		assertFalse(f.isStringRepresentationStartsWith("any "));
		assertFalse(f.isStringRepresentationStartsWith(" any"));
	}

	@Test
	void testGetName() throws SyntaxException {
		assertEquals("abs", new FunctionImpl(" abs ").getName());
	}

}
