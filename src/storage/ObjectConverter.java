package storage;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import parser.DateParser;
import taskList.Task;

/**
 * Converting between JSON format string and objects
 * 
 * @author Huang Weilong A0119392B
 * @version 2015 April 11
 */
public class ObjectConverter {
	//private static final String LOGGER_NAME = "TaskBuddy.log";
	private static final String KEY_FOR_UNFINISHED_TASKLIST = "unfinished taskList";
	private static final String KEY_FOR_FINISHED_TASKLIST = "finished taskList";
	private static final String KEY_FOR_FILE_PATH = "file path";
	private static final String KEY_FOR_FILE_PATH_LIST = "file path list";
	
	private static final String KEY_FOR_CONTENT = "content";
	private static final String KEY_FOR_DATE = "date";
	private static final String KEY_FOR_DEADLINE = "deadline";
	private static final String KEY_FOR_VENUE = "venue";
	
	//private static final Logger logger = Logger.getLogger(LOGGER_NAME);
	
	public ObjectConverter(){
	}
	
	/**
	 * encode unfinishedTaskList with JSON format to a string
	 * @param unfinishedTaskList
	 * @return result JSON string
	 */
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
	
	/**
	 * encode unfinishedTaskList and finishedTaskList with JSON format to a string
	 * @param unfinishedTaskList
	 * @param finishedTaskList
	 * @return result JSON string
	 */
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

	/**
	 * Converting task object to JSON object
	 * @param task
	 * @return the result JSON object
	 */
	private JSONObject getTaskFromJsonObject(Task task) {
		JSONObject tempJsonTask = new JSONObject();
		
		tempJsonTask.put(KEY_FOR_CONTENT, task.getContent());
		
		Date date = task.getDate();
		
		String dateString;
		if(date == null){
			dateString = null;
		}else{
			dateString = DateParser.formatDefault(date);
		}
		tempJsonTask.put(KEY_FOR_DATE, dateString);
		
		Date deadline = task.getDeadline();
		String deadlineString;
		if(deadline == null){
			deadlineString = null;
		}else{
			deadlineString = DateParser.formatDefault(deadline);
		}
		tempJsonTask.put(KEY_FOR_DEADLINE, deadlineString);
		
		tempJsonTask.put(KEY_FOR_VENUE, task.getVenue());
		
		return tempJsonTask;
	}
	
	/**
	 * encode fileName and filePathList with JSON format to a string
	 * @param fileName
	 * @param filePathList
	 * @return result JSON string
	 */
	public String getJsonStringFromConfiguration(String fileName, ArrayList<String> filePathList){
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(KEY_FOR_FILE_PATH, fileName);
		
		JSONArray filePathArray = new JSONArray();
		
		for (int i = 0; i < filePathList.size(); i++) {
			filePathArray.put(i,filePathList.get(i));
		}
		jsonObject.put(KEY_FOR_FILE_PATH_LIST, filePathArray);
		return jsonObject.toString();
	}
	
	/**
	 * converting JSON string to file path object
	 * @param jsonObject
	 * @return last opened file path
	 */
	public String getFilePathFromJsonString(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return getFilePathFromJsonObject(jsonObject);
	}
	
	/**
	 * converting JSON string to file path object
	 * @param jsonObject
	 * @return last opened file path
	 */
	public ArrayList<String> getFilePathListFromJsonString(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return getFilePathListFromJsonObject(jsonObject);
	}
	
	/**
	 * converting JSON object to file path list object
	 * @param jsonObject
	 * @return history opened file path
	 */
	private ArrayList<String> getFilePathListFromJsonObject(JSONObject jsonObject){
		ArrayList<String> filePathList = new ArrayList<String>();
		JSONArray jsonStringArray = jsonObject.getJSONArray(KEY_FOR_FILE_PATH_LIST);
		if(jsonStringArray == null){
			//showMessageNotFound(KEY_FOR_FILE_PATH_LIST);
			return filePathList;
		}
		
		for(int i = 0; i < jsonStringArray.length(); i++){
			String filePath = (String) jsonStringArray.get(i);
			filePathList.add(filePath);
		}
		return filePathList;
	}
	
