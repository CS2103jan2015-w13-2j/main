package ui.list.swing;


import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextField;

import taskList.Task;
import taskList.TaskList;

import javax.swing.border.TitledBorder;

/**
 * 
 * @author author: A0117971Y
 *
 */
public class UserInterface {
	
	public static final String COMMAND_GUIDE_DEFAULT_MESSAGE = "type \"add\"  \"delete\" \"modify\" to begin";
	public static final String COMMAND_GUIDE_HELP_MESSAGE = "Press esc to return";
	public static final String VIEW_TASK_INFO_MESSAGE = DisplaySetting.getViewTaskInfo();
	public static boolean isAdd = false;
	public static int currentPage = 0;
	public static int lastPage = 0;
	public static boolean atHelpMenu = false;
	public static TaskList BTL;
	private static ArrayList<Task> taskList;
	public static double printPerPage = 5.0;

	
	public static final JFrame frame = new JFrame("TaskBuddy - Your best personal assistant");
	public static JPanel panel = new JPanel();
	public static JLabel lblBackground = new JLabel("");
	public static JLabel lblCommandGuide = new JLabel(COMMAND_GUIDE_DEFAULT_MESSAGE);
	public static JLabel lblStatusMessage = new JLabel("");
	public static JLabel lblPageNumber = new JLabel("");
	public static JLabel lblHelp = new JLabel("F1 - Help");
	public static JLabel lblDate = new JLabel(DisplaySetting.getTodayDate());
	public static JScrollPane scrollPane = new JScrollPane();
	public static JTextField textField = new JTextField();




	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("unused")
			public void run() {
				try {
					UserInterface window = new UserInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserInterface() {
		BTL = new TaskList("sharmaine.txt");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		LayoutSetting.setAll();
		displayAll(0);
	}
	public static void processTextField() throws NullPointerException, IOException {
//		System.out.println("Enter pressed");
		String input = textField.getText();
		String[] tokens = input.split(" ");
		
		if (tokens.length > 0) {
			if (tokens[0].equals("add")) {
				isAdd = true;
			}
		}
		
		if (input != null) {
			TextFieldHistory.updateHistory(input);
		}
		
		
		textField.setText(null);
		BTL.executeCommand(input);
		printStatusMessage();
		taskList = BTL.getTasks();
		
		lastPage = (int) Math.ceil(taskList.size()/printPerPage) - 1;
		if (lastPage < 0) {
			lastPage = 0;
			currentPage = 0;
		}
//		System.out.println("tasklist size = " + taskList.size());
		
		System.out.println("added to tasklist! lastPage now is: " + lastPage);
		if (lastPage < currentPage) {
			currentPage = lastPage;
//			System.out.println("last page = " + lastPage + " current page = " + currentPage);
		}
		
		if (isAdd) {
			currentPage = lastPage;
			displayAll(lastPage);
//			System.out.println(" added! last page = " + lastPage + " current page = " + currentPage);
		}
		
		else {
			displayAll(currentPage);
//			System.out.println("last page = " + lastPage + " current page = " + currentPage);

		}
		
		isAdd = false;
	}
	
	public static boolean displayAll(int pageNumber) {
		int start = pageNumber * 5;
		int end = start + 5;
		taskList = BTL.getTasks();
		lastPage = getLastPage();
		
		clearPanel();
		panel.add(new JLabel(DisplaySetting.getViewTaskInfo()));
		
		lblPageNumber.setText(pageNumber+1 + "");
		
//		System.out.println("start = " + start + "end = " + end + "listSize = " + taskList.size());
		
		if (start >= taskList.size() || pageNumber < 0) {
			return false;
		}
		
		
		//not last page
		if (end < taskList.size()) {
//			System.out.println("printing from index " + start);
			for (int i=start; i < end; i++) {
				printTask(taskList.get(i),i);
			}
		}
		
		//last page
		else {
			
			for (int i=start; i<taskList.size(); i++) {
				printTask(taskList.get(i),i);
			}
		}
		
		return true;
	}
	
	public static void printTask (Task task, int i) {
		
		String str = new DisplaySetting(task,i).getData();
		String labelText = String.format("<html><div WIDTH=%d>%s</div><html>", 500, str);
		
		// to highlight added row
		if (i+1 == taskList.size() && isAdd) {
			JLabel addedRow = new JLabel(labelText);
			TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "new");
			title.setTitleJustification(TitledBorder.CENTER);
			addedRow.setBorder(BorderFactory.createTitledBorder(title));
			panel.add(addedRow);
			isAdd = false;
		}
		
		else {
			panel.add(new JLabel(labelText));
		}
		
		refreshPanel();
	}
	
	public static void printStatusMessage() {
		lblCommandGuide.setText(DisplaySetting.getFeedbackGuideInfo());
		resetGuide();
	}
	
	public void setBackground(JLabel lblBackground) {
//		System.out.println("setting background image");
		lblBackground.setIcon(new ImageIcon(UserInterface.class.getResource("/ui/images/TaskBuddy_BG.png")));
		frame.getContentPane().add(lblBackground);
	}
	
	public static void exit() {
		frame.dispose();
	}
	
	public static void printHelp() {
		frame.getContentPane().removeAll();
		LayoutSetting.setHelpInfoLabel();
		refreshFrame();
	}
	
	private static int getLastPage() {
		return (int) Math.ceil(taskList.size()/printPerPage) - 1;
	}
	
	private static void refreshPanel() {
		panel.revalidate();
		panel.repaint();
	}
	
	private static void refreshFrame() {
		frame.revalidate();
		frame.repaint();
	}
	
	private static void clearPanel() {
		panel.removeAll();
		refreshPanel();
	}
	
	public static void resetGuide() {
	     Timer timer = new Timer();
	     timer.schedule(new TimerTask() {
	         @Override
	         public void run() {
	             UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_DEFAULT_MESSAGE);
	         }
	     }, 3000);
	 }
}
