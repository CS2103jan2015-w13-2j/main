package parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestChecker {
	FormatChecker check = new FormatChecker();
	boolean result;
	
	@Test
	public void test1() {
		result = check.isArgumentsCorrect("");
		assertEquals(true, result);
	}
	@Test
	public void test2() {
		result = check.isArgumentsCorrect("add -d -d");
		assertEquals(false, result);
	}
	@Test
	public void test3() {
		result = check.isArgumentsCorrect("add -d -vv");
		assertEquals(false, result);
	}
	@Test
	public void test4() {
		result = check.isArgumentsCorrect("add -d -v -dd");
		assertEquals(true, result);
	}
}
