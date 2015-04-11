package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * 
 * @author Huang Weilong A0119392B
 * @version 2015 April 11
 */
public class FileOperation {
	private static final String LOGGER_NAME = "TaskBuddy.log";
	
	private static final String MESSAGE_NULL_FILENAME = "File name cannot be null\n";
	private static final String MESSAGE_INVALID_FILENAME = "File name is invalid\n";
	private static final String MESSAGE_FOLDER_FILENAME = "fileName is a Directory name.\n";
	private static final String MESSAGE_NEW_FILE = "The file is not existed, create new file.\n";
	private static final String MESSAGE_CANNOT_READ = "cannot read the file.\n";
	private static final String MESSAGE_CANNOT_WRITE = "cannot write the file.\n";
	
	private static final char[] invalidChar = {'\\', '?', '%'};
	
	private static final Logger logger = Logger.getLogger(LOGGER_NAME);
	
	private static final String EMPTY_STRING = "";
	private String fileName;

	public FileOperation(String fileName) throws IOException {
		if(isValidFileName(fileName)){
			this.fileName = fileName;
		}
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	public String readFile() throws IOException{
		if (!(new File(fileName).exists())) {
			return EMPTY_STRING;
		}
		try {
			FileInputStream fileInput = new FileInputStream(fileName);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"));
			String readTemp;
			StringBuffer fileContent = new StringBuffer();
			while ((readTemp = br.readLine()) != null) {
				fileContent.append(readTemp);
			}
			return fileContent.toString();
		}catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_READ);
		}
	}
	
	public void saveToFile(String filecontent) throws IOException{
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName, false);
			fileOutput.write(filecontent.getBytes());
			fileOutput.write('\n');
			fileOutput.close();
		}catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_WRITE);
		}
	}
	
	public void renameTo(FileOperation newfile){
		File file = new File(fileName);
		String newFileName = newfile.getFileName();
		if (!file.exists()) {
			this.fileName = newFileName;
		}else{
			file.renameTo(new File(newFileName));
			this.fileName = newFileName;
		}
	}
	
	public void delete(){
		File file = new File(fileName);
		if (file.exists()){
			file.delete();
		}
	}
	
	private boolean isValidFileName(String fileName) throws IOException{
		if(fileName == null)
			throw new IOException(MESSAGE_NULL_FILENAME);
		char lastChar = fileName.charAt(fileName.length()-1);
		for(char invalid: invalidChar){
			if(lastChar == invalid)
				throw new IOException(MESSAGE_INVALID_FILENAME);
		}
		if (new File(fileName).isDirectory()) {
			throw new IOException(MESSAGE_FOLDER_FILENAME);
		}
		if (!(new File(fileName).exists())) {
			logger.info(MESSAGE_NEW_FILE);
		}
		return true;
	}
}
