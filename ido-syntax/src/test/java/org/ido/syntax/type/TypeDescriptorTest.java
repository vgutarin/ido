package org.ido.syntax.type;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.IVo;
import org.ido.syntax.OperatorLogic;
import org.ido.syntax.ParserException;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.operator.GreaterOrEqualThan;
import org.ido.syntax.operator.GreaterThan;
import org.ido.syntax.operator.LessOrEqualThan;
import org.ido.syntax.operator.LessThan;
import org.ido.syntax.vo.HoldVoValue;
import org.junit.jupiter.api.Test;

class TypeDescriptorTest {
	
	private final TypeDescriptor<?> _typeDescriptor = new TypeDescriptor<Integer> () {
		@Override
		public boolean isCompartible(ITypeDescriptor<?> type) {
			return true;
		}

		@Override
		public Integer castToValue(IVo value) throws SyntaxException {
			return (Integer) value.getValue();
		}

		@Override
		public boolean isStringRepresentationValid(String src) {
			return false;
		}

		@Override
		public Object parseValue(String src) throws ParserException {
			return null;
		}

		@Override
		public boolean isStringRepresentationStartsWith(String src) {
			return false;
		}

		@Override
		protected OperatorLogic findOperatorLogic(IOperator operator, List<ITypeDescriptor<?>> operands) throws SyntaxException {
			return null;
		}
		
		@Override
		protected Comparator<Integer> findComparator() {
			return Integer::compare;
		}
	};
	
	private HoldVoValue _vo(int v) throws SyntaxException {
		return new HoldVoValue(_typeDescriptor, v);
	}
	
	@Test
	void testFindLogic() throws SyntaxException {
		IOperator greaterThan = new GreaterThan();
		
		assertNull(_typeDescriptor.findLogic(greaterThan, Arrays.asList()));
		assertNull(_typeDescriptor.findLogic(greaterThan, Arrays.asList(_typeDescriptor)));
		assertNull(_typeDescriptor.findLogic(greaterThan, Arrays.asList(_typeDescriptor, _typeDescriptor, _typeDescriptor)));
		assertNotNull(_typeDescriptor.findLogic(greaterThan, Arrays.asList(_typeDescriptor, _typeDescriptor)));
	}
	
	@Test
	void testGreaterThanLogic() throws SyntaxException {
		IOperator operator = new GreaterThan();
		OperatorLogic logic = _typeDescriptor.findLogic(operator, Arrays.asList(_typeDescriptor, _typeDescriptor));
		assertNotNull(logic);
		assertSame(BooleanTypeDescriptor.instance, logic.resultType);
		assertTrue(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(2), _vo(1)))
				)
		);
		
		assertFalse(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(2), _vo(2)))
				)
		);
	}

	@Test
	void testGreaterOrEqualThanLogic() throws SyntaxException {
		IOperator operator = new GreaterOrEqualThan();
		OperatorLogic logic = _typeDescriptor.findLogic(operator, Arrays.asList(_typeDescriptor, _typeDescriptor));
		
		assertNotNull(logic);
		assertSame(BooleanTypeDescriptor.instance, logic.resultType);
		
		assertTrue(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(2), _vo(1)))
				)
		);
		
		assertTrue(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(2), _vo(2)))
				)
		);
		
		assertFalse(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(1), _vo(2)))
				)
		);
	}
	
	@Test
	void testLessThanLogic() throws SyntaxException {
		IOperator operator = new LessThan();
		OperatorLogic logic = _typeDescriptor.findLogic(operator, Arrays.asList(_typeDescriptor, _typeDescriptor));
		assertNotNull(logic);
		assertSame(BooleanTypeDescriptor.instance, logic.resultType);
		assertFalse(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(2), _vo(1)))
				)
		);
		
		assertFalse(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(2), _vo(2)))
				)
		);
		
		assertTrue(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(1), _vo(2)))
				)
		);
	}

	@Test
	void testLessOrEqualThanLogic() throws SyntaxException {
		IOperator operator = new LessOrEqualThan();
		OperatorLogic logic = _typeDescriptor.findLogic(operator, Arrays.asList(_typeDescriptor, _typeDescriptor));
		assertNotNull(logic);
		assertSame(BooleanTypeDescriptor.instance, logic.resultType);
		assertFalse(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(2), _vo(1)))
				)
		);
		
		assertTrue(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(2), _vo(2)))
				)
		);
		
		assertTrue(
				BooleanTypeDescriptor.instance.castToValue(
						logic.apply(Arrays.asList(_vo(1), _vo(2)))
				)
		);
	}
}
