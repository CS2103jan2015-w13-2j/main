package taskList;


import java.util.ArrayList;
import java.util.Scanner;
import taskList.Task;
import FileOperation.FileOperation;
import parser.Parser;

public class TaskList {
	private static final String FILE_NAME_DEFAULT = "default_output.txt";
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %s is ready for use\n";
	private static final String MESSAGE_INVALID_COMMOND = "Invalid operation.\n";
	private static final String MESSAGE_EMPTY_FILE = "%s is empty\n";
	private static final String MESSAGE_ADD_OPERATION = "added to %s: \"%s\"\n";
	private static final String MESSAGE_DELETE_OPERATION = "deleted from %s: \"%s\"\n";
	private static final String MESSAGE_CLEAR_OPERATION = "all content deleted from %s\n";
	private static final String MESSAGE_DISPLAY_OPERATION = "%d. %s\n";

	private static final int OPERATION_UNKNOWN = 0;
	private static final int OPERATION_ADD = 1;
	private static final int OPERATION_DELETE = 2;
	private static final int OPERATION_CLEAR = 3;
	private static final int OPERATION_DISPLAY = 4;
	private static final int OPERATION_EXIT = 5;

	private static final int NORMAL_EXIT = 0;

	private static Scanner sc;
	private static String fileName;
	private static FileOperation fo;
	private static ArrayList<Task> taskList;
	private static Parser bp;
	private static ArrayList<String> feedBack = new ArrayList<String>();
	
	public TaskList(String inputFileName){
		fileName = inputFileName;
		fo = new FileOperation(fileName);
		taskList = fo.readFile();
		feedBack.clear();
		bp = new Parser();
		//Add in a initParser() command.
		bp.initParser();
	}
	
	/*
	 * print messages
	 */
	private static void showMessage(String message, String content) {
		feedBack.add(String.format(message, fileName, content));
		System.out.print(String.format(message, fileName, content));
	}

	/*
	 * show the file content in format
	 */
	private static void showFileContent(int index, String content) {
		System.out.print(String.format(MESSAGE_DISPLAY_OPERATION, index,
				content));
	}
	
	public static void startWaitingForCommand(){
		sc = new Scanner(System.in);
		String inputCommand;
		while (true) {
			System.out.print("Command: ");
			inputCommand = sc.nextLine();
			executeCommand(inputCommand);
		}
	}
	
	public static void executeCommand(String command) {
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
		case OPERATION_EXIT:
			exit();
			break;
		default:
			unknownOperation();
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
	private static void add(String command) {
		String content = "";
		if (command.indexOf(' ') != -1) {
			content = command.substring(command.indexOf(' ') + 1);
		}
		showMessage(MESSAGE_ADD_OPERATION, content);
		taskList.add(new Task(content));
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
			return;
		}
		showMessage(MESSAGE_DELETE_OPERATION, taskList.get(removeIndex - 1).getContent());
		taskList.remove(removeIndex - 1);
	}

	/*
	 * display the content in arraylist, which is the real-time file content
	 */
	private static void display() {
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
	 * clear all data in arraylist, but do not actully store to file
	 */
	private static void clear() {
		showMessage(MESSAGE_CLEAR_OPERATION, null);
		taskList.clear();
	}
	
	/*
	 * exit the program
	 * close the scanner, store the arraylist in disk to update the file
	 */
	private static void exit() {
		ui.BasicUI.exit(NORMAL_EXIT);
		fo.saveToFile(taskList);
		sc.close();
		System.exit(NORMAL_EXIT);
	}
	
	public static ArrayList<String> getFileContent(){
		ArrayList<String> content = new ArrayList<String>(); 
		for (Task task: taskList){
			content.add(task.getContent());
		}
		return content;
	}
	
	public static ArrayList<String> getFeedBacks(){
		return (ArrayList<String>) feedBack.clone();
	}
	
	public static String getLastFeedBack(){
		return feedBack.get(feedBack.size()-1);
	}
}
