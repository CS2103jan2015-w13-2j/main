package ui.list.swing;


import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTextField;
import taskList.Task;
import taskList.TaskList;

//@author A0117971Y

public class UserInterface {
	
	public static final String COMMAND_GUIDE_DEFAULT_MESSAGE = "type add | delete | modify | search | sort | undo | redo";
	public static final String COMMAND_GUIDE_HELP_MESSAGE = "Press esc to return";
	public static final String VIEW_TASK_INFO_MESSAGE = DisplaySetting.getViewTaskInfo();
	public static boolean isAdd = false;
	public static boolean isModify = false;
	public static boolean atHelpMenu = false;
	public static TaskList BTL;
	public static ArrayList<Task> taskList;
	public static int deleteIndex = -1;

	public static final JFrame frame = new JFrame("TaskBuddy - Your best personal assistant");
	public static JPanel panel = new JPanel();
	public static JLabel lblBackground = new JLabel("");
	public static JLabel lblCommandGuide = new JLabel(COMMAND_GUIDE_DEFAULT_MESSAGE);
	public static JLabel lblStatusMessage = new JLabel("");
	public static JLabel lblPageNumber = new JLabel("");
	public static JLabel lblHelp = new JLabel("F1 - Help");
	public static JLabel lblDate = new JLabel();
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
		BTL = new TaskList("default.txt");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws NullPointerException 
	 */
	private void initialize() throws NullPointerException, IOException {		
		LayoutSetting.setAll();
		display(0);
	}
	
	public static void display(int pageNumber) throws NullPointerException, IOException {		
		taskList = BTL.getTasks();	
		PrintHandler.clearPanel();
		panel.add(new JLabel(DisplaySetting.getViewTaskInfo()));		
		lblPageNumber.setText(pageNumber+1 + "");	
		PrintHandler.printPage(pageNumber);
	}
	
	public static void exit() {
		frame.dispose();
	}	
}
