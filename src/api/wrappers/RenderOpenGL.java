








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class RenderOpenGL extends Render{
	public Object currentObject;
	public static ClassHook currentHook;
	public RenderOpenGL(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("RenderOpenGL");
	}
	public static void resetHooks(){
		currentHook=null;
	}
}
