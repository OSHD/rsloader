








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Interactable extends EntityNode{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook plane;
	public Interactable(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Interactable");
			plane = currentHook.getFieldHook("getPlane");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		plane=null;
	}
	public byte getPlane(){
		if(plane==null)
			plane = currentHook.getFieldHook("getPlane");
		if(plane!=null){
			Object data = plane.get(currentObject);
			if(data!=null)
				return (Byte)data;
		}
		return -1;
	}
}
