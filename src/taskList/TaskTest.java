package taskList;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import parser.Parser;


public class TaskTest {	
	Parser myParser = new Parser();


	@Test
	public void testContainKeyWord() throws NullPointerException, IOException {
		Date date1 = myParser.getDate("add -d 2015-04-12 13:00");
		Task task1 = new Task("task1",  date1, date1, "lt27");
		assertEquals("contain function", false, task1.containKeyWord("2016")); 
		assertEquals("contain function", true, task1.containKeyWord("2015"));
	}
	
	@Test
	public void testOutofDate() throws NullPointerException, IOException {
		Date date1 = myParser.getDate("add -d 2014-04-12 13:00");
		Date date2 = myParser.getDate("add -d 2015-04-13 13:00");
		Task task1 = new Task("task1",  date1, date1, "lt27");
		Task task2 = new Task("task2",  date2, date2, "lt27");
		assertEquals("contain function", true, task1.isOutOfDate()); 
		assertEquals("contain function", false, task2.isOutOfDate());
	}


}