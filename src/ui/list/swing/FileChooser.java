package ui.list.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class FileChooser extends JFrame{
	
	/**
	 * Default serial
	 */
	private static final long serialVersionUID = 1L;
	private JTextField filename = new JTextField();
	private JTextField dir = new JTextField();
	private JButton open = new JButton("Open"), save = new JButton("Save");
	private JPanel p_1;
	
	public FileChooser() {
	    JPanel p = new JPanel();
	    p.setBounds(0, 115, 575, 39);
	    open.addActionListener(new OpenL());
	    p.add(open);
	    save.addActionListener(new SaveL());
	    p.add(save);
	    Container cp = getContentPane();
	    getContentPane().setLayout(null);
	    cp.add(p);
	    filename.setBounds(70, 65, 505, 44);
	    filename.setEditable(false);
	    p_1 = new JPanel();
	    p_1.setBounds(0, 0, 575, 115);
	    p_1.setLayout(null);
	    dir.setBounds(70, 9, 505, 44);
	    p_1.add(dir);
	    dir.setEditable(false);
	    p_1.add(filename);
	    cp.add(p_1);
	    
	    JLabel lblDirectory = new JLabel("Directory:");
	    lblDirectory.setBounds(6, 20, 73, 22);
	    p_1.add(lblDirectory);
	    
	    JLabel lblFileName = new JLabel("File Name:");
	    lblFileName.setBounds(6, 76, 73, 22);
	    p_1.add(lblFileName);
	  }
	
	class OpenL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(FileChooser.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				filename.setText(c.getSelectedFile().getName());
				dir.setText(c.getCurrentDirectory().toString());
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("You pressed cancel");
				dir.setText("");
			}
		}
	}

	class SaveL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			// Demonstrate "Save" dialog:
			int rVal = c.showSaveDialog(FileChooser.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				filename.setText(c.getSelectedFile().getName());
				dir.setText(c.getCurrentDirectory().toString());
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("You pressed cancel");
				dir.setText("");
			}
		}
	}
	
	  public static void run(JFrame frame, int width, int height) {
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize(width, height);
		    frame.setVisible(true);
		  }
	  
	  public static void main(String[] args) {
		    run(new FileChooser(), 250, 110);
		  }
}
