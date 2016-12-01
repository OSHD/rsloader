








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class NodeListCache extends Node{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook nodeList;
	public NodeListCache(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NodeListCache");
			nodeList = currentHook.getFieldHook("getNodeList");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		nodeList=null;
	}
	public NodeList getNodeList(){
		if(nodeList==null)
			nodeList = currentHook.getFieldHook("getNodeList");
		if(nodeList!=null){
			Object data = nodeList.get(currentObject);
			if(data!=null)
				return new NodeList(data);
		}
		return null;
	}
}
