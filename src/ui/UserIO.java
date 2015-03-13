package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import taskList.Task;
import taskList.TaskList;

public class UserIO {
		
	public static void userInputListener() {
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
