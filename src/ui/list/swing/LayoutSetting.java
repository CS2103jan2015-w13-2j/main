package ui.list.swing;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class LayoutSetting {
	
	private static HotKeyListener hotKeyListener = new HotKeyListener();
	
	public static void setFrameListener() {
		UserInterface.frame.addKeyListener(hotKeyListener);
	}
	
	public static void setTextFieldListener() {
		UserInterface.textField.addKeyListener(hotKeyListener);
	}
	
	public static void setFrame() {
		UserInterface.frame.setBounds(100, 100, 653, 562);
		UserInterface.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface.frame.getContentPane().setLayout(null);
	}
	
	public static void setPanels() {	
		UserInterface.panel.setLayout(new BoxLayout(UserInterface.panel, BoxLayout.Y_AXIS));
	}
	
	public static void setBackgroundLabel() {	
		UserInterface.lblBackground.setForeground(new Color(0, 0, 0));
		UserInterface.lblBackground.setIcon(new ImageIcon(UserInterface.class.getResource("/ui/images/TaskBuddy_BG.png")));
		UserInterface.lblBackground.setBounds(0, 0, 653, 562);
	}
	
	public static void setHelpInfoLabel() {
		UserInterface.frame.requestFocus();
		UserInterface.lblBackground.setForeground(new Color(0, 0, 0));
		UserInterface.lblBackground.setIcon(new ImageIcon(UserInterface.class.getResource("/ui/images/TaskBuddy_Help.png")));
		UserInterface.lblBackground.setBounds(0, 0, 653, 562);
		UserInterface.frame.getContentPane().add(UserInterface.lblBackground);
	}
	
	public static void setHelpLabel() {
		UserInterface.lblHelp.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		UserInterface.lblHelp.setBounds(537, 34, 72, 16);
	}
	
	public static void setStatusMessageLabel() {
		UserInterface.lblStatusMessage.setFont(new Font("HanziPen TC", Font.ITALIC, 18));
		UserInterface.lblStatusMessage.setBounds(59, 440, 537, 29);
	}
	
	public static void setPageLabel() {
		UserInterface.lblPageNumber.setForeground(Color.GRAY);
		UserInterface.lblPageNumber.setBounds(581, 504, 28, 23);
	}
	
	public static void setCommandGuideLabel() {
		UserInterface.lblCommandGuide.setFont(new Font("HanziPen TC", Font.ITALIC, 18));
		UserInterface.lblCommandGuide.setBounds(59, 498, 501, 29);
	}
	
	public static void setLabels() {
		setBackgroundLabel();
		setCommandGuideLabel();
		setPageLabel();
		setStatusMessageLabel();
		setHelpLabel();
	}
	
	public static void setScrollPane() {
		UserInterface.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		UserInterface.scrollPane.setBounds(76, 62, 525, 381);
		UserInterface.frame.getContentPane().add(UserInterface.scrollPane);
		UserInterface.scrollPane.setViewportView(UserInterface.panel);		
	}
	
	public static void setTextField() {
		UserInterface.textField.getDocument().addDocumentListener(new TextFieldListener());
		UserInterface.textField.requestFocusInWindow();		
		UserInterface.textField.setBounds(59, 466, 520, 36);
		UserInterface.textField.setColumns(10);
	}
	
	public static void addToContentPane() {
		UserInterface.frame.getContentPane().add(UserInterface.lblHelp);
		UserInterface.frame.getContentPane().add(UserInterface.textField);
		UserInterface.frame.getContentPane().add(UserInterface.lblStatusMessage);
		UserInterface.frame.getContentPane().add(UserInterface.lblPageNumber);
		UserInterface.frame.getContentPane().add(UserInterface.lblCommandGuide);		
		UserInterface.frame.getContentPane().add(UserInterface.lblBackground);	
	}
	
	public static void setShowTaskInfo() {
		setFrame();
		setPanels();
		setScrollPane();
		setLabels();
		addToContentPane();
		setTextField();

	}

	public static void setAll() {
		setFrameListener();
		setTextFieldListener();
		setShowTaskInfo();
	}

}
