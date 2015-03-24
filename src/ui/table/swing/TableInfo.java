package ui.table.swing;

public class TableInfo {
	protected String index;
	protected String task;
    protected String date;
    protected String venue;

    public TableInfo() {
    	index = "";
        task = "";
        date = "";
        venue = "";
    }
    
    public String getIndex() {
    	return index;
    }
    
    public void setIndex(String index) {
    	this.index = index;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
