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
	private static final String BY_STRING = "<b>  BY: </b>";
	private static final String DATE_STRING = "<b>Date: </b>";
	private static final String VENUE_STRING = "<b>Venue: </b>";
	private static final String TIME_STRING = "<b>Time: </b>";
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
	private static String time;
	private static String endTime;

	
	public static String getTaskInfoFormat (Task task, int i) throws NullPointerException, IOException {
		clearData();
		
		index = Integer.toString(i+1);
		taskName = task.getContent();
		date = DateTimeSetting.getDate(task);
		endDate = DateTimeSetting.getEndDate(task);
		time = DateTimeSetting.getTime(task);
		endTime = DateTimeSetting.getEndTime(task);
		venue = task.getVenue();

		
		setVenueDate();
		data.append(HTML_OPEN);
		data.append(String.format(HTML_FONT_TASK_HEADER,index+". ", taskName));
		setDate(task, date, endDate);
		setTime(task, time, endTime);
		data.append(HTML_BREAK);	
		setTaskVenue(HTML_FONT_TASK_DETAILS, venue);
		data.append(HTML_BREAK+HTML_CLOSE);
		
		return getData();
	}
	
//	private static boolean isEmptyTime(String time) {
//		if (time.trim().equals(EMPTY_TIME)) {
//			return true;
//		}
//		return false;
//	}
//	
//	private static String getTime(Task task) {
//		String time = trimTime(task.getDateString());
//		
//		return time;
//	}
//	
//	private static String getDate(Task task) {
//		String date = trimDate(task.getDateString());
//		
//		return date;
//	}
//	
//	private static String getEndDate(Task task) {
//		String endDate = trimDate(task.getDeadlineString());
//		
//		return endDate;
//	}
//	
//	private static String getEndTime(Task task) {
//		String endTime = trimTime(task.getDeadlineString());
//		
//		return endTime;
//	}
//	
//	private static String trimDate(String date) {
//
//		String trimDate = EMPTY_STRING;
//
//		if (date!= null && !date.equals(EMPTY_STRING)) {
//			trimDate = date.substring(0, date.indexOf(":")-2);
//			System.out.println("Date Trimmed: " + trimDate);
//		}
//		
//		return trimDate;
//	}

//	private static String trimTime(String date) {
//		String time = EMPTY_TIME;
//
//		if (date!= null && !date.equals(EMPTY_STRING)) {
//			time = date.substring(date.indexOf(":")-2, date.length());	
//			return timeFormat(time);
//		}
//
//		return time;
//	}

	private static void setTaskVenue(String format, String venue) {
		data.append(String.format(format,VENUE_STRING + venue));
	}
	
	private static boolean isValidOutOfDate (Task task, String date, String endDate) throws NullPointerException, IOException {
		return (!date.equals(EMPTY_STRING) || !endDate.equals(EMPTY_STRING)) && task.isOutOfDate();
	}
	
	private static void setDate(Task task, String date, String endDate) throws NullPointerException, IOException {	
		date = DateTimeSetting.dateFormat(date);
		endDate = DateTimeSetting.dateFormat(endDate);
		
		if (isValidOutOfDate(task,date, endDate)) {
			
			if (date.equals(EMPTY_STRING) && endDate.equals(EMPTY_STRING)) {
				data.append(String.format(HTML_FONT_OVERDUE, DATE_STRING + date));
			}
			
			else if (!date.equals(EMPTY_STRING) && endDate.equals(EMPTY_STRING))  {
				data.append(String.format(HTML_FONT_OVERDUE, DATE_STRING + date));
			}
			
			else if (date.equals(EMPTY_STRING) && !endDate.equals(EMPTY_STRING)) {
				data.append((String.format(HTML_FONT_OVERDUE, BY_STRING + endDate)));
			}
			
			else {
				data.append(String.format(HTML_FONT_OVERDUE, DATE_STRING + date) + " - " + endDate);
			}
		}	
		
		else {
			
			if (date.equals(EMPTY_STRING) && endDate.equals(EMPTY_STRING)) {
				data.append(String.format(HTML_FONT_TASK_DETAILS, DATE_STRING + date));
			}
			
			else if (!date.equals(EMPTY_STRING) && endDate.equals(EMPTY_STRING))  {
				data.append(String.format(HTML_FONT_TASK_DETAILS, DATE_STRING + date));
			}
			
			else if (date.equals(EMPTY_STRING) && !endDate.equals(EMPTY_STRING)) {
				data.append((String.format(HTML_FONT_TASK_DETAILS, BY_STRING + endDate)));
			}
			
			else {
				data.append(String.format(HTML_FONT_TASK_DETAILS, DATE_STRING + date) + " - " + endDate);
			}			
		}
	}
	
	private static void setTime(Task task, String time, String endTime) throws NullPointerException, IOException {
		if (!DateTimeSetting.isEmptyTime(time) && task.isOutOfDate()) {
			data.append(String.format(HTML_FONT_OVERDUE,TIME_STRING + time ));
			
			if (!DateTimeSetting.isEmptyTime(endTime)) {
				data.append(String.format(HTML_FONT_OVERDUE, " - " + endTime));
			}
		}
		
		else if (!DateTimeSetting.isEmptyTime(time)) {
			data.append(String.format(HTML_FONT_TASK_DETAILS,TIME_STRING + time ));
			
			if (!DateTimeSetting.isEmptyTime(endTime)) {
				data.append(String.format(HTML_FONT_TASK_DETAILS, " - " + endTime));
			}
		}
	}
	
