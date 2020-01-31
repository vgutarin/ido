package org.ido.syntax.variable;

import static org.junit.jupiter.api.Assertions.*;

import org.ido.syntax.IVariable;
import org.ido.syntax.SyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VariablesHolderTest {

	private VariablesHolder _holder;
	
	private final IVariable
		_just = new LongTypeVariable("justName"),
		_justAnother = new LongTypeVariable("justAnotherName");
	
	@BeforeEach
	void setUp() throws Exception {
		_holder = new VariablesHolder(
				_just,
				_justAnother
		);
	}

	@Test
	void testAdd() throws SyntaxException {
		final IVariable v = new LongTypeVariable("newVarName");
		
		assertNull(_holder.findByName("newVarName"));
		_holder.add(v);
		assertSame(v, _holder.findByName("newVarName"));
		
		assertThrows(
				SyntaxException.class,
				()-> _holder.add(v)
			);
		
		assertThrows(
				SyntaxException.class,
				()-> _holder.add(new LongTypeVariable("newVarName"))
			);
	}

	@Test
	void testHasAnyStartsWith() {
		assertTrue(_holder.hasAnyStartsWith("j"));
		assertTrue(_holder.hasAnyStartsWith("just"));
		assertTrue(_holder.hasAnyStartsWith("justName"));
		assertTrue(_holder.hasAnyStartsWith("justAnotherName"));
		
		assertFalse(_holder.hasAnyStartsWith("b"));
		assertFalse(_holder.hasAnyStartsWith("justB"));
		assertFalse(_holder.hasAnyStartsWith("justName1"));
		assertFalse(_holder.hasAnyStartsWith("justAnotherName2"));
	}

	@Test
	void testFindByName() {
		assertSame(_just, _holder.findByName("justName"));
		assertSame(_justAnother, _holder.findByName("justAnotherName"));
	}

}
