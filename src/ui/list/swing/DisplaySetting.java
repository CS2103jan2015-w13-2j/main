package ui.list.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Timer;

import taskList.Task;

/**
 * 
 * @author A0117971Y
 *
 */

public class DisplaySetting {
	
	//mode == 0 means the result shown in screen is taskList,
		//mode == 1 means the result shown in screen is searchResult
		//mode == 2 means the result shown in screen is completedTaskList
		//mode == 3 means the result shown in screen is all task (both finished and unfinished)
	
	private static final int TASK_INFO_UNCOMPLETED = 0;
	private static final int TASK_INFO_SEARCH_RESULT = 1;
	private static final int TASK_INFO_COMPLETED = 2;
	private static final int TASK_INFO_ALL_TASKS = 3;
	private static final String TASK_INFO_UNCOMPLETED_MSG = "Things to do: ";
	private static final String TASK_INFO_SEARCH_RESULT_MSG = "Search results: ";
	private static final String TASK_INFO_COMPLETED_MSG = "Completed Tasks: ";
	private static final String TASK_INFO_ALL_TASKS_MSG = "You are viewing all tasks";
	
	private static StringBuilder data = new StringBuilder();
	private static final String HTML_OPEN = "<html>";
	private static final String HTML_CLOSE = "</html>";
	private static final String HTML_BREAK = "<br>";
	private static final String HTML_FONT_INDEX = "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\">";
	private static final String HTML_FONT_TASKNAME = "<font size = \"6\" font face = \"Arial\">";
	private static final String HTML_FONT_TASK_DETAILS = "<font size = \"3\" font color = #363232>";
	private static final String HTML_FONT_CLOSE = "</font>";
	private static final String HTML_FONT_VIEW_TASK_INFO = "<font size = \"6\" font face = \"HanziPen TC\">";
	private static final String HTML_FONT_FEEDBACK_GUIDE_INFO = "<font color = #008000>";
	private static final String HTML_FONT_OVERDUE = "<font size = \"3\" font color = #FF0000>";
	
	public DisplaySetting(Task task, int i) throws NullPointerException, IOException {
		
		clearData();
		assert(data.length()==0);
		
		String index = Integer.toString(i+1);
		String taskName = task.getContent();
		String date = task.getDateString();
		String venue = task.getVenue();
		String endDate = task.getDeadlineString();
		
		data.append(HTML_OPEN + HTML_FONT_INDEX + index + ". " + HTML_FONT_CLOSE + HTML_FONT_TASKNAME + taskName + HTML_FONT_CLOSE + HTML_BREAK);
		
		if (date == null) {
			date = "---";
		}
		
		
		if ((date != null || endDate !=null) && task.isOutOfDate()) {
			System.out.println("is out of date");
			data.append(HTML_FONT_OVERDUE + "Date:" + date + HTML_FONT_CLOSE);
				if (endDate != null) {
					data.append(HTML_FONT_OVERDUE + " BY: " + endDate + HTML_FONT_CLOSE );
				}
			data.append(HTML_FONT_CLOSE);
		}
		
		else {
			System.out.println("is on time");

			data.append(HTML_FONT_TASK_DETAILS + "Date:" + date + HTML_FONT_CLOSE );
			
			if (endDate != null) {
				data.append(HTML_FONT_TASK_DETAILS + " BY: " + endDate + HTML_FONT_CLOSE );
			}
		}
				
		data.append(HTML_BREAK);
		
		if (venue == null || venue.equals("")) {
			venue = "---";
		}

		data.append(HTML_FONT_TASK_DETAILS + "Venue:" + venue + HTML_FONT_CLOSE + HTML_BREAK);

		
		data.append(HTML_CLOSE);
		
	}
	
	public String getData() {
		return data.toString();
	}
	
	public static String getViewTaskInfo() {
		clearData();
		
		data.append(HTML_OPEN);
		data.append(HTML_FONT_VIEW_TASK_INFO);
		data.append(getTaskInfoDetails());
		data.append(HTML_FONT_CLOSE);
		data.append(HTML_CLOSE);	
		
		return data.toString();
	}

	@SuppressWarnings("finally")
	private static String getTaskInfoDetails() {
		int mode = 0;

		try {
			mode = UserInterface.BTL.getCurrentMode();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
//			System.out.println("Continue after catch mode = " + mode);
			switch (mode) {

			case TASK_INFO_UNCOMPLETED: return TASK_INFO_UNCOMPLETED_MSG;
			case TASK_INFO_SEARCH_RESULT: return TASK_INFO_SEARCH_RESULT_MSG;
			case TASK_INFO_COMPLETED: return TASK_INFO_COMPLETED_MSG;
			case TASK_INFO_ALL_TASKS: return TASK_INFO_ALL_TASKS_MSG;
			default: return "UNDEFINED MODE";
			
			}
		}
	}
	
	public static String getFeedbackGuideInfo() {
		clearData();
		
		data.append(HTML_OPEN);
		data.append(HTML_FONT_FEEDBACK_GUIDE_INFO);
		data.append(UserInterface.BTL.getLastFeedBack());	
		data.append(HTML_FONT_CLOSE);
		data.append(HTML_CLOSE);
		
		return data.toString();
	}
	
	public static void clearData() {
		data.setLength(0);
	}

}
