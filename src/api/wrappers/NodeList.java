








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class NodeList {
	private Object currentObject;
	private static ClassHook currentHook;
	private static FieldHook tail;
	public NodeList(Object o){
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NodeList");
			tail = currentHook.getFieldHook("getTail");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		tail=null;
	}
	public Node getTail(){
		if(tail==null)
			tail = currentHook.getFieldHook("getTail");
		if(tail!=null){
			Object data = tail.get(currentObject);
			if(data!=null)
				return new Node(data);
		}
		return null;
	}
}
