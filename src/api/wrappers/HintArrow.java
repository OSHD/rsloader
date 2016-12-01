








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class HintArrow {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook headIconIndex;
	private static FieldHook targetIndex;
	public HintArrow(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("HintArrow");
			headIconIndex = currentHook.getFieldHook("getHeadIconIndex");
			targetIndex = currentHook.getFieldHook("getTargetIndex");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		headIconIndex=null;
		targetIndex=null;
	}
	public int getHeadIconIndex(){
		if(headIconIndex==null)
			headIconIndex = currentHook.getFieldHook("getHeadIconIndex");
		if(headIconIndex!=null){
			Object data = headIconIndex.get(currentObject);
			if(data!=null)
				return (Integer)data;
		}			
		return -1;
	}
	public int getTargetIndex(){
		if(targetIndex==null)
			targetIndex = currentHook.getFieldHook("getTargetIndex");
		if(targetIndex!=null){
			Object data = targetIndex.get(currentObject);
			if(data!=null)
				return (Integer)data;
		}			
		return -1;
	}
}
