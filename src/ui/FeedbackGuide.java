package ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class FeedbackGuide {
	private static StringProperty feedbackMessage = new SimpleStringProperty();
	private static String feedback = "";
	
	public static Label setFeedbackGuide(Label feedbackLabel, String lastFeedback) {
		feedbackLabel.textProperty().bind(feedbackMessage);
		feedback = lastFeedback;
		feedbackMessage.set(feedback);
		
		return feedbackLabel;
	}
}
