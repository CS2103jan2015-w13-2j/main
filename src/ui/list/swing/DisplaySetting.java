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
	private static final String HTML_FONT_TASK_DETAILS = "<font color = #848482>";
	private static final String HTML_FONT_CLOSE = "</font>";
	private static final String HTML_FONT_HELP_HEADER = "<font size = \"10\" color = \"#9F000F\" font face = \"Impact\">";
	
	public DisplaySetting(Task task, int i) {
		
		data.setLength(0);
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
		
//		if (date != null) {
//			data.append(HTML_FONT_TASK_DETAILS + "Date:" + date + HTML_FONT_CLOSE + HTML_BREAK);
//		}
//		
//		if (venue != null) {
//			data.append(HTML_FONT_TASK_DETAILS + "Venue:" + venue + HTML_FONT_CLOSE + HTML_BREAK);
//		}
		
		data.append(HTML_CLOSE);	
	}
	
	public String getData() {
		return data.toString();
	}
	
	public static String getHelpScreenInfo() {
		
		data.setLength(0);
		
		data.append(HTML_OPEN);
		data.append(HTML_FONT_HELP_HEADER);
		data.append("How to use TaskBuddy?" + HTML_BREAK);
		data.append(HTML_FONT_CLOSE);
		data.append("1. To add a task, type: add (task name) or -d (date) or -dd (deadline) or -v (venue)" + HTML_BREAK + HTML_BREAK +
				"2. To delete a task, type: delete (task index number shown beside the task name)" + HTML_BREAK + HTML_BREAK +
				 "3. To modify a task, type: modify (task inde) new task name " + HTML_BREAK + 
				 "or -d (new date) or -dd (new deadline) or -v (new venue)" + HTML_BREAK + HTML_BREAK + 
				 "4. To undo last operation, type: undo" + HTML_BREAK + HTML_BREAK +
				 "5. To redo, type: redo" + HTML_BREAK + HTML_BREAK + HTML_BREAK +
				 "Tags:" + HTML_BREAK + HTML_BREAK +
				 "-d for date" + HTML_BREAK + HTML_BREAK
				 + "-v for venue" + HTML_BREAK + HTML_BREAK +
				 "-dd for deadline" + HTML_BREAK + HTML_BREAK );
		data.append(HTML_CLOSE);
		
		return data.toString();
	}

}
