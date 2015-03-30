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

}
