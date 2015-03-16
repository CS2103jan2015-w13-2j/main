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
		List<DateGroup> groups =  dateParser.parse(dateString);
		Date date = groups.get(0).getDates().get(0);
		return date;
	}
}
