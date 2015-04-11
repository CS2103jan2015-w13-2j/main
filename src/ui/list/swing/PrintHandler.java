package ui.list.swing;

import java.awt.Color;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import taskList.Task;

//@author A0117971Y

public class PrintHandler {
	
	private static final int ADD_MODE = 1;
	private static final int MODIFY_MODE = 2;
	private static final int COMPLETE_MODE = 3;

	public static void printPage (int pageNumber) throws NullPointerException, IOException {
		
		PageHandler.setCurrentPage(pageNumber);		
		clearPanel();		
		String taskHeading = DisplaySetting.getTaskInfoDetails();		
		UserInterface.panel.add(new JLabel(taskHeading));		
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
		
		refreshPanel();
	}
	
	public static void printTask (Task task, int i) throws NullPointerException, IOException {		
		String str = DisplaySetting.getTaskInfoFormat(task, i);
		String labelText = String.format("<html><div WIDTH=%d>%s</div><html>", 500, str);
		
		// to highlight added row
		if (i+1 == UserInterface.taskList.size() && UserInterface.isAdd) {			
			printHighlightRow(labelText,ADD_MODE);
		}
		
		//highlight modify row
		else if (i+1 == UiLogic.isValidModifyListener() && UserInterface.isModify) {
			printHighlightRow(labelText, MODIFY_MODE);
		}
		
		//highlight completed row
		else if (UserInterface.completeIndex != -1 && i+1 == UserInterface.completeIndex) {
			printHighlightRow(labelText,COMPLETE_MODE);
		}

		
		else if (UserInterface.deleteIndex != -1 && i+1==UserInterface.deleteIndex) {
			printDeletedRow(task,i);
		}
		

		
		else {
			UserInterface.panel.add(new JLabel(labelText));
		}
		
		refreshPanel();
	}
	
	private static void printHighlightRow(String labelText, int mode) {
		
		if (mode == ADD_MODE) {
			
			JLabel addedRow = new JLabel(labelText);
			TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray), "newly added");
			title.setTitleJustification(TitledBorder.CENTER);
			addedRow.setBorder(BorderFactory.createTitledBorder(title));
			UserInterface.panel.add(addedRow);
			UserInterface.isAdd = false;
		}
		
		else if (mode == MODIFY_MODE) {
			JLabel modifyRow = new JLabel(labelText);
			modifyRow.setOpaque(true);
			modifyRow.setBackground(Color.yellow);
			TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.yellow), "modifying");
			title.setTitleJustification(TitledBorder.CENTER);
			modifyRow.setBorder(BorderFactory.createTitledBorder(title));
			UserInterface.panel.add(modifyRow);
			UserInterface.isModify = false;
		}
		
		else if (mode == COMPLETE_MODE) {
			JLabel finishedRow = new JLabel(labelText);
			TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.green),"COMPLETED");
			title.setTitleJustification(TitledBorder.CENTER);
			finishedRow.setBorder(BorderFactory.createTitledBorder(title));
			UserInterface.panel.add(finishedRow);
		}
	}
	
	private static void printDeletedRow(Task task, int i) throws NullPointerException, IOException {
		String labelText = DisplaySetting.getDeletedRowFormat(task, i);
		UserInterface.panel.add(new JLabel(labelText));
		UserInterface.deleteIndex = -1;
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
