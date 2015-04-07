package ui.list.swing;

public class BalloonTipSuggestion {
	
    private static void setBalloonTipSuggestion() {
    	
    	String autoFill = getAutoFill();
    	
        if (autoFill != null) {
        LayoutSetting.showBalloonTipSuggestion(autoFill);
        
        } 
    }
    
    public static String getAutoFill() {
    	return UserInterface.BTL.getAutoFill(TextFieldListener.inputStream);
    }

    public static void getBalloonTipTyped() {
    	String inputStream = TextFieldListener.getInputStream();

    	if (inputStream.length() == 0) {
    		LayoutSetting.closeBalloonTip();
    	}

    	else {
    		setBalloonTipSuggestion();
    	}

    }

    public static void getBalloonTipBackspaced() {
    	String inputStream = TextFieldListener.getInputStream();

    	if (inputStream.length() == 0) {
    		LayoutSetting.closeBalloonTip();
    	}

    	else{
    		setBalloonTipSuggestion();
    	}

    }

}
