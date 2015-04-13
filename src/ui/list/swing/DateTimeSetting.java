package ui.list.swing;

import taskList.Task;

//@author A0117971Y

/**
 * This class formats the date and time string
 * and returns a readable format
 * @author A0117971Y
 *
 */

public class DateTimeSetting {
	
	private static final String EMPTY_TIME = "00:00:00";
	private static final String EMPTY_STRING = "---";	

	
	static boolean isEmptyTime(String time) {
		if (time.trim().equals(EMPTY_TIME)) {
			return true;
		}
		return false;
	}
	
	static String getTime(Task task) {
		String time = trimTime(task.getDateString());
		
		return time;
	}
	
	static String getDate(Task task) {
		String date = trimDate(task.getDateString());
		
		return date;
	}
	
	static String getEndDate(Task task) {
		String endDate = trimDate(task.getDeadlineString());
		
		return endDate;
	}
	
	static String getEndTime(Task task) {
		String endTime = trimTime(task.getDeadlineString());
		
		return endTime;
	}
	
	private static String trimDate(String date) {

		String trimDate = EMPTY_STRING;

		if (date!= null && !date.equals(EMPTY_STRING)) {
			trimDate = date.substring(0, date.indexOf(":")-2);
			System.out.println("Date Trimmed: " + trimDate);
		}
		
		return trimDate;
	}
	
	private static String trimTime(String date) {
		String time = EMPTY_TIME;

		if (date!= null && !date.equals(EMPTY_STRING)) {
			time = date.substring(date.indexOf(":")-2, date.length());	
			return timeFormat(time);
		}

		return time;
	}
	
	static String dateFormat (String date) {
		String formattedDay = EMPTY_STRING;
		
		if (!date.equals(EMPTY_STRING)) {
			String tokens[] = date.split("-");
			String year = tokens[0];
			String mth = tokens[1];
			String day = tokens[2];
			
			switch (mth) {
			case "01": mth = "Jan"; break;
			case "02": mth = "Feb"; break;
			case "03": mth = "Mar"; break;
			case "04": mth = "April"; break;
			case "05": mth = "May"; break;
			case "06": mth = "Jun"; break;
			case "07": mth = "July"; break;
			case "08": mth = "Aug"; break;
			case "09": mth = "Sept"; break;
			case "10": mth = "Oct"; break;
			case "11": mth = "Nov"; break;
			case "12": mth = "Dec"; break;				
			}
			
			formattedDay = day+ " " + mth + " " + year;
		}
		
		return formattedDay;
	}
	
	private static String timeFormat(String time) {
		if (!isEmptyTime(time)) {
			String tokens[] = time.split(":");
			String hrs = tokens[0];
			String min = tokens[1];
			String day;
			int newHr = 0;
			
			if (Integer.parseInt(hrs) >= 12) {
				newHr = Integer.parseInt(hrs);
				day = "PM";

				if (newHr > 12) {
					newHr = newHr - 12;
				}
			}
			
			else {
				newHr = Integer.parseInt(hrs);
				day = "AM";
			}
			
			return newHr + ":" + min + day;
		}
		return time;
	}
	
}
