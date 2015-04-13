package ui.list.swing;

import java.io.IOException;

import taskList.Task;
import taskList.TaskManager.DISPLAY_MODE;

//@author A0117971Y

/**
 * This class generates the HTML formatting for items displayed
 * and returns a string of such formatting
 * @author A0117971Y
 *
 */

public class DisplayFormat {
	
	private static final String TASK_INFO_UNCOMPLETED_MSG = "Things to do: ";
	private static final String TASK_INFO_SEARCH_RESULT_MSG = "Search results: ";
	private static final String TASK_INFO_COMPLETED_MSG = "Completed Tasks: ";
	private static final String TASK_INFO_ALL_TASKS_MSG = "You are viewing all tasks";
	private static final String TASK_INFO_FILE_PATH_MSG = "Existing files: ";
	private static final String BY_STRING = "  BY: ";
	private static final String DATE_STRING = "Date: ";
	private static final String VENUE_STRING = "Venue: ";
	private static final String EMPTY_STRING = "---";
	
	private static StringBuilder data = new StringBuilder();
	private static final String HTML_OPEN = "<html>";
	private static final String HTML_CLOSE = "</html>";
	private static final String HTML_BREAK = "<br>";
	private static final String HTML_FONT_TASK_HEADER = "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\"> %s </font> <font size = \"6\" font face = \"Comic Sans Ms\"> %s </font><br>";
	private static final String HTML_FONT_TASK_DETAILS = "<font size = \"3\" font color = #363232> %s </font>";
	private static final String HTML_FONT_VIEW_TASK_INFO = "<html><font size = \"6\" font face = \"Century Gothic\"><i><u> %s </u></i></font></html>";
	private static final String HTML_FONT_FEEDBACK_GUIDE_INFO = "<font color = #008000> %s </font>";
	private static final String HTML_FONT_OVERDUE = "<font size = \"3\" font color = #FF0000> %s </font>";
	private static final String HTML_FONT_FINISHED_TASK_HEADING = "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\"><s> %s </s></font><font size = \"6\" font face = \"Comic Sans Ms\"><s> %s </s></font><br>";
	private static final String HTML_FONT_FINISHED_DETAILS = "<font size = \"3\" font color = #363232><s> %s </s></font>";
	
	private static String index;
	private static String taskName;
	private static String date;
	private static String venue;
	private static String endDate;
	
	public static String getTaskInfoFormat (Task task, int i) throws NullPointerException, IOException {
		clearData();
		
		index = Integer.toString(i+1);
		taskName = task.getContent();
		date = task.getDateString();
		venue = task.getVenue();
		endDate = task.getDeadlineString();
		
		setVenueDate();
		data.append(HTML_OPEN);
		data.append(String.format(HTML_FONT_TASK_HEADER,index+". ", taskName));
		setDate(task, date);
		setEndDate(task, endDate);
		data.append(HTML_BREAK);	
		setTaskVenue(HTML_FONT_TASK_DETAILS, venue);
		data.append(HTML_BREAK+HTML_CLOSE);
		
		return getData();
	}
	
	private static void setTaskVenue(String format, String venue) {
		data.append(String.format(format,VENUE_STRING + venue));
	}
	
	private static boolean isValidOutOfDate (Task task, String date) throws NullPointerException, IOException {
		return !date.equals(EMPTY_STRING) && task.isOutOfDate();
	}
	
	private static void setDate(Task task, String date) throws NullPointerException, IOException {
		if (isValidOutOfDate(task,date)) {
			data.append(String.format(HTML_FONT_OVERDUE, DATE_STRING + date));
		}	
		else {
			data.append(String.format(HTML_FONT_TASK_DETAILS, DATE_STRING + date));

		}
	}
	
	private static void setEndDate (Task task, String endDate) throws NullPointerException, IOException {
		if (endDate != null) {
			if (task.isOutOfDate()) {
				data.append(String.format(HTML_FONT_OVERDUE, BY_STRING + endDate));
			}		
			else {
				data.append(String.format(HTML_FONT_TASK_DETAILS, BY_STRING + endDate));	
			}
		}
	}
	
	public static String getDeletedRowFormat(Task task, int i) throws NullPointerException, IOException {
		clearData();

		index = Integer.toString(i+1);
		taskName = task.getContent();
		date = task.getDateString();
		venue = task.getVenue();
		endDate = task.getDeadlineString();

		setVenueDate();
		data.append(HTML_OPEN);
		data.append(String.format(HTML_FONT_FINISHED_TASK_HEADING, index + ". ", taskName));
		data.append(String.format(HTML_FONT_FINISHED_DETAILS, DATE_STRING + date));

		if (endDate != null) {
			data.append(String.format(HTML_FONT_FINISHED_DETAILS, BY_STRING + endDate));	
		}

		data.append(HTML_BREAK);		
		setTaskVenue(HTML_FONT_FINISHED_DETAILS,venue);
		data.append(HTML_BREAK+HTML_CLOSE);

		return getData();
	}

	public static String getData() {
		return data.toString();
	}
	
	private static void setVenueDate() {
		if (venue == null || venue.equals("")) {
			venue = EMPTY_STRING;
		}
		
		if (date == null || date.equals("")) {
			date = EMPTY_STRING;
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
		data.append(String.format(HTML_FONT_TASK_HEADER,(index+1)+". ", path));
		data.append(HTML_CLOSE);
		
		return data.toString();
	}
	
	public static String getFeedbackGuideInfo() {
		clearData();
		
		data.append(HTML_OPEN);
		data.append(String.format(HTML_FONT_FEEDBACK_GUIDE_INFO, UserInterface.BTM.getLastFeedBack()));
		data.append(HTML_CLOSE);
		
		return data.toString();
	}
	
	public static void clearData() {
		data.setLength(0);
	}
}
