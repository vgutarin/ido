package org.ido.syntax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScopeTest {
	
	@Test
	void testScope() {
		assertThrows(SyntaxException.class, ()-> new Scope('(', '(') );
	}

	@Test
	void testGetLexemeId() throws SyntaxException {
		Scope scope = new Scope('[', ']');
		assertEquals("org.ido.syntax.Scope [...] Open", scope.getLexemeId());
		assertEquals("org.ido.syntax.Scope [...] Close", scope.closeLexeme.getLexemeId());
		
		scope = new Scope('(', '|');
		assertEquals("org.ido.syntax.Scope (...| Open", scope.getLexemeId());
		assertEquals("org.ido.syntax.Scope (...| Close", scope.closeLexeme.getLexemeId());
	}

	@Test
	void testIsStringRepresentationStartsWith() throws SyntaxException {
		Scope scope = new Scope('[', ']');
		assertTrue( scope.isStringRepresentationStartsWith("["));
		assertFalse( scope.isStringRepresentationStartsWith("("));
		assertFalse( scope.isStringRepresentationStartsWith("]"));
		assertTrue( scope.closeLexeme.isStringRepresentationStartsWith("]"));
		assertFalse( scope.closeLexeme.isStringRepresentationStartsWith(")"));
		assertFalse( scope.closeLexeme.isStringRepresentationStartsWith("["));
		
		scope = new Scope('(', '|');
		assertTrue( scope.isStringRepresentationStartsWith("("));
		assertFalse( scope.isStringRepresentationStartsWith("["));
		assertFalse( scope.isStringRepresentationStartsWith("|"));
		assertFalse( scope.isStringRepresentationStartsWith(""));
		assertFalse( scope.isStringRepresentationStartsWith("( "));
		assertTrue( scope.closeLexeme.isStringRepresentationStartsWith("|"));
		assertFalse( scope.closeLexeme.isStringRepresentationStartsWith(""));
		assertFalse( scope.closeLexeme.isStringRepresentationStartsWith("| "));
		assertFalse( scope.closeLexeme.isStringRepresentationStartsWith("["));
		
	}

}
