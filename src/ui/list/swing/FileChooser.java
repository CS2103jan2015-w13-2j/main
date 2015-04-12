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
	public static JTextField filename = new JTextField();
	public static JTextField dir = new JTextField();
	public static JButton importFrom = new JButton("Import from");
	public static JButton exportTo = new JButton("Export to");
	public static JButton ok = new JButton("Ok");

	public static JPanel fileChooserPanel;
	public static JPanel buttonPanel;
	public static JLabel lblDirectory = new JLabel("Directory:");
	public static JLabel lblFileName = new JLabel("File Name:");
	public static JFileChooser fc = new JFileChooser();
	
	public FileChooser() {
		System.out.println("new instance of FileChooser");
	    buttonPanel = new JPanel();
	    fileChooserPanel = new JPanel();
	    Container cp = getContentPane();
	    getContentPane().setLayout(null);
	    LayoutSetting.setFileChooseLayout();
	    cp.add(buttonPanel);
	    cp.add(fileChooserPanel);
	    importFrom.addActionListener(new ImportListener());
	    exportTo.addActionListener(new ExportListener());
	    ok.addActionListener(new OkayListener());
	  }
	
	class OkayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String filePath = dir.getText() + "/" + filename.getText();
			System.out.println("file path = " + filePath);
			UserInterface.fileChooserFrame.dispose();
		}
		
	}
	
	class ImportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			JFileChooser c = new JFileChooser();
			// Demonstrate "Open" dialog:
			int rVal = fc.showOpenDialog(fc);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				filename.setText(fc.getSelectedFile().getName());
				dir.setText(fc.getCurrentDirectory().toString());
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("");
				dir.setText("");
			}
		}
	}

	class ExportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			JFileChooser c = new JFileChooser();
			// Demonstrate "Save" dialog:
			System.out.println("opening dialog");
			int rVal = fc.showSaveDialog(fc);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("User pressed save");
				filename.setText(fc.getSelectedFile().getName());
				dir.setText(fc.getCurrentDirectory().toString());
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("User pressed cancel");
				filename.setText("");
				dir.setText("");
			}
		}
	}
	
	  public static void run(JFrame frame, int width, int height) {
		  System.out.println("running fileChooser");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize(width, height);
		    frame.setVisible(true);
		  }
}
