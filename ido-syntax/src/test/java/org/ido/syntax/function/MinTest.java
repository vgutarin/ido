package org.ido.syntax.function;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ido.syntax.IOperator;
import org.ido.syntax.ITypeDescriptor;
import org.ido.syntax.Parser;
import org.ido.syntax.SyntaxException;
import org.ido.syntax.type.LongTypeDescriptor;
import org.ido.syntax.vo.HoldVoValue;
import org.junit.jupiter.api.Test;

class MinTest {
	
	private final Parser _parser;
	private final LongTypeDescriptor _longTypeDescriptor;
	
	public MinTest() throws SyntaxException {
		_longTypeDescriptor = LongTypeDescriptor.instance;
		_parser = new Parser(
				Arrays.asList(_longTypeDescriptor),
				new ArrayList<IOperator>()
		);
	}

	@Test
	void testIsApplicable() throws SyntaxException {
		Min min = new Min();
		List<ITypeDescriptor<?>> arguments = new ArrayList<ITypeDescriptor<?>>();
		assertNull(min.isApplicable(arguments));
		arguments.add(_parser.parse("12345").getTypeDescriptor());
		assertSame(_longTypeDescriptor, min.isApplicable(arguments));
		arguments.add(_parser.parse("1234589").getTypeDescriptor());
		assertSame(_longTypeDescriptor, min.isApplicable(arguments));
		arguments.add(null);
		assertNull(min.isApplicable(arguments));
	}

	@Test
	void testApply() throws SyntaxException {
		HoldVoValue minVo = new HoldVoValue(_parser.parse("3"));
		ArrayList<HoldVoValue> args = new ArrayList<HoldVoValue>();
		args.add(minVo);
		Min min = new Min();
		assertSame(minVo, min.apply(args));
		for(int i = 4; i < 7; ++i) {
			args.add(new HoldVoValue(_parser.parse(String.format("%d", i))));
			Collections.shuffle(args);
			
			assertSame(minVo, min.apply(args));
		}
		
		for(int i = 2; i > -1; --i) {
			minVo = new HoldVoValue(_parser.parse(String.format("%d", i)));
			args.add(minVo);
			Collections.shuffle(args);
			
			assertSame(minVo, min.apply(args));
		}
	}

}
