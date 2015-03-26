package taskList;


import java.util.ArrayList;  
  
public class Undo<T> {  
      
	private ArrayList<T> stack = new ArrayList<T>();
	private int pointer;
	
	
	@SuppressWarnings("unchecked")
	public Undo(T initElement){
		ArrayList<Task> newStatusClone = (ArrayList<Task>) initElement;
		ArrayList<Task> newStatusClone2 = (ArrayList<Task>) newStatusClone.clone();
		stack.add(((T) newStatusClone2));
		pointer = 0;
	}
	
	public T undo(){
		if (stack.size() == 1){
			return stack.get(pointer);
		}else{
			pointer--;
			System.out.println("undo here pointer is "+ pointer);
			
			return stack.get(pointer);
		}
	}
	
	public void reset(){
		for (int i=1; i< stack.size(); i++){
			stack.remove(i);
		}
		pointer = 0;
	}
	
	public T redo(){
		if (pointer < stack.size()-1){
			pointer++;
		}
		return stack.get(pointer);
	}
	
	@SuppressWarnings("unchecked")
	public void add(T newStatus){
		for (int i = pointer+1; i < stack.size();i++){
			stack.remove(i);
		}
		ArrayList<Task> newStatusClone = (ArrayList<Task>) newStatus;
		ArrayList<Task> newStatusClone2 = (ArrayList<Task>) newStatusClone.clone();
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