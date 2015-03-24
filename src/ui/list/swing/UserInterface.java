package ui.list.swing;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTextField;
import taskList.Task;
import taskList.TaskList;
import javax.swing.JButton;

public class UserInterface {

	private JFrame frame;
	public static JTextField textField;
	private static TaskList BTL;
	private static JPanel panel = new JPanel();
	private ArrayList<Task> taskList;
	private static JLabel lblStatusMessage = new JLabel("");
	public static final String COMMAND_GUIDE_DEFAULT_MESSAGE = "type \"add\" or \"delete\" to begin";
	public static final JLabel lblCommandGuide = new JLabel(COMMAND_GUIDE_DEFAULT_MESSAGE);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface window = new UserInterface();
					window.frame.setVisible(true);
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
		BTL = new TaskList("Test.txt");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 653, 562);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 62, 520, 381);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		display();
		
		
		textField = new JTextField();
		textField.getDocument().addDocumentListener(new TextFieldListener());
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						System.out.println("Enter pressed");
						processTextField();
						lblCommandGuide.setText(COMMAND_GUIDE_DEFAULT_MESSAGE);
						printStatusMessage();
				}
		});
		textField.setBounds(42, 478, 428, 36);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processTextField();
			}
		});
		btnEnter.setBounds(470, 480, 92, 34);
		frame.getContentPane().add(btnEnter);
		

		lblStatusMessage.setBounds(52, 451, 520, 29);
		frame.getContentPane().add(lblStatusMessage);
		lblCommandGuide.setBounds(52, 511, 510, 29);
		
		frame.getContentPane().add(lblCommandGuide);

		
	}
	public void processTextField() {
		System.out.println("Enter pressed");
		String input = textField.getText();
		BTL.executeCommand(input);
		display();
		textField.setText(null);
	}
	
	public void display() {
		
		panel.removeAll();
		taskList = BTL.getTasks();
		for (int i=0; i<taskList.size(); i++) {
			printTask(taskList.get(i),i);
		}
	}
	
	public void printTask (Task task, int i) {
		String str = new DisplaySetting(task,i).getData();
//		System.out.println("adding label with: " + str);
		panel.add(new JLabel(str));
		panel.revalidate();
		panel.repaint();
	}
	
	public void printStatusMessage() {
		String statusMessage = BTL.getLastFeedBack();
		lblStatusMessage.setText(statusMessage);
	}
}
