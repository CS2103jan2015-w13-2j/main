package Paser;

public class BasicPaser {
	private static final int OPERATION_UNKNOWN = 0;
	private static final int OPERATION_ADD = 1;
	private static final int OPERATION_DELETE = 2;
	private static final int OPERATION_CLEAR = 3;
	private static final int OPERATION_DISPLAY = 4;
	private static final int OPERATION_EXIT = 5;
	
	private int getOperation(String operation) {
		if (operation.indexOf(' ') != -1) {
			operation = operation.substring(0, operation.indexOf(' '));
		}
		operation = operation.trim();
		switch (operation) {
			case "add":
				return OPERATION_ADD;
			case "delete":
				return OPERATION_DELETE;
			case "display":
				return OPERATION_DISPLAY;
			case "clear":
				return OPERATION_CLEAR;
			case "exit":
				return OPERATION_EXIT;
			default:
				return OPERATION_UNKNOWN;
		}
	}
	
	public void excuteCommand(String command) {
		switch (getOperation(command)) {
			case OPERATION_ADD:
				System.out.println("add");
				//add(command);
				break;
			case OPERATION_DELETE:
				System.out.println("delete");
				//delete(command);
				break;
			case OPERATION_DISPLAY:
				System.out.println("display");
				//display();
				break;
			case OPERATION_CLEAR:
				System.out.println("clear");
				//clear();
				break;
			case OPERATION_EXIT:
				System.out.println("exit");
				System.exit(0);
				break;
			default:
				System.out.println("unknown");
				//unknownOperation();
		}
	}
}
