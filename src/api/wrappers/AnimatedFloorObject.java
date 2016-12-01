package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
public class AnimatedFloorObject extends FloorDecoration{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook animatedObject;
	public AnimatedFloorObject(Object o, int x, int y) {
		super(o);
		currentObject = o;
		localX=x;
		localY=y;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("AnimatedFloorObject");
			animatedObject = currentHook.getFieldHook("getAnimatedObject");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		animatedObject=null;
	}
	private int localX;
	private int localY;
	public AnimatedObject getAnimatedObject(){
		if(animatedObject==null)
			animatedObject = currentHook.getFieldHook("getAnimatedObject");
		if(animatedObject!=null){
			Object data = animatedObject.get(currentObject);
			if(data!=null){
				return new AnimatedObject(data, (short)localX, (short)localY);
			}
		}
		return null;
	}
}
