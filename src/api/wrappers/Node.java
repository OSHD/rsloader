








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Node {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook id;
	private static FieldHook next;
	private static FieldHook previous;
	public Node(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Node");
			id = currentHook.getFieldHook("getID");
			next = currentHook.getFieldHook("getNext");
			previous = currentHook.getFieldHook("getPrevious");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		id=null;
		next=null;
		previous=null;
	}
	public long getID(){
		if(id==null)
			id = currentHook.getFieldHook("getID");
		if(id!=null){
			Object data = id.get(currentObject);
			if(data!=null)
				return ((Long)data) * id.getLongMultiplier();
		}
		return -1;
	}
	public Node getNext(){
		if(next==null)
		next = currentHook.getFieldHook("getNext");
		if(next!=null){
			Object data = next.get(currentObject);
			if(data!=null){
				if(data.getClass().getName().equals(Data.runtimeClassHooks.get("MenuItemNode").getClassName()))
					return new MenuItemNode(data);
				return new Node(data);
			}
		}
		return null;
	}
	public Node getPrevious(){
		if(previous==null)	
			previous = currentHook.getFieldHook("getPrevious");
		if(previous!=null){
			Object data = previous.get(currentObject);
			if(data!=null){
				if(data.getClass().getName().equals(Data.runtimeClassHooks.get("MenuItemNode").getClassName()))
					return new MenuItemNode(data);
				return new Node(data);
			}
		}
		return null;
	}
}
