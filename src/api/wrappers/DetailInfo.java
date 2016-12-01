








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
public class DetailInfo {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook level;
	public DetailInfo(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("DetailInfo");
			level = currentHook.getFieldHook("getLevel");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		level=null;
	}
	public int getLevel(){
		if(level==null)
			level = currentHook.getFieldHook("getLevel");
		if(level!=null){
			Object data = level.get(currentObject);
			if(data!=null)
				return (Integer)data * level.getIntMultiplier();
		}
		return -1;
	}
}
