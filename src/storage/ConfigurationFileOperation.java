package storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * 
 * @author Huang Weilong A0119392B
 * @version 2015 April 11
 */
public class ConfigurationFileOperation{
	private static final String LOGGER_NAME = "TaskBuddy.log";
	
	private static final String MESSAGE_READ_CONFIGURATION_FILE = "Read configuration file successfully.";
	private static final String MESSAGE_SAVE_CONFIGURATION_FILE = "Save configuration file successfully.";
	private static final String MESSAGE_CONFIGURATION_CANNOT_PARSE = "Cannot parse the file with JSON format, create new configuration file.\n";
	
	private static final String CONFIGURATION_FILE_NAME = "/.configuration";
	private static final String USER_PATH = System.getProperty("user.home");
	private static final String DEFAULT_FILE_NAME = "TaskBuddy.txt";
	private static final String EMPTY_STRING = "";
	
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
		String fileContent = configurationFile.readFile();
		logger.info(MESSAGE_READ_CONFIGURATION_FILE);
		try{
			String filePath = converter.getFilePathFromJsonString(fileContent);
			if(isNull(filePath) || filePath.equals(EMPTY_STRING)){
				filePath = DEFAULT_FILE_NAME;
			}
			return filePath;
		}catch(Exception e){
			logger.info(MESSAGE_CONFIGURATION_CANNOT_PARSE);
			return DEFAULT_FILE_NAME;
		}
	}
	
	public ArrayList<String> getHistoryFilePath() throws IOException {
		String fileContent = configurationFile.readFile();
		logger.info(MESSAGE_READ_CONFIGURATION_FILE);
		try{
			ArrayList<String> x = converter.getFilePathListFromJsonString(fileContent);
			return x;
		}catch(Exception e){
			ArrayList<String> x = new ArrayList<String>();
			x.add(DEFAULT_FILE_NAME);
			return x;
		}
	}
	
	public void saveConfiguration(String fileName, ArrayList<String> filePathList) throws IOException {
		configurationFile.saveToFile(converter.getJsonStringFromConfiguration(fileName, filePathList));
		logger.info(MESSAGE_SAVE_CONFIGURATION_FILE);
	}
	
	private boolean isNull(Object obj){
		return (obj == null);
	}
}
