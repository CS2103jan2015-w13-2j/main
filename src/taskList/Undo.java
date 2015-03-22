package taskList;


import java.lang.reflect.Method;  
import java.util.ArrayList;
import java.util.LinkedList;  
import java.util.List;  
  
public class Undo<T> {  
      
	private ArrayList<T> stack = new ArrayList<T>();
	private int pointer;
	
	
	public Undo(T initElement){
		stack.add(initElement);
		pointer = 0;
	}
	
	public T undo(){
		if (stack.size() == 1){
			return stack.get(pointer);
		}else{
			pointer--;
			return stack.get(pointer);
		}
	}
	
	public void reset(){
		T initObject = stack.get(0);
		for (int i=1; i< stack.size(); i++){
			stack.remove(i);
		}
		pointer = 1;
	}
	
	public T redo(){
		if (pointer >= stack.size()-1){
			pointer++;
		}
		return stack.get(pointer);
	}
	
	public void add(T newStatus){
		for (int i = pointer+1; i < stack.size();i++){
			stack.remove(i);
		}
		stack.add(newStatus);
		pointer++;
	}
	
	public boolean canRedo(){
		return pointer<stack.size()-1;
	}
	
	//public boolean canUndo(){
	
	//}
	
}  