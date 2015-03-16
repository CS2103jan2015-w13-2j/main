package taskList;

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
	
	public Task(String content, String date){
		this.content = content;
		
		DateParser dateParser = new DateParser();
		this.date = dateParser.getDate(date);
		
		if(date != null)
			dateString = date.toString();
	}
	
	public Task(String content, String date, String deadline, String venue){
		this.content = content;
		
		DateParser dateParser = new DateParser();
		this.date = dateParser.getDate(date);
		this.deadline = dateParser.getDate(deadline);
		
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
		if(deadline == null)
			return null;
		else
			return this.deadline.toString();
	}
	public String getDateString(){
		//to specify the format 
		//return new SimpleDateFormat("YYYY-MM-dd HH:mm").format(date);
		if(date == null)
			return null;
		else
			return this.date.toString();
	}
}
