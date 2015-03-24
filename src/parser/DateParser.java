package parser;

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
	
	public Date getDate(String dateString) throws NullPointerException {
		if (dateString == null) {
			throw new NullPointerException("the command cannot be null");
		}
		
		List<DateGroup> groups =  dateParser.parse(dateString);
		if (groups.isEmpty()) {
			return null;
		} else {
			return groups.get(0).getDates().get(0);
		}
	}
	
	private String getDateElement(int intendedPotion, String dateString) {
		assert(dateString != null);
		for (String temp: dateIndicators) {
			if (dateString.contains(temp)) {
				int count = 0;
				int start = 0;
				while count < intended
				break;
			}
		}
		
	}
	
	private String getTimeElement(int intendedPotion, String dateString) {
		assert(dateString != null);
		for (String temp: dateIndicators) {
			if (dateString.contains(temp)) {
				int count = 0;
				
				break;
			}
		}
		
	}
}
