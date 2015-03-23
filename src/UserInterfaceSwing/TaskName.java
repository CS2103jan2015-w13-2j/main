package UserInterfaceSwing;

import java.util.List;

public class TaskName {
	private int id;
	private String name;
	private List<TaskDetails> details;
	
	public TaskName(int id, String name, List<TaskDetails> details) {
        super();
        this.id = id;        
        this.name = name;
        this.details = details;
    }
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TaskDetails> getTaskDetails() {
        return details;
    }

    public void setTaskDetils(List<TaskDetails> details) {
        this.details = details;
    }
}

