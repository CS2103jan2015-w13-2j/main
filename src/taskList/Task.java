package taskList;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import parser.DateParser;
import parser.Parser;

public class Task implements Comparable<Task>{
	private String content;
	private String venue;
	private Date date;
	private Date deadline;
	private String dateString;
	private String deadlineString;
	private boolean hasFinished = false;
	static Parser taskParser = new Parser();
	static SimpleDateFormat dateFormat; 
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
			dateString = new SimpleDateFormat(DateParser.FORMAT_DEFAULT).format(this.date);
		
		if(deadline != null)
			deadlineString = new SimpleDateFormat(DateParser.FORMAT_DEFAULT).format(this.deadline);
		
		this.venue = venue;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String newContent){
		if (newContent == null) return;
		this.content = newContent;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public void setDate(Date newDate){
		if (newDate == null) return;
		this.date = newDate;
	}
	
	public String getVenue(){
		return this.venue;
	}
	
	public void setVenue(String newVenue){
		if (newVenue == null){
			return;
		}
		this.venue = newVenue;
	}
	
	public Date getDeadline(){
		return this.deadline;
	}
	
	public void setDeadLine(Date newDeadLine){
		if (newDeadLine == null){
			return;
		}
		this.deadline = newDeadLine;
	}
	
	public String getDeadlineString(){
		return deadlineString;
	}
	public String getDateString(){
		return dateString;
	}

	@Override
	public int compareTo(Task o) {
		return -(o.getContent().compareTo(this.getContent()));
	}
	
	public boolean hasFinished(){
		return this.hasFinished;
	}
	
	public void finish(){
		this.hasFinished = true;
	}
	
	public boolean sameString(String string1, String string2){
		if (string1 == null){
			if (string2 == null) return true;
			return false;
		}else{
			return string1.equals(string2);
		}
	}
	
	public boolean sameDate(Date date1, Date date2){
		if (date1 == null){
			if (date2 == null) return true;
			return false;
		}else{
			return date1.equals(date2);
		}
	}
	
	
	public boolean isEqual(Task task2){

		return (sameString(this.content,task2.content) && sameString(this.venue,(task2.venue)) && sameDate(this.date,(task2.date)));
	}
	
	public boolean containKeyWord(String keyWord){
		boolean answer = false;
		if (this.content != null)
			answer |= this.content.contains(keyWord);
		if (this.venue != null)
			answer |= this.venue.contains(keyWord);
		if (this.date != null)
			System.out.println("debug time "+ new SimpleDateFormat(DateParser.FORMAT_DEFAULT).format(this.date));
		if (this.date != null)
			answer |= new SimpleDateFormat(DateParser.FORMAT_DEFAULT).format(this.date).contains(keyWord);
		return answer;
	}
	
	public boolean isTodayTask() throws NullPointerException, IOException{
		Date today = taskParser.getDate("add -d today");
		dateFormat = new SimpleDateFormat(DateParser.FORMAT_DEFAULT);
		if (date == null) {
			if (deadline == null)return false;else return (dateFormat.format(date).equals((dateFormat).format(today)));
		}else
		return (dateFormat.format(date).equals((dateFormat).format(today)));
	}
	
	public boolean isOutOfDate() throws NullPointerException, IOException{
		Date today = taskParser.getDate("add -d today");
		System.out.println("today is " +today);
		if (deadline == null){
			if (date == null) return false;else {
				System.out.println("debug ");
				System.out.println(today.after(date));
				
				return (today.after(date));
			}
		}else
		return (today.after(deadline));
	}
	
}
