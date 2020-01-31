package org.ido.syntax.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.ido.syntax.SyntaxException;
import org.ido.syntax.type.BooleanTypeDescriptor;
import org.ido.syntax.type.LongTypeDescriptor;
import org.junit.jupiter.api.Test;

class VariableTest {

	@Test
	void testGetLexemeId() {
		Variable<Long> v = new Variable<Long>(LongTypeDescriptor.instance, "justName");
		assertEquals(
				"(org.ido.syntax.variable.Variable) justName",
				v.getLexemeId()
		);
		
		v = new Variable<Long>(LongTypeDescriptor.instance, " justAnotherName\t ");
		assertEquals(
				"(org.ido.syntax.variable.Variable) justAnotherName",
				v.getLexemeId()
		);
	}

	@Test
	void testIsStringRepresentationStartsWith() {
		Variable<Long> v = new Variable<Long>(LongTypeDescriptor.instance, "justName");
		assertTrue(v.isStringRepresentationStartsWith("j"));
		assertTrue(v.isStringRepresentationStartsWith("jus"));
		assertTrue(v.isStringRepresentationStartsWith("justNam"));
		assertTrue(v.isStringRepresentationStartsWith("justName"));
		assertFalse(v.isStringRepresentationStartsWith("justName1"));
		assertFalse(v.isStringRepresentationStartsWith("justn"));
		
		v = new Variable<Long>(LongTypeDescriptor.instance, "v2");
		assertTrue(v.isStringRepresentationStartsWith("v2"));
		assertFalse(v.isStringRepresentationStartsWith("v3"));
	}

	@Test
	void testGetVo() throws SyntaxException {
		Variable<Long> longVar = new Variable<Long>(LongTypeDescriptor.instance, "varName");
		assertSame(LongTypeDescriptor.instance, longVar.getVo().getTypeDescriptor());
		assertNull(longVar.getVo().getValue());
		
		Variable<Boolean> booleanVar = new Variable<Boolean>(BooleanTypeDescriptor.instance, "varName");
		assertSame(BooleanTypeDescriptor.instance, booleanVar.getVo().getTypeDescriptor());
		assertNull(booleanVar.getVo().getValue());
	}

	@Test
	void testGetName() {
		Variable<Long> v = new Variable<Long>(LongTypeDescriptor.instance, "justName");
		assertEquals("justName", v.getName());
		v = new Variable<Long>(LongTypeDescriptor.instance, " \tvarName\n \t");
		assertEquals("varName", v.getName());
	}

	@Test
	void testSetValue() throws SyntaxException {
		Variable<Long> longVar = new Variable<Long>(LongTypeDescriptor.instance, "varName");
		assertNull(longVar.getVo().getValue());
		longVar.setValue(100L);
		assertEquals(new Long(100), longVar.getVo().getValue());
		longVar.setValue(555L);
		assertEquals(new Long(555), longVar.getVo().getValue());
		longVar.setValue(null);
		assertNull(longVar.getVo().getValue());
		
		Variable<Boolean> booleanVar = new Variable<Boolean>(BooleanTypeDescriptor.instance, "varName");
		assertNull(booleanVar.getVo().getValue());
		booleanVar.setValue(true);
		assertEquals(Boolean.TRUE, booleanVar.getVo().getValue());
		booleanVar.setValue(false);
		assertEquals(Boolean.FALSE, booleanVar.getVo().getValue());
		booleanVar.setValue(null);
		assertNull(booleanVar.getVo().getValue());

	}

}
