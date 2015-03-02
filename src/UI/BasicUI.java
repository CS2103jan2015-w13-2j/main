package UI;

import java.util.ArrayList;

import TaskList.BasicTaskList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BasicUI extends Application {

	public static final ObservableList<String> data = FXCollections.observableArrayList();

	final ListView<String> listView = new ListView<String>(data);
	final Label label = new Label("TaskBuddy");
	final TextField textField = new TextField();
	ArrayList<String> inputText = new ArrayList<String>();

	public static void main(String[] args) {
		BasicTaskList BTL = new BasicTaskList("Test.txt");
		launch(args);
		BTL.startWaitingForCommand();
	}

	@Override
	public void start(Stage primaryStage) {
		
		setLayout(primaryStage);
		displayList();

		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.ENTER) {
					String input = textField.getText();
					TaskList.BasicTaskList.executeCommand(input);
					displayList();
					textField.clear();
				}
			}
		});

		
	}
	
	private void displayList() {
		listView.getItems().clear();		
		inputText = TaskList.BasicTaskList.getFileContent();
		if (inputText.size() != 0) {
			System.out.println("printing from inputText");
			for (int i=0; i<inputText.size(); i++) {
				data.add(i+1 + ". " + inputText.get(i));
			}
		}
	}
	
	private void setLayout(Stage primaryStage) {
		primaryStage.setTitle("Task Buddy - your best personal assistant");        
		listView.setPrefSize(600, 450);
		listView.setEditable(false);
		listView.setItems(data);             
		textField.setPromptText("enter command");


		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);

		root.add(listView,5,4,1,2);
		root.add(label,5,2,1,1);
		root.add(textField,5,8,1,2);
		
		primaryStage.setScene(new Scene(root, 700, 600));
		primaryStage.show();
	}

	public static void exit(int status) {
		System.out.println("UI exiting...");
		System.exit(status);
	}
	
}
