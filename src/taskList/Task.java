package taskList;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import parser.DateParser;


public class Task {
	private String content;
	private int index;
	private String venue;
	private Date date;
	private Date deadline;
	private String dateString;
	private String deadlineString;
	
	public Task(String content){
		this.content = content;
	}
	
	public Task(String content, String date) throws NullPointerException, IOException{
		this.content = content;
		
		DateParser dateParser = new DateParser();
		try{
			this.date = dateParser.getDate(date);	
		}catch (Exception exception){
			new Exception("NullPointerException");
		}
		
		if(date != null)
			dateString = date.toString();
		//new SimpleDateFormat("YYYY-MM-dd HH:mm").format(date);
	}
	
	public Task(String content, String date, String deadline, String venue){
		this.content = content;
		
		DateParser dateParser = new DateParser();
		try {
			this.date = dateParser.getDate(date);
		} catch (Exception e) {
			this.date = null;
		}
		try {
			this.deadline = dateParser.getDate(deadline);
		} catch (Exception e) {
			this.deadline = null;
		}
		
		
		if(date != null)
			dateString = date.toString();
		
		if(deadline != null)
			deadlineString = deadline.toString();
		
		this.venue = venue;
	}
	
	public Task(String content, Date date, Date deadline, String venue){
		this.content = content;
		this.date = date;
		this.deadline = deadline;
		
		if(date != null)
			dateString = date.toString();
		
		if(deadline != null)
			deadlineString = deadline.toString();
		
		this.venue = venue;
	}
	
	public String getContent(){
		return content;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public String getVenue(){
		return this.venue;
	}
	
	public Date getDeadline(){
		return this.deadline;
	}
	
	public String getDeadlineString(){
		return deadlineString;
	}
	public String getDateString(){
		return dateString;
	}
}
