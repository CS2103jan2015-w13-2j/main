package UserInterfaceSwing;

import java.util.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTreeTable;


public class TreeTableMain extends JFrame 
{

    private JXTreeTable treeTable;

    public TreeTableMain() 
    {
        List<TaskName> taskList = new ArrayList<TaskName>();

        //create and add the first department with its list of Employee objects
        List<TaskDetails> detail1 = new ArrayList<TaskDetails>();
        detail1.add(new TaskDetails("orchard", "23 March 2015", "25 March 2015", "2pm", "5pm"));     
        taskList.add(new TaskName(1, "Buy stuff", detail1));

        //create and add the second department with its list of Employee objects
        List<TaskDetails> detail2 = new ArrayList<TaskDetails>();
        detail2.add(new TaskDetails("home", "23 March 2015", "25 March 2015", "2pm", "5pm"));     
        taskList.add(new TaskName(2, "Do work", detail2));
        
        //we use a no root model
        NoRootTreeTableModel noRootTreeTableModel = new NoRootTreeTableModel(taskList);
        treeTable = new JXTreeTable(noRootTreeTableModel);
        treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        treeTable.setRootVisible(false);
        treeTable.setRowHeight(50);

        add(new JScrollPane(treeTable));

        setTitle("JXTreeTable Example");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {        
                new TreeTableMain();
            }
        });
    }
}