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
	private final String COMMAND_GUIDE_ADD_MESSAGE = "Tip: add <task name> -d <date> -v <venue> to add a task with date and venue";
	private final String COMMAND_GUIDE_DELETE_MESSAGE = "Tip: delete <index number> to delete a task";

	@Override
	public void insertUpdate(DocumentEvent e) {
		inputStream = UserInterface.textField.getText();
		if (inputStream.toLowerCase().contains("add")) {
			System.out.println("add detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_ADD_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("delete")) {
			System.out.println("delete detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_DELETE_MESSAGE);
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
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_ADD_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("delete")) {
			System.out.println("delete detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_DELETE_MESSAGE);
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