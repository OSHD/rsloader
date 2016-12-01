








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Chatline {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook message;
	public Chatline(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Chatline");
			message = currentHook.getFieldHook("getMessage");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		message=null;
	}
	public String getMessage(){
		if(message==null)
			message = currentHook.getFieldHook("getMessage");
		if(message!=null){
			Object data = message.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
}
