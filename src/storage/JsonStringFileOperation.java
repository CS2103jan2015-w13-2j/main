package storage;

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

public class JsonStringFileOperation {
	private static final int NORMAL_EXIT = 0;
	private static final int ERROR_CANNOT_OPEN_FILE = 1;
	private static final int ERROR_CANNOT_READ_FILE = 2;
	private static final int ERROR_CANNOT_WRITE_FILE = 3;
	private static final int ERROR_DIRRCTORY_NAME = 4;
	
	private static ObjectStringConverter converter;
	
	
	
	private static final ArrayList<Task> EMPTY_FILE = new ArrayList<Task>();
	private String fileName;
	
	public JsonStringFileOperation(String fileName) {
		this.fileName = fileName;
		this.converter = new ObjectStringConverter();
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
			
			FileInputStream fileInput = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"));
			String readContent = br.readLine();
			if(readContent != null){
				
			}
			br.close();
			return converter.JsonStringToTaskList(readContent);
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
			fileOutput.write(converter.taskListToJsonString(taskList).getBytes());
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
