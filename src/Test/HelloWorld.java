package Test;

import TaskList.BasicTaskList;

public class HelloWorld {
	public static void main(String args[]){
		BasicTaskList BTL = new BasicTaskList("Test.txt");
		BTL.startWaitingForCommand();
	}
}
