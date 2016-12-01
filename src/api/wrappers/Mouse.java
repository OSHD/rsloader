








package api.wrappers;


import environment.Data;
import reflection.ClassHook;

public class Mouse {
	public Object currentObject;
	public static ClassHook currentHook;
	public Mouse(Object o){
		currentObject = o;
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("Mouse");
	}
	public static void resetHooks(){
		currentHook=null;
	}
}
