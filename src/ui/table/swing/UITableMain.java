package ui.table.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;

import taskList.Task;
import taskList.TaskList;
import ui.table.swing.InteractiveForm;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class UITableMain extends JPanel{

	private JFrame frame;
	public static final InteractiveForm interactiveForm = new InteractiveForm();
	ArrayList<Task> taskList;
	public static JTextField textField;
	private static TaskList BTL;
	private static JLabel statusFeedback = new JLabel("");	
	public static final String COMMAND_GUIDE_DEFAULT_MESSAGE = "type add to create a task";
	public static JLabel commandGuideLabel = new JLabel(COMMAND_GUIDE_DEFAULT_MESSAGE);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UITableMain window = new UITableMain();
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
	public UITableMain() {
		BTL = new TaskList("Test.txt");
		taskList = TaskList.getTasks();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 121, 750, 227);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		interactiveForm.table.setShowGrid(false);
		interactiveForm.table.setIntercellSpacing(new Dimension(0, 0));
		interactiveForm.table.setFillsViewportHeight(true);
		interactiveForm.table.setBackground(Color.WHITE);
		interactiveForm.scroller.setBounds(0, 0, 750, 227);
		interactiveForm.setBounds(22, 110, 750, 227);
		panel.add(interactiveForm);
		interactiveForm.setLayout(null);
		
		textField = new JTextField();
		textField.getDocument().addDocumentListener(new TextFieldListener());
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						System.out.println("Enter pressed");
						processTextField();
						commandGuideLabel.setText(COMMAND_GUIDE_DEFAULT_MESSAGE);
						generateStatusMessage(TaskList.getLastFeedBack());
				}
		});
		textField.setBounds(22, 382, 616, 33);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Enter pressed");
				processTextField();
				commandGuideLabel.setText(COMMAND_GUIDE_DEFAULT_MESSAGE);
				generateStatusMessage(TaskList.getLastFeedBack());
			}
		});
		btnEnter.setBounds(655, 382, 117, 33);
		panel.add(btnEnter);
		
		statusFeedback.setBounds(32, 335, 740, 50);
		panel.add(statusFeedback);
		
		commandGuideLabel.setBounds(32, 427, 571, 16);
		panel.add(commandGuideLabel);
		
		interactiveForm.updateTable(taskList);
	}
	
	public void generateStatusMessage(String message) {
		statusFeedback.setText(message);
	}
	
	public void exit() {
		frame.dispose();
	}
	
	public void processTextField() {
		String input = textField.getText();
		BTL.executeCommand(input);
		interactiveForm.updateTable(TaskList.getTasks());
		textField.setText(null);
	}
}
