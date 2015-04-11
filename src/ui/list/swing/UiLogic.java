package ui.list.swing;

import java.io.IOException;

//@author A0117971Y

public class UiLogic {
	
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
				try {
					int deleteIndex = Integer.parseInt(tokens[1]);

					if (deleteIndex <= UserInterface.taskList.size()) {
						UserInterface.deleteIndex = deleteIndex;
						return deleteIndex;
					}
				} catch (Exception e) {
					return -1;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * Check if input is a valid modify command
	 * @return index of modify opeataion, -1 if invalid
	 */
	
	public static int isValidModifyListener() {
		String currentInput = TextFieldListener.getInputStream();
		
		if (currentInput != null && !currentInput.equals("")) {
			String[] tokens = currentInput.split(" ");
				if (tokens.length >= 2 && tokens[0].toLowerCase().equals("modify")) {
					try {
						int modifyIndex = Integer.parseInt(tokens[1]);
						
						if (modifyIndex <= UserInterface.taskList.size()) {
							return modifyIndex;
						}
					} catch (Exception e) {
						return -1;
					}
				}
		}
		
		return -1;
	}
	
	public static int isValidComplete(String input) {
		
		String currentInput = input;
		
		if (currentInput != null && !currentInput.equals("")) {
			String[] tokens = currentInput.split(" ");
			if (tokens.length >= 2 && tokens[0].toLowerCase().equals("finish")) {
				try {
					int completeIndex = Integer.parseInt(tokens[1]);

					if (completeIndex <= UserInterface.taskList.size()) {
						UserInterface.completeIndex = completeIndex;
						return completeIndex;
					}
				} catch (Exception e) {
					return -1;
				}
			}
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
		PrintHandler.printStatusMessage();
		UserInterface.isAdd = false;
	}
	
	
	private static void processComplete(String input) throws NullPointerException, IOException {
		System.out.println("is valid complete");
		PrintHandler.printPage(PageHandler.getPageOfIndex( UserInterface.completeIndex-1));
		executeAndUpdate(input);	
	}
	
	
	/**
	 * Processes delete operations
	 * @param input
	 * @throws NullPointerException
	 * @throws IOException
	 */
	
	private static void processDelete(String input) throws NullPointerException, IOException {
		System.out.println("is valid delete");
		PrintHandler.printPage(PageHandler.getPageOfIndex( UserInterface.deleteIndex-1));
		executeAndUpdate(input);	
	}
	
	/**
	 * Processes non delete operation
	 * @param input
	 * @throws NullPointerException
	 * @throws IOException
	 */
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
		UserInterface.BTL.executeCommand(input);
		UserInterface.taskList =  UserInterface.BTL.getTasks();		
	}
}

