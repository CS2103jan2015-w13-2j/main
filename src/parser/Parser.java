package parser;

import java.io.IOException;
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
	
	private static final String REGEX_VENUE = "[at|in]?.*";
	private static final String REGEX_TIME = "[at|on]?.*";
	private static final String REGEX_DEADLINE = "[by|due on|due at]?.*";
	private static final String REGEX_ADD = "\".+\"\\s*.*";
	private static final String REGEX_DELETE = "[delete|rm|remove]\\s*[0..9]*";
	private static final String REGEX_CLEAR = "[clear|clean]";
	private static final String REGEX_DISPLAY = "[display|ls|show]";
	private static final String REGEX_EXIT = "[exit|quit]";
	private static final String REGEX_MODIFY = "[update|modify]\\s*[0..9]+\\s+.+\\s+"+
												REGEX_VENUE+"\\s+"+REGEX_TIME;
	private static final String REGEX_UNDO = "[undo]";
	private static final String REGEX_REDO = "[redo]";
	private static final String REGEX_SEARCH = "[search]\\s+.*";

	private static final int LARGE_CONSTANT = 500;
	private static final int FAIL = -1;
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
	
	private static final String[] KEYWORD_ADD = {"add", "insert"};
	private static final String[] KEYWORD_DELETE = {"delete", "remove", "rm"};
	private static final String[] KEYWORD_CLEAR = {"clear", "claen"};
	private static final String[] KEYWORD_DISPLAY = {"display", "ls", "show"};
	private static final String[] KEYWORD_EXIT = {"exit", "quit"};
	private static final String[] KEYWORD_MODIFY = {"modify", "update"};
	private static final String[] KEYWORD_UNDO = {"undo"};
	private static final String[] KEYWORD_REDO = {"redo"};
	private static final String[] KEYWORD_SORT = {"sort"};
	private static final String[] KEYWORD_SEARCH = {"find","search"};
	
	private static final String[] OPTIONS = {"-v", "-d", "-dd", "-c"};
	
	private static final Pattern NUMBERS = Pattern.compile(".*[^0-9].*");
	private static final Pattern COMMAND_ADD_TIME = Pattern.compile(REGEX_ADD+REGEX_VENUE+REGEX_TIME);
	private static final Pattern COMMAND_ADD_DEAD = Pattern.compile(REGEX_ADD+REGEX_VENUE+REGEX_DEADLINE);
	private static final Pattern COMMAND_DELETE = Pattern.compile(REGEX_DELETE);
	private static final Pattern COMMAND_CLEAR = Pattern.compile(REGEX_CLEAR);
	private static final Pattern COMMAND_DISPLAY = Pattern.compile(REGEX_DISPLAY);
	private static final Pattern COMMAND_MODIFY = Pattern.compile(REGEX_MODIFY);
	private static final Pattern COMMAND_SEARCH = Pattern.compile(REGEX_SEARCH);
	private static final Pattern COMMAND_UNDO = Pattern.compile(REGEX_UNDO);
	private static final Pattern COMMAND_REDO = Pattern.compile(REGEX_REDO);
	private static final Pattern COMMAND_EXIT = Pattern.compile(REGEX_EXIT);
	
	private static Hashtable<String, Integer> featureList = null; 
	private static DateParser dateParser = null;
	
	private static String name = Parser.class.getName(); 
	private static Logger logger = Logger.getLogger(name);
	
	public Parser() {
		initFeatureList();
		dateParser = new DateParser();
	}
	
	public int getOperation(String operation) throws NullPointerException {
		if (operation == null) {
			logNullPointer(EXCEPTION_NULLPOINTER);
		}
		if (operation.indexOf(' ') != -1) {
			operation = operation.substring(0, operation.indexOf(' '));
		}
		operation = operation.trim();
		Integer operationIndex = getOperationIndex(operation);
		if (operationIndex == null) {
			return OPERATION_UNKNOWN;
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
		assert(getOperation(operation) == OPERATION_MODIFY ||
				getOperation(operation) == OPERATION_DELETE);
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
		assert(getOperation(operation) == OPERATION_MODIFY);
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
		assert(getOperation(operation) == OPERATION_ADD ||
				getOperation(operation) == OPERATION_SEARCH);
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
	
	private Integer getOperationIndex(String operation) {
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
		featureList = new Hashtable<String, Integer>();
		addSelectedFeature(KEYWORD_ADD, OPERATION_ADD);
		addSelectedFeature(KEYWORD_DELETE, OPERATION_DELETE);
		addSelectedFeature(KEYWORD_CLEAR, OPERATION_CLEAR);
		addSelectedFeature(KEYWORD_DISPLAY, OPERATION_DISPLAY);
		addSelectedFeature(KEYWORD_EXIT, OPERATION_EXIT);
		addSelectedFeature(KEYWORD_MODIFY, OPERATION_MODIFY);
		addSelectedFeature(KEYWORD_UNDO, OPERATION_UNDO);
		addSelectedFeature(KEYWORD_REDO, OPERATION_REDO);
		addSelectedFeature(KEYWORD_REDO, OPERATION_REDO);
		addSelectedFeature(KEYWORD_SORT, OPERATION_SORT);
		addSelectedFeature(KEYWORD_SEARCH, OPERATION_SEARCH);
	}
	
	private void addSelectedFeature(String[] keyword, Integer operation) {
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
