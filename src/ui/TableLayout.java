package ui;

import taskList.Task;
import taskList.TaskList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableLayout {
	
//	private static final ObservableList<Task> data = FXCollections.observableArrayList(
//		    new Task("Task 1", "march 11", "", "this is a very very very very very very loooong venue"),
//		    new Task("Tsk 2", "april 30", "", "")
//		);
//	
	static ObservableList<Task> data = FXCollections.observableArrayList(TaskList.getTasks());
	


	public static TableView<Task> setTable(TableView<Task> table) {
		
				
		TableColumn<Task, String> taskColumn = new TableColumn<Task, String>("task");
		taskColumn.setPrefWidth(320);
        
		TableColumn<Task, String> dateColumn = new TableColumn<Task, String>("date");
		dateColumn.setPrefWidth(100);

        TableColumn<Task, String> venueColumn = new TableColumn<Task, String>("venue");
		venueColumn.setPrefWidth(100);
        
        taskColumn.setCellValueFactory(
        	    new PropertyValueFactory<Task,String>("content")
        	);
        	venueColumn.setCellValueFactory(
        	    new PropertyValueFactory<Task,String>("venue")
        	);
        	dateColumn.setCellValueFactory(
            	    new PropertyValueFactory<Task,String>("dateString")
            	);
        
        	table.setItems(data);
            table.getColumns().addAll(taskColumn, dateColumn, venueColumn);
            
            return table;
	}
}
