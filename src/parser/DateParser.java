package parser;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DateParser {
	ArrayList<SimpleDateFormat> format = new ArrayList<SimpleDateFormat>();
	
	public DateParser(){
		format.add(new SimpleDateFormat());
		//24 Hour
		format.add(new SimpleDateFormat("YYYY-MM HH:mm"));
		format.add(new SimpleDateFormat("YYYY-MM-dd HH:mm"));
		format.add(new SimpleDateFormat("YYYY/MM HH:mm"));
		format.add(new SimpleDateFormat("YYYY/MM/dd HH:mm"));
		format.add(new SimpleDateFormat("YYYY.MM.dd 'at' HH:mm"));
		format.add(new SimpleDateFormat("EEE, MMM d, ''yy"));

		format.add(new SimpleDateFormat("MM-YYYY HH:mm"));
		format.add(new SimpleDateFormat("dd-MM-YYYY HH:mm"));
		format.add(new SimpleDateFormat("MM/YYYY HH:mm"));
		format.add(new SimpleDateFormat("dd/MM/YYYY HH:mm"));
		format.add(new SimpleDateFormat("dd.MM.YYYY 'at' HH:mm"));
		format.add(new SimpleDateFormat("EEE, dd/MM/YYYY HH:mm"));
		
		//12 Hour
		format.add(new SimpleDateFormat("YYYY-MM hh:mm aaa"));
		format.add(new SimpleDateFormat("YYYY-MM-dd hh:mm aaa"));
		format.add(new SimpleDateFormat("YYYY/MM hh:mm aaa"));
		format.add(new SimpleDateFormat("YYYY/MM/dd hh:mm aaa"));
		format.add(new SimpleDateFormat("YYYY.MM.dd 'at' hh:mm aaa"));
		
		format.add(new SimpleDateFormat("MM-YYYY hh:mm aaa"));
		format.add(new SimpleDateFormat("dd-MM-YYYY hh:mm aaa"));
		format.add(new SimpleDateFormat("MM/YYYY hh:mm aaa"));
		format.add(new SimpleDateFormat("dd/MM/YYYY hh:mm aaa"));
		format.add(new SimpleDateFormat("dd.MM.YYYY 'at' hh:mm aaa"));
		format.add(new SimpleDateFormat("EEE, dd/MM/YYYY hh:mm aaa"));	
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
