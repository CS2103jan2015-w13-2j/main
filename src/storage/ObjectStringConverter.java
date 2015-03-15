package storage;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import taskList.Task;

public class ObjectStringConverter {
	private static final String KEY_FOR_CONTENT = "content";
	private static final String KEY_FOR_DATE = "date";
	private static final String KEY_FOR_TASKLIST = "taskList";
	
	
	public ObjectStringConverter(){
		
	}
	
	public String taskListToJsonString(ArrayList<Task> taskList){
		JSONObject jsonObject = new JSONObject();
		JSONArray taskArray = new JSONArray();
		for (int i = 0; i < taskList.size(); i++) {
			Task tempTask = taskList.get(i);
			JSONObject tempJsonTask = new JSONObject();
			tempJsonTask.put(KEY_FOR_CONTENT, tempTask.getContent());
			tempJsonTask.put(KEY_FOR_DATE, tempTask.getDate());
			taskArray.put(i,tempJsonTask);
		}
		jsonObject.put(KEY_FOR_TASKLIST, taskArray);
		return jsonObject.toString();
	}
	
	public ArrayList<Task> JsonStringToTaskList(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return JsonObjectToTaskList(jsonObject);
	}
	
	public ArrayList<Task> JsonObjectToTaskList(JSONObject jsonObject){
		
		Task tempTask;
		ArrayList<Task> taskList = new ArrayList<Task>();
		
		JSONArray taskArray = jsonObject.getJSONArray(KEY_FOR_TASKLIST);
		for(int i = 0; i < taskArray.length(); i++){
			JSONObject jsonTask = (JSONObject) taskArray.get(i);
			tempTask = new Task(jsonTask.getString(KEY_FOR_CONTENT),jsonTask.getString(KEY_FOR_DATE));
			taskList.add(tempTask);
		}
		
		return taskList;
	}

}
