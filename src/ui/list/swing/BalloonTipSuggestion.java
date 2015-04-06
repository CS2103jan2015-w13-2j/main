package ui.list.swing;

import java.util.ArrayList;
import java.util.Collections;

public class BalloonTipSuggestion {
	
	private static ArrayList<String> possibilities = new ArrayList<String>();
    private static int currentGuess  = -1;
    public static boolean areGuessing = false;
    private static boolean caseSensitive = false;

	
    public static void setPossibility() {
    	possibilities.add("add");
    	possibilities.add("display");
    	possibilities.add("modify");
    	possibilities.add("delete");
    	possibilities.add("search");
    	possibilities.add("sort");
    	possibilities.add("redo");
    	possibilities.add("undo");
    	possibilities.add("exit");
    	Collections.sort(possibilities);
    }
    
    private static void findCurrentGuess() {
        String entered = UserInterface.textField.getText();
        if (!caseSensitive) {
            entered = entered.toLowerCase();
        }

        for (int i = 0; i < possibilities.size(); i++) {
            currentGuess = -1;

            String possibility = possibilities.get(i);
            if (!caseSensitive)
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
    
    public static String getCurrentGuess() {
        if (currentGuess != -1) {
        	System.out.println("getting current guess " + possibilities.get(currentGuess));
            return possibilities.get(currentGuess);
        }

        return UserInterface.textField.getText();
    }
    
    public static void setText(String text) {
        UserInterface.textField.setText(text);
        areGuessing = false;
        currentGuess = -1;
    }
    
    public static void getBalloonTipTyped() {
    	String inputStream = TextFieldListener.getInputStream();
    	
		if (inputStream.length() == 0) {
			LayoutSetting.closeBalloonTip();
		}

		else if (inputStream.length() == 1) {
            areGuessing = true;
		}

        if (areGuessing) {
           findCurrentGuess();
        }
    }
    
    public static void getBalloonTipBackspaced() {
    	String inputStream = TextFieldListener.getInputStream();
        
    	if (inputStream.length() == 0) {
            areGuessing = false;
        	LayoutSetting.closeBalloonTip();
        }
        
		if (!areGuessing)
            areGuessing = true;

        else if (areGuessing) {
            findCurrentGuess();
        }
    	
    }
}
