package ui.list.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class HotKeyListener extends KeyAdapter{
	
	public static boolean isBackSpace = false;

	public void keyPressed(KeyEvent arg1) {
		
		if(arg1.getKeyCode() == KeyEvent.VK_ENTER) {
			
				try {
					UserInterface.processTextField();
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_TAB) {
//			System.out.println("tab pressed");
			
			if (BalloonTipSuggestion.areGuessing) {
                BalloonTipSuggestion.setText(BalloonTipSuggestion.getCurrentGuess() + " ");
                arg1.consume();
            }
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_LEFT) {
//			System.out.println("Left arrow pressed!");
			if (UserInterface.textField.getText().isEmpty()) {
				if (UserInterface.currentPage > 0 && !UserInterface.atHelpMenu) {
					if (UserInterface.displayAll(UserInterface.currentPage - 1) == true && UserInterface.currentPage > 0) {
						UserInterface.currentPage -= 1;
					}
				}
			}
			
//			System.out.println("current page = " + UserInterface.currentPage);
		}
			
		
		else if (arg1.getKeyCode() == KeyEvent.VK_RIGHT) {
//			System.out.println("Right Arrow Pressed!");			
			if (UserInterface.textField.getText().isEmpty()) {
				if (UserInterface.currentPage < UserInterface.lastPage && !UserInterface.atHelpMenu) {
					if (UserInterface.displayAll(UserInterface.currentPage + 1) == true) {
						UserInterface.currentPage += 1;
					}
				}
			}
			System.out.println("current page = " + UserInterface.currentPage);
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_F1) {
//			System.out.println("F1 pressed");
			UserInterface.printHelp();
			UserInterface.atHelpMenu = true;
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_HELP_MESSAGE);
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_ESCAPE && UserInterface.atHelpMenu) {
//			System.out.println("ESC pressed");
			UserInterface.atHelpMenu = false;
			LayoutSetting.setShowTaskInfo();
			UserInterface.displayAll(UserInterface.currentPage);
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_UP) {
//			System.out.println("Up pressed");
			String history = TextFieldHistory.getLastHistory();
			if (!history.equals("invalid")) {
				UserInterface.textField.setText(history);
			}
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_DOWN) {
//			System.out.println("Down Pressed");
			String history = TextFieldHistory.getForwardHistory();
			if (!history.equals("invalid")) {
				UserInterface.textField.setText(history);
			}
		}
		
	}
}

