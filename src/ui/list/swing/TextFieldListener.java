package ui.list.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author A0117971Y
 *
 */


public class TextFieldListener extends JTextField implements DocumentListener {
	
	private String inputStream = "";
	private final String COMMAND_GUIDE_ADD_MESSAGE = "Tip: add <task> -d <date> -v <venue> to add task with date & venue";
	private final String COMMAND_GUIDE_DELETE_MESSAGE = "Tip: delete <index number> to delete a task";
	private final String COMMAND_GUIDE_MODIFY_MESSAGE = "Tip: modify <index> <new name> -d <new date> -v <new venue>";
	private static ArrayList<String> possibilities = new ArrayList<String>();
    private static int currentGuess  = -1;
    private Color incompleteColor = Color.GRAY.brighter();
    public static boolean areGuessing = false;
    private boolean caseSensitive = false;
    
    
	/*
	 * Testing of GUI interface can be done by performing black-box testing. That is, just running the program without looking at any code.
	 * In order to be Effective and Efficient, we have to make use of some testing heuristics such as Equivalence partitioning, boundary value analysis (bva)
	 * and combining multiple inputs.
	 * 
	 * For testing of GUI, the most relevant testing heuristics would be combining of multiple inputs.
	 * Because user are required to enter task description, task date(optional), task venue (optional), etc...
	 * We can test input in such a way that only one invalid input per case 
	 * e.g. add <valid task name> -d <invalid date> -v <valid venue> or "add <valid task name> -d <valid date> -v <invalid venue>
	 * In this case, we need to consider the factor, whether the entire operation will be voided or only valid input will be registered. 
	 * 
	 */
	
    public static void setPossibility() {
    	possibilities.add("add");
    	possibilities.add("create");
    	possibilities.add("display");
    	possibilities.add("modify");
    	possibilities.add("delete");
    	possibilities.add("search");
    	possibilities.add("sort");
    	possibilities.add("redo");
    	possibilities.add("undo");
    	Collections.sort(possibilities);
    }
    
    private void findCurrentGuess() {
        String entered = UserInterface.textField.getText();
        if (!this.caseSensitive) {
            entered = entered.toLowerCase();
        }

        for (int i = 0; i < possibilities.size(); i++) {
            currentGuess = -1;

            String possibility = possibilities.get(i);
            if (!this.caseSensitive)
                possibility = possibility.toLowerCase();
            if (possibility.startsWith(entered)) {
                currentGuess = i;
            	System.out.println("found guess at index " + currentGuess);
            	LayoutSetting.showBalloonTipSuggestion(getCurrentGuess());
                break;
            }
            
            else {
            	LayoutSetting.closeBalloonTip();
            }
        }
 
        
    }

    public void setText(String text) {
        UserInterface.textField.setText(text);
        areGuessing = false;
        currentGuess = -1;
    }
    
    
    public static String getCurrentGuess() {
        if (currentGuess != -1) {
        	System.out.println("getting current guess " + possibilities.get(currentGuess));
            return possibilities.get(currentGuess);
        }

        return UserInterface.textField.getText();
    }
    
    public void keyReleased(KeyEvent e) { 
    	
    }
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		inputStream = UserInterface.textField.getText();

		if (inputStream.length() == 0) {
			LayoutSetting.closeBalloonTip();
		}

		else if (inputStream.length() == 1)
            areGuessing = true;

        if (areGuessing)
            this.findCurrentGuess();

    
		if (inputStream.toLowerCase().contains("add")) {
//			System.out.println("add detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_ADD_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("delete")) {
//			System.out.println("delete detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_DELETE_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("modify")) {
//			System.out.println("modify detected");
			UserInterface.lblCommandGuide.setText((COMMAND_GUIDE_MODIFY_MESSAGE));
		}
		
		else {
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
//			System.out.println("Is add is false!");

		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		inputStream = UserInterface.textField.getText();
		System.out.println("inputStream size = " + inputStream.length());

		if (!areGuessing)
            areGuessing = true;

        if (inputStream.length() == 0) {
            areGuessing = false;
        	LayoutSetting.closeBalloonTip();
        }
        else if (areGuessing) {
            findCurrentGuess();
        }
        
		if (inputStream.toLowerCase().contains("add")) {
			System.out.println("add detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_ADD_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("delete")) {
			System.out.println("delete detected");
			UserInterface.lblCommandGuide.setText(COMMAND_GUIDE_DELETE_MESSAGE);
		}
		
		else if (inputStream.toLowerCase().contains("modify")) {
			System.out.println("modify detected");
			UserInterface.lblCommandGuide.setText((COMMAND_GUIDE_MODIFY_MESSAGE));
		}
		
		else {
			UserInterface.lblCommandGuide.setText(UserInterface.COMMAND_GUIDE_DEFAULT_MESSAGE);
//		System.out.println("Is add is false!");

		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}