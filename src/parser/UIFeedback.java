package parser;

import java.util.ArrayList;
import parser.Parser.Operation;

public class UIFeedback {
	private static final String EXCEPTION_NULLPOINTER = "The command is null";
	
	private static final String FEEDBACK_ADD = 
			"Tip: add<task> -d<time> -v<venue> to add task with date & venue";
	private static final String FEEDBACK_DELETE = "Tip: delete<index> to delete a task";
	private static final String FEEDBACK_MODIFY = 
			"Tip: modify<index> <new title> -d<new time> -v<new venue> to modify task";
	private static final String FEEDBACK_SORT = "Tip: sort<time/venue/title> to sort tasks";
	private static final String FEEDBACK_SEARCH = "Tip: search<title/time/venue> to search tasks";
	private static final String FEEDBACK_COMPLETE = "Tip: complete<index> to mark a task completed";
	private static final String FEEDBACK_IMPORT = "Tip: import<index/path> to import a schedule file";
	private static final String FEEDBACK_EXPORT = "Tip: export<index/path> to save schedul to a file";
	
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
	
	public String findMatch(String str) throws NullPointerException {
		if (str == null) {
			throw new NullPointerException(EXCEPTION_NULLPOINTER);
		}
		ArrayList<String> matchResult = searchAllKeyword(str);
		if (matchResult.size() != 1) {
			return null;
		} else { 
			return matchResult.get(0);
		}
	}
	
	public String findTips(Operation operation) throws NullPointerException {
		if (operation == null) {
			throw new NullPointerException(EXCEPTION_NULLPOINTER);
		}
		String feedback;
		Operation temp = operation;
		switch (temp) {
		case ADD:
			feedback = FEEDBACK_ADD;
			break;
		case DELETE:
			feedback = FEEDBACK_DELETE;
			break;
		case MODIFY:
			feedback = FEEDBACK_MODIFY;
			break;
		case SORT:
			feedback = FEEDBACK_SORT;
			break;
		case SEARCH:
			feedback = FEEDBACK_SEARCH;
			break;
		case COMPLETE:
			feedback = FEEDBACK_COMPLETE;
			break;
		case IMPORT:
			feedback = FEEDBACK_IMPORT;
			break;
		case EXPORT:
			feedback = FEEDBACK_EXPORT;
			break;
		default:
			feedback = null;
			break;
		}
		return feedback;
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
		tempList.add(searchKeyword(str, KEYWORD_IMPORT));
		tempList.add(searchKeyword(str, KEYWORD_EXPORT));
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

}
