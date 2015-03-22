package UserInterfaceSwing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class TextFieldListener implements DocumentListener {
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		System.out.println(UserInterface.textField.getText());
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		System.out.println(UserInterface.textField.getText());

		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