//	private static String dateFormat (String date) {
//		String formattedDay = EMPTY_STRING;
//		
//		if (!date.equals(EMPTY_STRING)) {
//			String tokens[] = date.split("-");
//			String year = tokens[0];
//			String mth = tokens[1];
//			String day = tokens[2];
//			
//			switch (mth) {
//			case "01": mth = "Jan"; break;
//			case "02": mth = "Feb"; break;
//			case "03": mth = "Mar"; break;
//			case "04": mth = "April"; break;
//			case "05": mth = "May"; break;
//			case "06": mth = "Jun"; break;
//			case "07": mth = "July"; break;
//			case "08": mth = "Aug"; break;
//			case "09": mth = "Sept"; break;
//			case "10": mth = "Oct"; break;
//			case "11": mth = "Nov"; break;
//			case "12": mth = "Dec"; break;				
//			}
//			
//			formattedDay = day+ " " + mth + " " + year;
//		}
//		
//		return formattedDay;
//	}
	
	
//	private static String timeFormat(String time) {
//		if (!isEmptyTime(time)) {
//			String tokens[] = time.split(":");
//			String hrs = tokens[0];
//			String min = tokens[1];
//			String day;
//			int newHr = 0;
//			
//			if (Integer.parseInt(hrs) >= 12) {
//				newHr = Integer.parseInt(hrs);
//				day = "PM";
//
//				if (newHr > 12) {
//					newHr = newHr - 12;
//				}
//			}
//			
//			else {
//				day = "AM";
//			}
//			
//			return newHr + ":" + min + day;
//		}
//		return time;
//	}
	
	public static String getDeletedRowFormat(Task task, int i) throws NullPointerException, IOException {
		clearData();

		index = Integer.toString(i+1);
		taskName = task.getContent();
		date = DateTimeSetting.getDate(task);
		endDate = DateTimeSetting.getEndDate(task);
		time = DateTimeSetting.getTime(task);
		endTime = DateTimeSetting.getEndTime(task);
		venue = task.getVenue();

		
		setVenueDate();
		data.append(HTML_OPEN);
		data.append(String.format(HTML_FONT_FINISHED_TASK_HEADING, index + ". ", taskName));
		data.append(String.format(HTML_FONT_FINISHED_DETAILS, DATE_STRING + date));
		
		if (!DateTimeSetting.isEmptyTime(time)) {
			data.append(String.format(HTML_FONT_FINISHED_DETAILS, TIME_STRING + time));
		}

		if (endDate != null && !endDate.equals(EMPTY_STRING)) {
			data.append(String.format(HTML_FONT_FINISHED_DETAILS, BY_STRING + endDate));	
			
			if (!DateTimeSetting.isEmptyTime(endTime)) {
				data.append(String.format(HTML_FONT_FINISHED_DETAILS, TIME_STRING + endTime));	
			}
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
