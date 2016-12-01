








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class NPCDef {
	public Object currentObject;
	private static ClassHook currentHook;
	private static FieldHook actions;
	private static FieldHook id;
	private static FieldHook name;
	private static FieldHook nodeTable;
	public NPCDef(Object o){
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NPCDef");
			actions = currentHook.getFieldHook("getActions");
			id = currentHook.getFieldHook("getID");
			name = currentHook.getFieldHook("getName");
			nodeTable = currentHook.getFieldHook("getNodeTable");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		actions=null;
		id=null;
		name=null;
		nodeTable=null;		
	}
	public String[] getActions(){
		if(actions==null)
			actions = currentHook.getFieldHook("getActions");
		if(actions!=null){
			Object data = actions.get(currentObject);
			if(data!=null)
				return (String[])data;
		}
		return null;
	}
	public int getID(){
		if(id==null)
			id = currentHook.getFieldHook("getID");
		if(id!=null){
			Object data = id.get(currentObject);
			if(data!=null)
				return (Integer)data * id.getIntMultiplier();
		}
		return -1;
	}
	public String getName(){
		if(name==null)
			name = currentHook.getFieldHook("getName");
		if(name!=null){
			Object data = name.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public HashTable getNodeTable(){
		if(nodeTable==null)
			nodeTable = currentHook.getFieldHook("getNodeTable");
		if(nodeTable!=null){
			Object data = nodeTable.get(currentObject);
			if(data!=null)
				return new HashTable(data);
		}
		return null;
	}
}
