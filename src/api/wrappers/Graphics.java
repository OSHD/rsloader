








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Graphics {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook graphicsDevice;
	private static FieldHook displayMode;
	public Graphics(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Graphics");
			graphicsDevice = currentHook.getFieldHook("getGraphicsDevice");
			displayMode = currentHook.getFieldHook("getDisplayMode");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		graphicsDevice=null;
		displayMode=null;
	}
	public GraphicsDevice getGraphicsDevice(){
		if(graphicsDevice==null)
			graphicsDevice = currentHook.getFieldHook("getGraphicsDevice");
		if(graphicsDevice!=null){
			Object data = graphicsDevice.get(currentObject);
			if(data!=null)
				return (GraphicsDevice)data;
		}
		return null;		
	}
	public DisplayMode getDisplayMode(){
		if(displayMode==null)
			displayMode = currentHook.getFieldHook("getDisplayMode");
		if(displayMode!=null){
			Object data = displayMode.get(currentObject);
			if(data!=null)
				return (DisplayMode)data;
		}
		return null;		
	}
}
