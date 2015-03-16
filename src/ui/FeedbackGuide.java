package ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FeedbackGuide {
	private static StringProperty feedbackMessage = new SimpleStringProperty();
	private static String feedback = "";
	
	public static void setFeedbackGuide(String lastFeedback) {
		BasicUI.feedbackLabel.textProperty().bind(feedbackMessage);
		feedback = lastFeedback;
		feedbackMessage.set(feedback);
		
	}
}
