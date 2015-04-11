package taskList;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;
import storage.JsonStringFileOperation;
import taskList.Task;
import ui.list.swing.UserInterface;
import parser.Parser;
public class TaskManager {
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
	private ArrayList<Task> completedTaskList;	
	private ArrayList<Task> searchResult;
	//mode == 0 means the result shown in screen is taskList,
	//mode == 1 means the result shown in screen is searchResult
	//mode == 2 means the result shown in screen is completedTaskList
	//mode == 3 means the result shown in screen is all task (both finished and unfinished)
	private int mode = 0;
	private Parser bp;
	private Undo<ArrayList<Task>> undo;
	private Undo<ArrayList<Task>> undoForCompleted;
	private ArrayList<String> feedBack = new ArrayList<String>();
	private String name = TaskManager.class.getName(); 
	private int lastOperationIndex = -1;
	private Logger log = Logger.getLogger(name);// <= (2)  
	private static TaskManager sharedInstance; 
	private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
	public TaskManager(String inputFileName){
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
			completedTaskList = fo.getFinishedTaskListFromFile();
			if (completedTaskList == null) completedTaskList = new ArrayList<Task>();
			searchResult = new ArrayList<Task>();
			undo = new Undo<ArrayList<Task>>(taskList);
			undoForCompleted = new Undo<ArrayList<Task>>(completedTaskList);
		}catch(Exception e){
			log.info("There is a command invalid error");
			feedBack.add("Cannot open the file correctly");
		}
		
		bp = new Parser();
		//Add in a initParser() command.
	}
	
	public TaskManager getStaredInstance(String inputFileName){
		if (sharedInstance == null) {
			sharedInstance = new TaskManager(inputFileName);
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
			complete(command);
			break;
		case DISPLAY:
			try{
				display(command);
			}catch (Exception e){
				e.printStackTrace();
			}
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
		//User should not modify the completed task, so the mode would be switched to 0 automatically
		if (mode >= 1) mode = 0;
		assert(bp.isValid(command));
		String content = bp.getTitle(command);
		try{
			Date date = bp.getDate(command);
			System.out.println(date);
			Date deadLine = bp.getDeadline(command);
			String venue = bp.getVenue(command);
			showMessage(MESSAGE_ADD_OPERATION, content);
			taskList.add(new Task(content,date,deadLine,venue));
			System.out.println(taskList.get(2).isOutOfDate());
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
		//User should not modify the completed task, so the mode would be switched to 0 automatically
		if (mode > 1) mode = 0;
		int removeIndex = -1;
		try {
			removeIndex = bp.getIndex(command);
		} catch (IOException e) {
		
		}	
		if (removeIndex < 0 || removeIndex > taskList.size()) {
			showMessage(MESSAGE_DELETE_OPERATION_FAILURE, "");
			return;
		}
		if (mode == 0) {
			showMessage(MESSAGE_DELETE_OPERATION, taskList.get(removeIndex - 1).getContent());
			taskList.remove(removeIndex - 1);
			saveFile();
			undo.add(taskList); 
		} else {
			int indexinTaskList = 0;
			for (int i = 0; i < taskList.size(); i++){
				if (taskList.get(i).isEqual(searchResult.get(removeIndex - 1 ))){
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
	 * complete content in arraylist, save this task to finished list
	 */
	private void complete(String command) {
		//User should not modify the completed task, so the mode would be switched to 0 automatically
		if (mode > 1) mode = 0;
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
			Task finishedOne = taskList.remove(removeIndex - 1);
			
			//update hasfinished added here		
			finishedOne.finish();
			
			completedTaskList.add(finishedOne);
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
				if (taskList.get(i).isEqual(searchResult.get(removeIndex - 1 ))){
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
	private void display(String command) throws NullPointerException, IOException {
		String type = "unfinished";
		try {
			type = bp.getTitle(command);
		} catch (Exception e) {
			
		}
		if (type.equals("finished")){
			mode = 2;
			if (completedTaskList.size() == 0){
				showMessage(MESSAGE_EMPTY_FILE,null);
				return;
			}
			showMessage("Display finished Task");
		}else if (type.equals("all")){
			mode = 3;
			if (completedTaskList.size() + taskList.size() == 0){
				showMessage(MESSAGE_EMPTY_FILE,null);
				return;
			}
			showMessage("Display all Tasks");
		}else{
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
			showMessage("Display todo Task");
		}
	}
	
	/*
	 * modify the content in arraylist, which is the real-time file content
	 */
	private void modify(String command) throws Exception{
		//User should not modify the completed task, so the mode would be switched to 0 automatically
		if (mode > 1) mode = 0;
		if (mode == 0){
			assert(bp.isValid(command));
			String content = bp.getNewTitle(command);
			try{
				int index = bp.getIndex(command) - 1;
				lastOperationIndex = index + 1;
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
				lastOperationIndex = index + 1;
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
			if (undoForCompleted.canRedo()) completedTaskList = (ArrayList<Task>) undoForCompleted.redo();
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
			if (undoForCompleted.canUndo()) {
				completedTaskList = (ArrayList<Task>) undoForCompleted.undo();
			}
			showMessage("undo operation successfully");
		}else{
			showMessage("no undo operation avaiable");
		}
	}	
	
	public boolean compareString(String string1, String string2){
		if (string1 == null){
			if (string2 == null) return false;
			return false;
		}else if (string2 == null){
			return true;
		}else{
			return string1.compareTo(string2)<0;
		}
	}
	
	public boolean compareDate(Date date1, Date date2){
		if (date1 == null){
			if (date2 == null) return false;
			return false;
		}else if (date2 == null){
			return true;
		}else{
			return date1.compareTo(date2)<0;
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
		String content;
		try{
			content = bp.getTitle(command);
		}catch(Exception e){
			content = "time";
		}
		
		content.toLowerCase();
		if (content.equals("time")){
			try {
				sortTaskList(BY_TIME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (content.equals("venue")){
			try {
				sortTaskList(BY_VENUE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (content.equals("title")){
			try {
				sortTaskList(BY_TITLE);
			} catch (Exception e) {
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
		if (keyWord.equals("today")){
			dateFormat = new SimpleDateFormat ("YYYY-MM-dd");
			keyWord = dateFormat.format(bp.getDate("add -d today"));
			System.out.println("today is " + keyWord);
		}
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
		UserInterface.exit();
		System.exit(0);
	}
	
	private void saveFile(){
		try{
			fo.saveToFile(taskList,completedTaskList);
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
		}else if (mode == 1){
			return (ArrayList<Task>) searchResult.clone();
	
		}else if (mode == 2){
			return (ArrayList<Task>) completedTaskList.clone();
		}else{
			ArrayList<Task> bothTaskList = (ArrayList<Task>) taskList.clone();
			bothTaskList.addAll((ArrayList<Task>)completedTaskList.clone());
			return bothTaskList;
		}
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
	
	public boolean isEqual(TaskManager taskList2){
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
	
	public int getLastOperationIndex(){
		return lastOperationIndex;
	}
	
	public String getAutoFill(String command){
		return bp.autoFill(command);
	}
	
	public String getCommandTip(String command){
		return bp.provideTips(command);
	}
	
	public int getCurrentMode(){
		return mode;
	}
	
	
}
