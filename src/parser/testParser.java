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
		Date output = null;
		output = p.getDeadline("add school -dd 2015-03-25 3:00 pm");	
		System.out.println(output);
		
		
	}
	
	
}
