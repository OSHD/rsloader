
package api.wrappers;

import environment.Data;
import reflection.ClassHook;

public class WorldController {
	public Object currentObject;
	public static ClassHook currentHook;
	public WorldController(Object o){
		currentObject = o;
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("WorldController");
	}
	public static void resetHooks(){
		currentHook=null;
	}
}
