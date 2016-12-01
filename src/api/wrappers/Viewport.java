
package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class Viewport {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook floats;
	public Viewport(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Viewport");
			floats = currentHook.getFieldHook("getFloats");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		floats=null;
	}
	public float[] getFloats(){
		if(floats==null)
			floats = currentHook.getFieldHook("getFloats");
		if(floats!=null){
			Object data = floats.get(currentObject);
			if(data!=null)
				return (float[])data;
		}
		return new float[]{};		
	}
}
