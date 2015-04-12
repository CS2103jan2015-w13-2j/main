package taskList;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

import edu.emory.mathcs.backport.java.util.Collections;
import storage.ConfigurationFileOperation;
import storage.JsonStringFileOperation;
import taskList.Task;
import ui.list.swing.LayoutSetting;
import ui.list.swing.UserInterface;
import parser.DateParser;
import parser.Parser;
public class TaskManager {
	private static final String MESSAGE_EMPTY_FILE = "No related file found";
	private static final String MESSAGE_ADD_OPERATION = "Add new task successfully";
	private static final String MESSAGE_DELETE_OPERATION = "Delete operation successfully";
	private static final String MESSAGE_DELETE_OPERATION_FAILURE = "Index is not valid for delete operation";
	private static final String MESSAGE_CLEAR_OPERATION = "all content has been deleted";
	private static final String MESSAGE_MODIFY_OPERATION = "Modify successfully \n";
	private static final String MESSAGE_MODIFY_OPERATION_FAILURE = "Index is not valid for modify\n";
	public enum SORT_MODE {
		BY_TIME,BY_VENUE,BY_TITLE
	}
	public enum DISPLAY_MODE {
		TODO_TASKLIST, SEARCH_LIST, FINISHED_TASKLIST, ALL_TASKLIST, FILE_PATH
	}
	private String fileName;
	private JsonStringFileOperation fileOperation;
	private ConfigurationFileOperation configurationFileOperation;
	private ArrayList<Task> taskList;
	private ArrayList<Task> completedTaskList;	
	private ArrayList<Task> searchResult;
	
	private DISPLAY_MODE mode = DISPLAY_MODE.TODO_TASKLIST;
	private Parser myParser;
	
	private Undo<ArrayList<Task>> undo;
	private Undo<ArrayList<Task>> undoForCompleted;
	private ArrayList<String> feedBack = new ArrayList<String>();
	private ArrayList<String> fileList = new ArrayList<String>();
	private String name = TaskManager.class.getName(); 
	private int lastOperationIndex = -1;
	private Logger log = Logger.getLogger(name);
	//Only one instance for TaskManager, instead of new a TaskManager, other method get instance by getSharedInstance method
	private static TaskManager sharedInstance; 
	//A date format used to format date so that search and sort is easy to be implemented
	private SimpleDateFormat dateFormat = new SimpleDateFormat(DateParser.FORMAT_DEFAULT);
	
	/*
	 * Construct a TaskManager with a given file name
	 */
	public TaskManager(String inputFileName){
		mode = DISPLAY_MODE.TODO_TASKLIST;
		fileName = inputFileName;
		feedBack.clear();
		try{
			fileOperation = new JsonStringFileOperation(fileName);
			taskList = fileOperation.getUnfinishedTaskListFromFile();
			completedTaskList = fileOperation.getFinishedTaskListFromFile();
			if (completedTaskList == null) completedTaskList = new ArrayList<Task>();
			searchResult = new ArrayList<Task>();
			undo = new Undo<ArrayList<Task>>(taskList);
			undoForCompleted = new Undo<ArrayList<Task>>(completedTaskList);
		}catch(Exception e){
			log.info("There is a command invalid error");
			showMessage("Cannot open the file correctly");
		}
		
		myParser = new Parser();
		//Add in a initParser() command.
	}

	/*
	 * Construct a TaskManager based on the configuration file
	 */
	public TaskManager(){
		mode = DISPLAY_MODE.TODO_TASKLIST;
		feedBack.clear();
		try{
			configurationFileOperation = new ConfigurationFileOperation();
			fileName = configurationFileOperation.getLastOpenFilePath();
			System.out.println("debug "+fileName);
			fileList = configurationFileOperation.getHistoryFilePath();
			fileOperation = new JsonStringFileOperation(fileName);
		}catch (Exception e){
			showMessage("Something wrong happens when open configuration file");
		}
		try{
			fileOperation = new JsonStringFileOperation(fileName);
			taskList = fileOperation.getUnfinishedTaskListFromFile();
			completedTaskList = fileOperation.getFinishedTaskListFromFile();
			if (completedTaskList == null) completedTaskList = new ArrayList<Task>();
			searchResult = new ArrayList<Task>();
			undo = new Undo<ArrayList<Task>>(taskList);
			undoForCompleted = new Undo<ArrayList<Task>>(completedTaskList);
		}catch(Exception e){
			log.info("There is a command invalid error");
			showMessage("Cannot open the file correctly");
		}
		
		myParser = new Parser();
	}
	
