package taskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task {
	private String content;
	private int index;
	private String dateString;
	
	public Task(String content, String date){
		this.content = content;
		this.dateString = date;
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
}
