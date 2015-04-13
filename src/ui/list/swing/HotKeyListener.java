package ui.list.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

//@author A0117971Y

/**
 * Listens for hotkey and process it accordingly
 * @author A0117971Y
 *
 */
public class HotKeyListener extends KeyAdapter {
	
	private static final String INVALID = "invalid";
	
	public void keyPressed(KeyEvent arg1) {

		if(arg1.getKeyCode() == KeyEvent.VK_ENTER) {
			enterHandler();
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_TAB) {
			tabHandler();
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_LEFT) {
			leftKeyHandler();
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightKeyHandler();
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_F1) {
			F1Handler();
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_ESCAPE && UserInterface.atHelpMenu) {
			escHandler();
		}
		//detect ctrl+m
		else if ((arg1.getKeyCode() == KeyEvent.VK_M) && ((arg1.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			ctrlMHandler();
		}

		else if (arg1.getKeyCode() == KeyEvent.VK_UP) {
			upKeyHandler();
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_DOWN) {
			downKeyHandler();
		}
	}
	
	private static boolean canPressLeftTaskPage() {
		boolean checkTextField = UserInterface.textField.getText().isEmpty();
		boolean checkCurrentPage = PageHandler.getCurrentPage() > 0;
		boolean notAtHelpMenu = !UserInterface.atHelpMenu;
		boolean notAtFilePage = !PageHandler.isAtFilePage;
		
		return checkTextField && checkCurrentPage && notAtHelpMenu && notAtFilePage;
	}
	
	private static boolean canPressLeftFilePage() {
		boolean checkTextField = UserInterface.textField.getText().isEmpty();
		boolean checkCurrentPage =  PageHandler.getFileCurrentPage() > 0;
		boolean notAtHelpMenu = !UserInterface.atHelpMenu;
		boolean isAtFilePage = PageHandler.isAtFilePage;
		
		return checkTextField && checkCurrentPage && notAtHelpMenu && isAtFilePage;
	}
	
	private static void leftKeyHandler() {
		if (canPressLeftTaskPage()) {
			PageHandler.flipPrevPage();
			try {
				UserInterface.display(PageHandler.getCurrentPage());
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
			}
		}

		else if (canPressLeftFilePage()) {
			PageHandler.flipPrevFilePage();
			try {
				UserInterface.display(PageHandler.getFileCurrentPage());
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean canPressRightTaskPage() {
		boolean checkTextField = UserInterface.textField.getText().isEmpty();
		boolean checkCurrentPage = PageHandler.getCurrentPage() < PageHandler.getLastPage();
		boolean notAtHelpMenu = !UserInterface.atHelpMenu;
		boolean notAtFilePage = !PageHandler.isAtFilePage;
		
		return checkTextField && checkCurrentPage && notAtHelpMenu && notAtFilePage;
	}
	
	private static boolean canPressRightFilePage() {
		boolean checkTextField = UserInterface.textField.getText().isEmpty();
		boolean checkCurrentPage = PageHandler.getFileCurrentPage()<PageHandler.getFileLastPage();
		boolean notAtHelpMenu = !UserInterface.atHelpMenu;
		boolean isAtFilePage = PageHandler.isAtFilePage;
		
		return checkTextField && checkCurrentPage && notAtHelpMenu && isAtFilePage;
	}
	
	private static void rightKeyHandler() {		
		if (canPressRightTaskPage()) {
			PageHandler.flipNextPage();
			try {
				UserInterface.display(PageHandler.getCurrentPage());
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
			}
		}
		
		else if (canPressRightFilePage()) {
			PageHandler.flipNextFilePage();
			try {
				UserInterface.display(PageHandler.getFileCurrentPage());
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void F1Handler() {
		PrintHandler.printHelp();
		UserInterface.atHelpMenu = true;
	}
	
	private static void escHandler() {
		UserInterface.atHelpMenu = false;
		LayoutSetting.setShowTaskInfo();
		try {
			UserInterface.display(PageHandler.getCurrentPage());
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
		}
		UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
	}
	
	private static void ctrlMHandler() {
		UiLogic.processMaxMin();
	}
	
	private static void upKeyHandler() {
		String history = TextFieldHistory.getLastHistory();
		if (!history.equals(INVALID)) {
			UserInterface.textField.setText(history);
		}
	}
	
	private static void downKeyHandler() {
		String history = TextFieldHistory.getForwardHistory();
		if (!history.equals(INVALID)) {
			UserInterface.textField.setText(history);
		}
	}
	
	private static void tabHandler() {
		if (BalloonTipSuggestion.getAutoFill() != null) {
			UserInterface.textField.setText(BalloonTipSuggestion.getAutoFill() + " ");
		}
	}
	
	private static void enterHandler() {
		try {
			UiLogic.processTextField();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

