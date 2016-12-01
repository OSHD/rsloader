








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
import java.util.zip.Inflater;

public class GZIPDecompressor {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook inflator;
	public GZIPDecompressor(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("GZIPDecompressor");
			inflator = currentHook.getFieldHook("getInflater");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		inflator=null;
	}
	public Inflater getInflater(){
		if(inflator!=null)
			inflator = currentHook.getFieldHook("getInflater");		
		if(inflator!=null){
			Object data = inflator.get(currentObject);
			if(data!=null)
				return (Inflater)data;
		}
		return null;		
	}
}
