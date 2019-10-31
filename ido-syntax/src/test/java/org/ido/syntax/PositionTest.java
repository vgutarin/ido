package org.ido.syntax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PositionTest {

	private final String _sourceString = "some string (does not matter what exactly)"; 
	
	@Test
	void testPosition() {
		Position s = new Position (_sourceString);
		assertEquals(_sourceString, s.str);
		assertEquals(0, s.start);
		assertEquals(0, s.current);
		assertEquals(0, s.parentnessesOpenCount);
	}
	
	@Test
	void testCopyForward() {
		final Position s = new Position (_sourceString);
		Position copy = s.copyForward();
		
		assertNotSame(s, copy);
		assertEquals(_sourceString, copy.str);
		assertEquals(0, copy.start);
		assertEquals(0, copy.current);
		assertEquals(0, copy.parentnessesOpenCount);
		
		s.current = 1;
		s.parentnessesOpenCount = 2;
		copy = s.copyForward();
		
		assertEquals(_sourceString, copy.str);
		assertEquals(1, copy.start);
		assertEquals(1, copy.current);
		assertEquals(2, copy.parentnessesOpenCount);
		
		copy.current = 4;
		copy.parentnessesOpenCount = 5;
		copy = copy.copyForward();
		
		assertEquals(_sourceString, copy.str);
		assertEquals(4, copy.start);
		assertEquals(4, copy.current);
		assertEquals(5, copy.parentnessesOpenCount);		
	}

}
