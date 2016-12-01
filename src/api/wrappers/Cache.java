








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Cache {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook table;
	public Cache(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Cache");
			table=currentHook.getFieldHook("getTable");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		table=null;
	}
	public HashTable getTable(){
		if(table==null)
			table=currentHook.getFieldHook("getTable");
		if(table!=null){
			Object data = table.get(currentObject);
			if(data!=null)
				return new HashTable(data);
		}
		return null;
	}
}
