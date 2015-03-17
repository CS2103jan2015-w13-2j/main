package parser;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.*;

public class DateParser {
	private com.joestelmach.natty.Parser dateParser = null;
	
	public DateParser(){
		dateParser = new com.joestelmach.natty.Parser();
	}
	
	public Date getDate(String dateString){
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
}
