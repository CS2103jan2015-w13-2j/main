package parser;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class testParser {

	private static final int OPERATION_UNKNOWN = 0;
	private static final int OPERATION_ADD = 1;
	private static final int OPERATION_DELETE = 2;
	private static final int OPERATION_CLEAR = 3;
	private static final int OPERATION_DISPLAY = 4;
	private static final int OPERATION_EXIT = 5;
	private static final int OPERATION_MODIFY = 6;
	
	@Test
	public void test() {
		Parser p = new Parser();
		
		//test Arguments
		boolean test;
		test = p.isArgumentsCorrect("add have lessons -d tomorrow -v school");
		assertEquals(true, test);
		test = p.isArgumentsCorrect("add have lessons at 5-505 -d this afternoon");
		assertEquals(true, test);
		test = p.isArgumentsCorrect("add this is for fun -cs dou wo");
		assertEquals(false, test);
		
		
		
	}
	
	
}
