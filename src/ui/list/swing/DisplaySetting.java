package ui.list.swing;

import taskList.Task;

/**
 * 
 * @author A0117971Y
 *
 */

public class DisplaySetting {
	
	private static StringBuilder data = new StringBuilder();
	private static final String HTML_OPEN = "<html>";
	private static final String HTML_CLOSE = "</html>";
	private static final String HTML_BREAK = "<br>";
	private static final String HTML_FONT_INDEX = "<font size = \"6\" color = \"#9F000F\" font face = \"Impact\">";
	private static final String HTML_FONT_TASKNAME = "<font size = \"6\" font face = \"Arial\">";
	private static final String HTML_FONT_TASK_DETAILS = "<font size = \"3\" font color = #363232>";
	private static final String HTML_FONT_CLOSE = "</font>";
	private static final String HTML_FONT_HELP_HEADER = "<font size = \"6\" color = \"#9F000F\" font face = \"HanziPen TC\">";
	private static final String HTML_FONT_HELP_INFO = "<font size = \"5\" font face = \"HanziPen TC\">";
	private static final String HTML_FONT_VIEW_TASK_INFO = "<font size = \"6\" font face = \"HanziPen TC\">";
	
	public DisplaySetting(Task task, int i) {
		
		clearData();
		assert(data.length()==0);
		
		String index = Integer.toString(i+1);
		String taskName = task.getContent();
		String date = task.getDateString();
		String venue = task.getVenue();
		String endDate = task.getDeadlineString();
		
		if (date == null) {
			date = "---";
		}
		
		if (venue == null) {
			venue = "---";
		}
				
		data.append(HTML_OPEN + HTML_FONT_INDEX + index + ". " + HTML_FONT_CLOSE + HTML_FONT_TASKNAME + taskName + HTML_FONT_CLOSE + HTML_BREAK);
		data.append(HTML_FONT_TASK_DETAILS + "Date:" + date + HTML_FONT_CLOSE );
		
		if (endDate != null) {
			data.append(HTML_FONT_TASK_DETAILS + " BY: " + endDate + HTML_FONT_CLOSE );
		}
		
		data.append(HTML_BREAK);
		
		data.append(HTML_FONT_TASK_DETAILS + "Venue:" + venue + HTML_FONT_CLOSE + HTML_BREAK);
		
		data.append(HTML_CLOSE);
		
	}
	
	public String getData() {
		return data.toString();
	}
	
	public static String getHelpScreenInfo() {
		
		clearData();
		
		data.append(HTML_OPEN);
		data.append(HTML_FONT_HELP_INFO);
		data.append(HTML_FONT_HELP_HEADER);
		data.append("1. How to add a task?" + HTML_BREAK);
		data.append(HTML_FONT_CLOSE);
		data.append("type: add (task name) or -d (date) or -dd (deadline) or -v (venue)" + HTML_BREAK + 
				HTML_FONT_HELP_HEADER + "2. How to delete a task?"+ HTML_BREAK + HTML_FONT_CLOSE + 
				"type: delete (task index number shown beside the task name)" + HTML_BREAK +
				HTML_FONT_HELP_HEADER + "3. How to modify a task?" + HTML_BREAK + HTML_FONT_CLOSE +  
				 "type: modify (task index) new task name or respective tags " + HTML_BREAK  +
				 HTML_FONT_HELP_HEADER + "4. How to undo/redo an operation?"+ HTML_BREAK + HTML_FONT_CLOSE + 
				 "type: undo/redo" + HTML_BREAK +

				 HTML_FONT_HELP_HEADER + "6. Useful tags"+ HTML_BREAK + HTML_FONT_CLOSE + 
				 "-d for date" + HTML_BREAK
				 + "-v for venue" + HTML_BREAK +
				 "-dd for deadline" + HTML_BREAK);
		data.append(HTML_FONT_CLOSE);
		data.append(HTML_CLOSE);
		
		return data.toString();
	}
	
	public static String getViewTaskInfo() {
		data.setLength(0);
		
		data.append(HTML_OPEN);
		data.append(HTML_FONT_VIEW_TASK_INFO);
		data.append("You are currently viewing all tasks");
		data.append(HTML_FONT_CLOSE);
		data.append(HTML_CLOSE);	
		
		return data.toString();
	}
	
	public static void clearData() {
		data.setLength(0);
	}

}
