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
	private static final String MESSAGE_EMPTY_FILE = "%s is empty\n";
	private static final String MESSAGE_ADD_OPERATION = "added to %s: \"%s\"\n";
	private static final String MESSAGE_DELETE_OPERATION = "deleted from %s: \"%s\"\n";
	private static final String MESSAGE_DELETE_OPERATION_FAILURE = "index is not valid for delete from %s %s\n ";
	private static final String MESSAGE_CLEAR_OPERATION = "all content deleted from %s\n";
	private static final String MESSAGE_DISPLAY_OPERATION = "%d. %s\n";
	private static final String MESSAGE_MODIFY_OPERATION = "modify to %s: \"%s\"\n";
	private static final String MESSAGE_MODIFY_OPERATION_FAILURE = "index is not valid for modify from %s %s\n";
	private static final int BY_TIME = 0;
	private static final int BY_VENUE = 1;
	private static final int BY_TITLE = 2;

	private Scanner sc;
	private String fileName;
	private JsonStringFileOperation fo;
	private ArrayList<Task> taskList;
	private ArrayList<Task> searchResult;
	//mode == 0 means the result shown in screen is taskList,
	//mode == 1 means the result shown in screen is searchResult
	private int mode;
	private Parser bp;
	private Undo<ArrayList<Task>> undo;
	private ArrayList<String> feedBack = new ArrayList<String>();
	private String name = TaskList.class.getName(); 
	private Logger log = Logger.getLogger(name);// <= (2)  
	private static TaskList sharedInstance; 
	public TaskList(String inputFileName){
		mode = 0;
		fileName = inputFileName;
		try{
			fo = new JsonStringFileOperation(fileName);
		}catch(Exception e){
			log.info("There is a file reading error");
			feedBack.add("Cannot open the file correctly");
		}
		feedBack.clear();
		try{
			fo = new JsonStringFileOperation(fileName);
			taskList = fo.getUnfinishedTaskListFromFile();
			searchResult = new ArrayList<Task>();
			undo = new Undo<ArrayList<Task>>(taskList);
		}catch(Exception e){
			log.info("There is a command invalid error");
			feedBack.add("Cannot open the file correctly");
		}
		
		bp = new Parser();
		//Add in a initParser() command.
	}
	
	public TaskList getStaredInstance(String inputFileName){
		if (sharedInstance == null) {
			sharedInstance = new TaskList(inputFileName);
		}
		return sharedInstance;
	}
	
	/*
	 * print messages
	 */
	private void showMessage(String message, String content) {
		feedBack.add(String.format(message, this.fileName, content));
		System.out.print(String.format(message, this.fileName, content));
	}

	/*
	 * print single messages
	 */
	private void showMessage(String message) {
		this.feedBack.add(message);
		System.out.print(message);
	}

	
	
	/*
	 * show the file content in format
	 */
	private void showFileContent(int index, String content) {
		System.out.print(String.format(MESSAGE_DISPLAY_OPERATION, index,
				content));
	}
	
	public void startWaitingForCommand() throws NullPointerException, IOException{
		sc = new Scanner(System.in);
		String inputCommand;
		while (true) {
			System.out.print("Command: ");
			inputCommand = sc.nextLine();
			executeCommand(inputCommand);
		}
	}
	
	public void executeCommand(String command) {
		switch (bp.getOperation(command)) {
		case ADD:
			try {
				add(command);
			} catch (Exception e) {
				showMessage(e.getMessage());
				e.printStackTrace();
			}
			break;
		case DELETE:
			delete(command);
			break;
		case COMPLETE:
			delete(command);
			break;
		case DISPLAY:
			display();
			break;
		case CLEAR:
			clear();
			break;
		case MODIFY:
			try {
				modify(command);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case UNDO:
			undo();
			break;
		case REDO:
			redo();
			break;
		case SORT:
			try {
				sort(command);
			} catch (Exception e1 ) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case SEARCH:
			try{
				search(command);
			} catch (Exception e){
				e.printStackTrace();
			}
			break;
		case EXIT:
			exit();
			break;
		default:
			assert(false);
			showMessage("No such command");
		}
	}
	
	/*
	 * add new content to arraylist, but do not actully store to file
	 */
	private void add(String command) throws Exception{
		assert(bp.isValid(command));
		String content = bp.getTitle(command);
		try{
			Date date = bp.getDate(command);
			System.out.println(date);
			Date deadLine = bp.getDeadline(command);
			String venue = bp.getVenue(command);
			showMessage(MESSAGE_ADD_OPERATION, content);
			taskList.add(new Task(content,date,deadLine,venue));
			saveFile();
			undo.add(taskList);
		}catch (Exception e){
			throw e;
		}
	}
	
	/*
	 * delete content in arraylist, but do not actully store to file
	 */
	private void delete(String command) {
		if (mode == 0){
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
		}else{
			String content = "";
			if (command.indexOf(' ') != -1) {
				content = command.substring(command.indexOf(' ') + 1);
			}
			int removeIndex = Integer.valueOf(content);
			if (removeIndex < 0 || removeIndex > searchResult.size()) {
				showMessage(MESSAGE_DELETE_OPERATION_FAILURE, "");
				return;
			}
			int indexinTaskList = 0;
			for (int i = 0; i < taskList.size(); i++){
				if (taskList.get(i).isEqual(searchResult.get(removeIndex))){
					indexinTaskList = i;
					break;
				}
			}
			taskList.remove(indexinTaskList);
			showMessage(MESSAGE_DELETE_OPERATION, searchResult.get(removeIndex - 1).getContent());
			searchResult.remove(removeIndex - 1);
			saveFile();
			undo.add(taskList);
		}
	}

	/*
	 * display the content in arraylist, which is the real-time file content
	 */
	private void display() {
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
	private void modify(String command) throws Exception{
		if (mode == 0){
			assert(bp.isValid(command));
			String content = bp.getNewTitle(command);
			try{
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
			}catch (Exception e){
				throw e;
			}
		}else{
			assert(bp.isValid(command));
			String content = bp.getNewTitle(command);
			try{
				int index = bp.getIndex(command) - 1;
				Date newDate = bp.getDate(command);
				Date deadLine = bp.getDeadline(command);
				String newVenue = bp.getVenue(command);
				if ((index < 0)||(index > searchResult.size())){
					showMessage(MESSAGE_MODIFY_OPERATION_FAILURE);
					return;
				}
				if (content == null) content = searchResult.get(index).getContent();
				if (newVenue == null) newVenue = searchResult.get(index).getVenue();
				if (deadLine == null) deadLine = searchResult.get(index).getDeadline();
				if (newDate == null) newDate = searchResult.get(index).getDate();
				Task newTask = new Task(content,newDate,deadLine,newVenue);
				int indexinTaskList = 0;
				for (int i = 0; i < taskList.size(); i++){
					if (taskList.get(i).isEqual(searchResult.get(index))){
						indexinTaskList = i;
						break;
					}
				}
				taskList.remove(indexinTaskList);
				taskList.add(index, newTask);
				searchResult.remove(index);
				searchResult.add(newTask);
				showMessage(MESSAGE_MODIFY_OPERATION, content);
				saveFile();
				undo.add(taskList);
			}catch (Exception e){
				throw e;
			}
		}
	}	
	/*
	 * rodo, return the arrayList before last operation.
	 */
	private void redo() {
		if (undo.canRedo() && mode == 0){
			taskList = (ArrayList<Task>) undo.redo();
			showMessage("redo operation successfully");
		}else{
			showMessage("no redo operation avaiable");
		}
	}

	/*
	 * undo, return the arrayList before last undo operation.
	 */
	private void undo() {
		if (undo.canUndo() && mode == 0){
			taskList = (ArrayList<Task>) undo.undo();
			showMessage("undo operation successfully");
		}else{
			showMessage("no undo operation avaiable");
		}
	}	
	
	public boolean compareString(String string1, String string2){
		if (string1 == null){
			if (string2 == null) return true;
			return true;
		}else if (string2 == null){
			return false;
		}else{
			return string1.compareTo(string2)>0;
		}
	}
	
	public boolean compareDate(Date date1, Date date2){
		if (date1 == null){
			if (date2 == null) return true;
			return true;
		}else if (date2 == null){
			return false;
		}else{
			return date1.compareTo(date2)>0;
		}
	}
	
	private void sortTaskList(int type) throws Exception{
		Task[] taskArray = new Task[taskList.size()];
		taskList.toArray(taskArray);
		switch (type){
		case BY_TIME:
			System.out.println("begin time");
			for (int i = 0; i < taskList.size(); i++){
				for (int j = 0; j< i; j++){
				if (compareDate(taskArray[i].getDate(),taskArray[j].getDate())){
						Task tmp = taskArray[i];
						taskArray[i] = taskArray[j];
						taskArray[j] = tmp;
						System.out.println("change happens");
					}
				}
			}
			break;
		case BY_VENUE:
			for (int i = 0; i < taskList.size(); i++){
				for (int j = 0; j< i; j++){
				if (compareString(taskArray[i].getVenue(),taskArray[j].getVenue())){
						Task tmp = taskArray[i];
						taskArray[i] = taskArray[j];
						taskArray[j] = tmp;
					}
				}
			}
			break;
		case BY_TITLE:
			for (int i = 0; i < taskList.size(); i++){
				for (int j = 0; j< i; j++){
				if (compareString(taskArray[i].getContent(),taskArray[j].getContent())){
						Task tmp = taskArray[i];
						taskArray[i] = taskArray[j];
						taskArray[j] = tmp;
					}
				}
			}
			break;
		default:
			throw new Exception("Invalid Sort Operation");
		}
		taskList = new ArrayList<Task>();
		for (int i = 0; i< taskArray.length ; i++){
			taskList.add(taskArray[i]);
		}
		
		
	}
	
	
	/*
	 * sort operation would sort all the task in terms of their deadline and return the new tasklist
	 */
	private void sort(String command) throws Exception {
		String content = bp.getTitle(command);
		if (content == null){
			content = "Time";
		}
		
		content.toLowerCase();
		if (content.equals("time")){
			try {
				sortTaskList(BY_TIME);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (content.equals("venue")){
			try {
				sortTaskList(BY_VENUE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (content.equals("title")){
			try {
				sortTaskList(BY_TITLE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			throw new Exception("No such command");
		}
		
		undo.add(taskList);
		showMessage("sort finished");
		saveFile();
	}	
	
	/*
	 * search operation would return all the tasks which conform to the sort requirements.
	 */
	private void search(String command) throws NullPointerException, IOException {
		mode = 1;
		searchResult.clear();
		String keyWord = bp.getTitle(command);
		for (int i = 0; i < taskList.size(); i++){
			if (taskList.get(i).containKeyWord(keyWord)){
				searchResult.add(taskList.get(i));
			}
		}
		showMessage("search result is right here");
	}
	
	/*
	 * clear all data in arraylist, but do not actully store to file
	 */
	private void clear() {
		showMessage(MESSAGE_CLEAR_OPERATION, null);
		taskList.clear();
		saveFile();
	}
	
	
	/*
	 * exit the program
	 * close the scanner, store the arraylist in disk to update the file
	 */
	private void exit() {
		saveFile();
		sc.close();
		System.exit(0);
	}
	
	private void saveFile(){
		try{
			fo.saveToFile(taskList);
		}catch(Exception e){
			feedBack.add("cannot save to file successfully");
		}
	}
	
	public ArrayList<String> getFileContent(){
		ArrayList<String> content = new ArrayList<String>(); 
		for (Task task: taskList){
			content.add(task.getContent());
		}
		return content;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Task> getTasks(){
		if (mode == 0){
			ArrayList<Task> answers = new ArrayList<Task>();
			for (int i = 0; i < taskList.size(); i++){
				if (!taskList.get(i).hasFinished())
					answers.add(taskList.get(i));
			}
			return answers;
		}else
			return (ArrayList<Task>) searchResult.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getFeedBacks(){
		return (ArrayList<String>) feedBack.clone();
	}
	
	public String getLastFeedBack(){
		return feedBack.get(feedBack.size()-1);
	}
	
	public String getAllTitles(){
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
	
	public ArrayList<String> getTaskList(){
		ArrayList<String> answers = new ArrayList<String>();
		for (int i = 0; i < taskList.size(); i++){
			answers.add(taskList.get(i).getContent());
		}
		return answers;
	}
	
	public boolean isEqual(TaskList taskList2){
		if (this.taskList.size() != taskList2.taskList.size()) return false;
		@SuppressWarnings("unchecked")
		ArrayList<Task> taskListCopy1 = (ArrayList<Task>) this.taskList.clone();
		@SuppressWarnings("unchecked")
		ArrayList<Task> taskListCopy2 = (ArrayList<Task>) taskList2.taskList.clone();
		Collections.sort(taskListCopy1);
		Collections.sort(taskListCopy2);
		for (int i = 0; i< taskListCopy1.size(); i++){
			if (!taskListCopy1.get(i).isEqual(taskListCopy2.get(i))){
				System.out.println("failure at i "+ i);
				return false;
			}
		}
		return true;
		
	}
	
}
