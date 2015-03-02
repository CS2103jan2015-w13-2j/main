package Parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestGetVenue {
	private static final int OPERATION_UNKNOWN = 0;
	private static final int OPERATION_ADD = 1;
	private static final int OPERATION_DELETE = 2;
	private static final int OPERATION_CLEAR = 3;
	private static final int OPERATION_DISPLAY = 4;
	private static final int OPERATION_EXIT = 5;
	private static final int OPERATION_MODIFY = 6;
	
	@Test
	public void test() {
		BasicParser.initParser();
		String output = null;
		String command = null;
		Integer out = null;
		
		command = "add study -v my room -t 12:00 -dd tomorrow -d 13 May";
		out = BasicParser.getOperation(command);
		assertEquals(Integer.valueOf(OPERATION_ADD), out);
		output = BasicParser.getDeadline(command);
		assertEquals("tomorrow", output);
		output = BasicParser.getDate(command);
		assertEquals("13 May", output);
		output = BasicParser.getTime(command);
		assertEquals("12:00", output);
		output = BasicParser.getVenue(command);
		assertEquals("my room", output);
		
	}

}
