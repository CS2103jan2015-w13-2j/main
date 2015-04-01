package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import taskList.TaskList;

/**
 * APIs:
 * 	int getOperation(String operation) throws NullPointerException
 *  boolean isValid(String operation) throws NullPointerException
 * 	boolean isArgumentsCorrect(String operation) throws NullPointerException
 * 	int getIndex(String operation) throws NullPointerException, 
	StringIndexOutOfBoundsException, IOException
 *  String getNewTitle(String operation) throws NullPointerException, 
	StringIndexOutOfBoundsException
 *  String getTitle(String operation) throws NullPointerException, 
	StringIndexOutOfBoundsException
 * 	String getVenue(String operation) throws NullPointerException
 *  Date getDate(String operation) throws NullPointerException, IOException
 *  Date getDeadline(String operation) throws NullPointerException, IOException
 *  
 * Make sure your operation index is up-to-date every time before calling parser.
 * The latest operation indexes are:
 * 	private static final int OPERATION_UNKNOWN = 0;
 *	private static final int OPERATION_ADD = 1;
 *	private static final int OPERATION_DELETE = 2;
 *	private static final int OPERATION_CLEAR = 3;
 *	private static final int OPERATION_DISPLAY = 4;
 *	private static final int OPERATION_EXIT = 5;
 *	private static final int OPERATION_MODIFY = 6;
 * @author Colonel
 *
 */
public class Parser {
	private static final String EXCEPTION_NOTITLE = "no title inputed";
	private static final String EXCEPTION_INDEXILLEGAL = "the index you entered is illegal";
	private static final String EXCEPTION_NOINDEX = "you must enter an index";
	private static final String EXCEPTION_NULLPOINTER = "The command is null";
	
	private static final int LARGE_CONSTANT = 500;
	private static final int FAIL = -1;
	
	public enum Operation {
		UNKNOW, ADD, DELETE, CLEAR, DISPLAY, EXIT,
		MODIFY, UNDO, REDO, SORT, SEARCH, COMPLETE
	}
	
	private static final String[] KEYWORD_ADD = {"add", "insert"};
	private static final String[] KEYWORD_DELETE = {"delete", "remove", "rm"};
	private static final String[] KEYWORD_CLEAR = {"clear", "claen"};
	private static final String[] KEYWORD_DISPLAY = {"display", "ls", "show"};
	private static final String[] KEYWORD_EXIT = {"exit", "quit"};
	private static final String[] KEYWORD_MODIFY = {"modify", "update"};
	private static final String[] KEYWORD_UNDO = {"undo"};
	private static final String[] KEYWORD_REDO = {"redo"};
	private static final String[] KEYWORD_SORT = {"sort"};
	private static final String[] KEYWORD_SEARCH = {"find", "search"};
	private static final String[] KEYWORD_COMPLETE = {"finish", "complete"};
	
	private static final String[] OPTIONS = {"-v", "-d", "-dd", "-c"};
	
	private static final Pattern NUMBERS = Pattern.compile(".*[^0-9].*");
	
	private static Hashtable<String, Operation> featureList = null; 
	private static DateParser dateParser = null;
	
	private static String name = Parser.class.getName(); 
	private static Logger logger = Logger.getLogger(name);
	
	public Parser() {
		initFeatureList();
		dateParser = new DateParser();
	}

	public Operation getOperation(String operation) throws NullPointerException {
		if (operation == null) {
			logNullPointer(EXCEPTION_NULLPOINTER);
		}
		if (operation.indexOf(' ') != -1) {
			operation = operation.substring(0, operation.indexOf(' '));
		}
		operation = operation.trim();
		Operation operationIndex = getOperationIndex(operation);
		if (operationIndex == null) {
			return Operation.UNKNOW;
		} else {
			return operationIndex;
		}
	}
	
