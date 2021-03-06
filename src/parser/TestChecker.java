package parser;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//@author A0119503M
public class TestChecker {
	private static final String EXCEPTION_NOTITLE = "no title inputed";
	private static final String EXCEPTION_INDEXILLEGAL = "the index you entered is illegal";
	private static final String EXCEPTION_NOINDEX = "you must enter an index";
	private static final String EXCEPTION_NULLPOINTER = "The command is null";
	private static final String FAIL = "no exception thrown";
	
	FormatChecker check;
	boolean result;
	
	@Before
	public void initChecker() {
		check = new FormatChecker();
	}
	
	@Test
	public void test1() {
		result = check.isArgumentsFormatCorrect("");
		assertEquals(true, result);
	}
	
	@Test
	public void test2() {
		result = check.isArgumentsFormatCorrect("add -d -d");
		assertEquals(false, result);
	}
	
	@Test
	public void test3() {
		result = check.isArgumentsFormatCorrect("add -d -vv");
		assertEquals(false, result);
	}
	
	@Test
	public void test4() {
		result = check.isArgumentsFormatCorrect("add -d -v -dd");
		assertEquals(true, result);
	}
	
	@Test
	public void test5() {
		try {
			result = check.isArgumentsFormatCorrect(null);
			fail(FAIL);
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
			assertTrue(e.getMessage().contains(EXCEPTION_NULLPOINTER));
		}
	}
	
	@After
	public void cleanUp() {
		check = null;
	}
}
