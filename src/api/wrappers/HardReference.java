








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
public class HardReference extends Reference{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook reference;
	public HardReference(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("HardReference");
			reference = currentHook.getFieldHook("getReference");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		reference=null;
	}
	public Object getHardReference(){
		if(reference==null)
			reference = currentHook.getFieldHook("getReference");
		if(reference!=null){
			Object data = reference.get(currentObject);
			if(data!=null)
				return data;
		}
		return null;
	}
	public static boolean isInstance(Object o){
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("HardReference");
		if(currentHook!=null)
			return o.getClass().getName().equals(currentHook.getClassName());
		return false;
	}
}
