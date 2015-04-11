package storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import parser.DateParser;
import taskList.Task;

public class ObjectConverter {
	private static final String LOGGER_NAME = "TaskBuddy.log";
	

	private static final String KEY_FOR_UNFINISHED_TASKLIST = "unfinished taskList";
	private static final String KEY_FOR_FINISHED_TASKLIST = "finished taskList";
	private static final String KEY_FOR_FILE_PATH_LIST = "file path list";
	
	private static final String KEY_FOR_CONTENT = "content";
	private static final String KEY_FOR_DATE = "date";
	private static final String KEY_FOR_DEADLINE = "deadline";
	private static final String KEY_FOR_VENUE = "venue";
	
	private static final Logger logger = Logger.getLogger(LOGGER_NAME);
	private DateFormat dateFormat;
	
	public ObjectConverter(){
		dateFormat = new SimpleDateFormat(DateParser.FORMAT_DEFAULT);
	}
	
	public String getJsonStringFromTaskList(ArrayList<Task> unfinishedTaskList){
		JSONObject jsonObject = new JSONObject();
		JSONArray taskArray = new JSONArray();
		
		for (int i = 0; i < unfinishedTaskList.size(); i++) {
			Task tempTask = unfinishedTaskList.get(i);
			
			JSONObject tempJsonTask = getTaskFromJsonObject(tempTask);
			
			taskArray.put(i,tempJsonTask);
		}
		jsonObject.put(KEY_FOR_UNFINISHED_TASKLIST, taskArray);
		return jsonObject.toString();
	}
	
	public String getJsonStringFromTaskList(ArrayList<Task> unfinishedTaskList, ArrayList<Task> finishedTaskList){
		JSONObject jsonObject = new JSONObject();
		JSONArray taskArray = new JSONArray();
		
		for (int i = 0; i < unfinishedTaskList.size(); i++) {
			Task tempTask = unfinishedTaskList.get(i);
			
			JSONObject tempJsonTask = getTaskFromJsonObject(tempTask);
			
			taskArray.put(i,tempJsonTask);
		}
		jsonObject.put(KEY_FOR_UNFINISHED_TASKLIST, taskArray);
		taskArray = new JSONArray();
		
		for (int i = 0; i < finishedTaskList.size(); i++) {
			Task tempTask = finishedTaskList.get(i);
			
			JSONObject tempJsonTask = getTaskFromJsonObject(tempTask);
			
			taskArray.put(i,tempJsonTask);
		}
		jsonObject.put(KEY_FOR_FINISHED_TASKLIST, taskArray);
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
	
	public ArrayList<String> getFilePathListFromJsonString(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return getFilePathListFromJsonObject(jsonObject, KEY_FOR_FILE_PATH_LIST);
	}
	
	private ArrayList<String> getFilePathListFromJsonObject(JSONObject jsonObject, String keyForFilePathList){
		ArrayList<String> filePathList = new ArrayList<String>();
		JSONArray jsonStringArray = jsonObject.getJSONArray(keyForFilePathList);
		if(jsonStringArray == null){
			showMessageNotFound(keyForFilePathList);
			return filePathList;
		}
		
		for(int i = 0; i < jsonStringArray.length(); i++){
			String filePath = (String) jsonStringArray.get(i);
			filePathList.add(filePath);
		}
		return filePathList;
	}
	
	
	public ArrayList<Task> getUnfinishedTaskListFromJsonString(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return getTaskListFromJsonObject(jsonObject, KEY_FOR_UNFINISHED_TASKLIST);
	}
	
	public ArrayList<Task> getFinishedTaskListFromJsonString(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return getTaskListFromJsonObject(jsonObject, KEY_FOR_FINISHED_TASKLIST);
	}
	
	private ArrayList<Task> getTaskListFromJsonObject(JSONObject jsonObject, String keyForTaskList){
		
		Task tempTask;
		ArrayList<Task> taskList = new ArrayList<Task>();
		
		JSONArray jsonTaskArray = jsonObject.getJSONArray(keyForTaskList);
		if(jsonTaskArray == null){
			showMessageNotFound(keyForTaskList);
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
		//logger.info(String.format(MESSAGE_NOT_FOUND, key));
	}

}
