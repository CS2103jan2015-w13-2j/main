package ui;

import java.util.ArrayList;

import taskList.Task;
import taskList.TaskList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class BasicUI extends Application {
	
	//Data
//	private static final ObservableList<String> data = FXCollections.observableArrayList();
//	private static final ObservableList<String> list = FXCollections.observableArrayList();	
		
	//Labels
	private Label brandLabel = new Label("TaskBuddy");
	public static Label feedbackLabel = new Label();

	//User IO
	public final static TextField textField = new TextField();
	private static final String TEXTFIELD_PROMPT_TEXT = "enter command";
	private static final String PROGRAM_TITLE = "TaskBuddy, your best personal assistant";
	public static TableView<Task> table = new TableView<Task>();


	
	public static void main(String[] args) {
		TaskList BTL = new TaskList("Test.txt");
		launch(args);
		TaskList.startWaitingForCommand();
	}

	@Override
	public void start(Stage primaryStage) {		
		setLayout(primaryStage);
		UserIO.userInputListener();
	}
	
	
	private void setLayout(Stage primaryStage) {
		primaryStage.setTitle(PROGRAM_TITLE); 
		GridPane root = new GridPane();
		TableLayout.setTable(table);
		brandLabel.setStyle("-fx-font-family: Courier New;" + "-fx-font-size: 30;" + "-fx-font-weight: bold;");		
		textField.setPromptText(TEXTFIELD_PROMPT_TEXT);
		gridPaneSettings(root);
		
		primaryStage.setScene(new Scene(root, 800, 550));
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			public void handle(final WindowEvent event) {
				taskList.TaskList.executeCommand("exit");
			}
		});
		primaryStage.show();
	}

	public static void exit(int status) {
		System.out.println("UI exiting...");
		System.exit(status);
	}
	
	private void gridPaneSettings(GridPane root) {
		root.setStyle("-fx-background-color: #c6e2ff;"); 
		root.setHgap(10);
		root.setVgap(10);
		root.add(table,1,5,1,2);
		root.add(brandLabel,1,2,1,1);
		root.add(textField,1,7,1,2);
		root.add(feedbackLabel,1,9,1,2);
	}
	
}