	public boolean isValid(String operation) throws NullPointerException {
		if (operation == null) {
			logNullPointer(EXCEPTION_NULLPOINTER);
		} 
		if (operation.indexOf(' ') != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isArgumentsCorrect(String operation) throws NullPointerException {
		if (operation == null) {
			logNullPointer(EXCEPTION_NULLPOINTER);
		}
		return isArgumentsNumberCorrect(operation) &&
				isArgumentsTypeCorrect(operation);
	}

	public int getIndex(String operation) throws IOException {
		assert(getOperation(operation) == Operation.MODIFY ||
				getOperation(operation) == Operation.DELETE);
		String temp = getTitle(operation);
		if (temp == "" || temp == null) {
			logIOException(EXCEPTION_NOINDEX);
		}
		String[] temps = temp.split(" ");
		Matcher m = NUMBERS.matcher(temps[0]);
		if (m.matches()) {
			logIOException(EXCEPTION_INDEXILLEGAL);
		} 
		return Integer.valueOf(temps[0]);
	}
	
	public String getNewTitle(String operation) throws NullPointerException, 
	IOException {
		assert(getOperation(operation) == Operation.MODIFY);
		String temp = getTitle(operation);
		String[] temps = temp.split(" ");
		if (temps.length < 2) {
			return null;
		} else {
			return combineString(temps);
		}
	}

	public String getTitle(String operation) throws NullPointerException, 
	IOException {
		if (operation == null) {
			logNullPointer(EXCEPTION_NULLPOINTER);
		}
		assert(getOperation(operation) == Operation.ADD ||
				getOperation(operation) == Operation.SEARCH);
		int start = operation.indexOf(' ') + 1;
		if (start >= operation.length() || start == 0) {
			logIOException(EXCEPTION_NOTITLE);
		}
		int end = getFirstOptionIndex(operation);
		if (end == -1) {
			end = operation.length();
		}
		if (start >= end) {
			logIOException(EXCEPTION_NOTITLE);
		}
		return operation.substring(start, end);
	}

	public String getVenue(String operation) throws NullPointerException {
		if (operation == null) {
			logNullPointer(EXCEPTION_NULLPOINTER);
		}
		return getContent("-v", operation);
	}
	
	public Date getDate(String operation) throws NullPointerException, IOException {
		if (operation == null) {
			logNullPointer(EXCEPTION_NULLPOINTER);
		}
		String dateString = getContent("-d", operation);
		if (dateString == null) {
			return null;
		} else {
			return dateParser.getDate(dateString);
		}
	}
	
	public Date getDeadline(String operation) throws NullPointerException, IOException {
		if (operation == null) {
			logNullPointer(EXCEPTION_NULLPOINTER);
		}
		String deadLineString = getContent("-dd", operation);
		if (deadLineString == null) {
			return null;
		} else {
			return dateParser.getDate(deadLineString);
		}
	}
	
	public String autoFill(String str) {
		ArrayList<String> matchResult = searchAllKeyword(str);
		if (matchResult.size() != 1) {
			return null;
		} else { 
			return matchResult.get(0);
		}
	}
	
	private ArrayList<String> searchAllKeyword(String str) {
		ArrayList<String> tempList = new ArrayList<String>();
		ArrayList<String> resultList = new ArrayList<String>();
		tempList.add(searchKeyword(str, KEYWORD_ADD));
		tempList.add(searchKeyword(str, KEYWORD_DELETE));
		tempList.add(searchKeyword(str, KEYWORD_CLEAR));
		tempList.add(searchKeyword(str, KEYWORD_DISPLAY));
		tempList.add(searchKeyword(str, KEYWORD_EXIT));
		tempList.add(searchKeyword(str, KEYWORD_MODIFY));
		tempList.add(searchKeyword(str, KEYWORD_UNDO));
		tempList.add(searchKeyword(str, KEYWORD_REDO));
		tempList.add(searchKeyword(str, KEYWORD_SORT));
		tempList.add(searchKeyword(str, KEYWORD_SEARCH));
		tempList.add(searchKeyword(str, KEYWORD_COMPLETE));
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i) != null) {
				resultList.add(tempList.get(i));
			}
		}
		return resultList;
	}
	private String searchKeyword(String str, String[] keyword) {
		for (String temp:keyword) {
			if (temp.startsWith(str)) {
				return temp;
			}
		}
		return null;
	}

	private boolean isArgumentsTypeCorrect(String operation) {
		assert(operation != null);
		String temp = null;
		int start = operation.indexOf(" -");
		while (start != FAIL) {
			int end = operation.indexOf(" ", start+1);
			if (end == FAIL) {
				end = operation.length();
			}
			temp = operation.substring(start + 1, end);
			if (!isInOptions(temp)) {
				return false;
			}
			start = operation.indexOf(" -", end);
		}
		return true;
	}
	
	private boolean isArgumentsNumberCorrect(String operation) {
		assert(operation != null);
		for (String temp : OPTIONS) {
			if (countOptions(temp, operation) > 1) {
				return false;
			}
		}
		return true;
	}
	
	private int countOptions(String operationType, String operation) {
		assert(isInOptions(operationType));
		assert(operation != null);
		String tempOperation = operation;
		int count = 0;
		int start = findType(operationType, tempOperation);
		while (start != FAIL) {
			count++;
			tempOperation = tempOperation.substring(start + operationType.length(), tempOperation.length());
			start = findType(operationType, tempOperation);
		}
		return count;
	}
	
	private Operation getOperationIndex(String operation) {
		assert(operation != null);
		return featureList.get(operation);
	}
	
	//combine the array of String from the second element onwards
	private String combineString(String[] temps) {
		String str = "";
		temps[0] = "";
		for (int i = 1; i < temps.length; i++) {
			str = str + temps[i] + " ";
		}
		return str.substring(0, str.length()-1);
	}
	
	private int getFirstOptionIndex(String operation) {
		assert(operation != null);
		int tempIndex = LARGE_CONSTANT;
		int temp = 0;
		for (int i = 0; i < OPTIONS.length; i++) {
			temp = operation.indexOf(OPTIONS[i]);
			if (temp > 0) {
				tempIndex = Math.min(temp, tempIndex);
			}
		}
		if (tempIndex == LARGE_CONSTANT) {
			tempIndex = -1;
		}
		return tempIndex;
		
	}
	
	private void initFeatureList() {
		featureList = new Hashtable<String, Operation>();
		addSelectedFeature(KEYWORD_ADD, Operation.ADD);
		addSelectedFeature(KEYWORD_DELETE, Operation.DELETE);
		addSelectedFeature(KEYWORD_CLEAR, Operation.CLEAR);
		addSelectedFeature(KEYWORD_DISPLAY, Operation.DISPLAY);
		addSelectedFeature(KEYWORD_EXIT, Operation.EXIT);
		addSelectedFeature(KEYWORD_MODIFY, Operation.MODIFY);
		addSelectedFeature(KEYWORD_UNDO, Operation.UNDO);
		addSelectedFeature(KEYWORD_REDO, Operation.REDO);
		addSelectedFeature(KEYWORD_REDO, Operation.REDO);
		addSelectedFeature(KEYWORD_SORT, Operation.SORT);
		addSelectedFeature(KEYWORD_SEARCH, Operation.SEARCH);
		addSelectedFeature(KEYWORD_COMPLETE, Operation.COMPLETE);
	}
	
	private void addSelectedFeature(String[] keyword, Operation operation) {
		assert(keyword != null);
		assert(operation != null);
		for (int i = 0; i < keyword.length; i++) {
			featureList.put(keyword[i], operation);
		}
	}
	
	private String getContent(String operationType, String operation) {
		assert(isInOptions(operationType));
		assert(operation != null);
		int operationIndex = findType(operationType, operation);
		if (operationIndex == FAIL) return null;
		int begin = operationIndex + operationType.length() + 1;
		int end = operation.indexOf(" -", begin);
		if (end == FAIL) {
			end = operation.length();
		}
		return operation.substring(begin, end);
	}
	
	private boolean isInOptions(String operationType) {
		for (String temp : OPTIONS) {
			if (temp.equals(operationType)) {
				return true;
			}
		}
		return false;
	}
	
	//return the index of an certain exact operation type
	private int findType(String operationType, String operation) {
		assert(isInOptions(operationType));
		assert(operation != null);
		int temp = operation.indexOf(operationType);
		boolean isFound = false;
		while (temp != -1 && !isFound) {
			if (operation.charAt(temp+operationType.length()) == ' ') {
				isFound = true;
			} else {
				temp = operation.indexOf(operationType, temp + 1);
			}
		}
		if (isFound) {
			return temp;
		} else {
			return FAIL;
		}
	}
	
	private void logNullPointer(String msg) throws NullPointerException {
		logger.log(Level.WARNING, msg);
		throw new NullPointerException(msg);
	}
	
	private void logIOException(String msg) throws IOException {
		logger.log(Level.WARNING, msg);
		throw new IOException(msg);
	}
}
