








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
import java.io.File;

public class FileOnDisk {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook file;
	public FileOnDisk(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("FileOnDisk");
			file = currentHook.getFieldHook("getFile");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		file=null;
	}
	public File getFile(){
		if(file==null)
			file = currentHook.getFieldHook("getFile");
		if(file!=null){
			Object data = file.get(currentObject);
			if(data!=null)
				return (File)data;
		}
		return null;		
	}
}
