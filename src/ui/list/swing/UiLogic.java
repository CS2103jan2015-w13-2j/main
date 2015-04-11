package ui.list.swing;

public class UiLogic {
	
	public static boolean isValidAdd(String currentInput) {

		if (currentInput != null && !currentInput.equals("")) {
			String[] tokens = currentInput.split(" ");
			if (tokens.length >= 2 && tokens[0].toLowerCase().equals("add")) {
				return true;
			}
		}

		return false;
	}
	
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
}
