package ui;

import java.util.ArrayList;

import taskList.Task;
import taskList.TaskList;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class BasicUI extends Application {
	
	//Data
//	private static final ObservableList<String> data = FXCollections.observableArrayList();
	private static final ObservableList<String> list = FXCollections.observableArrayList();
//	private final ListView<String> listViewData = new ListView<String>(data);
	private final ListView<String> listViewList = new ListView<String>(list);
	private final String dummyCategory[] = {"All", "School", "Family", "Others", "Completed" };
	private final TableView table = new TableView();
	
	final ObservableList<Task> data = FXCollections.observableArrayList(
		    new Task("Task 1", "march 11", "", "this is a very very very very very very loooong venue"),
		    new Task("Tsk 2", "april 30", "", "place 2")
		);
	
	//Labels
	private Label brandLabel = new Label("TaskBuddy");
	private Label feedbackLabel = new Label();
	private Label listLabel = new Label("CATEGORIES");
	private Label taskLabel = new Label("TASKS"); 
	
	//User IO
	private final TextField textField = new TextField();
	private ArrayList<String> inputText = new ArrayList<String>();
	private StringProperty feedbackMessage = new SimpleStringProperty();
	private String feedback = "";
	
	public static void main(String[] args) {
		TaskList BTL = new TaskList("Test.txt");
		launch(args);
		TaskList.startWaitingForCommand();
	}

	@Override
	public void start(Stage primaryStage) {
		
		
		
		
		feedbackLabel.textProperty().bind(feedbackMessage);
		setLayout(primaryStage);
//		displayData();
//		displayCategory();

		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.ENTER) {
					String input = textField.getText();
					taskList.TaskList.executeCommand(input);
					feedback = TaskList.getLastFeedBack();
					feedbackMessage.set(feedback);
//					displayData();
//					displayCategory();
					textField.clear();
				}
			}
		});
		

		
	}
	
//	private void displayCategory(){
//		listViewList.getItems().clear();
//		for (int i=0; i<dummyCategory.length; i++) {
//			list.add(dummyCategory[i]);
//		}
//	}
	
//	private void displayData() {
//		listViewData.getItems().clear();		
//		inputText = taskList.TaskList.getFileContent();
//		if (inputText.size() != 0) {
//			System.out.println("printing from inputText");
//			for (int i=0; i<inputText.size(); i++) {
//				data.add(i+1 + ". " + inputText.get(i));
//			}
//		}
//	}
	
	
	
	
	private void setLayout(Stage primaryStage) {
		primaryStage.setTitle("Task Buddy - your best personal assistant"); 
		GridPane root = new GridPane();
		
		TableColumn taskColumn = new TableColumn("task");
		taskColumn.setPrefWidth(320);
        TableColumn dateColumn = new TableColumn("date");
		dateColumn.setPrefWidth(100);

        TableColumn venueColumn = new TableColumn("venue");
		venueColumn.setPrefWidth(100);
        
        taskColumn.setCellValueFactory(
        	    new PropertyValueFactory<Task,String>("content")
        	);
        	venueColumn.setCellValueFactory(
        	    new PropertyValueFactory<Task,String>("venue")
        	);
        
        	table.setItems(data);
            table.getColumns().addAll(taskColumn, dateColumn, venueColumn);
		
		root.setStyle("-fx-background-color: #c6e2ff;"); 
		brandLabel.setStyle("-fx-font-family: Courier New;" + "-fx-font-size: 30;" + "-fx-font-weight: bold;");
		
//		listViewData.setPrefSize(500, 450);
//		listViewData.setEditable(false);
//		listViewData.setItems(data);            
		listViewList.setPrefSize(200,450);
		listViewList.setItems(list);
		textField.setPromptText("enter command");
				
		root.setHgap(10);
		root.setVgap(10);
		
//		root.add(taskLabel,2,8,1,2);
//		root.add(listLabel,1,8,1,2);
		
		//V0.0 (without showing category)
		root.add(table,1,5,1,2);
		
//		root.add(listViewData,2,10,1,2);
//		root.add(listViewList,1,10,1,2);
		root.add(brandLabel,1,2,1,1);
		root.add(textField,1,7,1,2);
		root.add(feedbackLabel,1,9,1,2);
		
		primaryStage.setScene(new Scene(root, 550, 450));
//		primaryStage.setScene(new Scene(root, 760, 600));
		primaryStage.show();
	}

	public static void exit(int status) {
		System.out.println("UI exiting...");
		System.exit(status);
	}
	
}
