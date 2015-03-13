package parser;

import static org.junit.Assert.*;

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
	public void testOperation() {
		Parser p = new Parser();
		int output = p.getOperation("add hahhahah");
		assertEquals(OPERATION_ADD, output);
		
	}
	
	@Test
	public void testVenue() {
		
	}
	
	@Test
	public void testDate() {
		
	}
	
	@Test
	public void testDeadline() {
		
	}
	
}
