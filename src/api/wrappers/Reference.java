








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class Reference extends NodeSub{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook index;
	public Reference(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Reference");
			index = currentHook.getFieldHook("getIndex");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		index=null;
	}
	public int getIndex(){
		if(index==null)
			index = currentHook.getFieldHook("getIndex");
		if(index!=null){
			Object data = index.get(currentObject);
			if(data!=null)
				return ((Integer)data) * index.getIntMultiplier();	
		}
		return -1;
	}
}
