








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class InteractableLocation {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook x;
	private static FieldHook y;
	public InteractableLocation(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("InteractableLocation");
			x = currentHook.getFieldHook("getX");
			y = currentHook.getFieldHook("getY");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		x=null;
		y=null;
	}
	public float getX(){
		if(x==null)
			x = currentHook.getFieldHook("getX");
		if(x!=null){
			Object data = x.get(currentObject);
			if(data!=null)
				return (Float)data;
		}
		return -1;
	}
	public float getY(){
		if(y==null)
			y = currentHook.getFieldHook("getY");
		if(y!=null){
			Object data = y.get(currentObject);
			if(data!=null)
				return (Float)data;
		}
		return -1;
	}
}
