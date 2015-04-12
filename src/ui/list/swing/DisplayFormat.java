package ui.list.swing;

import java.io.IOException;

import taskList.Task;
import taskList.TaskManager.DISPLAY_MODE;

//@author A0117971Y

public class DisplayFormat {
	
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
	private static final String TASK_INFO_FILE_PATH_MSG = "Existing files: ";
	
	private static StringBuilder data = new StringBuilder();
	private static final String HTML_OPEN = "<html>";
	private static final String HTML_CLOSE = "</html>";
	private static final String HTML_BREAK = "<br>";
	private static final String HTML_FONT_INDEX = "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\"> %s </font>";
	private static final String HTML_FONT_TASKNAME = "<font size = \"6\" font face = \"Comic Sans Ms\"> %s </font><br>";
	private static final String HTML_FONT_TASK_DETAILS = "<font size = \"3\" font color = #363232> %s </font>";
	private static final String HTML_FONT_CLOSE = "</font>";
	private static final String HTML_FONT_VIEW_TASK_INFO = "<html><font size = \"6\" font face = \"Century Gothic\"><i><u> %s </u></i></font></html>";
	private static final String HTML_FONT_FEEDBACK_GUIDE_INFO = "<font color = #008000> %s </font>";
	private static final String HTML_FONT_OVERDUE = "<font size = \"3\" font color = #FF0000> %s </font>";
	private static final String HTML_FONT_FINISHED_INDEX = "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\"><s> %s </s></font>";
	private static final String HTML_FONT_FINISHED_TASKNAME = "<font size = \"6\" font face = \"Comic Sans Ms\"><s> %s </s></font><br>";
	private static final String HTML_FONT_FINISHED_DETAILS = "<font size = \"3\" font color = #363232><s> %s </s></font>";
	
	private static String index;
	private static String taskName;
	private static String date;
	private static String venue;
	private static String endDate;
	
	public static String getTaskInfoFormat (Task task, int i) throws NullPointerException, IOException {
		
		clearData();
		assert(data.length()==0);
		
		index = Integer.toString(i+1);
		taskName = task.getContent();
		date = task.getDateString();
		venue = task.getVenue();
		endDate = task.getDeadlineString();
		
		setVenueDate();
		
		data.append(HTML_OPEN);
		data.append(String.format(HTML_FONT_INDEX, index + ". ") + String.format(HTML_FONT_TASKNAME, taskName));
				
		if (!date.equals("---") && task.isOutOfDate()) {
			data.append(String.format(HTML_FONT_OVERDUE, "Date: " + date));
		}
		
		else {
			data.append(String.format(HTML_FONT_TASK_DETAILS, "Date: " + date));

		}
		
		if (endDate != null) {
			if (task.isOutOfDate()) {
				data.append(String.format(HTML_FONT_OVERDUE,"  BY: " + endDate));
			}		
			else {
				data.append(String.format(HTML_FONT_TASK_DETAILS, "  BY: " + endDate));	
			}
		}

		data.append(HTML_BREAK);		
		data.append(String.format(HTML_FONT_TASK_DETAILS, "Venue: " + venue));
		data.append(HTML_BREAK+HTML_CLOSE);
		
		return getData();
	}
	
	public static String getDeletedRowFormat(Task task, int i) throws NullPointerException, IOException {
		clearData();
		assert(data.length()==0);
		

		index = Integer.toString(i+1);
		taskName = task.getContent();
		date = task.getDateString();
		venue = task.getVenue();
		endDate = task.getDeadlineString();
		
		setVenueDate();
		
		data.append(HTML_OPEN);
		data.append(String.format(HTML_FONT_FINISHED_INDEX, index + ". ") + String.format(HTML_FONT_FINISHED_TASKNAME, taskName));
				
		if (!date.equals("---") && task.isOutOfDate()) {
			data.append(String.format(HTML_FONT_FINISHED_DETAILS, "Date: " + date));
		}
		
		else {
			data.append(String.format(HTML_FONT_FINISHED_DETAILS, "Date: " + date));

		}
		
		if (endDate != null) {
			if (task.isOutOfDate()) {
				data.append(String.format(HTML_FONT_FINISHED_DETAILS,"  BY: " + endDate));
			}		
			else {
				data.append(String.format(HTML_FONT_FINISHED_DETAILS, "  BY: " + endDate));	
			}
		}

		data.append(HTML_BREAK);		
		data.append(String.format(HTML_FONT_FINISHED_DETAILS, "Venue: " + venue));
		data.append(HTML_BREAK+HTML_CLOSE);
		
		return getData();
		
	}
	
	public static String getData() {
		return data.toString();
	}
	
	private static void setVenueDate() {
		
		if (venue == null || venue.equals("")) {
			venue = "---";
		}
		
		if (date == null || date.equals("")) {
			date = "---";
		}
	}
	
	@SuppressWarnings("finally")
	public static String getTaskInfoDetails() {
		DISPLAY_MODE mode = DISPLAY_MODE.TODO_TASKLIST;

		try {
			mode = UserInterface.BTM.getCurrentMode();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
						System.out.println("Continue after catch mode = " + mode);
			switch (mode) {

			case TODO_TASKLIST: PageHandler.isAtFilePage = false; 
			return String.format(HTML_FONT_VIEW_TASK_INFO,TASK_INFO_UNCOMPLETED_MSG);
			case SEARCH_LIST: PageHandler.isAtFilePage = false; 
			return String.format(HTML_FONT_VIEW_TASK_INFO,TASK_INFO_SEARCH_RESULT_MSG);
			case FINISHED_TASKLIST: PageHandler.isAtFilePage = false; 
			return String.format(HTML_FONT_VIEW_TASK_INFO,TASK_INFO_COMPLETED_MSG);
			case ALL_TASKLIST: PageHandler.isAtFilePage = false; 
			return String.format(HTML_FONT_VIEW_TASK_INFO,TASK_INFO_ALL_TASKS_MSG);
			case FILE_PATH: PageHandler.isAtFilePage = true; 
			return String.format(HTML_FONT_VIEW_TASK_INFO, TASK_INFO_FILE_PATH_MSG);

			default: PageHandler.isAtFilePage = false; 
			return String.format(HTML_FONT_VIEW_TASK_INFO,"undefined!");

			}
		}
	}
	
	public static String getPathInfoFormat(String path, int index) {
		clearData();
		data.append(HTML_OPEN);
		data.append(String.format(HTML_FONT_INDEX, (index+1) + ". ") + String.format(HTML_FONT_TASKNAME, path));
		data.append(HTML_CLOSE);
		
		return data.toString();
	}
	
	public static String getFeedbackGuideInfo() {
		clearData();
		
		data.append(HTML_OPEN);
		data.append(HTML_FONT_FEEDBACK_GUIDE_INFO);
		data.append(UserInterface.BTM.getLastFeedBack());	
		data.append(HTML_FONT_CLOSE);
		data.append(HTML_CLOSE);
		
		return data.toString();
	}
	
	public static void clearData() {
		data.setLength(0);
	}

}
