package ui.list.swing;

public class BalloonTipSuggestion {
	
    private static void setBalloonTipSuggestion() {
    	
    	String autoFill = getAutoFill();
    	
        if (autoFill != null) {
        LayoutSetting.showBalloonTipSuggestion(autoFill);
        
        } 
        
        else {
        	LayoutSetting.closeBalloonTip();
        }
    }

    public static void getBalloonTip() {
    	String inputStream = TextFieldListener.getInputStream();

    	if (inputStream.length() == 0) {
    		LayoutSetting.closeBalloonTip();
    	}

    	else {
    		setBalloonTipSuggestion();
    	}

    }
    
    public static String getAutoFill() {
    	return UserInterface.BTL.getAutoFill(TextFieldListener.getInputStream());
    }

}