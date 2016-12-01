package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class AnimatedWallObject extends WallDecoration{
	public Object currentObject;
	public static ClassHook currentHook;
	private int localX;
	private int localY;
	private static FieldHook animatedObject;
	public AnimatedWallObject(Object o, int x, int y) {
		super(o);
		currentObject = o;
		localX=x;
		localY=y;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("AnimatedWallObject");
			animatedObject=currentHook.getFieldHook("getAnimatedObject");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		animatedObject=null;
	}
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
