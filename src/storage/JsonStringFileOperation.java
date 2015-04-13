package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import taskList.Task;

//@author A0119392B
/**
 * Reading and loading task list in file with JSON format.
 * 
 * @version 2015 April 11
 */
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
	
	private FileOperation savedFile;
	private FileOperation tempSavedFile;
	
	/**
	 * @param fileName
	 * @throws IOException, If the file name is invalid or null or dictionary name, throw IOException
	 */
	public JsonStringFileOperation(String fileName) throws IOException{
		String tempFileName = generateTempFileName(fileName);
		savedFile = new FileOperation(fileName);
		tempSavedFile = new FileOperation(tempFileName);
		this.converter = new ObjectConverter();
	}
	
	/**
	 * Return unfinished task list which is read from file.
	 * If there is any parsing error, return the empty task list.
	 * @return unfinished task list which is read from file.
	 * @throws IOException, If the file cannot be read, throw IOException.
	 */
	public ArrayList<Task> getUnfinishedTaskListFromFile() throws IOException {
		String jsonString = savedFile.readFile();
		logger.info(MESSAGE_READ_FILE);
		try{
			ArrayList<Task> unfinishedTaskList = converter.getUnfinishedTaskListFromJsonString(jsonString);
			return unfinishedTaskList;
		}catch(Exception e){
			logger.info(MESSAGE_CANNOT_PARSE);
			return EMPTY_UNFINISHED_TASKLIST;
		}
		
	}
	
	/**
	 * Return finished task list which is read from file.
	 * If there is any parsing error, return the empty task list.
	 * @return finished task list which is read from file.
	 * @throws IOException, If the file cannot be read, throw IOException.
	 */
	public ArrayList<Task> getFinishedTaskListFromFile() throws IOException {
		String jsonString = savedFile.readFile();
		logger.info(MESSAGE_READ_FILE);
		try{
			ArrayList<Task> finishedTaskList = converter.getFinishedTaskListFromJsonString(jsonString);
			return finishedTaskList;
		}catch(Exception e){
			logger.info(MESSAGE_CANNOT_PARSE);
			return EMPTY_FINISHED_TASKLIST;
		}
	}
	
	/**
	 * Write the unfinished task list into original file.
	 * If the file existed, just override it.
	 * @param unfinishedTaskList
	 * @throws IOException, If the file cannot be written, throw IOException.
	 */
	public void saveToFile(ArrayList<Task> unfinishedTaskList) throws IOException{
		savedFile.saveToFile(converter.getJsonStringFromTaskList(unfinishedTaskList));
		logger.info(MESSAGE_SAVE_FILE);
	}
	
	/**
	 * Write the unfinished and finished task list into original file.
	 * If the file existed, just override it.
	 * @param unfinishedTaskList
	 * @param finishedTaskList
	 * @throws IOException, If the file cannot be written, throw IOException.
	 */
	public void saveToFile(ArrayList<Task> unfinishedTaskList, ArrayList<Task> finishedTaskList) throws IOException{		
		savedFile.saveToFile(converter.getJsonStringFromTaskList(unfinishedTaskList, finishedTaskList));
		logger.info(MESSAGE_SAVE_FILE);
	}
	
	/**
	 * Write the unfinished and finished task list into temporary file.
	 * If the file existed, just override it.
	 * @param unfinishedTaskList
	 * @throws IOException, If the file cannot be written, throw IOException.
	 */
	public void saveToTmpFile(ArrayList<Task> unfinishedTaskList) throws IOException{
		tempSavedFile.saveToFile(converter.getJsonStringFromTaskList(unfinishedTaskList));
		logger.info(MESSAGE_SAVE_TEMP_FILE);
	}
	
	/**
	 * Write the unfinished and finished task list into temporary file.
	 * If the file existed, just override it.
	 * @param unfinishedTaskList
	 * @param finishedTaskList
	 * @throws IOException, If the file cannot be written, throw IOException.
	 */
	public void saveToTmpFile(ArrayList<Task> unfinishedTaskList, ArrayList<Task> finishedTaskList) throws IOException{
		tempSavedFile.saveToFile(converter.getJsonStringFromTaskList(unfinishedTaskList, finishedTaskList));
		logger.info(MESSAGE_SAVE_TEMP_FILE);
	}
	
	/**
	 * replace the original file with the temporary file
	 * @throws IOException
	 */
	public void replaceFileWithTempFile() throws IOException{
		savedFile.delete();
		tempSavedFile.renameTo(tempSavedFile);
		logger.info(MESSAGE_SAVE_FILE);
	}
	
	/**
	 * @param fileName
	 * @return the fileName with temporary file extension
	 */
	private String generateTempFileName(String fileName){
		return fileName + TEMP_FILE_EXTENTION;
	}
}
