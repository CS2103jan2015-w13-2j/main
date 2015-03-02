package Parser;

import java.util.Hashtable;

public class BasicParser {
	private static final int OPERATION_UNKNOWN = 0;
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
	
	private static Hashtable<String, Integer> featureList = null; 
	
	public static void initParser() {
		featureList = new Hashtable<String, Integer>();
		addSelectedFeature(KEYWORD_ADD, OPERATION_ADD);
		addSelectedFeature(KEYWORD_DELETE, OPERATION_DELETE);
		addSelectedFeature(KEYWORD_CLEAR, OPERATION_CLEAR);
		addSelectedFeature(KEYWORD_DISPLAY, OPERATION_DISPLAY);
		addSelectedFeature(KEYWORD_EXIT, OPERATION_EXIT);
		addSelectedFeature(KEYWORD_MODIFY, OPERATION_MODIFY);
	}
	
	private static void addSelectedFeature(String[] keyword, Integer operation) {
		for (int i = 0; i < keyword.length; i++) {
			featureList.put(keyword[i], operation);
		}
	}
	
	public static int getOperation(String operation) {
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
	
	private static int getOperationIndex(String operation) {
		return featureList.get(operation);
	}

	public static String getVenue(String operation) {
		return getContent("-v", operation);
	}
	
	public static String getDate(String operation) {
		return getContent("-d", operation);
	}
	
	public static String getTime(String operation) {
		return getContent("-t", operation);
	}
	
	public static String getDeadline(String operation) {
		return getContent("-dd", operation);
	}

	private static String getContent(String operationType, String operation) {
		if (operation.contains(operationType)) {
			int begin = findType(operationType, operation) + operationType.length() + 1;
			int end = operation.indexOf("-", begin);
			if (end == -1) {
				end = operation.length();
			} else {
				end--;
			}
			return operation.substring(begin, end);
		} else {
			return null;
		}
	}
		
	//return the index of an certain exact operation type
	private static int findType(String operationType, String operation) {
		int temp = operation.indexOf(operationType);
		boolean isFound = false;
		while (temp != -1 && !isFound) {
			if (operation.charAt(temp+operationType.length()) == ' ') {
				isFound = true;
			} else {
				temp = operation.indexOf(operationType, temp + 1);
			}
		}
		return temp;
	}
}
