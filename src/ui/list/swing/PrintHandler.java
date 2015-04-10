package ui.list.swing;

import java.awt.Color;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import taskList.Task;

public class PrintHandler {
	
	public static void printPage (int pageNumber) throws NullPointerException, IOException {
		int start = pageNumber * 5;

		//not last page
		if (PageHandler.getCurrentPage()<PageHandler.getLastPage()) {
			for (int i=start; i < start+5; i++) {
				printTask(UserInterface.taskList.get(i),i);
			}
		}

		//last page
		else {

			for (int i=start; i<UserInterface.taskList.size(); i++) {
				printTask(UserInterface.taskList.get(i),i);
			}
		}
	}
	
	public static void printTask (Task task, int i) throws NullPointerException, IOException {
		
		String str = new DisplaySetting(task,i).getData();
		String labelText = String.format("<html><div WIDTH=%d>%s</div><html>", 500, str);
		
		// to highlight added row
		if (i+1 == UserInterface.taskList.size() && UserInterface.isAdd) {
			JLabel addedRow = new JLabel(labelText);
			TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "new");
			title.setTitleJustification(TitledBorder.CENTER);
			addedRow.setBorder(BorderFactory.createTitledBorder(title));
			UserInterface.panel.add(addedRow);
			UserInterface.isAdd = false;
		}
		
		else {
			UserInterface.panel.add(new JLabel(labelText));
		}
		
		refreshPanel();
	}
	
	public static void printStatusMessage() {
		UserInterface.lblCommandGuide.setText(DisplaySetting.getFeedbackGuideInfo());
		resetGuide();
	}
	
	public static void printHelp() {
		UserInterface.frame.getContentPane().removeAll();
		LayoutSetting.setHelpInfoLabel();
		refreshFrame();
	}
	
	private static void refreshPanel() {
		UserInterface.panel.revalidate();
		UserInterface.panel.repaint();
	}
	
	private static void refreshFrame() {
		UserInterface.frame.revalidate();
		UserInterface.frame.repaint();
	}
	
	public static void clearPanel() {
		UserInterface.panel.removeAll();
		refreshPanel();
	}
	
	public static void resetGuide() {
	     Timer timer = new Timer();
	     timer.schedule(new TimerTask() {
	         @Override
	         public void run() {
	             UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
	         }
	     }, 3000);
	 }
	

}
