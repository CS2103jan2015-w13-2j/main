package ui.list.swing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class TextFieldHistory {
	public static Stack<String> history = new Stack<String> ();
	public static Stack<String> temp = new Stack<String>();
	private final static String UNABLE_TO_OBTAIN_LAST_HISTORY = "invalid";
	
	public static void updateHistory(String input) {
	
		while (!temp.isEmpty()) {
			history.push(temp.pop());
		}

		history.push(input);
	
	}
	
	public static String getLastHistory() {
		
		if (!history.isEmpty()) {
			temp.push(history.pop());
			System.out.println(temp.peek());
			return temp.peek();

		}
		
		return UNABLE_TO_OBTAIN_LAST_HISTORY;
	}
	
	public static String getForwardHistory() {
		
		if (!temp.isEmpty()) {
			
			history.push(temp.pop());
			if (!temp.isEmpty()) {
			System.out.println(temp.peek());
			return temp.peek();

			}
		}
		
		return "";
	}
	
}
