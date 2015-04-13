
package taskList;


import java.util.ArrayList;  
//@author A0119403N
public class Undo<T> {  
      
	private ArrayList<T> stack = new ArrayList<T>();
	private int pointer;
	
	
	@SuppressWarnings("unchecked")
	public Undo(T initElement){
		ArrayList<Task> newStatusClone = (ArrayList<Task>) initElement;
		ArrayList<Task> newStatusClone2 = new ArrayList<Task>();
		for (int i=0; i< newStatusClone.size(); i++){
			newStatusClone2.add(newStatusClone.get(i).getCopy());
		}
		stack.add(((T) newStatusClone2));
		pointer = 0;
	}
	
	@SuppressWarnings("unchecked")
	public T undo(){
		if (stack.size() == 1){
			ArrayList<Task> returnCopy = new ArrayList<Task>();
			returnCopy = (ArrayList<Task>) stack.get(pointer);
			ArrayList<Task> returnCopy2 = (ArrayList<Task>) returnCopy.clone();
			return (T) returnCopy2;
		}else{
			pointer--;
			ArrayList<Task> returnCopy = new ArrayList<Task>();
			returnCopy = (ArrayList<Task>) stack.get(pointer);
			ArrayList<Task> returnCopy2 = (ArrayList<Task>) returnCopy.clone();
			return (T) returnCopy2;
		}
	}
	
	public void reset(){
		for (int i=1; i< stack.size(); i++){
			stack.remove(i);
		}
		pointer = 0;
	}
	
	@SuppressWarnings("unchecked")
	public T redo(){
		if (pointer < stack.size()-1){
			pointer++;
		}
		ArrayList<Task> returnCopy = new ArrayList<Task>();
		returnCopy = (ArrayList<Task>) stack.get(pointer);
		ArrayList<Task> returnCopy2 = (ArrayList<Task>) returnCopy.clone();
		return (T) returnCopy2;
	}
	
	@SuppressWarnings("unchecked")
	public void add(T newStatus){
		for (int i = pointer+1; i < stack.size();i++){
			stack.remove(i);
		}
		ArrayList<Task> newStatusClone = (ArrayList<Task>) newStatus;
		ArrayList<Task> newStatusClone2 = new ArrayList<Task>();
		for (int i=0; i< newStatusClone.size(); i++){
			newStatusClone2.add(newStatusClone.get(i).getCopy());
		}
		stack.add((T)newStatusClone2);
		pointer++;
	}
	
	public boolean canRedo(){
		return pointer<stack.size()-1;
	}
	
	public boolean canUndo(){
		return pointer>0;
	}

	
	
	
}  