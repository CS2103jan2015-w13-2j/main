package parser;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

public class DateParser {
	private com.joestelmach.natty.Parser dateParser = null;
	private String[] dateIndicators = {"/", "-"};
	private String[] timeIndicators = {".", ":"};
	public DateParser(){
		dateParser = new com.joestelmach.natty.Parser();
	}
	
	public Date getDate(String dateString) throws NullPointerException, IOException {
		if (dateString == null) {
			throw new NullPointerException("the command cannot be null");
		}
		checkDateFormat(dateString);
		checkTimeFormat(dateString);
		List<DateGroup> groups =  dateParser.parse(dateString);
		if (groups.isEmpty()) {
			return null;
		} else {
			return groups.get(0).getDates().get(0);
		}
	}
	
	private void checkDateFormat(String dateString) throws IOException {
		assert(dateString != null);
		String year = null;
		String month = null;
		String day = null;
		year = getDateElement(0, dateString);
		if (year == null) {
			return;
		}
		if (year.length() != 4) {
			year = getDateElement(2, dateString);
			month = getDateElement(0, dateString);
			day = getDateElement(1, dateString);
		} else {
			month = getDateElement(1, dateString);
			day = getDateElement(2, dateString);
		}
		if (day != null && Integer.parseInt(day) > 31 
		 || day != null && Integer.parseInt(day) < 1  
		 || month != null && Integer.parseInt(month) > 12 
		 || month != null && Integer.parseInt(month) < 1) {
			throw new IOException("the date format you entered is incorrect");
		}
	}
	
	private void checkTimeFormat(String dateString) throws IOException {
		assert(dateString != null);
		String hour = null;
		String min = null;
		String sec = null;
		hour = getTimeElement(0, dateString);
		min = getTimeElement(1, dateString);
		sec = getTimeElement(2, dateString);
		if (min != null && Integer.parseInt(min) > 59 
		 || min != null && Integer.parseInt(min) < 0
		 || sec != null && Integer.parseInt(sec) > 59
		 || sec != null && Integer.parseInt(sec) < 0) {
			throw new IOException("the date format you entered is incorrect");
		}
	}
	
	private String getDateElement(int intendedPotion, String dateString) {
		assert(dateString != null);
		int start = 0;
		int end = 0;
		for (String temp: dateIndicators) {
			if (dateString.contains(temp)) {
				int count = 0;
				start = -1;
				while (count < intendedPotion) {
					count++;
					start = dateString.indexOf(temp, start + 1);
				}
				end = dateString.indexOf(temp, start + 1);
				if (end == -1) {
					end = dateString.indexOf(" ", start + 1);
				}
				if (end == -1) {
					end = dateString.length();
				}
				break;
			}
		}
		if (start == end) {
			return null;
		} else {
			return dateString.substring(start + 1, end);
		}
	}
	
	private String getTimeElement(int intendedPotion, String dateString) {
		assert(dateString != null);
		int start = 0;
		int end = 0;
		for (String temp: timeIndicators) {
			if (dateString.contains(temp)) {
				int count = 0;
				start = getNearestSpaceBefore(dateString, temp);
				if (start == -1) {
					start = 0;
				}
				while (count < intendedPotion) {
					count++;
					start = dateString.indexOf(temp, start + 1);
				}
				if (start == -1) {
					return null;
				}
				end = dateString.indexOf(temp, start + 1);
				if (end == -1) {
					end = dateString.indexOf(" ", start + 1);
				}
				if (end == -1) {
					end = dateString.length();
				}
				break;
			}
		}
		if (start == end) {
			return null;
		} else {
			return dateString.substring(start + 1, end);
		}
	}
	//return the index of the nearest space in str which is before the first someString
	private int getNearestSpaceBefore(String str, String someString) {
		assert(str != null);
		assert(someString != null);
		int index = str.indexOf(someString);
		while (index != -1 && str.charAt(index) != ' ') {
			index--;
		}
		return index;
	}
}
