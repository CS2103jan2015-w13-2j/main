package taskList;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;
import storage.JsonStringFileOperation;
import taskList.Task;
import parser.Parser;

public class TaskList {
	private static final String FILE_NAME_DEFAULT = "default_output.txt";
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %s is ready for use\n";
	private static final String MESSAGE_INVALID_COMMOND = "Invalid operation.\n";
	private static final String MESSAGE_EMPTY_FILE = "%s is empty\n";
	private static final String MESSAGE_ADD_OPERATION = "added to %s: \"%s\"\n";
	private static final String MESSAGE_DELETE_OPERATION = "deleted from %s: \"%s\"\n";
	private static final String MESSAGE_DELETE_OPERATION_FAILURE = "index is not valid for delete from %s %s\n ";
	private static final String MESSAGE_CLEAR_OPERATION = "all content deleted from %s\n";
	private static final String MESSAGE_DISPLAY_OPERATION = "%d. %s\n";
	private static final String MESSAGE_MODIFY_OPERATION = "modify to %s: \"%s\"\n";
	private static final String MESSAGE_MODIFY_OPERATION_FAILURE = "index is not valid for modify from %s %s\n";
	
	private static final int OPERATION_UNKNOWN = 0;
	private static final int OPERATION_ADD = 1;
	private static final int OPERATION_DELETE = 2;
	private static final int OPERATION_CLEAR = 3;
	private static final int OPERATION_DISPLAY = 4;
	private static final int OPERATION_EXIT = 5;
	private static final int OPERATION_MODIFY = 6;
	private static final int OPERATION_UNDO = 7;
	private static final int OPERATION_REDO = 8;
	private static final int OPERATION_SORT = 9;
	private static final int OPERATION_SEARCH = 10;
	private static final int NORMAL_EXIT = 0;

	private static Scanner sc;
	private static String fileName;
	private static JsonStringFileOperation fo;
	private static ArrayList<Task> taskList;
	private static ArrayList<Task> searchResult;
	//mode == 0 means the result shown in screen is taskList,
	//mode == 1 means the result shown in screen is searchResult
	private static int mode;
	private static Parser bp;
	private static Undo undo;
	private static ArrayList<String> feedBack = new ArrayList<String>();
	private static String name = TaskList.class.getName(); 
	private static Logger log = Logger.getLogger(name);// <= (2)  
	public TaskList(String inputFileName){
		mode = 0;
		fileName = inputFileName;
		fo = new JsonStringFileOperation(fileName);
		feedBack.clear();
		try{
			taskList = fo.readFile();
			searchResult = new ArrayList<Task>();
			undo = new Undo(taskList);
		}catch(Exception e){
			log.info("There is a command invalid error");
			feedBack.add("Cannot open the file correctly");
		}
		
		bp = new Parser();
		//Add in a initParser() command.
	}
	
	/*
	 * print messages
	 */
	private static void showMessage(String message, String content) {
		feedBack.add(String.format(message, fileName, content));
		System.out.print(String.format(message, fileName, content));
	}

	/*
	 * print single messages
	 */
	private static void showMessage(String message) {
		feedBack.add(message);
		System.out.print(message);
	}

	
	
	/*
	 * show the file content in format
	 */
	private static void showFileContent(int index, String content) {
		System.out.print(String.format(MESSAGE_DISPLAY_OPERATION, index,
				content));
	}
	
	public static void startWaitingForCommand() throws NullPointerException, IOException{
		sc = new Scanner(System.in);
		String inputCommand;
		while (true) {
			System.out.print("Command: ");
			inputCommand = sc.nextLine();
			executeCommand(inputCommand);
		}
	}
	
	public static void executeCommand(String command) throws NullPointerException, IOException {
		switch (bp.getOperation(command)) {
		case OPERATION_ADD:
			add(command);
			break;
		case OPERATION_DELETE:
			delete(command);
			break;
		case OPERATION_DISPLAY:
			display();
			break;
		case OPERATION_CLEAR:
			clear();
			break;
		case OPERATION_MODIFY:
			modify(command);
			break;
		case OPERATION_UNDO:
			undo();
			break;
		case OPERATION_REDO:
			redo();
			break;
		case OPERATION_SORT:
			sort();
			break;
		case OPERATION_SEARCH:
			search(command);
			break;
		case OPERATION_EXIT:
			exit();
			break;
		default:
			assert(false);
			feedBack.add("No such command");
		}
	}
	
	/*
	 * show the input operation is invalid
	 */
	private static void unknownOperation() {
		System.err.print(MESSAGE_INVALID_COMMOND);
	}
	
	/*
	 * add new content to arraylist, but do not actully store to file
	 */
	private static void add(String command) throws NullPointerException, IOException {
		assert(bp.isValid(command));
		String content = bp.getTitle(command);
		Date date = bp.getDate(command);
		System.out.println(date);
		Date deadLine = bp.getDeadline(command);
		String venue = bp.getVenue(command);
		showMessage(MESSAGE_ADD_OPERATION, content);
		taskList.add(new Task(content,date,deadLine,venue));
		saveFile();
		undo.add(taskList);
	}
	
