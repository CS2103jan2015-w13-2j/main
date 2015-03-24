package parser;

import static org.junit.Assert.*;

import java.io.IOException;
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
		boolean testBoolean;
		int testNumber;
		
		//test Arguments
		testBoolean = p.isArgumentsCorrect("add have lessons -d tomorrow -v school");
		assertEquals(true, testBoolean);
		testBoolean = p.isArgumentsCorrect("add have lessons at 5-505 -d this afternoon");
		assertEquals(true, testBoolean);
		testBoolean = p.isArgumentsCorrect("add this is for fun -cs dou wo");
		assertEquals(false, testBoolean);
		
		//test getIndex
		try {
			testNumber = p.getIndex("modify 7 -d the day after tomorrow");
			assertEquals(7, testNumber);
			testNumber = p.getIndex("modfi 7");
			assertEquals(7, testNumber);
			testNumber = p.getIndex("update 7s7");
		} catch (NullPointerException e) {
			System.out.println("fail to get index");
		} catch (IOException e) {
			System.out.println("index format incorrect");
		}
		
	}
	
	
}
