package taskList;

import java.util.Date;

import parser.DateParser;


public class Task {
	private String content;
	private int index;
	private String venue;
	private Date date;
	private Date deadline;
	
	public Task(String content){
		this.content = content;
	}
	
	public Task(String content, String date){
		this.content = content;
		
		DateParser dateParser = new DateParser();
		this.date = dateParser.getDate(date);
	}
	
	public Task(String content, String date, String deadline, String venue){
		this.content = content;
		
		DateParser dateParser = new DateParser();
		this.date = dateParser.getDate(date);
		this.deadline = dateParser.getDate(deadline);
		
		this.venue = venue;
	}
	
	public Task(String content, Date date, Date deadline, String venue){
		this.content = content;
		this.date = date;
		this.deadline = deadline;
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
	
	public Date getDeadLine(){
		return this.deadline;
	}
	
	public String getDeadLineString(){
		if(deadline == null)
			return null;
		else
			return this.deadline.toString();
	}
	public String getDateString(){
		if(date == null)
			return null;
		else
			return this.date.toString();
	}
}
