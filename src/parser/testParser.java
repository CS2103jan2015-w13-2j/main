package parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testParser {
	
	private static final String EXCEPTION_NOTITLE = "no title inputed";
	private static final String EXCEPTION_INDEXILLEGAL = "the index you entered is illegal";
	private static final String EXCEPTION_NOINDEX = "you must enter an index";
	private static final String EXCEPTION_NULLPOINTER = "The command is null";
	
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
	private ArrayList<String> testArrayList;
	private Parser p;
	
	@Before
	public void initParser() {
		p = new Parser();
	}
	/*
	@Test
	public void testGetOperation() {
		testNumber = p.getOperation(null);
		
	}*/
	
	@Test
	public void testArguments() {
		//test Arguments null
		try {
			testBoolean = p.isArgumentsCorrect(null);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
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
		//test getIndex null
		try {
			testNumber = p.getIndex(null);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		//test getIndex normal
		try {
			testNumber = p.getIndex("modify 7 -d the day after tomorrow");
			assertEquals(7, testNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//test getIndex no index
		try {
			testNumber = p.getIndex("modify -d the day after tomorrow");
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
			assertTrue(e.getMessage().contains(EXCEPTION_NOTITLE));
		}
		//test getIndex index not digits
		try {
			testNumber = p.getIndex("modify sd -d the day after tomorrow");
		} catch (Exception e) {
			assertTrue(e instanceof IOException);
			assertTrue(e.getMessage().contains(EXCEPTION_INDEXILLEGAL));
		}
	}
	
	@Test
	public void testGetNewTitle() {
		//test getNewTitle null
		try {
			testString = p.getNewTitle(null);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
		//test getNewTitle normal
		try {
			testString = p.getNewTitle("modify 8 go to school -d tomorrow");
			assertEquals("go to school", testString);
			//test getNewTitle no new title
			testString = p.getNewTitle("modify 8   -d tomorrow");
			assertEquals(null, testString);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRegexAdd(){
		Scanner in = new Scanner(System.in);
		String input = in.next();
		testArrayList = p.check(input);
				//"\"hahahahha\"at school on Monday");
		printall(testArrayList);
		//assertEquals(true, testBoolean);
		testArrayList = p.check("\"hahahah");
		assertEquals(null, testArrayList);
	}
	
	private void printall(ArrayList<String> list) {
		System.out.println("the components are:");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i+": "+list.get(i));
		}
	}
	
	@After
	public void cleanUp() {
		p = null;
	}
	
}
