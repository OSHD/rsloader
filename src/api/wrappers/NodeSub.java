








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class NodeSub extends Node{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook idSub;
	private static FieldHook nextSub;
	private static FieldHook previousSub;
	public NodeSub(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NodeSub");
			idSub = currentHook.getFieldHook("getIDSub");
			nextSub = currentHook.getFieldHook("getNextSub");
			previousSub = currentHook.getFieldHook("getPreviousSub");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		idSub=null;
		nextSub=null;
		previousSub=null;
	}
	public int getIDSub(){
		if(idSub==null)
			idSub = currentHook.getFieldHook("getIDSub");
		if(idSub!=null){
			Object data = idSub.get(currentObject);
			if(data!=null)
				return (Integer)data;
		}
		return -1;
	}
	public NodeSub getNextSub(){
		if(nextSub==null)
			nextSub = currentHook.getFieldHook("getNextSub");
		if(nextSub!=null){
			Object data = nextSub.get(currentObject);
			if(data!=null)
				return new NodeSub(data);
		}
		return null;
	}
	public NodeSub getPreviousSub(){
		if(previousSub==null)
			previousSub = currentHook.getFieldHook("getPreviousSub");
		if(previousSub!=null){
			Object data = previousSub.get(currentObject);
			if(data!=null)
				return new NodeSub(data);
		}
		return null;
	}
}
