package ui.list.swing;

//@author A0117971Y

/**
 * Unit Tests to check the accuracy of HTML formatting generated
 */

/*
 * Testing of GUI interface can be done by performing black-box testing. That is, just running the program without looking at any code.
 * In order to be Effective and Efficient, we have to make use of some testing heuristics such as Equivalence partitioning, boundary value analysis (bva)
 * and combining multiple inputs.
 * 
 * For testing of GUI, the most relevant testing heuristics would be combining of multiple inputs.
 * Because user are required to enter task description, task date(optional), task venue (optional), etc...
 * We can test input in such a way that only one invalid input per case 
 * e.g. add <valid task name> -d <invalid date> -v <valid venue> or "add <valid task name> -d <valid date> -v <invalid venue>
 * In this case, we need to consider the factor, whether the entire operation will be voided or only valid input will be registered. 
 * 
 */

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import taskList.Task;

public class UnitTestForDisplaySetting {

	@Test
	public void test() throws NullPointerException, IOException {
//		fail("Not yet implemented");
		
		Task test1 = new Task("task1","2015/03/24", "", "venue1");
		Task test2 = new Task("task2");
		Task test3 = new Task("task3", "2015/03/24", "", null);
		Task test4 = new Task("task4", "","","venue4");
		
		
		
		String result1 = "<html>" + "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\">" + "1. " + "</font>" + 
		"<font size = \"6\" font face = \"Arial\">" + " task1 " + "</font>" + "<br>" + "<font color = #848482>" + " Date: " + test1.getDateString() + "</font>" + "<br>"
				+ "<font color = #848482>" + "Venue: " + " venue1"+ "</font>" + "<br>" + "</html>";
		
		String result2 = "<html>" + "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\">" + "2. " + "</font>" + 
		"<font size = \"6\" font face = \"Arial\">" + " task2 " + "</font>" + "<br>" + "<font color = #848482>" + " Date: " + test2.getDateString() + "</font>" + "<br>"
				+ "<font color = #848482>" + "Venue: "+ " null" + "</font>" + "<br>" + "</html>";
		
		String result3 = "<html>" + "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\">" + "3. " + "</font>" + 
		"<font size = \"6\" font face = \"Arial\">" + " task3 " + "</font>" + "<br>" + "<font color = #848482>" + " Date: " + test3.getDateString() + "</font>" + "<br>"
				+ "<font color = #848482>" + "Venue: " + " null "+ "</font>" + "<br>" + "</html>";
		
		String result4 = "<html>" + "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\">" + "4. " + "</font>" + 
		"<font size = \"6\" font face = \"Arial\">" + " task4 " + "</font>" + "<br>" + "<font color = #848482>" + " Date: " + test4.getDateString() + "</font>" + "<br>"
				+ "<font color = #848482>" + "Venue: " + " venue4 "+ "</font>" + "<br>" + "</html>";
		
		String output1 = DisplayFormat.getTaskInfoFormat(test1,0);
		String output2 = DisplayFormat.getTaskInfoFormat(test2,1);
		String output3 = DisplayFormat.getTaskInfoFormat(test3,2);
		String output4 = DisplayFormat.getTaskInfoFormat(test4,3);		

		
		assertEquals(result1,output1);
		assertEquals(result2,output2);
		assertEquals(result3,output3);
		assertEquals(result4,output4);

		
		
	}

}
