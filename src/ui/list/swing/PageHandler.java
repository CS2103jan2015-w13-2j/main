package ui.list.swing;

//@author A0117971Y

/**
 * Handles the navigation of pages
 * @author A0117971Y
 *
 */

public class PageHandler {
	
	private static double printPerPage = 4.0;
	private static double printFilePerPage = 10.0;
	private static int currentPage = 0;
	private static int lastPage = 0;
	private static int fileCurrentPage = 0;
	private static int fileLastPage = 0;

	public static boolean isAtFilePage = false;

	/**
	 * This method ensures that the tasks page do not go out of bound
	 */
	public static void updatePage() {
		lastPage = getLastPage();
		currentPage = getCurrentPage();		
		
		if (lastPage < currentPage || UserInterface.isAdd) {
			currentPage = getLastPage();
		}			
	}
	
	/**
	 * This method ensures that the file page do not go out of bound
	 */
	public static void updateFilePage() {
		fileLastPage = getFileLastPage();
		fileCurrentPage = getFileCurrentPage();
		
		if (fileLastPage < fileCurrentPage) {
			fileCurrentPage = getLastPage();
		}	
	}
	
	/**
	 * @return currentPage user is at when viewing files
	 */
	
	public static int getFileCurrentPage() {
		return fileCurrentPage;
	}
	
	public static void setFileCurrentPage(int page) {
		if (page <= getFileLastPage()) {
			fileCurrentPage = page;
		}
	}
	
	public static void flipPrevFilePage() {
		fileCurrentPage--;
		updateFilePage();
	}
	
	public static void flipNextFilePage() {
		fileCurrentPage++;
		updateFilePage();
	}
	
	
	public static void setCurrentPage(int page) {
		if (page <= getLastPage()) {
			currentPage = page;
		}
	}
	
	/**
	 * 
	 * @return total pages of tasks
	 */
	public static int getTotalPage() {
		int taskSize = UserInterface.taskList.size();	
		int totalPage = (int) Math.ceil(taskSize/printPerPage);
		
		return totalPage;
	}
	
	/**
	 * 
	 * @return total pages of files
	 */
	
	public static int getFilePathTotalPage() {
		int pathSize = UserInterface.files.size();
		int totalPathPage = (int) Math.ceil(pathSize/printFilePerPage);
		
		return totalPathPage;
	}
	
	public static int getFileLastPage() {
		int totalPage = getFilePathTotalPage();
		
		if (totalPage > 0) {
			return totalPage - 1;
		}
		
		return 0;
	}
	
	public static int getLastPage() {	
		int totalPage = getTotalPage();
		
		if (totalPage > 0) {
			return totalPage - 1;
		}
		
		return 0;
	}
	
	public static int getCurrentPage() {
		return currentPage;
	}
	
	public static void flipPrevPage() {
		currentPage--;
		updatePage();
	}
	
	public static void flipNextPage() {
		currentPage++;
		updatePage();
	}
	
	/**
	 * 
	 * @param index
	 * @return page number of a task is residing in
	 */
	
	public static int getPageOfIndex(int index) {
		int page = index / (int) printPerPage;
		return page;
	}
	
}
