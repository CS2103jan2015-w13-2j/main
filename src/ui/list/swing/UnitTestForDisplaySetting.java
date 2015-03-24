package ui.list.swing;

import static org.junit.Assert.*;

import org.junit.Test;

import taskList.Task;

public class UnitTestForDisplaySetting {

	@Test
	public void test() {
//		fail("Not yet implemented");
		
		Task test1 = new Task("task1","2015/03/24", "", "venue1");
		Task test2 = new Task("task2");
		Task test3 = new Task("task3", "2015/03/24", "", "");
		Task test4 = new Task("task4", "","","venue4");
		
		
		
		String result1 = "<html>" + "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\"> " + "1. " + "</font>" + 
		"<font size = \"6\" font face = \"Arial\">" + "task1" + "</font>" + "<br>" + "<font color = #848482>" + "Date:" + test1.getDateString() + "</font>" + "<br>"
				+ "<font color = #848482>" + "Venue:" + "venue1"+ "</font>" + "<br>" + "</html>";
		
		String result2 = "<html>" + "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\"> " + "2. " + "</font>" + 
		"<font size = \"6\" font face = \"Arial\">" + "task2" + "</font>" + "<br>" + "</html>";
		
		String result3 = "<html>" + "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\"> " + "3. " + "</font>" + 
		"<font size = \"6\" font face = \"Arial\">" + "task3" + "</font>" + "<br>" + "<font color = #848482>" + "Date:" + test3.getDateString() + "</font>" + "<br>" + "</html>";
		
		String result4 = "<html>" + "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\"> " + "4. " + "</font>" + 
		"<font size = \"6\" font face = \"Arial\">" + "task1" + "</font>" + "<br>"
				+ "<font color = #848482>" + "Venue:" + "venue4"+ "</font>" + "<br>" + "</html>";
		
		String output1 = new DisplaySetting(test1,0).getData();
		String output2 = new DisplaySetting(test2,1).getData();
		String output3 = new DisplaySetting(test3,2).getData();
		String output4 = new DisplaySetting(test4,3).getData();
		
		assertEquals(result1,output1);
		assertEquals(result2,output2);
		assertEquals(result3,output3);
		assertEquals(result4,output4);

		
		
	}

}