	/*
	 * delete content in arraylist, but do not actully store to file
	 */
	private static void delete(String command) {
		String content = "";
		if (command.indexOf(' ') != -1) {
			content = command.substring(command.indexOf(' ') + 1);
		}
		int removeIndex = Integer.valueOf(content);
		if (removeIndex < 0 || removeIndex > taskList.size()) {
			showMessage(MESSAGE_DELETE_OPERATION_FAILURE, "");
			return;
		}
		showMessage(MESSAGE_DELETE_OPERATION, taskList.get(removeIndex - 1).getContent());
		taskList.remove(removeIndex - 1);
		saveFile();
		undo.add(taskList);
	}

	/*
	 * display the content in arraylist, which is the real-time file content
	 */
	private static void display() {
		mode = 0;
		if (taskList.size() == 0) {
			showMessage(MESSAGE_EMPTY_FILE, null);
			return;
		}
		int i = 1;
		for (Task content : taskList) {
			showFileContent(i, content.getContent());
			i += 1;
		}
	}
	
	/*
	 * modify the content in arraylist, which is the real-time file content
	 */
	private static void modify(String command) throws NullPointerException, IOException {
		assert(bp.isValid(command));
		String content = bp.getNewTitle(command);
		int index = bp.getIndex(command) - 1;
		Date newDate = bp.getDate(command);
		Date deadLine = bp.getDeadline(command);
		String newVenue = bp.getVenue(command);
		if ((index < 0)||(index > taskList.size())){
			showMessage(MESSAGE_MODIFY_OPERATION_FAILURE);
			return;
		}
		if (content == null) content = taskList.get(index).getContent();
		if (newVenue == null) newVenue = taskList.get(index).getVenue();
		if (deadLine == null) deadLine = taskList.get(index).getDeadline();
		if (newDate == null) newDate = taskList.get(index).getDate();
		Task newTask = new Task(content,newDate,deadLine,newVenue);
		taskList.remove(index);
		taskList.add(index, newTask);
		showMessage(MESSAGE_MODIFY_OPERATION, content);
		saveFile();
		undo.add(taskList);
	}	
	/*
	 * rodo, return the arrayList before last operation.
	 */
	private static void redo() {
		if (undo.canRedo()){
			taskList = (ArrayList<Task>) undo.redo();
			showMessage("redo operation successfully");
		}else{
			showMessage("no redo operation avaiable");
		}
	}

	/*
	 * undo, return the arrayList before last undo operation.
	 */
	private static void undo() {
		if (undo.canUndo()){
			taskList = (ArrayList<Task>) undo.undo();
			showMessage("undo operation successfully");
		}else{
			showMessage("no undo operation avaiable");
		}
	}	
	/*
	 * sort operation would sort all the task in terms of their deadline and return the new tasklist
	 */
	private static void sort() {
		Collections.sort(taskList);
		undo.add(taskList);
		showMessage("sort finished");
		saveFile();
	}	
	
	/*
	 * search operation would return all the tasks which conform to the sort requirements.
	 */
	private static void search(String command) {
		mode = 1;
		searchResult.clear();
		String keyWord = bp.getTitle(command);
		for (int i = 0; i < taskList.size(); i++){
			if (taskList.get(i).getContent().contains(keyWord)){
				searchResult.add(taskList.get(i));
			}
		}
		showMessage("search result is right here");
	}
	
	/*
	 * clear all data in arraylist, but do not actully store to file
	 */
	private static void clear() {
		showMessage(MESSAGE_CLEAR_OPERATION, null);
		taskList.clear();
		saveFile();
	}
	
	
	/*
	 * exit the program
	 * close the scanner, store the arraylist in disk to update the file
	 */
	private static void exit() {
		saveFile();
		ui.BasicUI.exit(NORMAL_EXIT);
		sc.close();
		System.exit(NORMAL_EXIT);
	}
	
	private static void saveFile(){
		try{
			fo.saveToFile(taskList);
		}catch(Exception e){
			feedBack.add("cannot save to file successfully");
		}
	}
	
	public static ArrayList<String> getFileContent(){
		ArrayList<String> content = new ArrayList<String>(); 
		for (Task task: taskList){
			content.add(task.getContent());
		}
		return content;
	}

	public static ArrayList<Task> getTasks(){
		if (mode == 0)
			return (ArrayList<Task>) taskList.clone();
		else
			return (ArrayList<Task>) searchResult.clone();
	}
	
	public static ArrayList<String> getFeedBacks(){
		return (ArrayList<String>) feedBack.clone();
	}
	
	public static String getLastFeedBack(){
		return feedBack.get(feedBack.size()-1);
	}
	
	public static String getAllTitles(){
		String answer = new String("");
		if (mode == 0){
			for (int i = 0; i < taskList.size(); i++){
				answer = answer + (i+1) + ". "+ taskList.get(i).getContent()+"\n";
			}
			return answer;
		}else{
			for (int i = 0; i < searchResult.size(); i++){
				answer = answer + (i+1) + ". "+ searchResult.get(i).getContent()+"\n";
			}
			if (answer.equals("")){
				return "No results found\n";
			}
			return answer;		
		}
	}
	
	public static ArrayList<String> getTaskList(){
		ArrayList<String> answers = new ArrayList<String>();
		for (int i = 0; i < taskList.size(); i++){
			answers.add(taskList.get(i).getContent());
		}
		return answers;
	}
	
}
