package ui;

import java.util.ArrayList;

import taskList.TaskList;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
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
	public static final ObservableList<String> list = FXCollections.observableArrayList();
	
	final ListView<String> listViewData = new ListView<String>(data);
	final ListView<String> listViewList = new ListView<String>(list);
	
	final Label brandLabel = new Label("TaskBuddy");
	final Label feedbackLabel = new Label();
	final TextField textField = new TextField();
	ArrayList<String> inputText = new ArrayList<String>();
	private StringProperty feedbackMessage = new SimpleStringProperty();

	public static void main(String[] args) {
		TaskList BTL = new TaskList("Test.txt");
		launch(args);
		TaskList.startWaitingForCommand();
	}

	@Override
	public void start(Stage primaryStage) {
		
		feedbackLabel.textProperty().bind(feedbackMessage);
		setLayout(primaryStage);
		displayList();

		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.ENTER) {
					String input = textField.getText();
					taskList.TaskList.executeCommand(input);
					feedbackMessage.set("Command executed");
					displayList();
					textField.clear();
				}
			}
		});
		

		
	}
	
	private void displayList() {
		listViewData.getItems().clear();		
		inputText = taskList.TaskList.getFileContent();
		if (inputText.size() != 0) {
			System.out.println("printing from inputText");
			for (int i=0; i<inputText.size(); i++) {
				data.add(i+1 + ". " + inputText.get(i));
			}
		}
	}
	
	private void setLayout(Stage primaryStage) {
		primaryStage.setTitle("Task Buddy - your best personal assistant");        
		listViewData.setPrefSize(500, 450);
		listViewData.setEditable(false);
		listViewData.setItems(data);            
		listViewList.setPrefSize(200,450);
		listViewList.setItems(list);
		textField.setPromptText("enter command");


		GridPane root = new GridPane();
		root.setHgap(20);
		root.setVgap(10);

		root.add(listViewData,2,10,1,2);
		root.add(listViewList,1,10,1,2);
		root.add(brandLabel,1,2,1,1);
		root.add(textField,1,14,2,2);
		root.add(feedbackLabel,1,16,1,4);
		
		primaryStage.setScene(new Scene(root, 760, 600));
		primaryStage.show();
	}

	public static void exit(int status) {
		System.out.println("UI exiting...");
		System.exit(status);
	}
	
}
