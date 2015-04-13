package ui.list.swing;

//@author A0117971Y

import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//@author A0117971Y

/**
 * This class listens for changes in the text field
 * It will then process modify and non modify commands seperately
 * modify operations will be called even before user presses enter.
 * @author A0117971Y
 *
 */

@SuppressWarnings("serial")
public class TextFieldListener extends JTextField implements DocumentListener {
	
	private static String inputStream = "";
	private static final int INVALID_SYNTAX = -1;
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		inputStream = UserInterface.textField.getText();
		
		if (UiLogic.isValidModifyListener() != INVALID_SYNTAX) {
			processModify();
		}
		
		else if (!inputStream.isEmpty() && !PageHandler.isAtFilePage) {
			processNonModify();

		}
		
		setCommandGuideAndTip();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		
		inputStream = UserInterface.textField.getText();
		
		if (UiLogic.isValidModifyListener() != INVALID_SYNTAX) {
			processModify();

		}
		
		else if (!inputStream.isEmpty() && !PageHandler.isAtFilePage){
			processNonModify();

		}
		
		setCommandGuideAndTip();

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	
	}
	
	private static void processNonModify() {
		PageHandler.updatePage();
		print(PageHandler.getCurrentPage());
	}
	
	private static void setCommandGuideAndTip() {
		BalloonTipSuggestion.getBalloonTip();
		String commandTip = UserInterface.BTM.getCommandTip(inputStream);
		setCommandGuideText(commandTip);
	}
	
	private static void processModify() {
		UserInterface.isModify = true;
		int pageOfModify = PageHandler.getPageOfIndex(UiLogic.isValidModifyListener()-1);
		print(pageOfModify);
	}
	
	public static String getInputStream() {
		return inputStream;
	}
	
	private static void print(int page) {
		try {
			PrintHandler.printPage(page);
		} catch (NullPointerException | IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private static void setCommandGuideText( String commandTip) {
		if (commandTip != null) {
			UserInterface.lblCommandGuide.setText(commandTip);
		}
		
		else {
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
		}
	}
}