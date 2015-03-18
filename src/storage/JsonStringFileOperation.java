package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import parser.DateParser;
import taskList.Task;

public class JsonStringFileOperation {
	private static final String MESSAGE_NULL_FILENAME = "File name cannot be null";
	private static final String MESSAGE_INVALID_FILENAME = "File name is invalid";
	private static final String MESSAGE_CANNOT_READ = "cannot read the file.\n";
	private static final String MESSAGE_CANNOT_WRITE = "cannot write the file.\n";
	private static final String MESSAGE_FOLDER_FILENAME = "fileName is a Directory name.\n";
	private static final String MESSAGE_CANNOT_PARSE = "Cannot parse the file with JSON format, return empty task list.\n";
	private static final String MESSAGE_NEW_FILE = "The file is not existed, create new file.\n";
	
	private static final char[] invalidChar = {'\\', '?', '%'};
	
	private static final String EMPTY_STRING = "";
	
	private ObjectConverter converter;
	
	private static final ArrayList<Task> EMPTY_FILE = new ArrayList<Task>();
	private String fileName;
	
	public JsonStringFileOperation(String fileName) {
		if(isValidFileName(fileName))
			this.fileName = fileName;
		this.converter = new ObjectConverter();
	}
	
	public ArrayList<Task> readFile() throws IOException {
		if (new File(fileName).isDirectory()) {
			throw new IOException(MESSAGE_FOLDER_FILENAME);
		}
		if (!(new File(fileName).exists())) {
			System.err.println(MESSAGE_NEW_FILE);
			return EMPTY_FILE;
		}
		try {
			FileInputStream fileInput = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"));
			String readContent = br.readLine();
			if(readContent == null){
				readContent = EMPTY_STRING;
			}
			br.close();
			return converter.getTaskListFromJsonString(readContent);
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_READ);
		} catch(Exception e){
			System.err.println(MESSAGE_CANNOT_PARSE);
			return EMPTY_FILE;
		}
	}
	
	public void saveToFile(ArrayList<Task> taskList) throws IOException{
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName, false);
			fileOutput.write(converter.getJsonStringFromTaskList(taskList).getBytes());
			fileOutput.write('\n');
			fileOutput.close();
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_WRITE);
		}
	}
	
	private boolean isValidFileName(String fileName){
		if(fileName == null)
			throw new NullPointerException(MESSAGE_NULL_FILENAME);
		char lastChar = fileName.charAt(fileName.length()-1);
		for(char invalid: invalidChar){
			if(lastChar == invalid)
				throw new NullPointerException(MESSAGE_INVALID_FILENAME);
		}
		return true;
	}
}
