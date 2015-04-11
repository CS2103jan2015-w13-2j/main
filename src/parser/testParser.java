package parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Parser.Operation;

public class testParser {
	
	private static final String EXCEPTION_NOTITLE = "no title inputed";
	private static final String EXCEPTION_INDEXILLEGAL = "the index you entered is illegal";
	private static final String EXCEPTION_NOINDEX = "you must enter an index";
	private static final String EXCEPTION_NULLPOINTER = "The command is null";
	private static final String FAIL = "no exception thrown";
	
	private static final String FEEDBACK_ADD = 
			"Tip: add<task> -d<time> -v<venue> to add task with date & venue";
	private static final String FEEDBACK_DELETE = "Tip: delete<index> to delete a task";
	private static final String FEEDBACK_MODIFY = 
			"Tip: modify<index> <new title> -d<new time> -v<new venue> to modify task";
	private static final String FEEDBACK_SORT = "Tip: sort<time/venue/title> to sort tasks";
	private static final String FEEDBACK_SEARCH = "Tip: search<title/time/venue> to search tasks";
	private static final String FEEDBACK_COMPLETE = "Tip: complete<index> to mark a task completed";
	private static final String FEEDBACK_IMPORT = "Tip: import<index/path> to import a schedule file";
	private static final String FEEDBACK_EXPORT = "Tip: export<index/path> to save schedul to a file";
	
	private boolean testBoolean;
	private Operation testOperation;
	private int testNumber;
	private String testString;
	private Date testDate;
	private Date answerDate;
	private ArrayList<String> testArrayList;
	private Parser p;
	SimpleDateFormat sdf;
	
	@Before
	public void initParser() {
		p = new Parser();
		sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}
	
	@Test
	public void testGetOperation() {
		try {
			testOperation = p.getOperation(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		testOperation = p.getOperation("");
		assertEquals(Operation.UNKNOW, testOperation);
		testOperation = p.getOperation("add aaaaaa");
		assertEquals(Operation.ADD, testOperation);	
	}
	
	@Test
	public void testIsArgumentsCorrect() {
		try {
			testBoolean = p.isArgumentsCorrect(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		testBoolean = p.isArgumentsCorrect("add have lessons -d tomorrow -v school");
		assertEquals(true, testBoolean);
		testBoolean = p.isArgumentsCorrect("add have lessons at 5-505 -d this afternoon");
		assertEquals(true, testBoolean);
		testBoolean = p.isArgumentsCorrect("add this is for fun -cs dou wo");
		assertEquals(false, testBoolean);
		testBoolean = p.isArgumentsCorrect("add have lessons -d tomorrow -d this Monday");
		assertEquals(false, testBoolean);
	}
	
	@Test
	public void testGetIndex() {
		try {
			testNumber = p.getIndex(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		try {
			testNumber = p.getIndex("modify 7 -d the day after tomorrow");
			assertEquals(7, testNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			testNumber = p.getIndex("modify -d the day after tomorrow");
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
			assertTrue(e.getMessage().contains(EXCEPTION_NOTITLE));
		}
	}
	
	@Test
	public void testGetNewTitle() {
		try {
			testString = p.getNewTitle(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		try {
			testString = p.getNewTitle("modify 8 go to school -d tomorrow");
			assertEquals("go to school", testString);
			testString = p.getNewTitle("modify 8   -d tomorrow");
			assertEquals(null, testString);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTitle() {
		try {
			testString = p.getTitle(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		try {
			testString = p.getTitle("add testtask -v school");
			assertEquals("testtask", testString);
			testString = p.getTitle("add    -v dummy place");
			assertEquals(null, testString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetVenue() {
		try {
			testString = p.getVenue(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		testString = p.getVenue("add testTask -v icube");
		assertEquals("icube", testString);
		testString = p.getVenue("add test Task");
		assertEquals(null, testString);
	}
	
	@Test
	public void testGetDate() {
		try {
			testDate = p.getDate(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		try {
			testDate = p.getDate("add testTask -d");
			assertEquals(null, testDate);
			testDate = p.getDate("add testTask -d 2015-12-10 13:00");
			assertEquals(sdf.parse("2015-12-10 13:00"), testDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetDeadline() {
		try {
			testDate = p.getDeadline(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		try {
			testDate = p.getDeadline("add testTask -dd");
			assertEquals(null, testDate);
			testDate = p.getDeadline("add testTask -dd 2015-12-10 13:00");
			assertEquals(sdf.parse("2015-12-10 13:00"), testDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAutoFill() {
		try {
			testString = p.autoFill(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		testString = p.autoFill("a");
		assertEquals("add", testString);
		testString = p.autoFill("z");
		assertEquals(null, testString);
		testString = p.autoFill("s");
		assertEquals(null, testString);
	}
	
	@Test
	public void testProvideTips() {
		try {
			testString = p.provideTips(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		testString = p.provideTips("someknownmethod jajod");
		assertEquals(null, testString);
		testString = p.provideTips("add go ");
		assertEquals(FEEDBACK_ADD, testString);
	}
	
	@After
	public void cleanUp() {
		p = null;
		sdf = null;
	}
	
}
