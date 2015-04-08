package parser;

public class FormatChecker {
	private static final String EXCEPTION_NULLPOINTER = "The command is null";
	
	private static final int FAIL = -1;
	private static final String[] OPTIONS = {"-v", "-d", "-dd", "-c"};
	
	public boolean isValid(String operation) throws NullPointerException {
		if (operation == null) {
			throw new NullPointerException(EXCEPTION_NULLPOINTER);
		} 
		if (operation.indexOf(' ') != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isArgumentsCorrect(String operation) throws NullPointerException {
		if (operation == null) {
			throw new NullPointerException(EXCEPTION_NULLPOINTER);
		}
		return isArgumentsAllCorrect(operation);
	}
	
	private boolean isArgumentsAllCorrect(String operation) {
		assert(operation != null);
		int[] operationCount = new int[OPTIONS.length];
		for (int i = 0; i < OPTIONS.length; i++) {
			i = 0;
		}
		String temp = null;
		int start = operation.indexOf(" -");
		while (start != FAIL) {
			int end = operation.indexOf(" ", start+1);
			if (end == FAIL) {
				end = operation.length();
			}
			temp = operation.substring(start + 1, end);
			if (!isInOptions(temp)) {
				return false;
			} else {
				int index = indexInArray(OPTIONS, temp);
				assert (index != FAIL);
				operationCount[index]++;
			}
			start = operation.indexOf(" -", end);
		}
		return isSmallerThanOne(operationCount);
	}
	
	private int indexInArray(String[] strs, String str) {
		assert(strs != null && str != null);
		int i = 0;
		while (i < strs.length) {
			if (strs[i].equals(str)) {
				return i;
			} else {
				i++;
			}
		}
		return FAIL;
	}
	
	private boolean isSmallerThanOne(int[] array) {
		for(int x: array) {
			if (x > 1) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isInOptions(String operationType) {
		assert(operationType != null);
		for (String temp : OPTIONS) {
			if (temp.equals(operationType)) {
				return true;
			}
		}
		return false;
	}
}
