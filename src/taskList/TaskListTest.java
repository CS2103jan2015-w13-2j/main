package taskList;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;


public class TaskListTest {
	//String used to test CE1
	private static final String TEST_FILENAME = "TestFileName.txt";
	private static final String CLEAR_COMMAND = "clear";
	private static final String DISPLAY_COMMAND = "display";
	private static final String ADD_COMMAND_1 = "add jumped over the moon";
	private static final String ADD_COMMAND_2 = "add little brown fox";
	private static final String DELETE_COMMAND_1 = "delete 1";
	private static final String SORT_COMMAND = "sort title";
	private static final String SEARCH_COMMAND_1 = "search word1";
	private static final String SEARCH_COMMAND_2 = "search word2";
	private static final String SEARCH_COMMAND_NOT_EXIST = "search word22222";
	
	//Excepted Answer to CE1
	private static final String EXCEPTED_ANSWER_CLEAR = "";
	private static final String EXCEPTED_ANSWER_ADD_1 = "1. little brown fox\n";
	private static final String EXCEPTED_ANSWER_ADD_2 = "1. little brown fox\n2. jumped over the moon\n";
	private static final String EXCEPTED_ANSWER_DELETE_1 = "1. jumped over the moon\n";
	private static final String EXCEPTED_ANSWER_DISPLAY = "1. jumped over the moon\n";
	
	//Strings used for sort test
	private static final String A_STRING = "a this is a string starts with a";
	private static final String B_STRING = "b this is a string starts with b";
	private static final String C_STRING = "c this is a string starts with c";
	private static final String D_STRING = "d this is a string starts with d";
	private static final String E_STRING = "e this is a string starts with e";
	private static final String F_STRING = "f this is a string starts with f";
	private static final String G_STRING = "g this is a string starts with g";
	private static final String EXCEPTED_ANSWER_SORT = "1. a this is a string starts with a\n2. b this is a string starts with b\n3. c this is a string starts with c\n4. d this is a string starts with d\n5. e this is a string starts with e\n6. f this is a string starts with f\n7. g this is a string starts with g\n";
	//Strings used for search test
	private static final String SEARCH_STRING_1 = "word1 word2 word3 word4 word5 word6 word7 word8";
	private static final String SEARCH_STRING_2 = "word1 word3 word5 word7";
	private static final String SEARCH_STRING_3 = "word2 word4 word6 word8";
	private static final String SEARCH_STRING_4 = "word1 word2 word3 word4";
	private static final String SEARCH_STRING_5 = "word5 word6 word7 word8";
	private static final String SEARCH_STRING_6 = "word1 word6 word7 word8";
	private static final String SEARCH_STRING_7 = "word2 word3 word4 word5";
	private static final String SEARCH_STRING_8 = "word4 word6 word7 word8";
	private static final String SEARCH_STRING_9 = "word1 word3 word5 word6";
	private static final String SEARCH_STRING_10 = "word1 word3 word7 word8";
	private static final String NOT_EXIST = "No results found\n";
	
	
	
	@Test
	public void testBasicFunctions() throws NullPointerException, IOException {
		
		//Set up test case
		String[] args = new String[1];
		args[0] = TEST_FILENAME;
		TaskManager taskList = new TaskManager(args[0]);
		
		//test clear
		testOneCommand("simple clear",EXCEPTED_ANSWER_CLEAR, CLEAR_COMMAND,taskList);
		
		//test add
		testOneCommand("simple add1",EXCEPTED_ANSWER_ADD_1, ADD_COMMAND_2,taskList);
		
		//test add
		testOneCommand("simple add2",EXCEPTED_ANSWER_ADD_2, ADD_COMMAND_1,taskList);
		
		//test delete
		testOneCommand("simple delete 1",EXCEPTED_ANSWER_DELETE_1, DELETE_COMMAND_1,taskList);
		
		//test delete negative 
		testOneCommand("simple delete 2",EXCEPTED_ANSWER_DELETE_1, "delete -10",taskList);
		
				
		//test delete large index 
		testOneCommand("simple delete 4",EXCEPTED_ANSWER_DELETE_1, "delete 100",taskList);
		
		//test display
		testOneCommand("simple display",EXCEPTED_ANSWER_DISPLAY, DISPLAY_COMMAND,taskList);
		
	}
	@Test
	public void testSearch() throws NullPointerException, IOException {
		
		//set up testcase
		String[] args = new String[1];
		args[0] = TEST_FILENAME;
		TaskManager taskList = new TaskManager(args[0]);
		
		//testcase 1
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+SEARCH_STRING_1);
		taskList.executeCommand("add "+SEARCH_STRING_2);
		taskList.executeCommand("add "+SEARCH_STRING_3);
		taskList.executeCommand("add "+SEARCH_STRING_4);
		taskList.executeCommand("add "+SEARCH_STRING_5);
		taskList.executeCommand("add "+SEARCH_STRING_6);
		taskList.executeCommand("add "+SEARCH_STRING_7);
		taskList.executeCommand("add "+SEARCH_STRING_8);
		taskList.executeCommand("add "+SEARCH_STRING_9);
		taskList.executeCommand("add "+SEARCH_STRING_10);
		String exceptedSearchResult = String.format("1. %s\n2. %s\n3. %s\n4. %s\n5. %s\n6. %s\n", SEARCH_STRING_1,SEARCH_STRING_2,SEARCH_STRING_4,SEARCH_STRING_6,SEARCH_STRING_9,SEARCH_STRING_10);
		testOneCommand("simple search",exceptedSearchResult, SEARCH_COMMAND_1,taskList);
	
