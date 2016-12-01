








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Canvas {
	public Object currentObject;
	public static ClassHook currentHook;
	public Canvas(Object o){
		currentObject = o;
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("Canvas");
	}
	public static void resetHooks(){
		currentHook=null;
	}
}
