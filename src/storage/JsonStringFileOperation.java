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
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import parser.DateParser;
import taskList.Task;

public class JsonStringFileOperation {
	private static final String LOGGER_NAME = "TaskBuddy.log";
	
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
	
	private static final String TEMP_FILE_EXTENTION = ".tmp";
	
	private static final Logger logger = Logger.getLogger(LOGGER_NAME);
	
	private static final char[] invalidChar = {'\\', '?', '%'};
	
	private static final String EMPTY_STRING = "";
	
	private ObjectConverter converter;
	
	private static final ArrayList<Task> EMPTY_FILE = new ArrayList<Task>();
	private String fileName;
	private String tempFileName;
	
	/*
	 * If the file name is invalid, will throw IOException
	 */
	public JsonStringFileOperation(String fileName) throws IOException{
		if(isValidFileName(fileName)){
			this.fileName = fileName;
			this.tempFileName = generateTempFileName(fileName);
		}
		this.converter = new ObjectConverter();
	}
	
	public ArrayList<Task> getUnfinishedTaskListFromFile() throws IOException {
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
			logger.info(MESSAGE_READ_FILE);
			return converter.getUnfinishedTaskListFromJsonString(readContent);
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_READ);
		} catch(Exception e){
			System.err.println(MESSAGE_CANNOT_PARSE);
			return EMPTY_FILE;
		}
	}
	
	public ArrayList<Task> getFinishedTaskListFromFile() throws IOException {
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
			logger.info(MESSAGE_READ_FILE);
			return converter.getFinishedTaskListFromJsonString(readContent);
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_READ);
		} catch(Exception e){
			System.err.println(MESSAGE_CANNOT_PARSE);
			return EMPTY_FILE;
		}
	}
	
	public void saveToFile(ArrayList<Task> unfinishedTaskList) throws IOException{
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName, false);
			fileOutput.write(converter.getJsonStringFromTaskList(unfinishedTaskList).getBytes());
			fileOutput.write('\n');
			fileOutput.close();
			logger.info(MEAAGE_SAVE_TO_FILE);
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_WRITE);
		}
	}
	
	public void saveToFile(ArrayList<Task> unfinishedTaskList, ArrayList<Task> finishedTaskList) throws IOException{
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName, false);
			fileOutput.write(converter.getJsonStringFromTaskList(unfinishedTaskList, finishedTaskList).getBytes());
			fileOutput.write('\n');
			fileOutput.close();
			logger.info(MEAAGE_SAVE_TO_FILE);
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_WRITE);
		}
	}
	
	public void saveToTmpFile(ArrayList<Task> unfinishedTaskList) throws IOException{
		try {
			FileOutputStream fileOutput = new FileOutputStream(tempFileName, false);
			fileOutput.write(converter.getJsonStringFromTaskList(unfinishedTaskList).getBytes());
			fileOutput.write('\n');
			fileOutput.close();
			logger.info(MESSAGE_SAVE_TO_TEMP_FILE);
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_WRITE);
		}
	}
	
	public void saveToTmpFile(ArrayList<Task> unfinishedTaskList, ArrayList<Task> finishedTaskList) throws IOException{
		try {
			FileOutputStream fileOutput = new FileOutputStream(tempFileName, false);
			fileOutput.write(converter.getJsonStringFromTaskList(unfinishedTaskList, finishedTaskList).getBytes());
			fileOutput.write('\n');
			fileOutput.close();
			logger.info(MESSAGE_SAVE_TO_TEMP_FILE);
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_WRITE);
		}
	}
	
	public void replaceFileWithTempFile() throws IOException{
		File originalFile = new File(fileName);
		File tempFile = new File(tempFileName);
		if (tempFile.exists()) {
			throw new IOException(MESSAGE_NO_TEMPFILE);
		}
		tempFile.renameTo(originalFile);
		logger.info(MEAAGE_SAVE_TO_FILE);
	}
	
	private boolean isValidFileName(String fileName) throws IOException{
		if(fileName == null)
			throw new IOException(MESSAGE_NULL_FILENAME);
		char lastChar = fileName.charAt(fileName.length()-1);
		for(char invalid: invalidChar){
			if(lastChar == invalid)
				throw new IOException(MESSAGE_INVALID_FILENAME);
		}
		return true;
	}
	
	private String generateTempFileName(String fileName){
		return fileName + TEMP_FILE_EXTENTION;
	}
}
