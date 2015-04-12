package ui.list.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

//@author A0117971Y

public class HotKeyListener extends KeyAdapter {
	
	public static boolean isBackSpace = false;

	public void keyPressed(KeyEvent arg1) {

		if(arg1.getKeyCode() == KeyEvent.VK_ENTER) {
			
				try {
					UiLogic.processTextField();
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				

		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_TAB) {
//			System.out.println("tab pressed");
			if (BalloonTipSuggestion.getAutoFill() != null) {
				UserInterface.textField.setText(BalloonTipSuggestion.getAutoFill() + " ");
			}
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_LEFT) {
//			System.out.println("Left arrow pressed!");
			if (UserInterface.textField.getText().isEmpty()) {
				if (PageHandler.getCurrentPage() > 0 && !UserInterface.atHelpMenu) {
					if (PageHandler.getCurrentPage() > 0) {
						PageHandler.flipPrevPage();
						System.out.println("flipped prev page");
						try {
							UserInterface.display(PageHandler.getCurrentPage());
						} catch (NullPointerException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		}
			
		
		else if (arg1.getKeyCode() == KeyEvent.VK_RIGHT) {
//			System.out.println("Right Arrow Pressed!");			
			if (UserInterface.textField.getText().isEmpty() && !UserInterface.atHelpMenu && PageHandler.getCurrentPage() < PageHandler.getLastPage() ) {
						PageHandler.flipNextPage();
						System.out.println("flipped next page");
						try {
							UserInterface.display(PageHandler.getCurrentPage());
						} catch (NullPointerException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
			}
//			System.out.println("current page = " + UserInterface.currentPage);
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_F1) {
//			System.out.println("F1 pressed");
			PrintHandler.printHelp();
			UserInterface.atHelpMenu = true;
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_HELP_MESSAGE);
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_F2) {
			System.out.println("F2 pressed");
			FileChooser.run(new FileChooser(), 250, 110);
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_ESCAPE && UserInterface.atHelpMenu) {
//			System.out.println("ESC pressed");
			UserInterface.atHelpMenu = false;
			LayoutSetting.setShowTaskInfo();
			try {
				UserInterface.display(PageHandler.getCurrentPage());
			} catch (NullPointerException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
		}
		
		// cntrl - m

		else if ((arg1.getKeyCode() == KeyEvent.VK_M) && ((arg1.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			System.out.println("cntrl m detected");
			UiLogic.processMaxMin();
		}

		else if (arg1.getKeyCode() == KeyEvent.VK_UP) {
			System.out.println("Up pressed");
			String history = TextFieldHistory.getLastHistory();
			if (!history.equals("invalid")) {
				UserInterface.textField.setText(history);
			}
		}
		
		else if (arg1.getKeyCode() == KeyEvent.VK_DOWN) {
			System.out.println("Down Pressed");
			String history = TextFieldHistory.getForwardHistory();
			if (!history.equals("invalid")) {
				UserInterface.textField.setText(history);
			}
		}
		
	}
}

