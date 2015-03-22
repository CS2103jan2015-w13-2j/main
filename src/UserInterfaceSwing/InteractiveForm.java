package UserInterfaceSwing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import taskList.Task;

public class InteractiveForm extends JPanel {
	public static final String[] columnNames = {
        "No.", "Task", "Date", "Venue", ""
    };

    protected JTable table;
    protected JScrollPane scroller;
    protected InteractiveTableModel tableModel;
    
	public void updateTable(ArrayList<Task> taskList) {

		tableModel.updateTable(taskList);
	}

    public InteractiveForm() {
        initComponent();
    }

    public void initComponent() {
        tableModel = new InteractiveTableModel(columnNames);
        tableModel.addTableModelListener(new InteractiveForm.InteractiveTableModelListener());
        table = new JTable();
        table.setModel(tableModel);
        table.setSurrendersFocusOnKeystroke(true);
        if (!tableModel.hasEmptyRow()) {
            tableModel.addEmptyRow();
        }

        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        TableColumn hidden = table.getColumnModel().getColumn(InteractiveTableModel.HIDDEN_INDEX);
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);
        hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX));
        
        TableColumn index = table.getColumnModel().getColumn(InteractiveTableModel.TASK_INDEX);
        index.setMinWidth(50);
        index.setPreferredWidth(50);
        index.setMaxWidth(50);
        
        TableColumn date = table.getColumnModel().getColumn(InteractiveTableModel.DATE_INDEX);
        date.setMinWidth(150);
        date.setPreferredWidth(150);
        date.setMaxWidth(150);
        
        TableColumn venue = table.getColumnModel().getColumn(InteractiveTableModel.VENUE_INDEX);
        venue.setMinWidth(150);
        venue.setPreferredWidth(150);
        venue.setMaxWidth(150);

        setLayout(new BorderLayout());
        add(scroller, BorderLayout.CENTER);
    }

    public void highlightLastRow(int row) {
        int lastrow = tableModel.getRowCount();
        if (row == lastrow - 1) {
            table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
        } else {
            table.setRowSelectionInterval(row + 1, row + 1);
        }

        table.setColumnSelectionInterval(0, 0);
    }

    class InteractiveRenderer extends DefaultTableCellRenderer {
        protected int interactiveColumn;

        public InteractiveRenderer(int interactiveColumn) {
            this.interactiveColumn = interactiveColumn;
        }

        public Component getTableCellRendererComponent(JTable table,
           Object value, boolean isSelected, boolean hasFocus, int row,
           int column)
        {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == interactiveColumn && hasFocus) {
                if ((InteractiveForm.this.tableModel.getRowCount() - 1) == row &&
                   !InteractiveForm.this.tableModel.hasEmptyRow())
                {
                    InteractiveForm.this.tableModel.addEmptyRow();
                }

                highlightLastRow(row);
            }

            return c;
        }
    }

    public class InteractiveTableModelListener implements TableModelListener {
        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
//               System.out.println("row: " + row + " column: " + column);
                table.setColumnSelectionInterval(column + 1, column + 1);
//                table.setRowSelectionInterval(row, row);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            JFrame frame = new JFrame("Interactive Form");
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    System.exit(0);
                }
            });
            frame.getContentPane().add(new InteractiveForm());
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}