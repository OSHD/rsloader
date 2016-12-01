








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class InterfaceNode extends Node{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook mainID;
	public InterfaceNode(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("InterfaceNode");
			mainID = currentHook.getFieldHook("getMainID");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		mainID=null;
	}
	public int getMainID(){
		if(mainID==null)
			mainID = currentHook.getFieldHook("getMainID");
		if(mainID!=null){
			Object data = mainID.get(currentObject);
			if(data!=null)
				return (Integer)data * mainID.getIntMultiplier();
		}
		return -1;		
	}
}