	/*
	 * return the sharedInstance of TaskManager instead of creating more than one instances
	 */
	public static TaskManager getSharedInstance(){
		if (sharedInstance == null) {
			sharedInstance = new TaskManager();
			return sharedInstance;
		}
		return sharedInstance;
	}
	


	/*
	 * parameters: String message
	 * return: Nothing
	 * Description: add message into feedback and provide api for UI to get feedback of last operation
	 */
	private void showMessage(String message) {
		this.feedBack.add(message);
		System.out.print(message);
	}

	
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: apis provided for UI to executeCommand
	 */
	
	public void executeCommand(String command) {
		try{
			switch (myParser.getOperation(command)) {
		
			case ADD:
				add(command);
				break;
			case DELETE:
				deleteMultiple(command);
				break;
			case COMPLETE:
				completeMultiple(command);
				break;
			case DISPLAY:
				display(command);
				break;
			case CLEAR:
				clear();
				break;
			case MODIFY:
				modifyMultiple(command);
				break;
			case UNDO:
				undo();
				break;
			case REDO:
				redo();
				break;
			case SORT:
				sort(command);
				break;
			case SEARCH:
				search(command);
				break;
			case EXIT:
				exit();
				break;
			case IMPORT:
				importFile(command);
				break;
			case EXPORT:
				exportFile(command);
				break;
			default:
				assert(false);
				showMessage("No such command");
			}
		}catch (Exception e){
			showMessage(e.getMessage());
		}
	}
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: add task into task list. Can be used in search mode but would not change what displays inside search result
	 */
	private void add(String command) throws Exception{
		//User should not modify the completed task, so the mode would be switched to TODO_TASKLIST automatically
		switchToChangeableMode();
		assert(myParser.isValid(command));
		String content = myParser.getTitle(command);
		try{
			Date date = myParser.getDate(command);
			System.out.println(date);
			Date deadLine = myParser.getDeadline(command);
			String venue = myParser.getVenue(command);
			showMessage(MESSAGE_ADD_OPERATION);
			taskList.add(new Task(content,date,deadLine,venue));
			System.out.println(taskList.get(0).isOutOfDate());
			saveFile();
			saveConfiguration();
			undo.add(taskList);
		}catch (Exception e){
			throw e;
		}
	}
	
	/*
	 * parameters: int removeIndex
	 * return: Nothing
	 * Description: delete a single task based on its index. If it is on the search mode, it would delete related task inside taskList
	 */
	private void delete(int removeIndex) throws IOException {
		//User should not modify the completed task, so the mode would be switched to 0 automatically
		switchToChangeableMode();

		if (removeIndex <= 0 || removeIndex > taskList.size()) {
			showMessage(MESSAGE_DELETE_OPERATION_FAILURE);
			return;
		}
		if (mode == DISPLAY_MODE.TODO_TASKLIST) {
			showMessage(MESSAGE_DELETE_OPERATION);
			taskList.remove(removeIndex - 1);
			System.out.println("here here "+removeIndex);
			saveFile(); 
		} else {
			int indexinTaskList = 0;
			for (int i = 0; i < taskList.size(); i++){
				if (taskList.get(i).isEqual(searchResult.get(removeIndex - 1 ))){
					indexinTaskList = i;
					break;
				}
			}
			taskList.remove(indexinTaskList);
			showMessage(MESSAGE_DELETE_OPERATION);
			searchResult.remove(removeIndex - 1);
			saveFile();
		}
	}
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: delete several tasks at the same time
	 */
	private void deleteMultiple(String command) throws Exception{
		ArrayList<Integer> deleteIndex = myParser.getIndex(command);
		Collections.sort(deleteIndex);
		for (int i = deleteIndex.size()-1; i >= 0; i--){
			System.out.println("index for this operation is "+deleteIndex.get(i));
			delete(deleteIndex.get(i));
		}
		undo.add(taskList);
	}	
	
	
	
