package storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import taskList.Task;

public class ObjectConverter {
	private static final String MESSAGE_NOT_FOUND = "%s is not found in the JSON Object.\n";

	private static final String KEY_FOR_TASKLIST = "taskList";
	
	private static final String KEY_FOR_CONTENT = "content";
	private static final String KEY_FOR_DATE = "date";
	private static final String KEY_FOR_DEADLINE = "deadline";
	private static final String KEY_FOR_VENUE = "venue";
	
	private DateFormat dateFormat;
	
	public ObjectConverter(){
		//new SimpleDateFormat("YYYY-MM-dd HH:mm").format(date);
		dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
	}
	
	public String getJsonStringFromTaskList(ArrayList<Task> taskList){
		JSONObject jsonObject = new JSONObject();
		JSONArray taskArray = new JSONArray();
		
		for (int i = 0; i < taskList.size(); i++) {
			Task tempTask = taskList.get(i);
			
			JSONObject tempJsonTask = getTaskFromJsonObject(tempTask);
			
			taskArray.put(i,tempJsonTask);
		}
		jsonObject.put(KEY_FOR_TASKLIST, taskArray);
		return jsonObject.toString();
	}

	private JSONObject getTaskFromJsonObject(Task tempTask) {
		JSONObject tempJsonTask = new JSONObject();
		
		tempJsonTask.put(KEY_FOR_CONTENT, tempTask.getContent());
		
		Date date = tempTask.getDate();
		
		String dateString;
		if(date == null){
			dateString = null;
		}else{
			dateString = dateFormat.format(date);
		}
		tempJsonTask.put(KEY_FOR_DATE, dateString);
		
		Date deadline = tempTask.getDeadline();
		String deadlineString;
		if(deadline == null){
			deadlineString = null;
		}else{
			deadlineString = dateFormat.format(deadline);
		}
		tempJsonTask.put(KEY_FOR_DEADLINE, deadlineString);
		
		tempJsonTask.put(KEY_FOR_VENUE, tempTask.getVenue());
		
		return tempJsonTask;
	}
	
	public ArrayList<Task> getTaskListFromJsonString(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return getTaskListFromJsonObject(jsonObject);
	}
	
	public ArrayList<Task> getTaskListFromJsonObject(JSONObject jsonObject){
		
		Task tempTask;
		ArrayList<Task> taskList = new ArrayList<Task>();
		
		JSONArray jsonTaskArray = jsonObject.getJSONArray(KEY_FOR_TASKLIST);
		if(jsonTaskArray == null){
			System.err.println(MESSAGE_NOT_FOUND);
			return taskList;
		}
		
		for(int i = 0; i < jsonTaskArray.length(); i++){
			JSONObject jsonTask = (JSONObject) jsonTaskArray.get(i);
			
			String content = getContent(jsonTask);
			String date = getDateString(jsonTask);
			String deadline = getDeadlineString(jsonTask);
			String venue = getVenue(jsonTask);
			
			assert content != null;
			tempTask = new Task(content, date, deadline, venue);
			
			taskList.add(tempTask);
		}
		
		return taskList;
	}

	private String getContent(JSONObject jsonTask) {
		try{
			return jsonTask.getString(KEY_FOR_CONTENT);
		}catch(JSONException notFound){
			showMessageNotFound(KEY_FOR_CONTENT);
			return null;
		}
	}

	private String getDateString(JSONObject jsonTask) {
		try{
			return jsonTask.getString(KEY_FOR_DATE);
		}catch(JSONException notFound){
			showMessageNotFound(KEY_FOR_DATE);
			return null;
		}
	}

	private String getDeadlineString(JSONObject jsonTask) {
		try{
			return jsonTask.getString(KEY_FOR_DEADLINE);
		}catch(JSONException notFound){
			showMessageNotFound(KEY_FOR_DEADLINE);
			return null;
		}
	}

	private String getVenue(JSONObject jsonTask) {
		try{
			return jsonTask.getString(KEY_FOR_VENUE);
		}catch(JSONException notFound){
			showMessageNotFound(KEY_FOR_VENUE);
			return null;
		}
	}
	
	private void showMessageNotFound(String key){
		System.err.print(String.format(MESSAGE_NOT_FOUND, key));
	}

}
