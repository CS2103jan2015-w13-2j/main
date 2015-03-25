package storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import taskList.Task;


public class testObjectConverter {
	private static final int TEST_SIZE = 200;
	private static final int BIG_PRIME_NUMBER = 107;
	final static String NAME_TEST_STRING = "%05d";
	private static final String KEY_FOR_TASKLIST = "taskList";
	
	private static final String KEY_FOR_CONTENT = "content";
	private static final String KEY_FOR_DATE = "date";
	private static final String KEY_FOR_DEADLINE = "deadline";
	private static final String KEY_FOR_VENUE = "venue";
	
	ObjectConverter converter;
	
	
	/*
	 * init the test
	 * if the file exist, delete the file
	 * create the new textbuddy object
	 */
	@Before
	public void initTextBuddy(){
	    converter = new ObjectConverter();
	}
	
	/*
	 * check the whether the filename is correct
	 */
	@Test
	public void testgetTaskFromJsonObject() {
		ArrayList<Task> taskList = new ArrayList<Task>();
		for(int i = 0; i < TEST_SIZE; i++){
			taskList.add(new Task(String.format(NAME_TEST_STRING, i)));
		}
		JSONObject jsObj = new JSONObject(converter.getJsonStringFromTaskList(taskList));
		
		for(int i = 0; i < TEST_SIZE; i++){
			System.out.println(((JSONObject) jsObj.getJSONArray(KEY_FOR_TASKLIST).get(i)).get("content"));
			Assert.assertEquals("Testing for tasklist to JSONObject",String.format(NAME_TEST_STRING, i),((JSONObject) jsObj.getJSONArray(KEY_FOR_TASKLIST).get(i)).get(KEY_FOR_CONTENT));
		}
	}
	
	@Test
	public void testgetTaskFromJsonObjectWithEmptyList() {
		ArrayList<Task> taskList = new ArrayList<Task>();
		
		JSONObject jsObj = new JSONObject(converter.getJsonStringFromTaskList(taskList));
		Assert.assertEquals("Testing for empty tasklist to JSONObject","[]",jsObj.getJSONArray(KEY_FOR_TASKLIST).toString());
	}

	@Test
	public void testJSONObjectToString() {
		ArrayList<Task> taskList = new ArrayList<Task>();
		for(int i = 0; i < TEST_SIZE; i++){
			taskList.add(new Task(String.format(NAME_TEST_STRING, i)));
		}
		String jsonString = converter.getJsonStringFromTaskList(taskList);
		for(int i = 0; i < TEST_SIZE; i++){
			Assert.assertEquals("Testing for converting back",taskList.get(i).getContent(),converter.getTaskListFromJsonString(jsonString).get(i).getContent());
		}
	}
	
	@Test
	public void testEmptyJSONObjectToString() {
		ArrayList<Task> taskList = new ArrayList<Task>();
		
		String jsonString = converter.getJsonStringFromTaskList(taskList);
		for(int i = 0; i < TEST_SIZE; i++){
			Assert.assertEquals("Testing for converting back",0,converter.getTaskListFromJsonString(jsonString).size());
		}
	}
	
	@After
	public void cleanUp(){
		converter = null;
	}
	
}