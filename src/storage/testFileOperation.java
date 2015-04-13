package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import taskList.Task;

//@author A0119392B
/**
 * 
 * @version 2015 April 11
 */
public class testFileOperation {
	private static final String TEST_STRING = "123456890-=QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm,./[];'!@#$%^&*()_+";
	private static final String TEST_FILENAME = "fileStorageTest";
	
	private static final String EMPTY_STRING = "";
	private static final String MESSAGE_CANNOT_READ = "cannot read the file.\n";
	private static final String MESSAGE_FOLDER_FILENAME = "fileName is a Directory name.\n";
	private static final String MESSAGE_NEW_FILE = "The file is not existed, create new file.\n";
	
	ObjectConverter converter;
	FileOperation fileOperation;

	@Before
	public void init(){
	    converter = new ObjectConverter();
	}

	private String getFileContent(String fileName) throws IOException{
		if (new File(fileName).isDirectory()) {
			throw new IOException(MESSAGE_FOLDER_FILENAME);
		}
		if (!(new File(fileName).exists())) {
			System.err.println(MESSAGE_NEW_FILE);
			return EMPTY_STRING;
		}
		try {
			FileInputStream fileInput = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"));
			String readContent = br.readLine();
			if(readContent == null){
				readContent = EMPTY_STRING;
			}
			br.close();
			return readContent;
		} catch (IOException e) {
			throw new IOException(MESSAGE_CANNOT_READ);
		}
	}
	
	@Test
	public void testSaving() {
		try {
			fileOperation = new FileOperation(TEST_FILENAME);
			fileOperation.saveToFile(TEST_STRING);
			Assert.assertEquals("Testing for saving unfinished tasklist", getFileContent(TEST_FILENAME), TEST_STRING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReading() {
		try {
			fileOperation = new FileOperation(TEST_FILENAME);
			fileOperation.saveToFile(TEST_STRING);
			Assert.assertEquals("Testing for saving unfinished tasklist", getFileContent(TEST_FILENAME), fileOperation.readFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	@After
	public void cleanUp(){
		converter = null;
		new File(TEST_FILENAME).delete();
	}
	
}
