package ui.list.swing;

import java.awt.Frame;
import java.io.IOException;

//@author A0117971Y

public class UiLogic {
	
	public static final int DELETE_MODE = 0;
	public static final int MODIFY_MODE = 1;
	public static final int COMPLETE_MODE = 2;

	
	/**
	 * Checks if its a valid add operation
	 * @param input by user
	 * @return
	 */
	public static boolean isValidAdd(String input) {

		if (input != null && !input.equals("")) {
			String[] tokens = input.split(" ");
			if (tokens.length >= 2 && tokens[0].toLowerCase().equals("add")) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Checks if input is a valid delete command
	 * @param input, input is the text field stream
	 * @return index of delete operation, return -1 if invalid
	 */
	
	public static int isValidDeleteIndex(String input) {
		String currentInput = input;

		if (currentInput != null && !currentInput.equals("")) {
			String[] tokens = currentInput.split(" ");
			if (tokens.length >= 2 && tokens[0].toLowerCase().equals("delete")) {
				return getOperationIndex(tokens[1], DELETE_MODE);
			}
		}
		return -1;
	}
	
	/**
	 * Check if input is a valid modify command while user is typing
	 * @return index of modify operation, -1 if invalid
	 */
	
	public static int isValidModifyListener() {
		String currentInput = TextFieldListener.getInputStream();

		if (currentInput != null && !currentInput.equals("")) {
			String[] tokens = currentInput.split(" ");
			if (tokens.length >= 2 && tokens[0].toLowerCase().equals("modify")) {
				return getOperationIndex(tokens[1], MODIFY_MODE);
			}
		}

		return -1;
	}
	
	/**
	 * Check if input is a valid complete command
	 * @param input
	 * @return index of complete operation, -1 if invalid
	 */
	
	public static int isValidComplete(String input) {

		String currentInput = input;

		if (currentInput != null && !currentInput.equals("")) {
			String[] tokens = currentInput.split(" ");
			if (tokens.length >= 2) {
				switch (tokens[0].toLowerCase()) {
				case "finish": return getOperationIndex(tokens[1],COMPLETE_MODE);
				case "complete": return getOperationIndex(tokens[1],COMPLETE_MODE);
				default: return -1;
				}

			}
		}
		return -1;
	}

	/**
	 * 
	 * @param index
	 * @param mode
	 * @return index of operation performed
	 */
	private static int getOperationIndex(String index, int mode) {
		try {
			int operationIndex = Integer.parseInt(index);
			if (operationIndex <= UserInterface.taskList.size()) {
				
				switch (mode) {
				case COMPLETE_MODE: UserInterface.completeIndex = operationIndex; return operationIndex;
				case DELETE_MODE: UserInterface.deleteIndex = operationIndex; return operationIndex;
				case MODIFY_MODE: return operationIndex;				
				}				
			}
		} catch (Exception e) {
			return -1;
		
		}
		return -1;
	}
	
	
	/**
	 * Processes text field after user pressed enter
	 * @throws NullPointerException
	 * @throws IOException
	 */
	
	public static void processTextField() throws NullPointerException, IOException {

		String input = UserInterface.textField.getText();
		UserInterface.deleteIndex = UiLogic.isValidDeleteIndex(input);
		UserInterface.completeIndex=UiLogic.isValidComplete(input);
	
		//valid delete
		if ( UserInterface.deleteIndex != -1) {
			processDelete(input);
		}
		
		else if (UserInterface.completeIndex != -1) {
			processComplete(input);
		}

		else {
			processNonDelete(input);
		}
		
		TextFieldHistory.updateHistory(input);	
		UserInterface.textField.setText(null);
		PrintHandler.printStatusMessage();
		UserInterface.isAdd = false;
	}
	
	
	private static void processComplete(String input) throws NullPointerException, IOException {
		System.out.println("is valid complete");
		PrintHandler.printPage(PageHandler.getPageOfIndex( UserInterface.completeIndex-1));
		executeAndUpdate(input);	
	}
	
	private static void processDelete(String input) throws NullPointerException, IOException {
		System.out.println("is valid delete");
		PrintHandler.printPage(PageHandler.getPageOfIndex( UserInterface.deleteIndex-1));
		executeAndUpdate(input);	
	}
	
	private static void processNonDelete(String input) throws NullPointerException, IOException {
		if (UiLogic.isValidAdd(input)) {
			UserInterface.isAdd = true;
		}
		
		executeAndUpdate(input);	
		PageHandler.updatePage();
		UserInterface.display(PageHandler.getCurrentPage());
	}
	
	/**
	 * Executes command in program logic and updates tasklist
	 * @param input
	 */
	private static void executeAndUpdate(String input) {
		UserInterface.BTM.executeCommand(input);
		UserInterface.taskList =  UserInterface.BTM.getTasks();		
	}
	
	/**
	 * Executes maximize and minimize of window
	 */
	public static void processMaxMin() {

		int state = UserInterface.frame.getExtendedState(); // get current state

		if (UserInterface.isMinimized) {
			
			//maximize
			state = state & ~Frame.ICONIFIED; // remove minimized from the state
			UserInterface.frame.setExtendedState(state);
			UserInterface.isMinimized = false;
		}
		
		else {	
			
			//minimize
			state = state | Frame.ICONIFIED; // add minimized to the state
			UserInterface.frame.setExtendedState(state); // set that state
			UserInterface.isMinimized = true;
		}
	}
}

