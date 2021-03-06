package ui.list.swing;

//@author A0117971Y

/**
 * This class manages the text field history
 * Note: Text field history will be stored from start of application to end.
 * History will not be saved upon exit. Empty input will not be stored.
 * Author: A0117971Y
 */
import java.util.Stack;

public class TextFieldHistory {
	private static Stack<String> history = new Stack<String> ();
	private static Stack<String> temp = new Stack<String>();
	private final static String UNABLE_TO_OBTAIN_LAST_HISTORY = "invalid";
	
	public static void updateHistory(String input) {
		if (!input.trim().isEmpty() || input == null) {
			while (!temp.isEmpty()) {
				history.push(temp.pop());
			}
			history.push(input);	
		}
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
