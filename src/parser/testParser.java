package parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testParser {
	
	private static final int OPERATION_UNKNOWN = 0;
	private static final int OPERATION_ADD = 1;
	private static final int OPERATION_DELETE = 2;
	private static final int OPERATION_CLEAR = 3;
	private static final int OPERATION_DISPLAY = 4;
	private static final int OPERATION_EXIT = 5;
	private static final int OPERATION_MODIFY = 6;
	private static final int OPERATION_UNDO = 7;
	private static final int OPERATION_REDO = 8;
	private static final int OPERATION_SORT = 9;
	private static final int OPERATION_SEARCH = 10;
	private boolean testBoolean;
	private int testNumber;
	private String testString;
	private Parser p;
	
	@Before
	public void initParser() {
		p = new Parser();
	}
	
	@Test
	public void testArguments() {
		//test Arguments normal
		testBoolean = p.isArgumentsCorrect("add have lessons -d tomorrow -v school");
		assertEquals(true, testBoolean);
		//test Arguments some string with '-'
		testBoolean = p.isArgumentsCorrect("add have lessons at 5-505 -d this afternoon");
		assertEquals(true, testBoolean);
		//test Arguments unknown arguments
		testBoolean = p.isArgumentsCorrect("add this is for fun -cs dou wo");
		assertEquals(false, testBoolean);
	}
	
	@Test
	public void testGetIndex() {
		//test getIndex normal
		try {
			testNumber = p.getIndex("modify 7 -d the day after tomorrow");
			assertEquals(7, testNumber);
		} catch (Exception e) {
			fail("unexpected exception");
		}
		//test getIndex no index
		try {
			testNumber = p.getIndex("modify -d the day after tomorrow");
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
			assertTrue(e.getMessage().contains("you must enter an index"));
		}
		//test getIndex index not digits
		try {
			testNumber = p.getIndex("modify sd -d the day after tomorrow");
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
			assertTrue(e.getMessage().contains("the index you entered is illegal"));
		}
	}
	
	@Test
	public void testGetNewTitle() {
		//test getNewTitle normal
		testString = p.getNewTitle("modify 8 go to school -d tomorrow");
		assertEquals("go to school", testString);
		//test getNewTitle no new title
		testString = p.getNewTitle("modify 8   -d tomorrow");
		assertEquals(null, testString);
	}
	
	@After
	public void cleanUp() {
		p = null;
	}
	
}
