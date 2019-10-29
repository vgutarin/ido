package org.ido.syntax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SourceTest {

	private final String _sourceString = "some string (does not matter what exactly)"; 
	
	@Test
	void testSoure() {
		Source s = new Source (_sourceString);
		assertEquals(_sourceString, s.str);
		assertEquals(0, s.startPosition);
		assertEquals(0, s.currentPosition);
		assertEquals(0, s.parentnessesOpenCount);
	}
	
	@Test
	void testCopyForward() {
		final Source s = new Source (_sourceString);
		Source copy = s.copyForward();
		
		assertNotSame(s, copy);
		assertEquals(_sourceString, copy.str);
		assertEquals(0, copy.startPosition);
		assertEquals(0, copy.currentPosition);
		assertEquals(0, copy.parentnessesOpenCount);
		
		s.currentPosition = 1;
		s.parentnessesOpenCount = 2;
		copy = s.copyForward();
		
		assertEquals(_sourceString, copy.str);
		assertEquals(1, copy.startPosition);
		assertEquals(1, copy.currentPosition);
		assertEquals(2, copy.parentnessesOpenCount);
		
		copy.currentPosition = 4;
		copy.parentnessesOpenCount = 5;
		copy = copy.copyForward();
		
		assertEquals(_sourceString, copy.str);
		assertEquals(4, copy.startPosition);
		assertEquals(4, copy.currentPosition);
		assertEquals(5, copy.parentnessesOpenCount);		
	}

}
