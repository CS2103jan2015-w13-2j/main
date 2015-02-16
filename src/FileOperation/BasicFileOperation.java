package FileOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BasicFileOperation {
	
	private static final int NORMAL_EXIT = 0;
	private static final int ERROR_CANNOT_OPEN_FILE = 1;
	private static final int ERROR_CANNOT_READ_FILE = 2;
	private static final int ERROR_CANNOT_WRITE_FILE = 3;
	private static final int ERROR_DIRRCTORY_NAME = 4;
	
	private static final ArrayList<String> EMPTY_FILE = new ArrayList<String>();
	private static String fileName;

	public BasicFileOperation(String fileName) {
		this.fileName = fileName;
	}
	
	public ArrayList<String> readFile() {
		if (new File(fileName).isDirectory()) {
			System.err.println("File Name is a Directory.");
			System.exit(ERROR_DIRRCTORY_NAME);
		}
		if (!(new File(fileName).exists())) {
			return EMPTY_FILE;
		}
		try {
			ArrayList<String> fileContent = new ArrayList<String>();
			FileInputStream fileInput = new FileInputStream(fileName);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fileInput, "UTF-8"));
			String content;
			while ((content = br.readLine()) != null) {
				fileContent.add(content);
			}
			return fileContent;
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open the file.");
			System.exit(ERROR_CANNOT_OPEN_FILE);
		} catch (IOException e) {
			System.err.println("Cannot read the file.");
			System.exit(ERROR_CANNOT_READ_FILE);
		}
		return EMPTY_FILE;
	}
	
	public void saveToFile(ArrayList<String> fileContent){
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName, false);
			for (String content : fileContent) {
				fileOutput.write(content.getBytes());
				fileOutput.write('\n');
			}
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
