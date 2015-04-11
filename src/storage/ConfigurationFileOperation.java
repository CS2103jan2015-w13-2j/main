package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import taskList.Task;

public class ConfigurationFileOperation{
	private static final String LOGGER_NAME = "TaskBuddy.log";
	
	private static final String MESSAGE_READ_FILE = "Read file successfully.";
	private static final String MEAAGE_SAVE_TO_FILE = "Save to file successfully.";
	
	private static final String CONFIGURATION_FILE_NAME = "/.configuration";
	private static final String USER_PATH = System.getProperty("user.home");
	private static final String DEFAULT_FILE_NAME = "TaskBuddy.txt";
	
	
	private static final Logger logger = Logger.getLogger(LOGGER_NAME);
	
	private ObjectConverter converter;

	FileOperation configurationFile;
	
	/*
	 * If the file name is invalid, will throw IOException
	 */
	public ConfigurationFileOperation() throws IOException{
		configurationFile = new FileOperation(USER_PATH + CONFIGURATION_FILE_NAME);
		this.converter = new ObjectConverter();
	}
	
	public String getLastOpenFilePath() throws IOException {
		return DEFAULT_FILE_NAME;
		
	}
	
	public ArrayList<String> getHistoryFilePath() throws IOException {
		ArrayList<String> x = new ArrayList<String>();
		x.add(DEFAULT_FILE_NAME);
		return x;
	}
	

}
