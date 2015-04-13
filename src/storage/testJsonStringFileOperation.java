package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import taskList.Task;

//@author A0119392B

/**
 * 
 * @author Huang Weilong A0119392B
 * @version 2015 April 11
 */
public class testJsonStringFileOperation {
	private static final int TEST_SIZE = 200;
	private static final int BIG_PRIME_NUMBER1 = 107;
	private static final int BIG_PRIME_NUMBER2 = 101;
	private static final String NAME_TEST_STRING = "%05d";
	private static final String TEST_FILENAME = "fileStorageTest.json";
	private static final String KEY_FOR_UNFINISHED_TASKLIST = "unfinished taskList";
	private static final String KEY_FOR_FINISHED_TASKLIST = "finished taskList";
	
	private static final String KEY_FOR_CONTENT = "content";
	private static final String KEY_FOR_DATE = "date";
	private static final String KEY_FOR_DEADLINE = "deadline";
	private static final String KEY_FOR_VENUE = "venue";
	
	private static final String EMPTY_STRING = "";
	
	private static final String MESSAGE_READ_FILE = "Read file successfully.";
	private static final String MEAAGE_SAVE_TO_FILE = "Save to file successfully.";
	private static final String MESSAGE_SAVE_TO_TEMP_FILE = "Save to temp file successfully.";
	
	private static final String MESSAGE_NULL_FILENAME = "File name cannot be null\n";
	private static final String MESSAGE_NO_TEMPFILE = "Temp file is not found.\n";
	private static final String MESSAGE_INVALID_FILENAME = "File name is invalid\n";
	private static final String MESSAGE_CANNOT_READ = "cannot read the file.\n";
	private static final String MESSAGE_CANNOT_WRITE = "cannot write the file.\n";
	private static final String MESSAGE_FOLDER_FILENAME = "fileName is a Directory name.\n";
	private static final String MESSAGE_CANNOT_PARSE = "Cannot parse the file with JSON format, return empty task list.\n";
	private static final String MESSAGE_NEW_FILE = "The file is not existed, create new file.\n";
	
	ObjectConverter converter;
	JsonStringFileOperation fileOperation;
	
	

	@Before
	public void init(){
	    converter = new ObjectConverter();
	}

	private String getFileContent(String fileName) throws IOException{
		if (new File(fileName).isDirectory()) {
			throw new IOException(MESSAGE_FOLDER_FILENAME);
		}
		if (!(new File(fileName).exists())) {
			System.err.println(MESSAGE_NEW_FILE);
			return EMPTY_STRING;
		}
		try {
			FileInputStream fileInput = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"));
			String readContent = br.readLine();
			if(readContent == null){
				readContent = EMPTY_STRING;
			}
			br.close();
			return readContent;
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_READ);
		}
	}
	
	@Test
	public void testSavingUnfinishedTasklist() {
		ArrayList<Task> taskList = new ArrayList<Task>();
		for(int i = 0; i < TEST_SIZE; i++){
			taskList.add(new Task(String.format(NAME_TEST_STRING, i*BIG_PRIME_NUMBER1%TEST_SIZE)));
		}
		
		try {
			fileOperation = new JsonStringFileOperation(TEST_FILENAME);
			fileOperation.saveToFile(taskList);
			Assert.assertEquals("Testing for saving unfinished tasklist", getFileContent(TEST_FILENAME), converter.getJsonStringFromTaskList(taskList));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSavingTwoTasklist() {
		ArrayList<Task> taskList1 = new ArrayList<Task>();
		ArrayList<Task> taskList2 = new ArrayList<Task>();
		for(int i = 0; i < TEST_SIZE; i++){
			taskList1.add(new Task(String.format(NAME_TEST_STRING, i*BIG_PRIME_NUMBER1%TEST_SIZE)));
			taskList2.add(new Task(String.format(NAME_TEST_STRING, i*BIG_PRIME_NUMBER2%TEST_SIZE)));
		}
		
		try {
			fileOperation = new JsonStringFileOperation(TEST_FILENAME);
			fileOperation.saveToFile(taskList1, taskList2);
			Assert.assertEquals("Testing for saving two tasklist", getFileContent(TEST_FILENAME), converter.getJsonStringFromTaskList(taskList1, taskList2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	@After
	public void cleanUp(){
		converter = null;
		new File(TEST_FILENAME).delete();
	}
	
}
