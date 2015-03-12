package ui;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import taskList.TaskList;

public class UserIO {
	
	public static void enterListener() {
		BasicUI.textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.ENTER) {
					String input = BasicUI.textField.getText();
					taskList.TaskList.executeCommand(input);
					FeedbackGuide.setFeedbackGuide(TaskList.getLastFeedBack());
					BasicUI.textField.clear();
				}
			}
		});
	}
}
