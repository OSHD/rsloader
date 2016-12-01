








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class AnimableNode {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook next;
	private static FieldHook animable;
	public AnimableNode(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("AnimableNode");
			next=currentHook.getFieldHook("getNext");
			animable=currentHook.getFieldHook("getAnimable");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		next=null;
		animable=null;
	}
	public AnimableNode getNext(){
		if(next==null)
			next=currentHook.getFieldHook("getNext");
		if(next!=null){
			Object data = next.get(currentObject);
			if(data!=null)
				return new AnimableNode(data);
		}
		return null;
	}
	public Animable getAnimable(){
		if(animable==null)
			animable=currentHook.getFieldHook("getAnimable");
		if(animable!=null){
			Object data = animable.get(currentObject);
			if(data!=null)
				return new Animable(data);
		}
		return null;
	}
}
