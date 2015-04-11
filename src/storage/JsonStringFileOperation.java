package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import taskList.Task;

public class JsonStringFileOperation{
	private static final String LOGGER_NAME = "TaskBuddy.log";
	
	private static final String MESSAGE_READ_FILE = "Read file successfully.";
	private static final String MESSAGE_SAVE_FILE = "Save to file successfully.";
	private static final String MESSAGE_SAVE_TEMP_FILE = "Save to temp file successfully.";
	private static final String MESSAGE_CANNOT_PARSE = "Cannot parse the file with JSON format, return empty task list.\n";
	
	private static final String TEMP_FILE_EXTENTION = ".tmp";
	
	private static final Logger logger = Logger.getLogger(LOGGER_NAME);
	
	private ObjectConverter converter;
	
	private static final ArrayList<Task> EMPTY_UNFINISHED_TASKLIST = new ArrayList<Task>();
	private static final ArrayList<Task> EMPTY_FINISHED_TASKLIST = new ArrayList<Task>();
	
	FileOperation savedFile;
	FileOperation tempSavedFile;
	
	/*
	 * If the file name is invalid, will throw IOException
	 */
	public JsonStringFileOperation(String fileName) throws IOException{
		String tempFileName = generateTempFileName(fileName);
		savedFile = new FileOperation(fileName);
		tempSavedFile = new FileOperation(tempFileName);
		this.converter = new ObjectConverter();
	}
	
	public ArrayList<Task> getUnfinishedTaskListFromFile() throws IOException {
		String jsonString = savedFile.readFile();
		logger.info(MESSAGE_READ_FILE);
		try{
			ArrayList<Task> x = converter.getUnfinishedTaskListFromJsonString(jsonString);
			return x;
		}catch(Exception e){
			logger.info(MESSAGE_CANNOT_PARSE);
			return EMPTY_UNFINISHED_TASKLIST;
		}
		
	}
	
	public ArrayList<Task> getFinishedTaskListFromFile() throws IOException {
		String jsonString = savedFile.readFile();
		logger.info(MESSAGE_READ_FILE);
		try{
			ArrayList<Task> x = converter.getFinishedTaskListFromJsonString(jsonString);
			return x;
		}catch(Exception e){
			logger.info(MESSAGE_CANNOT_PARSE);
			return EMPTY_FINISHED_TASKLIST;
		}
	}
	
	public void saveToFile(ArrayList<Task> unfinishedTaskList) throws IOException{
		savedFile.saveToFile(converter.getJsonStringFromTaskList(unfinishedTaskList));
		logger.info(MESSAGE_SAVE_FILE);
	}
	
	public void saveToFile(ArrayList<Task> unfinishedTaskList, ArrayList<Task> finishedTaskList) throws IOException{		
		savedFile.saveToFile(converter.getJsonStringFromTaskList(unfinishedTaskList, finishedTaskList));
		logger.info(MESSAGE_SAVE_FILE);
	}
	
	public void saveToTmpFile(ArrayList<Task> unfinishedTaskList) throws IOException{
		tempSavedFile.saveToFile(converter.getJsonStringFromTaskList(unfinishedTaskList));
		logger.info(MESSAGE_SAVE_TEMP_FILE);
	}
	
	public void saveToTmpFile(ArrayList<Task> unfinishedTaskList, ArrayList<Task> finishedTaskList) throws IOException{
		tempSavedFile.saveToFile(converter.getJsonStringFromTaskList(unfinishedTaskList, finishedTaskList));
		logger.info(MESSAGE_SAVE_TEMP_FILE);
	}
	
	public void replaceFileWithTempFile() throws IOException{
		savedFile.delete();
		tempSavedFile.renameTo(tempSavedFile);
		logger.info(MESSAGE_SAVE_FILE);
	}
	
	private String generateTempFileName(String fileName){
		return fileName + TEMP_FILE_EXTENTION;
	}
}
