package parser;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DatePaser {
	ArrayList<SimpleDateFormat> format = new ArrayList<SimpleDateFormat>();
	
	public DatePaser(){
		format.add(new SimpleDateFormat());
		format.add(new SimpleDateFormat("YYYY-MM HH:mm"));
		format.add(new SimpleDateFormat("YYYY-MM-dd HH:mm"));
		format.add(new SimpleDateFormat("YYYY/MM HH:mm"));
		format.add(new SimpleDateFormat("YYYY/MM/dd HH:mm"));
	}
	
	public Date getDate(String dateString){
		for(SimpleDateFormat fmt: format){
			try {
	            return fmt.parse(dateString); 
	        } catch (ParseException ignored) {
	        }
		}
		return null;
	}
}
