package ui.list.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.BalloonTip.AttachLocation;
import net.java.balloontip.BalloonTip.Orientation;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.EdgedBalloonStyle;

//@author A0117971Y

public class LayoutSetting {
	
	private static HotKeyListener hotKeyListener = new HotKeyListener();
	private static 	BalloonTip suggestion;
	
	public static void setFrameListener() {
		UserInterface.frame.addKeyListener(hotKeyListener);
	}
		
	public static void setTextFieldListener() {
		UserInterface.textField.addKeyListener(hotKeyListener);
	}
	
	public static void setFrame() {
		UserInterface.frame.setBounds(100, 100, 653, 582);
		UserInterface.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface.frame.setResizable(false);
		UserInterface.frame.getContentPane().setLayout(null);
		UserInterface.frame.setIconImage(Toolkit.getDefaultToolkit().getImage("ui/images/TaskBuddy_Icon.png"));
	}
	
	public static void setPanels() {	
		UserInterface.panel.setLayout(new BoxLayout(UserInterface.panel, BoxLayout.Y_AXIS));
	}
	
	public static void setBackgroundLabel() {	
		UserInterface.lblBackground.setForeground(new Color(0, 0, 0));
		UserInterface.lblBackground.setIcon(new ImageIcon(UserInterface.class.getResource("/ui/images/TaskBuddy_BG.png")));
		UserInterface.lblBackground.setBounds(0, 0, 653, 562);
	}
	
	public static void setDateLabel() {
		UserInterface.lblDate.setFont(new Font("Century Gothic", Font.ITALIC, 16));
		UserInterface.lblDate.setBounds(350, 540, 270, 16);
	}
	
	public static void setHelpInfoLabel() {
		UserInterface.frame.requestFocus();
		UserInterface.lblBackground.setForeground(new Color(0, 0, 0));
		UserInterface.lblBackground.setIcon(new ImageIcon(UserInterface.class.getResource("/ui/images/TaskBuddy_Help.png")));
		UserInterface.lblBackground.setBounds(0, 0, 653, 562);
		UserInterface.frame.getContentPane().add(UserInterface.lblBackground);
	}
	
	public static void setFilePathLabel() {
		UserInterface.lblFile.setFont(new Font("Century Gothic", Font.ITALIC, 16));
		UserInterface.lblFile.setBounds(20,540,500,16);
		UserInterface.lblFile.setText("Current File: " + UserInterface.BTM.getCurrentPath());
	}
	
	public static void setHelpLabel() {
		UserInterface.lblHelp.setFont(new Font("Century Gothic", Font.ITALIC, 16));
		UserInterface.lblHelp.setBounds(537, 30, 72, 20);
	}
	
	public static void setStatusMessageLabel() {
		UserInterface.lblStatusMessage.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		UserInterface.lblStatusMessage.setBounds(59, 440, 537, 29);
	}
	
	public static void setPageLabel() {
		UserInterface.lblPageNumber.setForeground(Color.GRAY);
		UserInterface.lblPageNumber.setBounds(581, 504, 28, 23);
	}
	
	public static void setCommandGuideLabel() {
		UserInterface.lblCommandGuide.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		UserInterface.lblCommandGuide.setBounds(59, 498, 520, 29);
	}
	
	public static void setLabels() {
		setBackgroundLabel();
		setDateLabel();
		setCommandGuideLabel();
		setPageLabel();
		setStatusMessageLabel();
		setHelpLabel();
		setFilePathLabel();
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
		UserInterface.textField.setFocusTraversalKeysEnabled(false);
	}
	
	public static void setBalloonTipStyle() {
		BalloonTipStyle style = new EdgedBalloonStyle(new Color(224,224,224), Color.BLACK);
		 suggestion = new BalloonTip(UserInterface.textField, new JLabel() ,style,Orientation.LEFT_ABOVE, AttachLocation.ALIGNED, 15, 7, false);
	}
	
	public static void showBalloonTipSuggestion(String guess) {
		suggestion.setTextContents("press tab to \""+ guess + "\"");
		suggestion.setVisible(true);
	}
	
	public static void closeBalloonTip() {
		suggestion.setVisible(false);
	}
	
	public static void getTodayDate() {	
		
		Timer SimpleTimer = new Timer(1000, new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String dayOfWeek = getDayOfWeek();
		        UserInterface.lblDate.setText(dayOfWeek + ", " + java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));	        
		    }

		});
		SimpleTimer.start();
	}
	
	private static String getDayOfWeek() {
		String dayOfWeekString = "";
		Calendar calendar = Calendar.getInstance();
		int dayOfWeekInt = calendar.get(Calendar.DAY_OF_WEEK);

		switch (dayOfWeekInt) {
			case Calendar.MONDAY: dayOfWeekString = "Monday"; 
				break; 
			case Calendar.TUESDAY: dayOfWeekString = "Tuesday";
				break; 
			case Calendar.WEDNESDAY: dayOfWeekString = "Wednesday"; 
				break; 
			case Calendar.THURSDAY: dayOfWeekString = "Thursday";
				break; 
			case Calendar.FRIDAY: dayOfWeekString = "Friday"; 
				break; 
			case Calendar.SATURDAY: dayOfWeekString = "Saturday"; 
				break; 
			case Calendar.SUNDAY: dayOfWeekString = "Sunday"; 
				break; 
		}
		
		return dayOfWeekString;
	}
	
	public static void addToContentPane() {	
		UserInterface.frame.getContentPane().add(UserInterface.lblFile);	
		UserInterface.frame.getContentPane().add(UserInterface.lblHelp);
		UserInterface.frame.getContentPane().add(UserInterface.lblDate);
		UserInterface.frame.getContentPane().add(UserInterface.textField);
		UserInterface.frame.getContentPane().add(UserInterface.lblStatusMessage);
		UserInterface.frame.getContentPane().add(UserInterface.lblPageNumber);
		UserInterface.frame.getContentPane().add(UserInterface.lblCommandGuide);		
		UserInterface.frame.getContentPane().add(UserInterface.lblBackground);
	}
	
	public static void setShowTaskInfo() {
		setBalloonTipStyle();
		suggestion.setVisible(false);
		setFrame();
		setPanels();
		setScrollPane();
		addToContentPane();
		setLabels();
		setTextField();
	}

	public static void setAll() {
		setBalloonTipStyle();
		suggestion.setVisible(false);
		setFrameListener();
		setTextFieldListener();
		setShowTaskInfo();
		getTodayDate();
	}
}
