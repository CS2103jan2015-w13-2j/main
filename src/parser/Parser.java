package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@author A0119503M
/**
 * This class supports main functions to understand users' command, 
 * the controller can call these functions to get the operation type 
 * as well as various contents of users' commands.
 * APIs:
 * 	getOperation(String): Operation throws NullPointerException
 *  isValid(String): boolean throws NullPointerException
 * 	isArgumentsCorrect(String): boolean throws NullPointerException
 * 	getIndex(String): int throws IOException
 *  getNewTitle(String): String throws NullPointerException, IOException
 *  getTitle(String): String throws NullPointerException, IOException
 * 	getVenue(String): String throws NullPointerException
 *  getDate(String): Date throws NullPointerException, IOException
 *  getDeadline(String): Date throws NullPointerException, IOException
 *  autoFill(String): String throws NullPointerException
 *  provideTips(String): String throws NullPointerException
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
		MODIFY, UNDO, REDO, SORT, SEARCH, COMPLETE,
		IMPORT, EXPORT
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
	private static final String[] KEYWORD_IMPORT = {"import", "load"};
	private static final String[] KEYWORD_EXPORT = {"export", "save"};
	
	private static final String[] OPTIONS = {"-v", "-d", "-dd", "-c"};
	
	private static final Pattern REGEX_NUMBERS = Pattern.compile(".*[^0-9].*");
	private static final Pattern REGEX_COMMA = Pattern.compile(",");
	private static final Pattern REGEX_SPACE = Pattern.compile(" ");
	
	private static Hashtable<String, Operation> featureList = null; 
	private static DateParser dateParser = null;
	private static FormatChecker checker = null;
	private static UIFeedback feedback = null; 
	private static String name = Parser.class.getName(); 
	private static Logger logger = Logger.getLogger(name);
	
	public Parser() {
		initFeatureList();
		dateParser = new DateParser();
		checker = new FormatChecker();
		feedback = new UIFeedback();
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
		boolean result = false;
		try {
			result = checker.isValidFormat(operation);
		} catch (NullPointerException e) {
			logNullPointer(e.getMessage());
		}
		return result;
	}
	
	public boolean isArgumentsCorrect(String operation) throws NullPointerException {
		boolean result = false;
		try {
			result = checker.isArgumentsFormatCorrect(operation);
		} catch (NullPointerException e) {
			logNullPointer(e.getMessage());
		}
		return result;
	}

	public ArrayList<Integer> getIndex(String operation) throws IOException {
		assert(getOperation(operation) == Operation.MODIFY ||
				getOperation(operation) == Operation.DELETE);
		String temp = getTitle(operation);
		Matcher m;
		if (temp == "" || temp == null) {
			logIOException(EXCEPTION_NOINDEX);
		}
		String[] temps = REGEX_SPACE.split(temp);
		String[] tempGroup = REGEX_COMMA.split(temps[0]);
		ArrayList<Integer> indexGroup = new ArrayList<Integer>();
		for (String str : tempGroup) {
			m = REGEX_NUMBERS.matcher(str);
			if (m.matches()) {
				logIOException(EXCEPTION_INDEXILLEGAL);
			} else {
				indexGroup.add(Integer.valueOf(str));
			}
		}
		return indexGroup;
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
		String temp = operation.substring(start, end);
		return eliminateSpace(temp);
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
	
	public String autoFill(String str) throws NullPointerException {
		String result = null;
		try {
			result = feedback.findMatch(str);
		} catch (NullPointerException e) {
			logNullPointer(e.getMessage());
		}
		return result;
	}
	
	public String provideTips(String operation) throws NullPointerException {
		String result = null;
		try {
			Operation operationType = getOperation(operation);
			result = feedback.findTips(operationType);
		} catch (NullPointerException e) {
			logNullPointer(e.getMessage());
		}
		return result;
	}
	
	private String eliminateSpace(String str) {
		if (str == null) return "";
		assert(str != null);
		if (str.equals("")) {
			return str;
		}
		int start = 0;
		while (str.charAt(start) == ' ') {
			if (start < str.length()) {
				start++;
			}
		}
		int end = str.length() - 1;
		while (str.charAt(end) == ' ') {
			if (end > 0) {
				end--;
			}
		}
		return str.substring(start, end+1);
		
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
		addSelectedFeature(KEYWORD_IMPORT, Operation.IMPORT);
		addSelectedFeature(KEYWORD_EXPORT, Operation.EXPORT);
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
