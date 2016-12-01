








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class GroundBytes {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook bytes;
	public GroundBytes(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("GroundBytes");
			bytes = currentHook.getFieldHook("getBytes");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		bytes=null;
	}
	public byte[][][] getBytes(){
		if(bytes==null)
			bytes = currentHook.getFieldHook("getBytes");
		if(bytes!=null){
			Object data = bytes.get(currentObject);
			if(data!=null)
				return (byte[][][])data;
		}
		return new byte[][][]{};		
	}
}
