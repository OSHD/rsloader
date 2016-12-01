








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class NodeSubQueue {
	private Object currentObject;
	private static ClassHook currentHook;
	private static FieldHook tail;
	public NodeSubQueue(Object o){
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NodeSubQueue");
			tail = currentHook.getFieldHook("getTail");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		tail=null;
	}
	public NodeSub getTail(){
		if(tail==null)
			tail = currentHook.getFieldHook("getTail");
		if(tail!=null){
			Object data = tail.get(currentObject);
			if(data!=null)
				return new NodeSub(data);
		}
		return null;
	}
}
