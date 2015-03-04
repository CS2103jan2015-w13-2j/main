package taskList;


import java.lang.reflect.Method;  
import java.util.LinkedList;  
import java.util.List;  
  
public class Undo<T> {  
      
    private LinkedList<T> primaryList = new LinkedList<T>();  
      
    //�����б�  
    private LinkedList<List> snapshotList = new LinkedList<List>();  
      
    //��һ�β������õķ���  
    private Method lastOperation;  
      
    //��һ�β������õķ�������  
    private Object[] lastOperationArgs;  
      
    //�Ŀ��գ�ÿ����һ�����¿���  
    private void takeSnapshot() {  
        List<T> snapshot = new LinkedList<T>();  
        for (T element : primaryList) {  
            snapshot.add(element);  
        }  
        snapshotList.add(snapshot);  
    }  
      
    //�����µĿ��ջָ�  
    private void restoreFromSnapshot() {  
        //pollLast()��ȡ���Ƴ����б�����һ��Ԫ�أ�������б�Ϊ�գ��򷵻� null��  
        List<T> snapshot = snapshotList.pollLast();    
        primaryList.clear();  
        for (T element : snapshot) {  
            primaryList.add(element);  
        }  
    }  
      
    //�����ϴβ���  
    private void setLastInvokedOperation(String methodName, Object... params) {  
        try {  
            this.lastOperation = this.getClass().  
                getMethod(methodName, this.getParamTypes(params));  
        } catch (Exception e) {  
            if (setLastMethodParamsGeneric(methodName, params)==false){  
                e.printStackTrace();  
            }  
        }  
        this.lastOperationArgs = params;  
    }  
       
    //������һ�������Ĳ���  
    private boolean setLastMethodParamsGeneric(String methodName, Object... params) {  
        Method[] methods = this.getClass().getMethods();  
        for (int i=0; i<methods.length; i++) {  
            if (this.isMethodMatch(methods[i], methodName, params)) {  
                this.lastOperation = methods[i];  
                return true;  
            }  
        }  
        return false;  
    }  
      
    //�жϷ����Ƿ�ƥ��  
    private boolean isMethodMatch(Method method, String methodName, Object... params){  
        if (!method.getName().equalsIgnoreCase(methodName)) {  
            return false;  
        }  
        Class<?>[] paramTypes = method.getParameterTypes();  
        if (paramTypes.length != params.length) {  
            return false;  
        }  
        for (int i=0; i<params.length; i++) {  
            if (!paramTypes[i].isAssignableFrom(params[i].getClass())) {  
                return false;  
            }  
        }  
        return true;  
    }  
      
    //��ȡ����������  
    private Class<?>[] getParamTypes(Object... params) {  
        Class<?>[] paramTypes = new Class<?>[params.length];  
        for (int i=0; i<params.length; i++) {  
            paramTypes[i] = params[i].getClass();   
        }  
        return paramTypes;  
    }  
      
    //ִ����һ������  
    private void invokeLastMethod() {  
        try {  
            this.lastOperation.invoke(this, this.lastOperationArgs);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
    }  
      
    //ɾ������  
    public void delete(Integer beginIndex, Integer endIndex) {  
        //ɾ������֮ǰ���¿���  
        this.takeSnapshot();  
        for (int i=beginIndex; i<=endIndex; i++) {  
            primaryList.remove(beginIndex);  
        }  
        this.setLastInvokedOperation("delete", beginIndex, endIndex);  
    }  
      
    //�������  
    public void insert(T element, Integer index) {  
        //�������֮ǰ���¿���  
        this.takeSnapshot();  
        primaryList.add(index, element);  
        this.setLastInvokedOperation("insert", element, index);  
    }  
      
    //�޸Ĳ���  
    public void modify(T element, Integer index) {  
        //�޸Ĳ���֮ǰ���¿���  
        this.takeSnapshot();  
        primaryList.set(index, element);  
        this.setLastInvokedOperation("modify", element, index);  
    }  
      
    //������ȡ����ԭ  
    public void redo() {  
        //ִ���ϴβ����ķ���  
        this.invokeLastMethod();  
    }  
      
    //����  
    public void undo() {  
        //�����µĿ����лָ�  
        this.restoreFromSnapshot();  
    }  
      
    @Override
	public String toString() {  
        return this.primaryList.toString();  
    }  
      
    public static void main(String[] args) {  
        Undo<String> ell = new Undo<String>();  
        ell.insert("hey", 0);  
        System.out.println(ell);  
        ell.redo();  
        System.out.println(ell);  
        ell.undo();  
        System.out.println(ell);  
        ell.redo();  
        System.out.println(ell);  
        ell.undo();  
        System.out.println(ell);  
        ell.undo();  
        System.out.println(ell);  
    }  
}  