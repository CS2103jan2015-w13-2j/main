package ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import taskList.Task;
import taskList.TaskList;

public class UserIO {
	
	private static final String LOGGER_NAME = "Taskbuddy.log";
	private static final Logger logger = Logger.getLogger(LOGGER_NAME);
		
	public static void userInputListener() {
		BasicUI.textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

		
			@Override
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.ENTER) {
					String input = BasicUI.textField.getText();
					logger.info("UI user input received is: " + input);
					assert (input != null);
					taskList.TaskList.executeCommand(input);
					TableLayout.data.setAll(TaskList.getTasks());
					FeedbackGuide.setFeedbackGuide(TaskList.getLastFeedBack());
					BasicUI.textField.clear();
				}
			}
		});
	}
}
