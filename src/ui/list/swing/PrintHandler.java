package ui.list.swing;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import taskList.Task;

//@author A0117971Y

/**
 * This class handles all printing methods
 * @author A0117971Y
 *
 */

public class PrintHandler {
	
	private static final int ADD_MODE = 1;
	private static final int MODIFY_MODE = 2;
	private static final int COMPLETE_MODE = 3;
	private static final int printPerPage = 4;
	private static final int printFilePerPage = 10;
		
	/**
	 * This class prints the a page given their page number(including page 0)
	 * @param pageNumber
	 * @throws NullPointerException
	 * @throws IOException
	 */
	
	public static void printFilePage(int pageNumber) {
		PageHandler.setFileCurrentPage(pageNumber);
		printTaskHeading();
		int startIndex = pageNumber * printFilePerPage;
		int endIndex = startIndex + printFilePerPage;
				
		//not last page
		if (PageHandler.getFileCurrentPage()<PageHandler.getFileLastPage()) {
			for (int i=startIndex; i<endIndex; i++) {
				System.out.println("printing non last page");
				printFilePaths(i);
			}
		}

		else {
			for (int i=startIndex; i<UserInterface.BTM.getAllFilePath().size(); i++) {
				System.out.println("printing last page");
				printFilePaths(i);
			}
		}
		
		refreshPanel();
	}


	public static void printPage (int pageNumber) throws NullPointerException, IOException {
				
		PageHandler.setCurrentPage(pageNumber);		
		printTaskHeading();
		int startIndex = pageNumber * printPerPage;
		int endIndex = startIndex + printPerPage;
			//not last page
			if (PageHandler.getCurrentPage()<PageHandler.getLastPage()) {
				for (int i=startIndex; i < endIndex; i++) {
					printTask(UserInterface.taskList.get(i),i);
				}
			}
			//last page
			else {
				for (int i=startIndex; i<UserInterface.taskList.size(); i++) {
					printTask(UserInterface.taskList.get(i),i);
				}
			}

		refreshPanel();
	}
	
	/**
	 * Prints task heading
	 */
	
	public static void printTaskHeading() {
		clearPanel();		
		String taskHeading = DisplayFormat.getTaskInfoDetails();		
		UserInterface.panel.add(new JLabel(taskHeading));	
	}
	
	/**
	 * Prints an individual task
	 * @param task
	 * @param index
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public static void printTask (Task task, int index) throws NullPointerException, IOException {		
		String str = DisplayFormat.getTaskInfoFormat(task, index);
		String labelText = String.format("<html><div WIDTH=%d>%s</div><html>", 500, str);
		
		// to highlight added task
		if (index+1 == UserInterface.taskList.size() && UserInterface.isAdd) {			
			printHighlightRow(labelText,ADD_MODE);
		}
		
		//highlight modifying task
		else if (index+1 == UiLogic.isValidModifyListener() && UserInterface.isModify) {
			printHighlightRow(labelText, MODIFY_MODE);
		}
		
		//highlight completed task
		else if (UserInterface.completeIndex != -1 && index+1 == UserInterface.completeIndex || task.hasFinished()) {
			printHighlightRow(labelText,COMPLETE_MODE);
		}
		//strike off deleted task
		else if (UserInterface.deleteIndex != -1 && index+1==UserInterface.deleteIndex) {
			printDeletedRow(task,index);
		}
		
		else {
			UserInterface.panel.add(new JLabel(labelText));
		}
		
		refreshPanel();
	}
	
	public static void printFilePaths(int i) {
		ArrayList<String> paths = UserInterface.BTM.getAllFilePath();
		String labelText;

		labelText = DisplayFormat.getPathInfoFormat(paths.get(i), i);
		UserInterface.panel.add(new JLabel(labelText));


	}
	
	/**
	 * Prints highlighted tasks used in add, modify, complete functions
	 * @param labelText
	 * @param mode
	 */
	
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
	
	/**
	 * Prints deleted task with a strike formatting
	 * @param task
	 * @param index
	 * @throws NullPointerException
	 * @throws IOException
	 */
	
	private static void printDeletedRow(Task task, int index) throws NullPointerException, IOException {
		String labelText = DisplayFormat.getDeletedRowFormat(task, index);
		UserInterface.panel.add(new JLabel(labelText));
		UserInterface.deleteIndex = -1;
	}
	
	/**
	 * Prints status message after user execute command
	 */
	public static void printStatusMessage() {
		UserInterface.lblCommandGuide.setText(DisplayFormat.getFeedbackGuideInfo());
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
	
	/**
	 * Set timer of 3000millis for statusMessage to switch back to commandGuide message
	 */
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
