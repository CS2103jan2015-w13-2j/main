package UserInterfaceSwing;

import java.util.List;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import taskList.Task;

public class NoRootTreeTableModel extends AbstractTreeTableModel {
//    private final static String[] COLUMN_NAMES = {"Id", "Name", "Doj", "Photo"};
    
    private List<TaskName> taskList;

    public NoRootTreeTableModel(List<TaskName> taskList) {
        super(new Object());
        this.taskList = taskList;
    }

//    @Override
//    public int getColumnCount() {
////        return COLUMN_NAMES.length;
//    	return 0;
//    }
//
//    @Override
//    public String getColumnName(int column) {
//        return COLUMN_NAMES[column];
//    }
    
    @Override
    public boolean isCellEditable(Object node, int column) {
        if (node instanceof TaskName && column == 2) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isLeaf(Object node) {
        return node instanceof TaskDetails;
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof TaskName) {
            TaskName task = (TaskName) parent;
            return task.getTaskDetails().size();
        }
        return taskList.size();
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof TaskName) {
        	TaskName task = (TaskName) parent;
            return task.getTaskDetails().get(index);
        }
        return taskList.get(index);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
    	TaskName task = (TaskName) parent;
        TaskDetails details = (TaskDetails) child;
        return task.getTaskDetails().indexOf(details);
    }

    @Override
    public Object getValueAt(Object node, int column) {
        if (node instanceof TaskName) {
        	TaskName task = (TaskName) node;
            switch (column) {
                case 0:
                    return task.getId();
                case 1:
                    return task.getName();
            }
        } else if (node instanceof TaskDetails) {
            TaskDetails details = (TaskDetails) node;
            switch (column) {
                case 0:
                    return details.getVenue();
                case 1:
                    return details.getStartDate();
                case 2:
                    return details.getEndDate();
                case 3:
                    return details.getStartTime();
                case 4:
                	return details.getEndTime();
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object value, Object node, int column) {
        String strValue = (String) value;
        if (node instanceof TaskName) {
        	TaskName task = (TaskName) node;
            switch (column) {
                case 0:
                    task.setId(Integer.valueOf(strValue));
                    break;
                case 1:
                    task.setName(strValue);
                    break;
            }
        } else if (node instanceof TaskDetails) {
            TaskDetails details = (TaskDetails) node;
            switch (column) {
                case 0:
                    details.setVenue(strValue);
                    break;
                case 1:
                    details.setStartDate(strValue);
                    break;
                case 2:
                    details.setEndDate(strValue);
                    break;
                case 3:
                    details.setStartTime(strValue);
                    break;
                case 4:
                    details.setEndTime(strValue);
                    break;
            }
        }
    }

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}
}