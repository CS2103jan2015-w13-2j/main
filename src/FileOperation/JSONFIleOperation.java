package FileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import taskList.Task;

public class JSONFIleOperation {
	private static final int NORMAL_EXIT = 0;
	private static final int ERROR_CANNOT_OPEN_FILE = 1;
	private static final int ERROR_CANNOT_READ_FILE = 2;
	private static final int ERROR_CANNOT_WRITE_FILE = 3;
	private static final int ERROR_DIRRCTORY_NAME = 4;
	
	private static final String KEY_FOR_CONTENT = "content";
	private static final String KEY_FOR_DATE = "date";
	private static final String KEY_FOR_TASKLIST = "taskList";
	
	private static final ArrayList<Task> EMPTY_FILE = new ArrayList<Task>();
	private String fileName;
	
	public JSONFIleOperation(String fileName) {
		this.fileName = fileName;
	}
	

	public ArrayList<Task> readFile() {
		if (new File(fileName).isDirectory()) {
			System.err.println("File Name is a Directory.");
			System.exit(ERROR_DIRRCTORY_NAME);
		}
		if (!(new File(fileName).exists())) {
			return EMPTY_FILE;
		}
		try {
			ArrayList<Task> taskList = new ArrayList<Task>();
			FileInputStream fileInput = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"));
			Task tempTask;
			String readContent = br.readLine();
			if(readContent != null){
				JSONObject jsonFile = new JSONObject(readContent);
				JSONArray taskArray = jsonFile.getJSONArray(KEY_FOR_TASKLIST);
				for(int i = 0; i < taskArray.length(); i++){
					JSONObject jsonTask = (JSONObject) taskArray.get(i);
					tempTask = new Task(jsonTask.getString(KEY_FOR_CONTENT),jsonTask.getString(KEY_FOR_DATE));
					taskList.add(tempTask);
				}
			}
			br.close();
			return taskList;
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open the file.");
			System.exit(ERROR_CANNOT_OPEN_FILE);
		} catch (IOException e) {
			System.err.println("Cannot read the file.");
			System.exit(ERROR_CANNOT_READ_FILE);
		}
		return EMPTY_FILE;
	}
	
	public void saveToFile(ArrayList<Task> taskList){
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName, false);
			JSONObject jsonFile = new JSONObject();
			JSONArray taskArray = new JSONArray();
			
			for (int i = 0; i < taskList.size(); i++) {
				Task tempTask = taskList.get(i);
				JSONObject tempJsonTask = new JSONObject();
				tempJsonTask.put(KEY_FOR_CONTENT, tempTask.getContent());
				tempJsonTask.put(KEY_FOR_DATE, tempTask.getDate());
				taskArray.put(i,tempJsonTask);
			}
			jsonFile.put(KEY_FOR_TASKLIST, taskArray);
			fileOutput.write(jsonFile.toString().getBytes());
			fileOutput.write('\n');
			fileOutput.close();
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open the file.");
			System.exit(ERROR_CANNOT_OPEN_FILE);
		} catch (IOException e) {
			System.err.println("Cannot write the file.");
			System.exit(ERROR_CANNOT_WRITE_FILE);
		}
	}
}
