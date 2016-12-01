








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Animator {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook animation;
	public Animator(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Animator");
			animation=currentHook.getFieldHook("getAnimation");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		animation=null;
	}
	public Animation getAnimation(){
		if(animation==null)
			animation=currentHook.getFieldHook("getAnimation");
		if(animation!=null){
			Object data = animation.get(currentObject);
			if(data!=null)
				return new Animation(data);
		}
		return null;
	}
}
