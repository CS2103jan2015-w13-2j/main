package storage;

import java.util.ArrayList;

import taskList.Task;

public class test {
	public static void main(String[] a){
		JsonStringFileOperation jsto = new JsonStringFileOperation("test.json");
		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task("01"));
		taskList.add(new Task("02"));
		taskList.add(new Task("03"));
		taskList.add(new Task("04"));
		taskList.add(new Task("05"));
		taskList.add(new Task("06"));
		
		taskList.add(new Task("11","2014-03-07 17:51"));
		taskList.add(new Task("12","2014-03-07 17:51"));
		taskList.add(new Task("13","2014-03-07 17:51"));
		taskList.add(new Task("14","2014-03-07 17:51"));
		taskList.add(new Task("15","2014-03-07 17:51"));
		taskList.add(new Task("16","2014-03-07 17:51"));
		
		taskList.add(new Task("21","2014-03-07 17:51","2014-03-07 18:51","11"));
		taskList.add(new Task("22","2014-03-07 17:52","2014-03-07 18:52","12"));
		taskList.add(new Task("23","2014-03-07 17:53","2014-03-07 18:53","13"));
		taskList.add(new Task("24","2014-03-07 17:54","2014-03-07 18:54","14"));
		taskList.add(new Task("25","2014-03-07 17:55","2014-03-07 18:55","15"));
		taskList.add(new Task("26","2014-03-07 17:56","2014-03-07 18:56","16"));
		for(Task i:taskList){
			System.out.println(i.getContent()+" | "+i.getDateString()+" | "+i.getDeadlineString()+" | "+i.getVenue());
		}
		jsto.saveToFile(taskList);
		taskList = jsto.readFile();
		for(Task i:taskList){
			System.out.println(i.getContent()+" | "+i.getDateString()+" | "+i.getDeadlineString()+" | "+i.getVenue());
		}
		return;
	}
}
