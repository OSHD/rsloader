








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Animation {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook id;
	public Animation(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Animation");
			id=currentHook.getFieldHook("getID");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		id=null;
	}
	public int getID(){
		if(id==null)
			id=currentHook.getFieldHook("getID");
		if(id!=null){
			Object data = id.get(currentObject);
			if(data!=null)
				return (Integer)data * id.getIntMultiplier();
		}
		return -1;
	}
}
