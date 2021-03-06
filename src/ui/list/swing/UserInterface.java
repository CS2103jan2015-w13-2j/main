package ui.list.swing;


import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

import taskList.Task;
import taskList.TaskManager;

//@author A0117971Y

/**
 * Main class that displays and update tasks. 
 * @author A0117971Y
 *
 */
public class UserInterface {
	
	public static final String COMMAND_GUIDE_DEFAULT_MESSAGE = "type add | delete | modify | search | sort | undo | redo | finish ";
	public static String VIEW_TASK_INFO_MESSAGE = "Things to do: ";
	public static boolean isAdd = false;
	public static boolean isModify = false;
	public static boolean atHelpMenu = false;
	public static TaskManager BTM;
	public static ArrayList<Task> taskList;
	public static ArrayList<String> files;
	public static int deleteIndex = -1;	
	public static int completeIndex = -1;
	public static boolean isMinimized = false;
	public static final JFrame frame = new JFrame("TaskBuddy - Your best personal assistant");
	public static JPanel panel = new JPanel();
	public static JLabel lblBackground = new JLabel("");
	public static JLabel lblCommandGuide = new JLabel(COMMAND_GUIDE_DEFAULT_MESSAGE);
	public static JLabel lblStatusMessage = new JLabel("");
	public static JLabel lblPageNumber = new JLabel("");
	public static JLabel lblHelp = new JLabel("F1 - Help");
	public static JLabel lblDate = new JLabel();
	public static JLabel lblFile = new JLabel();
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
	 * @throws IOException 
	 * @throws NullPointerException 
	 */
	public UserInterface() throws NullPointerException, IOException {
		BTM = TaskManager.getSharedInstance();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws NullPointerException 
	 */
	private void initialize() throws NullPointerException, IOException {		
		LayoutSetting.setAll();
		addSystemTrayWindowStateListener();
		updateAllFiles();
		display(0);
	}
	
	public static void display(int pageNumber) throws NullPointerException, IOException {		
		PrintHandler.clearPanel();
		panel.add(new JLabel(DisplayFormat.getTaskInfoDetails()));		
		lblPageNumber.setText(pageNumber+1 + "");
		
		if (TaskManager.DISPLAY_MODE.FILE_PATH == UserInterface.BTM.getCurrentMode()) {
			files = BTM.getAllFilePath();
			PrintHandler.printFilePage(PageHandler.getFileCurrentPage());			
		}
		else {
			PageHandler.isAtFilePage = false;
			taskList = BTM.getTasks();	
			PrintHandler.printPage(pageNumber);
		}
	}
	
	public static void addSystemTrayWindowStateListener() {
		frame.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent arg) {
				SystemTray_TaskBuddy.Minimise(arg);
			}
		});
	}
	
	public static void exit() {
		frame.dispose();
	}
	
	private static void updateAllFiles() {
		files = BTM.getAllFilePath();
		taskList = BTM.getTasks();	
	}
}
