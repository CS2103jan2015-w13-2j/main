package ui.list.swing;

//@author A0117971Y

public class PageHandler {
	
	private static double printPerPage = 4.0;
	private static double printFilePerPage = 10.0;
	private static int currentPage = 0;
	private static int lastPage = 0;
	public static boolean isAtFilePage = false;
	private static int fileCurrentPage = 0;
	private static int fileLastPage = 0;

	public static void updatePage() {
		
		lastPage = getLastPage();
		currentPage = getCurrentPage();		
		
		if (lastPage < currentPage || UserInterface.isAdd) {
			currentPage = getLastPage();
		}			
	}
	
	public static void updateFilePage() {
		fileLastPage = getFileLastPage();
		fileCurrentPage = getFileCurrentPage();
		
		if (fileLastPage < fileCurrentPage) {
			fileCurrentPage = getLastPage();
		}	
	}
	
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
		System.out.println("Flip prev file page " + fileCurrentPage);
		updateFilePage();
	}
	
	public static void flipNextFilePage() {
		fileCurrentPage++;
		System.out.println("Flip next file page " + fileCurrentPage);
		updateFilePage();
	}
	
	
	public static void setCurrentPage(int page) {
		if (page <= getLastPage()) {
			currentPage = page;
		}
	}
	
	public static int getTotalPage() {
		int taskSize = UserInterface.taskList.size();	
		int totalPage = (int) Math.ceil(taskSize/printPerPage);
		
		return totalPage;
	}
	
	public static int getFilePathTotalPage() {
		System.out.println("path size = " + UserInterface.files.size());
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
	
	public static int getPageOfIndex(int index) {
		int page = index / (int) printPerPage;
		return page;
	}
	
}
