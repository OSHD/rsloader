








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class Settings {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook data;
	public Settings(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Settings");
			data = currentHook.getFieldHook("getData");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		data=null;
	}
	public int[] getData(){
		if(data==null)
			data = currentHook.getFieldHook("getData");
		if(data!=null){
			Object dat = data.get(currentObject);
			if(dat!=null)
				return (int[])dat;
		}
		return new int[]{};
	}
}
