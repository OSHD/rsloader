








package api.wrappers;

import java.util.Random;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class NPCNode extends Node{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook npc;
	
	public NPCNode(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("NPCNode");
			npc = currentHook.getFieldHook("getNPC");
		}
	}
	
	public int getUnique()
	{
		return 0;
	}
	
	public static void resetHooks(){
		currentHook=null;
		npc=null;
	}
	public NPC getNPC(){
		if(npc==null)
			npc = currentHook.getFieldHook("getNPC");
		if(npc!=null){
			Object data = npc.get(currentObject);
			if(data!=null) return new NPC(data);
		}
		return null;
	}
}
