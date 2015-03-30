package ui.list.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class TextFieldListener implements DocumentListener {
	
	private String inputStream = "";
	private final String COMMAND_GUIDE_ADD_MESSAGE = "Tip: add <task> -d <date> -v <venue> to add task with date & venue";
	private final String COMMAND_GUIDE_DELETE_MESSAGE = "Tip: delete <index number> to delete a task";
	private final String COMMAND_GUIDE_MODIFY_MESSAGE = "Tip: modify <index> <new name> -d <new date> -v <new venue>";
	/*
	 * Testing of GUI interface can be done by performing black-box testing. That is, just running the program without looking at any code.
	 * In order to be Effective and Efficient, we have to make use of some testing heuristics such as Equivalence partitioning, boundary value analysis (bva)
	 * and combining multiple inputs.
	 * 
	 * For testing of GUI, the most relevant testing heuristics would be combining of multiple inputs.
	 * Because user are required to enter task description, task date(optional), task venue (optional), etc...
	 * We can test input in such a way that only one invalid input per case 
	 * e.g. add <valid task name> -d <invalid date> -v <valid venue> or "add <valid task name> -d <valid date> -v <invalid venue>
	 * In this case, we need to consider the factor, whether the entire operation will be voided or only valid input will be registered. 
	 * 
	 */
	
	
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		inputStream = UserInterface.textField.getText();
		if (inputStream.toLowerCase().contains("add")) {
			System.out.println("add detected");
			UserInterface.isAdd = true;
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_ADD_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("delete")) {
			System.out.println("delete detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_DELETE_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("modify")) {
			System.out.println("modify detected");
			UserInterface.lblCommandGuide.setText((COMMAND_GUIDE_MODIFY_MESSAGE));
		}
		
		else {
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		inputStream = UserInterface.textField.getText();
		if (inputStream.toLowerCase().contains("add")) {
			System.out.println("add detected");
			UserInterface.isAdd = true;
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_ADD_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("delete")) {
			System.out.println("delete detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_DELETE_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("modify")) {
			System.out.println("modify detected");
			UserInterface.lblCommandGuide.setText((COMMAND_GUIDE_MODIFY_MESSAGE));
		}
		
		else {
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}