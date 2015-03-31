package ui.list.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class LayoutSetting {
	
	public static void setFrame() {
		
		UserInterface.frame.setBounds(100, 100, 653, 562);
		UserInterface.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface.frame.getContentPane().setLayout(null);
		
	}
	
	public static void setPanels() {
		
		UserInterface.panel.setLayout(new BoxLayout(UserInterface.panel, BoxLayout.Y_AXIS));

		
	}
	
	public static void setLabels() {
		
		UserInterface.lblBackground.setForeground(new Color(0, 0, 0));
		UserInterface.lblBackground.setIcon(new ImageIcon(UserInterface.class.getResource("/ui/images/TaskBuddy_BG.png")));
		UserInterface.lblBackground.setBounds(0, 0, 653, 562);
		
		UserInterface.lblHelp.setFont(new Font("HanziPen TC", Font.BOLD, 15));
		UserInterface.lblHelp.setBounds(537, 34, 72, 16);
		
		UserInterface.lblStatusMessage.setFont(new Font("HanziPen TC", Font.ITALIC, 18));
		UserInterface.lblStatusMessage.setBounds(59, 440, 537, 29);
		
		UserInterface.lblPageNumber.setForeground(Color.GRAY);
		UserInterface.lblPageNumber.setBounds(581, 504, 28, 23);
		
		UserInterface.lblCommandGuide.setFont(new Font("HanziPen TC", Font.ITALIC, 18));
		UserInterface.lblCommandGuide.setBounds(59, 498, 501, 29);
		
	}
	
	public static void setScrollPane() {
		
		UserInterface.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		UserInterface.scrollPane.setBounds(76, 62, 525, 381);
		UserInterface.frame.getContentPane().add(UserInterface.scrollPane);
		UserInterface.scrollPane.setViewportView(UserInterface.panel);

		
	}
	
	public static void setTextField() {
		UserInterface.textField.getDocument().addDocumentListener(new TextFieldListener());
		UserInterface.textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg1) {
				
				if(arg1.getKeyCode() == KeyEvent.VK_ENTER) {
						try {
							UserInterface.processTextField();
						} catch (NullPointerException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
				
				else if (arg1.getKeyCode() == KeyEvent.VK_LEFT) {
					System.out.println("Left arrow pressed!");
					if (UserInterface.currentPage > 0 && !UserInterface.atHelpMenu) {
						if (UserInterface.display(UserInterface.currentPage - 1) == true && UserInterface.currentPage > 0) {
							UserInterface.currentPage -= 1;
						}
					}
					
					System.out.println("current page = " + UserInterface.currentPage);
				}
					
				
				else if (arg1.getKeyCode() == KeyEvent.VK_RIGHT) {
					System.out.println("Right Arrow Pressed!");
					if (UserInterface.currentPage < UserInterface.lastPage && !UserInterface.atHelpMenu) {
					if (UserInterface.display(UserInterface.currentPage + 1) == true) {
						UserInterface.currentPage += 1;
					}
					}
					System.out.println("current page = " + UserInterface.currentPage);
				}
				
				else if (arg1.getKeyCode() == KeyEvent.VK_F1) {
					System.out.println("F1 pressed");
					UserInterface.printHelp();
					UserInterface.atHelpMenu = true;
					UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_HELP_MESSAGE);
				}
				
				else if (arg1.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.out.println("ESC pressed");
					UserInterface.atHelpMenu = false;
					UserInterface.display(UserInterface.currentPage);
					UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
				}
				
				else if (arg1.getKeyCode() == KeyEvent.VK_UP) {
					System.out.println("Up pressed");
					String history = TextFieldHistory.getLastHistory();
					if (!history.equals("invalid")) {
						UserInterface.textField.setText(history);
					}
				}
				
				else if (arg1.getKeyCode() == KeyEvent.VK_DOWN) {
					System.out.println("Down Pressed");
					String history = TextFieldHistory.getForwardHistory();
					if (!history.equals("invalid")) {
						UserInterface.textField.setText(history);
					}
				}
			}
		});
		
		UserInterface.textField.setBounds(59, 466, 445, 36);
		UserInterface.textField.setColumns(10);
	}
	
	public static void setButton() {
		UserInterface.btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UserInterface.processTextField();
				} catch (NullPointerException | IOException e1) {
					new Exception("NullPointerException");
					e1.printStackTrace();
				}
			}
		});
		UserInterface.btnEnter.setBounds(509, 468, 92, 34);
	}
	
	public static void addToContentPane() {
		UserInterface.frame.getContentPane().add(UserInterface.lblHelp);
		UserInterface.frame.getContentPane().add(UserInterface.textField);
		UserInterface.frame.getContentPane().add(UserInterface.btnEnter);
		UserInterface.frame.getContentPane().add(UserInterface.lblStatusMessage);
		UserInterface.frame.getContentPane().add(UserInterface.lblPageNumber);
		UserInterface.frame.getContentPane().add(UserInterface.lblCommandGuide);		
		UserInterface.frame.getContentPane().add(UserInterface.lblBackground);	
	}
	
	public static void setAll() {

		setFrame();
		setPanels();
		setScrollPane();
		setLabels();
		setTextField();
		setButton();
		addToContentPane();

	}

}
