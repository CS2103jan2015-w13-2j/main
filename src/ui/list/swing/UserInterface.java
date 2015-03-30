package ui.list.swing;


import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

import taskList.Task;
import taskList.TaskList;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;

public class UserInterface {

	public static final JFrame frame = new JFrame("TaskBuddy!");
	public static JTextField textField;
	private static TaskList BTL;
	private static JPanel panel = new JPanel();
	private ArrayList<Task> taskList;
	private static JLabel lblStatusMessage = new JLabel("");
	public static final String COMMAND_GUIDE_DEFAULT_MESSAGE = "type \"add\"  \"delete\" \"modify\" to begin";
	public static final JLabel lblCommandGuide = new JLabel(COMMAND_GUIDE_DEFAULT_MESSAGE);
	private final JLabel lblBackground = new JLabel("");
	public static boolean isAdd = false;
	public static int currentPage = 0;
	private static double printPerPage = 5.0;
	public static int lastPage = 0;
	public static int isComplete = -1;


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
		frame.setBounds(100, 100, 653, 562);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblBackground.setIcon(new ImageIcon(UserInterface.class.getResource("/ui/images/TaskBuddy_BG.png")));
		lblBackground.setBounds(0, 0, 653, 562);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setBounds(76, 62, 525, 381);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		display(0);
		
		
		textField = new JTextField();
		textField.getDocument().addDocumentListener(new TextFieldListener());
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg1) {
				if(arg1.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						processTextField();
					} catch (NullPointerException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				else if (arg1.getKeyCode() == KeyEvent.VK_LEFT) {
					System.out.println("Left arrow pressed!");
					if (currentPage > 0) {
					if (display(currentPage - 1) == true && currentPage > 0) {
						currentPage -= 1;
					}
					}
					
					System.out.println("current page = " + currentPage);
				}
					
				
				else if (arg1.getKeyCode() == KeyEvent.VK_RIGHT) {
					System.out.println("Right Arrow Pressed!");
					if (currentPage < lastPage) {
					if (display(currentPage + 1) == true) {
						currentPage += 1;
					}
					}
					System.out.println("current page = " + currentPage);
				}
			}
		});
		textField.setBounds(59, 466, 445, 36);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					processTextField();
				} catch (NullPointerException | IOException e1) {
					new Exception("NullPointerException");
					e1.printStackTrace();
				}
			}
		});
		btnEnter.setBounds(509, 468, 92, 34);
		frame.getContentPane().add(btnEnter);
		

		lblStatusMessage.setBounds(76, 440, 520, 29);
		frame.getContentPane().add(lblStatusMessage);
		lblCommandGuide.setBounds(76, 505, 510, 29);
		
		frame.getContentPane().add(lblCommandGuide);
		
		frame.getContentPane().add(lblBackground);

		
	}
	public void processTextField() throws NullPointerException, IOException {
		System.out.println("Enter pressed");
		String input = textField.getText();
		textField.setText(null);
		BTL.executeCommand(input);
		taskList = BTL.getTasks();
		lastPage = (int) Math.ceil(taskList.size()/printPerPage) - 1;
		if (lastPage < 0) {
			lastPage = 0;
			currentPage = 0;
		}
		System.out.println("tasklist size = " + taskList.size());
		
		System.out.println("added to tasklist! lastPage now is: " + lastPage);
		if (lastPage < currentPage) {
			currentPage = lastPage;
			System.out.println("last page = " + lastPage + " current page = " + currentPage);
		}
		
		if (isAdd) {
			currentPage = lastPage;
			display(lastPage);
			System.out.println(" added! last page = " + lastPage + " current page = " + currentPage);
		}
		
		else {
			display(currentPage);
			System.out.println("last page = " + lastPage + " current page = " + currentPage);

		}
		
		isAdd = false;
	}
	
	public boolean display(int pageNumber) {
		int start = pageNumber * 5;
		int end = start + 5;
		taskList = BTL.getTasks();
		lastPage = (int) Math.ceil(taskList.size()/printPerPage) - 1;
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		
		System.out.println("start = " + start + "end = " + end + "listSize = " + taskList.size());
		
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
	
	public void printTask (Task task, int i) {
		String str = new DisplaySetting(task,i).getData();
//		System.out.println("adding label with: " + str);
		
		// to highlight added row
		if (i+1 == taskList.size() && isAdd) {
//			System.out.println("adding last row");
			JLabel addedRow = new JLabel(str);
			TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "new");
			title.setTitleJustification(TitledBorder.CENTER);
			addedRow.setBorder(BorderFactory.createTitledBorder(title));
			panel.add(addedRow);
		}
		
		else {
//			System.out.println("printing non last row");
			panel.add(new JLabel(str));
		}
		
		panel.revalidate();
		panel.repaint();
	}
	
	public void printStatusMessage() {
		String statusMessage = BTL.getLastFeedBack();
		lblStatusMessage.setText(statusMessage);
	}
	
	public void setBackground(JLabel lblBackground) {
//		System.out.println("setting background image");
		lblBackground.setIcon(new ImageIcon(UserInterface.class.getResource("/ui/images/TaskBuddy_BG.png")));
		frame.getContentPane().add(lblBackground);
	}
	
	public void exit() {
		frame.dispose();
	}
	
	
	
}