	/*
	 * parameters: int removeIndex
	 * return: Nothing
	 * Description: complete a single task based on its index. If it is on the search mode, it would complete related task inside taskList
	 * The completed one would be marked as finish and moved to completedTaskList
	 */
	private void complete(int removeIndex) throws IOException {
		//User should not modify the completed task, so the mode would be switched to 0 automatically
		switchToChangeableMode();
		if (mode == DISPLAY_MODE.TODO_TASKLIST){
			
			if (removeIndex < 0 || removeIndex > taskList.size()) {
				showMessage(MESSAGE_DELETE_OPERATION_FAILURE);
				return;
			}
			showMessage(MESSAGE_DELETE_OPERATION);
			Task finishedOne = taskList.remove(removeIndex - 1);
			
			//update hasfinished added here		
			finishedOne.finish();
			
			completedTaskList.add(finishedOne);
			saveFile();
			undo.add(taskList);
		}else{
			if (removeIndex < 0 || removeIndex > searchResult.size()) {
				showMessage(MESSAGE_DELETE_OPERATION_FAILURE);
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
			showMessage(MESSAGE_DELETE_OPERATION);
			searchResult.remove(removeIndex - 1);
			saveFile();
			undo.add(taskList);
		}
	}
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: complete several tasks at the same time
	 */
	private void completeMultiple(String command) throws Exception{
		ArrayList<Integer> completeIndex = myParser.getIndex(command);
		Collections.sort(completeIndex);
		for (int i = completeIndex.size()-1; i >= 0; i--){
			complete(completeIndex.get(i));
		}
	}	
	
	
	
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: switch DISPLAY_MODE to relative type based on command
	 */
	private void display(String command) throws NullPointerException, IOException {
		String type = "unfinished";
		try {
			type = myParser.getTitle(command);
		} catch (Exception e) {
			
		}
		if (type.equals("finished")){
			mode = DISPLAY_MODE.FINISHED_TASKLIST;
			if (completedTaskList.size() == 0){
				showMessage(MESSAGE_EMPTY_FILE);
				return;
			}
			showMessage("Display finished Task");
		}else if (type.equals("all")){
			mode = DISPLAY_MODE.ALL_TASKLIST;
			if (completedTaskList.size() + taskList.size() == 0){
				showMessage(MESSAGE_EMPTY_FILE);
				return;
			}
			showMessage("Display all Tasks");
		}else if(type.equals("file")){
			mode = DISPLAY_MODE.FILE_PATH;
			showMessage("Display all files");
		}else{
			mode = DISPLAY_MODE.TODO_TASKLIST;
			if (taskList.size() == 0) {
				showMessage(MESSAGE_EMPTY_FILE);
				return;
			}
			showMessage("Display todo Task");
		}
	}
	
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: modify one task based on its index
	 */
	private void modify(int index, String command) throws Exception{
		//User should not modify the completed task, so the mode would be switched to 0 automatically
		index-= 1;
		switchToChangeableMode();
		if (mode == DISPLAY_MODE.TODO_TASKLIST){
			assert(myParser.isValid(command));
			String content = myParser.getNewTitle(command);
			try{
				lastOperationIndex = index + 1;
				Date newDate = myParser.getDate(command);
				Date deadLine = myParser.getDeadline(command);
				String newVenue = myParser.getVenue(command);
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
				showMessage(MESSAGE_MODIFY_OPERATION);
				saveFile();
				undo.add(taskList);
			}catch (Exception e){
				throw e;
			}
		}else{
			assert(myParser.isValid(command));
			String content = myParser.getNewTitle(command);
			try{
				lastOperationIndex = index + 1;
				Date newDate = myParser.getDate(command);
				Date deadLine = myParser.getDeadline(command);
				String newVenue = myParser.getVenue(command);
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
				showMessage(MESSAGE_MODIFY_OPERATION);
				saveFile();
				undo.add(taskList);
			}catch (Exception e){
				throw e;
			}
		}
	}	
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: modify several tasks at the same time
	 */
	private void modifyMultiple(String command) throws Exception{
		ArrayList<Integer> modifyIndex = myParser.getIndex(command);
		for (int i = 0; i < modifyIndex.size(); i++){
			modify(modifyIndex.get(i),command);
		}
	}	
	
	
	/*
	 * parameters: Nothing
	 * return: Nothing
	 * Description: Redo operation
	 */
	void redo() {
		if (undo.canRedo() && mode == DISPLAY_MODE.TODO_TASKLIST){
			taskList = (ArrayList<Task>) undo.redo();
			if (undoForCompleted.canRedo()) completedTaskList = (ArrayList<Task>) undoForCompleted.redo();
			showMessage("redo operation successfully");
			
		}else{
			showMessage("no redo operation avaiable");
		}			
	}

	/*
	 * parameters: Nothing
	 * return: Nothing
	 * Description: Undo operation
	 */
	void undo() {
		if (undo.canUndo() && mode == DISPLAY_MODE.TODO_TASKLIST){
			taskList = (ArrayList<Task>) undo.undo();
			if (undoForCompleted.canUndo()) {
				completedTaskList = (ArrayList<Task>) undoForCompleted.undo();
			}
			showMessage("undo operation successfully");
		}else{
			showMessage("no undo operation avaiable");
		}
	}	
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: import file based on given command, if file not exist, create a new file
	 */
	private void importFile(String command) throws NullPointerException, IOException{
		System.out.println("import command");
		String newFileName = myParser.getTitle(command);
		fileName = newFileName;
		fileOperation = new JsonStringFileOperation(newFileName);
		if (!fileList.contains(newFileName)){
			mode = DISPLAY_MODE.TODO_TASKLIST;
			fileList.add(newFileName);
			System.out.println("fafaaf");
			loadFile();
		}else{
			mode = DISPLAY_MODE.TODO_TASKLIST;
			loadFile();
		}
		saveFile();
		saveConfiguration();
		LayoutSetting.setFilePathLabel();
		showMessage("Import successfully");
	}
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: export file based on given command, if file not exist, create a new file.
	 * If file exist already, just modify the old one
	 */
	private void exportFile(String command) throws IOException{
		System.out.println("export command");
		String newFileName = myParser.getTitle(command);
		fileName = newFileName;
		fileOperation = new JsonStringFileOperation(newFileName);
		if (!fileList.contains(newFileName)){
			fileList.add(newFileName);
		}
		saveFile();
		saveConfiguration();
		LayoutSetting.setFilePathLabel();
		showMessage("Export successfully");
	}
	
	
	/*
	 * parameters: String string1, String string2
	 * return: boolean
	 * Description: check which string is larger
	 */
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
	
	/*
	 * parameters: Date date1, Date date2
	 * return: boolean
	 * Description: check which date is larger
	 */
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
	
	/*
	 * parameters: Sort_MODE, type
	 * return: Nothing
	 * Description: sort the taskList based on given SORT_MDOE
	 */
	private void sortTaskList(SORT_MODE type) throws Exception{
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
	 * parameters: String command
	 * return: Nothing
	 * Description: sort taskList based on receive command
	 */
	private void sort(String command) throws Exception {
		String content;
		try{
			content = myParser.getTitle(command);
		}catch(Exception e){
			content = "time";
		}
		
		content.toLowerCase();
		if (content.equals("time")){
			try {
				sortTaskList(SORT_MODE.BY_TIME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (content.equals("venue")){
			try {
				sortTaskList(SORT_MODE.BY_VENUE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (content.equals("title")){
			try {
				sortTaskList(SORT_MODE.BY_TITLE);
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
	 * parameters: String command
	 * return: Nothing
	 * Description: search taskList based on receive command
	 */
	private void search(String command) throws NullPointerException, IOException {
		mode = DISPLAY_MODE.SEARCH_LIST;
		searchResult.clear();
		String keyWord = myParser.getTitle(command);
		if (keyWord.equals("today")){
			dateFormat = new SimpleDateFormat (DateParser.FORMAT_DEFAULT);
			keyWord = dateFormat.format(myParser.getDate("add -d today"));
		}
		for (int i = 0; i < taskList.size(); i++){
			if (taskList.get(i).containKeyWord(keyWord)){
				searchResult.add(taskList.get(i));
			}
		}
		showMessage("search result is right here");
	}
	
	/*
	 * parameters: String command
	 * return: Nothing
	 * Description: delete all tasks inside taskList
	 */
	private void clear() throws IOException {
		showMessage(MESSAGE_CLEAR_OPERATION);
		taskList.clear();
		saveFile();
	}
	
	
	/*
	 * parameters: Nothing
	 * return: Nothing
	 * Description: save file and exit safely
	 */
	private void exit() throws IOException {
		saveFile();
		saveConfiguration();
		UserInterface.exit();
		System.exit(0);
	}
	
	/*
	 * parameters: Nothing
	 * return: Nothing
	 * Description: save file
	 */
	private void saveFile() throws IOException{
			fileOperation.saveToFile(taskList,completedTaskList);
	}
	
	/*
	 * parameters: Nothing
	 * return: Nothing
	 * Description: load file
	 */
	private void loadFile() throws IOException{
		System.out.println("load file");
		taskList = fileOperation.getUnfinishedTaskListFromFile();
		completedTaskList = fileOperation.getFinishedTaskListFromFile();
		fileList = configurationFileOperation.getHistoryFilePath();
	}
	
	/*
	 * parameters: Nothing
	 * return: Nothing
	 * Description: save configuration
	 */
	private void saveConfiguration() throws IOException{
		configurationFileOperation.saveConfiguration(fileName, fileList);;
	}
	
	/*
	 * parameters: Nothing
	 * return: Nothing
	 * Description: There are 5 DISPLAY_MODE, only two of them can accept CRUD operation, if 
	 * mode is at some one which cannot accept CRUD, change it to TODO_TASKLIST mode.
	 */
	private void switchToChangeableMode(){
		if (mode != DISPLAY_MODE.SEARCH_LIST){
			mode = DISPLAY_MODE.TODO_TASKLIST;
		}
	}
	
	/*
	 * parameters: Nothing
	 * return: ArrayList<String>
	 * Description: return a copy of task content
	 */
	public ArrayList<String> getFileContent(){
		ArrayList<String> content = new ArrayList<String>(); 
		for (Task task: taskList){
			content.add(task.getContent());
		}
		return content;
	}

	/*
	 * parameters: Nothing
	 * return: ArrayList<Task>
	 * Description: return a copy of taskList based on current DISPLAY_MODE
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Task> getTasks(){
		switch (mode){
		case TODO_TASKLIST:
			ArrayList<Task> answers = new ArrayList<Task>();
			for (int i = 0; i < taskList.size(); i++){
				if (!taskList.get(i).hasFinished())
					answers.add(taskList.get(i));
			}
			return answers;
		case SEARCH_LIST:
			return (ArrayList<Task>) searchResult.clone();
		case FINISHED_TASKLIST:
			return (ArrayList<Task>) completedTaskList.clone();
		case ALL_TASKLIST:
			ArrayList<Task> bothTaskList = (ArrayList<Task>) taskList.clone();
			bothTaskList.addAll((ArrayList<Task>)completedTaskList.clone());
			return bothTaskList;
		default:
			ArrayList<Task> answers2 = new ArrayList<Task>();
			for (int i = 0; i < taskList.size(); i++){
				if (!taskList.get(i).hasFinished())
					answers2.add(taskList.get(i));
			}
			return answers2;
		}
	}
		
	/*
	 * parameters: Nothing
	 * return: ArrayList<String>
	 * Description: return a copy of all feedbacks
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getFeedBacks(){
		return (ArrayList<String>) feedBack.clone();
	}
	
	/*
	 * parameters: Nothing
	 * return: String
	 * Description: return last feedBack
	 */
	public String getLastFeedBack(){
		if (feedBack.size() == 0) feedBack.add("No feedback");
		return feedBack.get(feedBack.size()-1);
	}
	
	/*
	 * parameters: Nothing
	 * return: String
	 * Description: return all titles in one String, used for unit tests
	 */
	public String getAllTitles(){
		String answer = new String("");
		if (mode == DISPLAY_MODE.TODO_TASKLIST){
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
	
	/*
	 * parameters: Nothing
	 * return: ArrayList<String>
	 * Description: return all titles in one arraylist of string, used for unit tests
	 */
	public ArrayList<String> getTaskList(){
		ArrayList<String> answers = new ArrayList<String>();
		for (int i = 0; i < taskList.size(); i++){
			answers.add(taskList.get(i).getContent());
		}
		return answers;
	}
	

	/*
	 * parameters: TaskManager taskList2
	 * return: boolean
	 * Description: check whether two TaskManager is the same. Return a boolean value.
	 */
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
	
	/*
	 * parameters: Nothing
	 * return: int
	 * Description: return last index. For UI to get to know more information
	 */
	public int getLastOperationIndex(){
		return lastOperationIndex;
	}
	
	/*
	 * parameters: String command
	 * return: String
	 * Description: accept a String as a parameter, return the suggested filled up string
	 */
	public String getAutoFill(String command){
		return myParser.autoFill(command);
	}
	
	/*
	 * parameters: String command
	 * return: String
	 * Description: accept a String as a parameter, return the suggested tip
	 */
	public String getCommandTip(String command){
		return myParser.provideTips(command);
	}
	
	/*
	 * parameters: Nothing
	 * return: DISPLAY_MODE
	 * Description: used for UI to get to know what is shown now
	 */
	public DISPLAY_MODE getCurrentMode(){
		
		return mode;
	}
	
	/*
	 * parameters: Nothing
	 * return: String
	 * Description: used for UI to get to know what is current path
	 */
	public String getCurrentPath(){
		return  fileName;
	}

	/*
	 * parameters: Nothing
	 * return: ArrayList<String>
	 * Description: used for UI to get to know all file paths.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getAllFilePath(){
		return (ArrayList<String>) this.fileList.clone();
	}
	
}
