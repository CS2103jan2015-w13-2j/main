package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class MainUI {
	private String[] tasks = {"Task 1", "Task 2", "Task 3"};
	
	private JFrame frame;
	private JTable table_1;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
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
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		table = new JTable();
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(52, 71, 404, 223);
		frame.getContentPane().add(table);
		
		JTextArea textArea = new JTextArea("hello world");
		textArea.setBounds(66, 86, 200, 56);
		
		// adding of task names into textArea
		for (int i=0; i<tasks.length; i++) {
			textArea.append(tasks[i]);
		}
		
		
		
		frame.getContentPane().add(textArea);
		
		textField = new JTextField();
		textField.setBounds(52, 315, 404, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		frame.setBounds(100, 100, 608, 384);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