		//testcase 2
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+SEARCH_STRING_1);
		taskList.executeCommand("add "+SEARCH_STRING_2);
		taskList.executeCommand("add "+SEARCH_STRING_3);
		taskList.executeCommand("add "+SEARCH_STRING_4);
		taskList.executeCommand("add "+SEARCH_STRING_5);
		taskList.executeCommand("add "+SEARCH_STRING_6);
		taskList.executeCommand("add "+SEARCH_STRING_7);
		taskList.executeCommand("add "+SEARCH_STRING_8);
		taskList.executeCommand("add "+SEARCH_STRING_9);
		taskList.executeCommand("add "+SEARCH_STRING_10);
		exceptedSearchResult = String.format("1. %s\n2. %s\n3. %s\n4. %s\n", SEARCH_STRING_1,SEARCH_STRING_3,SEARCH_STRING_4,SEARCH_STRING_7);
		testOneCommand("simple search",exceptedSearchResult, SEARCH_COMMAND_2,taskList);
	
		//testcase 3
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+SEARCH_STRING_1);
		taskList.executeCommand("add "+SEARCH_STRING_2);
		taskList.executeCommand("add "+SEARCH_STRING_3);
		taskList.executeCommand("add "+SEARCH_STRING_4);
		taskList.executeCommand("add "+SEARCH_STRING_5);
		taskList.executeCommand("add "+SEARCH_STRING_6);
		taskList.executeCommand("add "+SEARCH_STRING_7);
		taskList.executeCommand("add "+SEARCH_STRING_8);
		taskList.executeCommand("add "+SEARCH_STRING_9);
		taskList.executeCommand("add "+SEARCH_STRING_10);
		testOneCommand("simple search",NOT_EXIST, SEARCH_COMMAND_NOT_EXIST,taskList);
	}
	
	@Test
	public void testSort() throws NullPointerException, IOException{
		//set up testcase
		String[] args = new String[1];
		args[0] = TEST_FILENAME;
		TaskManager taskList = new TaskManager(args[0]);
		
		//testcase 1
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+G_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+A_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		ArrayList<String> exceptedList = new ArrayList<String>();
		exceptedList.add(A_STRING);
		exceptedList.add(B_STRING);
		exceptedList.add(C_STRING);
		exceptedList.add(D_STRING);
		exceptedList.add(E_STRING);
		exceptedList.add(F_STRING);
		exceptedList.add(G_STRING);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
	
		//testcase 2
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+A_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+G_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
	

	}
	
	@Test
	public void testUndo() throws NullPointerException, IOException{
		//set up testcase
		String[] args = new String[1];
		args[0] = TEST_FILENAME;
		TaskManager taskList = new TaskManager(args[0]);
		
		//testcase 1
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+G_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+A_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		ArrayList<String> exceptedList = new ArrayList<String>();
		exceptedList.add(A_STRING);
		exceptedList.add(B_STRING);
		exceptedList.add(C_STRING);
		exceptedList.add(D_STRING);
		exceptedList.add(E_STRING);
		exceptedList.add(F_STRING);
		exceptedList.add(G_STRING);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
		taskList.executeCommand(CLEAR_COMMAND);
		taskList.undo();
		assertEquals("undo test", exceptedList, taskList.getTaskList());
		
		//testcase 2
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+A_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+G_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
		taskList.executeCommand(CLEAR_COMMAND);
		taskList.undo();
		assertEquals("undo test", exceptedList, taskList.getTaskList());

	}
	
	@Test
	public void testRedo() throws NullPointerException, IOException{
		//set up testcase
		String[] args = new String[1];
		args[0] = TEST_FILENAME;
		TaskManager taskList = new TaskManager(args[0]);
		
		//testcase 1
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+G_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+A_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		ArrayList<String> exceptedList = new ArrayList<String>();
		exceptedList.add(A_STRING);
		exceptedList.add(B_STRING);
		exceptedList.add(C_STRING);
		exceptedList.add(D_STRING);
		exceptedList.add(E_STRING);
		exceptedList.add(F_STRING);
		exceptedList.add(G_STRING);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
		taskList.undo();
		taskList.redo();
		assertEquals("undo test", exceptedList, taskList.getTaskList());
		
		//testcase 2
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+A_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+G_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
		taskList.undo();
		taskList.redo();
		assertEquals("undo test", exceptedList, taskList.getTaskList());

	}
	
	@Test
	public void testImport() throws NullPointerException, IOException{
		//set up testcase
		String[] args = new String[1];
		args[0] = TEST_FILENAME;
		TaskManager taskList = new TaskManager(args[0]);
		
		//testcase 1
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+G_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+A_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		ArrayList<String> exceptedList = new ArrayList<String>();
		exceptedList.add(A_STRING);
		exceptedList.add(B_STRING);
		exceptedList.add(C_STRING);
		exceptedList.add(D_STRING);
		exceptedList.add(E_STRING);
		exceptedList.add(F_STRING);
		exceptedList.add(G_STRING);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
		taskList.undo();
		taskList.redo();
		assertEquals("undo test", exceptedList, taskList.getTaskList());
		
		//testcase 2
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+A_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+G_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
		taskList.undo();
		taskList.redo();
		assertEquals("undo test", exceptedList, taskList.getTaskList());
		
		TaskManager taskList2 = new TaskManager("Another File");
		taskList2.executeCommand("import "+args[0]);
		assertEquals("undo test", exceptedList, taskList2.getTaskList());
		
	}
	
	@Test
	public void testExport() throws NullPointerException, IOException{
		//set up testcase
		String[] args = new String[1];
		args[0] = TEST_FILENAME;
		TaskManager taskList = new TaskManager(args[0]);
		
		//testcase 1
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+G_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+A_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		ArrayList<String> exceptedList = new ArrayList<String>();
		exceptedList.add(A_STRING);
		exceptedList.add(B_STRING);
		exceptedList.add(C_STRING);
		exceptedList.add(D_STRING);
		exceptedList.add(E_STRING);
		exceptedList.add(F_STRING);
		exceptedList.add(G_STRING);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
		taskList.undo();
		taskList.redo();
		assertEquals("undo test", exceptedList, taskList.getTaskList());
		
		//testcase 2
		taskList.executeCommand("clear");
		taskList.executeCommand("add "+A_STRING);
		taskList.executeCommand("add "+B_STRING);
		taskList.executeCommand("add "+C_STRING);
		taskList.executeCommand("add "+D_STRING);
		taskList.executeCommand("add "+E_STRING);
		taskList.executeCommand("add "+F_STRING);
		taskList.executeCommand("add "+G_STRING);
		testOneCommand("simple sort",EXCEPTED_ANSWER_SORT, SORT_COMMAND,taskList);
		assertEquals("Change to todoList", exceptedList, taskList.getTaskList());
		taskList.undo();
		taskList.redo();
		assertEquals("undo test", exceptedList, taskList.getTaskList());
		
		TaskManager taskList2 = new TaskManager("Another File");
		taskList.executeCommand("export anotherfile");
		taskList2.executeCommand("import anotherfile");
		assertEquals("undo test", exceptedList, taskList2.getTaskList());
		
	}
	private void testOneCommand(String description, String expected, String command, TaskManager taskList) throws NullPointerException, IOException {
		taskList.executeCommand(command);
		System.out.println("Debug");
		System.out.println(expected);
		System.out.println(taskList.getAllTitles());
		assertEquals(description, expected, taskList.getAllTitles()); 
	}
	
	

}