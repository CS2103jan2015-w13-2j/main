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
 * @author Huang Weilong A0119392B
 * @version 2015 April 11
 */
public class testConfigurationFileOperation {
	private static final int TEST_SIZE = 200;
	private static final int BIG_PRIME_NUMBER1 = 107;
	private static final String NAME_TEST_STRING = "%05d";
	private static final String TEST_FILENAME = "fileStorageTest.json";
	
	private static final String EMPTY_STRING = "";
	
	private static final String MESSAGE_CANNOT_READ = "cannot read the file.\n";
	private static final String MESSAGE_FOLDER_FILENAME = "fileName is a Directory name.\n";
	private static final String MESSAGE_NEW_FILE = "The file is not existed, create new file.\n";
	
	ObjectConverter converter;
	ConfigurationFileOperation fileOperation;
	
	

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
		String fileName = TEST_FILENAME+TEST_FILENAME+TEST_FILENAME;
		ArrayList<String> fileList = new ArrayList<String>();
		for(int i = 0; i < TEST_SIZE; i++){
			fileList.add(String.format(NAME_TEST_STRING, i*BIG_PRIME_NUMBER1%TEST_SIZE));
		}
		
		try {
			fileOperation = new ConfigurationFileOperation(TEST_FILENAME);
			fileOperation.saveConfiguration(fileName,fileList);
			Assert.assertEquals("Testing for saving configuration", getFileContent(TEST_FILENAME), converter.getJsonStringFromConfiguration(fileName, fileList));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetFilePath() {
		String fileName = TEST_FILENAME+TEST_FILENAME+TEST_FILENAME;
		ArrayList<String> fileList = new ArrayList<String>();
		for(int i = 0; i < TEST_SIZE; i++){
			fileList.add(String.format(NAME_TEST_STRING, i*BIG_PRIME_NUMBER1%TEST_SIZE));
		}
		
		try {
			fileOperation = new ConfigurationFileOperation(TEST_FILENAME);
			fileOperation.saveConfiguration(fileName,fileList);
			Assert.assertEquals("Testing for saving configuration", fileName, fileOperation.getLastOpenFilePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetFilePathList() {
		String fileName = TEST_FILENAME+TEST_FILENAME+TEST_FILENAME;
		ArrayList<String> fileList = new ArrayList<String>();
		for(int i = 0; i < TEST_SIZE; i++){
			fileList.add(String.format(NAME_TEST_STRING, i*BIG_PRIME_NUMBER1%TEST_SIZE));
		}
		
		try {
			fileOperation = new ConfigurationFileOperation(TEST_FILENAME);
			fileOperation.saveConfiguration(fileName,fileList);
			ArrayList<String> history = fileOperation.getHistoryFilePath();
			for(int i = 0; i < TEST_SIZE; i++){
				Assert.assertEquals("Testing for saving configuration", fileList.get(i), history.get(i));
			}
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
