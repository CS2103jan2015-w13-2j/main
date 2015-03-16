package parser;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DateParser {
	private ArrayList<SimpleDateFormat> format = new ArrayList<SimpleDateFormat>();
	
	public DateParser(){
		//if anyone match, this parser will return
		//match the long format first and then try those shorter format
		
		//default one
		format.add(new SimpleDateFormat());
		
		//12 Hour
		format.add(new SimpleDateFormat("yyyy-MM-dd hh:mm aa"));
		/*
		format.add(new SimpleDateFormat("yyyy/MM/dd hh:mm aa"));
		format.add(new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm aa"));
		
		format.add(new SimpleDateFormat("dd-MM-yyyy hh:mm aa"));
		format.add(new SimpleDateFormat("dd/MM/yyyy hh:mm aa"));
		format.add(new SimpleDateFormat("dd.MM.yyyy 'at' hh:mm aa"));
		format.add(new SimpleDateFormat("EEE, dd/MM/yyyy hh:mm aa"));	*/
		
		//24 Hour
		format.add(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		/*
		format.add(new SimpleDateFormat("yyyy/MM/dd HH:mm"));
		format.add(new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm"));
		format.add(new SimpleDateFormat("EEE, MMM d, ''yy"));

		format.add(new SimpleDateFormat("dd-MM-yyyy HH:mm"));
		format.add(new SimpleDateFormat("dd/MM/yyyy HH:mm"));
		format.add(new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm"));
		format.add(new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm"));
		
		//time not specified
		//put these shortest at last
		format.add(new SimpleDateFormat("yyyy-MM-dd"));
		format.add(new SimpleDateFormat("yyyy-MM"));
		format.add(new SimpleDateFormat("MM-dd"));*/
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
