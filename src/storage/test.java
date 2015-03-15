package storage;

import java.util.ArrayList;

import taskList.Task;

public class test {
	public static void main(String[] a){
		JsonStringFileOperation jsto = new JsonStringFileOperation("test.json");
		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task("1","2013-11-12 11:54"));
		taskList.add(new Task("2"));
		taskList.add(new Task("3"));
		taskList.add(new Task("4"));
		taskList.add(new Task("5"));
		taskList.add(new Task("6"));
		for(Task i:taskList){
			System.out.println(i.getContent()+" | "+i.getDateString()+" | "+i.getDeadLineString()+" | "+i.getVenue());
		}
		jsto.saveToFile(taskList);
		taskList = jsto.readFile();
		for(Task i:taskList){
			System.out.println(i.getContent()+" | "+i.getDateString()+" | "+i.getDeadLineString()+" | "+i.getVenue());
		}
		return;
	}
}
