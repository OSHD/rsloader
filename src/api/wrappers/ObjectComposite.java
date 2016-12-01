package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class ObjectComposite {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook model;
	public ObjectComposite(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ObjectComposite");
			model = currentHook.getFieldHook("getModel");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		model=null;
	}
	public ModelLD getModel(){
		if(model==null)
			model = currentHook.getFieldHook("getModel");
		if(model!=null){
			Object data = model.get(currentObject);
			if(data!=null)
				return new ModelLD(data);
		}
		return null;
	}
}
