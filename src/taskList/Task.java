package taskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task {
	private String content;
	private int index;
	private String dateString;
	private String venue;
	private String deadLineString;
	private Date date;
	private Date deadLine;
	
	public Task(String content, String date){
		this.content = content;
		this.dateString = date;
	}
	
	public Task(String content, String date, String deadLine, String venue){
		this.content = content;
		this.dateString = date;
		this.deadLineString = deadLine;
		this.venue = venue;
	}
	
	public Task(String content, Date date, Date deadLine, String venue){
		this.content = content;
		this.date = date;
		this.deadLine = deadLine;
		this.venue = venue;
	}
	
	public Task(String content){
		this.content = content;
	}
	public String getContent(){
		return content;
	}
	
	
	
	public Date getDate(){
		try{  
		    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd ");  
		    Date date = (Date) sdf.parse(dateString);  
			return date;  
		}  
		catch (ParseException e){  
		    System.out.println(e.getMessage());  
		}
		return null;
	}
	
	public String getVenue(){
		return this.venue;
	}
	
	public Date getDeadLine(){

		try{  
		    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd ");  
		    Date date = (Date) sdf.parse(deadLineString);  
			return date;  
		}  
		catch (ParseException e){  
		    System.out.println(e.getMessage());  
		}
		return null;
	}
	
	public String getDeadLineString(){
		return this.deadLineString;
	}
	public String getDateString(){
		return this.getDateString();
	}
}
