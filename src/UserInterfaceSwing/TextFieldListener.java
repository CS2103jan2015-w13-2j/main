package UserInterfaceSwing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class TextFieldListener implements DocumentListener {
	
	private String inputStream = "";
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		inputStream = UserInterface.textField.getText();
		if (inputStream.equals("add")) {
			System.out.println("add detected");
			UserInterface.commandGuideLabel.setText("Tip: add <task name> -d <date> -v <venue> to add a task with date and venue");
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		inputStream = UserInterface.textField.getText();
		if (inputStream.equals("add")) {
			System.out.println("add detected");
			UserInterface.commandGuideLabel.setText("Tip: add <task name> -d <date> -v <venue> to add a task with date and venue");
		}
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
