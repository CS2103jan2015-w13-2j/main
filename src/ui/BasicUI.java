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
import javafx.scene.Node;
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
	private final ListView<String> listViewList = new ListView<String>(list);
	private final String dummyCategory[] = {"All", "School", "Family", "Others", "Completed" };
	
		
	//Labels
	private Label brandLabel = new Label("TaskBuddy");
	private Label feedbackLabel = new Label();

	//User IO
	private final TextField textField = new TextField();
	private ArrayList<String> inputText = new ArrayList<String>();
	private static final String TEXTFIELD_PROMPT_TEXT = "enter command";
	private static final String PROGRAM_TITLE = "TaskBuddy, your best personal assistant";

	
	public static void main(String[] args) {
		TaskList BTL = new TaskList("Test.txt");
		launch(args);
		TaskList.startWaitingForCommand();
	}

	@Override
	public void start(Stage primaryStage) {
		
		setLayout(primaryStage);

		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.ENTER) {
					String input = textField.getText();
					taskList.TaskList.executeCommand(input);
					feedbackLabel = FeedbackGuide.setFeedbackGuide(feedbackLabel, TaskList.getLastFeedBack());
					textField.clear();
				}
			}
		});
		

		
	}
	
	
	private void setLayout(Stage primaryStage) {
		primaryStage.setTitle(PROGRAM_TITLE); 
		GridPane root = new GridPane();
		brandLabel.setStyle("-fx-font-family: Courier New;" + "-fx-font-size: 30;" + "-fx-font-weight: bold;");		
		textField.setPromptText(TEXTFIELD_PROMPT_TEXT);
		gridPaneSettings(root);
		primaryStage.setScene(new Scene(root, 550, 450));
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
		root.add(TableLayout.getTable(),1,5,1,2);
		root.add(brandLabel,1,2,1,1);
		root.add(textField,1,7,1,2);
		root.add(feedbackLabel,1,9,1,2);
	}
	
}
