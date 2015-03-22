package UserInterfaceSwing;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import taskList.Task;

 public class InteractiveTableModel extends AbstractTableModel {
	 public static final int TASK_INDEX = 0;
     public static final int CONTENT_INDEX = 1;
     public static final int DATE_INDEX = 2;
     public static final int VENUE_INDEX = 3;
     public static final int HIDDEN_INDEX = 4;

     protected String[] columnNames;
     protected Vector dataVector;

     public InteractiveTableModel(String[] columnNames) {
         this.columnNames = columnNames;
         dataVector = new Vector();
     }

     public String getColumnName(int column) {
         return columnNames[column];
     }

     public boolean isCellEditable(int row, int column) {
         if (column == HIDDEN_INDEX) return false;
         else return true;
     }

     public Class getColumnClass(int column) {
         switch (column) {
             case TASK_INDEX:
             case CONTENT_INDEX:
             case DATE_INDEX:
             case VENUE_INDEX:
                return String.class;
             default:
                return Object.class;
         }
     }

     public Object getValueAt(int row, int column) {
         TableInfo record = (TableInfo)dataVector.get(row);
         switch (column) {
         	 case TASK_INDEX:
         		 return record.getIndex();
             case CONTENT_INDEX:
                return record.getTask();
             case DATE_INDEX:
                return record.getDate();
             case VENUE_INDEX:
                return record.getVenue();
             default:
                return new Object();
         }
     }

     public void setValueAt(String value, int row, int column) {
    	 TableInfo record = (TableInfo)dataVector.get(row);
         switch (column) {
         	 case TASK_INDEX:
         		 record.setIndex(value);
             case CONTENT_INDEX:
            	 record.setTask(value);
             case DATE_INDEX:
                record.setDate(value);
                break;
             case VENUE_INDEX:
                record.setVenue(value);
                break;
             default:
                System.out.println("invalid index");
         }
         fireTableCellUpdated(row, column);
     }

     public int getRowCount() {
         return dataVector.size();
     }

     public int getColumnCount() {
         return columnNames.length;
     }

     public boolean hasEmptyRow() {
         if (dataVector.size() == 0) return false;
         TableInfo tableInfo = (TableInfo)dataVector.get(dataVector.size() - 1);
         if (tableInfo.getIndex().trim().equals("") &&
        	tableInfo.getTask().trim().equals("") &&
            tableInfo.getDate().trim().equals("") &&
            tableInfo.getVenue().trim().equals(""))
         {
            return true;
         }
         else return false;
     }

     public void addEmptyRow() {
         dataVector.add(new TableInfo());
         fireTableRowsInserted(dataVector.size() - 1,dataVector.size() - 1);
     }
     
     public void clearRows() {
    	 dataVector.clear();
     }
     
     public void updateTable(ArrayList<Task> taskList) {
    	 System.out.println("In updateTable method");
    	 this.fireTableDataChanged();
    	 int row = 0;
       	 clearRows();

    		 for (Task task : taskList) {
    			 if (!this.hasEmptyRow()) {
    				 this.addEmptyRow();
    			 }

    			 //         if (task.getStartDate().isAfter(CORRECT_YEAR_FORMAT)
    			 //                 && task.getEndDate().isAfter(CORRECT_YEAR_FORMAT)) {
    			 //             dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    			 //         } else {
    			 //             dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    			 //         }

    			 System.out.println("Task name is " + task.getContent());
    			 System.out.println("Date is " + task.getDateString());
    			 System.out.println("Venue name is " + task.getVenue());

    			 this.setValueAt("" + (row + 1), row, TASK_INDEX);
    			 this.setValueAt("" + task.getContent(), row, CONTENT_INDEX);
    			 this.setValueAt("" + task.getDateString(), row, DATE_INDEX);
    			 this.setValueAt("" + task.getVenue(),  row, VENUE_INDEX);

    			 row++;
    		 }
    	 }
     }
