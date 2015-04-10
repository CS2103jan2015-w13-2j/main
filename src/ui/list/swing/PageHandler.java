package ui.list.swing;

//@author A0117971Y

public class PageHandler {
	
	private static double printPerPage = 5.0;
	private static int currentPage = 0;
	private static int lastPage = 0;

	public static void updatePage() {
		
		lastPage = getLastPage();
		currentPage = getCurrentPage();		
		
		if (lastPage < currentPage || UserInterface.isAdd) {
			currentPage = getLastPage();
		}			
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
	
	public static int getLastPage() {
		
		int totalPage = getTotalPage();
		
		if (totalPage > 0) {
			System.out.println("last page = " + (totalPage-1));
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
		System.out.println("current page = " + currentPage);
	}
	
	public static void flipNextPage() {
		currentPage++;
		updatePage();
		System.out.println("current page = " + currentPage);
	}
	
	public static int getPageOfIndex(int index) {
		int page = Math.floorDiv(index, (int) printPerPage);
		
		return page;
	}
	
}
