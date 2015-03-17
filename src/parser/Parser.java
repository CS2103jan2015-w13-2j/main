package parser;

import java.util.Date;
import java.util.Hashtable;

/**
 * APIs:
 * 	int getOperation(String): implemented
 * 	String getTitle(String) throws StringIndexOutOfBoundsException: implemented
 * 	String getVenue(String): implemented
 *  Date getDate(String): implemented
 *  	notice! time also included in getDate() method
 *  Date getDeadline(String): implemented
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
	private static final int LARGE_CONSTANT = 500;
	private static final int FAIL = -1;
	private static final int OPERATION_ADD = 1;
	private static final int OPERATION_DELETE = 2;
	private static final int OPERATION_CLEAR = 3;
	private static final int OPERATION_DISPLAY = 4;
	private static final int OPERATION_EXIT = 5;
	private static final int OPERATION_MODIFY = 6;
	
	private static final String[] KEYWORD_ADD = {"add", "insert"};
	private static final String[] KEYWORD_DELETE = {"delete", "remove", "rm"};
	private static final String[] KEYWORD_CLEAR = {"clear"};
	private static final String[] KEYWORD_DISPLAY = {"display", "ls", "show"};
	private static final String[] KEYWORD_EXIT = {"exit", "quit"};
	private static final String[] KEYWORD_MODIFY = {"modify", "update"};
	
	private static final String[] OPTIONS = {"-v", "-d", "-dd", "-c"};
	
	private static Hashtable<String, Integer> featureList = null; 
	private static DateParser dateParser = null;
	
	public Parser() {
		initFeatureList();
		dateParser = new DateParser();
	}
	
	private void initFeatureList() {
		featureList = new Hashtable<String, Integer>();
		addSelectedFeature(KEYWORD_ADD, OPERATION_ADD);
		addSelectedFeature(KEYWORD_DELETE, OPERATION_DELETE);
		addSelectedFeature(KEYWORD_CLEAR, OPERATION_CLEAR);
		addSelectedFeature(KEYWORD_DISPLAY, OPERATION_DISPLAY);
		addSelectedFeature(KEYWORD_EXIT, OPERATION_EXIT);
		addSelectedFeature(KEYWORD_MODIFY, OPERATION_MODIFY);
	}
	
	private void addSelectedFeature(String[] keyword, Integer operation) {
		for (int i = 0; i < keyword.length; i++) {
			featureList.put(keyword[i], operation);
		}
	}
	
	public int getOperation(String operation) {
		if (operation.indexOf(' ') != -1) {
			operation = operation.substring(0, operation.indexOf(' '));
		}
		operation = operation.trim();
		Integer operationIndex = getOperationIndex(operation);
		if (operationIndex == null) {
			return OPERATION_ADD;
		} else {
			return operationIndex;
		}
	}
	
	private Integer getOperationIndex(String operation) {
		return featureList.get(operation);
	}
	
	public String getTitle(String operation) throws 
	StringIndexOutOfBoundsException {
		int start;
		int end = getFirstOptionIndex(operation);
		if (end != -1) {
			end = end - 1;
		} else {
			end = operation.length();
		}
		if (containsOperation(operation)) {
			start = operation.indexOf(' ');
			start = start + 1;
			if (start >= operation.length()) {
				throw new StringIndexOutOfBoundsException("no title inputed");
			}
		} else {
			start = 0;
		}
		return operation.substring(start, end);
	}
	
	//true is the command contains an operation mark
	private boolean containsOperation(String operation) {
		String operationString;
		int end = operation.indexOf(' ');
		if (end == -1) {
			operationString = operation;
		} else {
			operationString = operation.substring(0, end);
		}
		return isInList(operationString);
	}
	
	private boolean isInList(String operationString) {
		for (String temp:KEYWORD_ADD) {
			if (temp.equals(operationString)) {
				return true;
			}
		}
		for (String temp:KEYWORD_DELETE) {
			if (temp.equals(operationString)) {
				return true;
			}
		}
		return false;
	}
	
	private int getFirstOptionIndex(String operation) {
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

	public String getVenue(String operation) {
		return getContent("-v", operation);
	}
	
	public Date getDate(String operation) {
		String dateString = getContent("-d", operation);
		if (dateString == null) {
			return null;
		} else {
			return dateParser.getDate(dateString);
		}
	}
	
	public Date getDeadline(String operation) {
		String deadLineString = getContent("-dd", operation);
		if (deadLineString == null) {
			return null;
		} else {
			return dateParser.getDate(deadLineString);
		}
	}

	private String getContent(String operationType, String operation) {
		int operationIndex = findType(operationType, operation);
		if (operationIndex == FAIL) return null;
		int begin = operationIndex + operationType.length() + 1;
		int end = operation.indexOf(" -", begin);
		if (end == FAIL) {
			end = operation.length();
		}
		return operation.substring(begin, end);
	}
	
	//return the index of an certain exact operation type
	private int findType(String operationType, String operation) {
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
}
