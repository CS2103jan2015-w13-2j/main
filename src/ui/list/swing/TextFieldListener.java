package ui.list.swing;

//@author A0117971Y

import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


//@author A0117971Y

@SuppressWarnings("serial")
public class TextFieldListener extends JTextField implements DocumentListener {
	
	private static String inputStream = "";
	private static final int INVALID_SYNTAX = -1;
    
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
		
		if (UiLogic.isValidModifyListener() != INVALID_SYNTAX) {
			UserInterface.isModify = true;
			System.out.println("valid modify index = " + UiLogic.isValidModifyListener());
			int pageOfModify = PageHandler.getPageOfIndex(UiLogic.isValidModifyListener()-1);
			try {
				PrintHandler.printPage(pageOfModify);
			} catch (NullPointerException | IOException e1) {
				e1.printStackTrace();
			}
		}

		BalloonTipSuggestion.getBalloonTip();		
		String commandTip = UserInterface.BTL.getCommandTip(inputStream);
		setCommandGuideText(commandTip);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		
		inputStream = UserInterface.textField.getText();
		
		if (UiLogic.isValidModifyListener() != INVALID_SYNTAX) {
			UserInterface.isModify = true;
			System.out.println("valid modify index = " + UiLogic.isValidModifyListener());
			int pageOfModify = PageHandler.getPageOfIndex(UiLogic.isValidModifyListener()-1);
			try {
				PrintHandler.printPage(pageOfModify);
			} catch (NullPointerException | IOException e1) {
				e1.printStackTrace();
			}
		}
		
		else if (!inputStream.isEmpty()){
			try {
				PrintHandler.printPage(PageHandler.getCurrentPage());
			} catch (NullPointerException | IOException e1) {
				e1.printStackTrace();
			}
		}
		
		BalloonTipSuggestion.getBalloonTip();
		String commandTip = UserInterface.BTL.getCommandTip(inputStream);
		setCommandGuideText(commandTip);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub		
	}
	
	public static String getInputStream() {
		return inputStream;
	}
	
	private void setCommandGuideText( String commandTip) {
		if (commandTip != null) {
			UserInterface.lblCommandGuide.setText(commandTip);
		}
		
		else {
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
		}
	}
}