	/**
	 * converting JSON object to file path object
	 * @param jsonObject
	 * @return last opened file path
	 */
	private String getFilePathFromJsonObject(JSONObject jsonObject){
		String filePath = jsonObject.getString(KEY_FOR_FILE_PATH);
		return filePath;
	}
	
	/**
	 * converting JSON string to unfinished task list object
	 * @param jsonString
	 * @return unfinished task list object
	 */
	public ArrayList<Task> getUnfinishedTaskListFromJsonString(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return getTaskListFromJsonObject(jsonObject, KEY_FOR_UNFINISHED_TASKLIST);
	}
	
	/**
	 * converting JSON string to finished task list object
	 * @param jsonString
	 * @return finished task list object
	 */
	public ArrayList<Task> getFinishedTaskListFromJsonString(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		return getTaskListFromJsonObject(jsonObject, KEY_FOR_FINISHED_TASKLIST);
	}
	
	/**
	 * Converting JSON object to task list object
	 * @param jsonObject
	 * @param keyForTaskList
	 * @return required task list with the key keyForTaskList
	 */
	private ArrayList<Task> getTaskListFromJsonObject(JSONObject jsonObject, String keyForTaskList){
		Task tempTask;
		ArrayList<Task> taskList = new ArrayList<Task>();
		
		JSONArray jsonTaskArray = jsonObject.getJSONArray(keyForTaskList);
		if(jsonTaskArray == null){
			//showMessageNotFound(keyForTaskList);
			return taskList;
		}
		
		for(int i = 0; i < jsonTaskArray.length(); i++){
			tempTask = getTask((JSONObject) jsonTaskArray.get(i));
			
			taskList.add(tempTask);
		}
		
		return taskList;
	}
	
	/**
	 * @param jsonTask
	 * @return return the task object
	 */
	private Task getTask(JSONObject jsonTask) {
		String content = getContent(jsonTask);
		String date = getDateString(jsonTask);
		String deadline = getDeadlineString(jsonTask);
		String venue = getVenue(jsonTask);
		
		assert content != null;
		return new Task(content, date, deadline, venue);
	}
	
	/**
	 * @param jsonTask
	 * @return the string for content, if not existed, return null
	 */
	private String getContent(JSONObject jsonTask) {
		try{
			return jsonTask.getString(KEY_FOR_CONTENT);
		}catch(JSONException notFound){
			//showMessageNotFound(KEY_FOR_CONTENT);
			return null;
		}
	}
	
	/**
	 * @param jsonTask
	 * @return the string for date, if not existed, return null
	 */
	private String getDateString(JSONObject jsonTask) {
		try{
			return jsonTask.getString(KEY_FOR_DATE);
		}catch(JSONException notFound){
			//showMessageNotFound(KEY_FOR_DATE);
			return null;
		}
	}

	/**
	 * @param jsonTask
	 * @return the string for deadline, if not existed, return null
	 */
	private String getDeadlineString(JSONObject jsonTask) {
		try{
			return jsonTask.getString(KEY_FOR_DEADLINE);
		}catch(JSONException notFound){
			//showMessageNotFound(KEY_FOR_DEADLINE);
			return null;
		}
	}
	
	/**
	 * @param jsonTask
	 * @return the string for venue, if not existed, return null
	 */
	private String getVenue(JSONObject jsonTask) {
		try{
			return jsonTask.getString(KEY_FOR_VENUE);
		}catch(JSONException notFound){
			//showMessageNotFound(KEY_FOR_VENUE);
			return null;
		}
	}
	
//	private void showMessageNotFound(String key){
//		//logger.info(String.format(MESSAGE_NOT_FOUND, key));
//	}

}
