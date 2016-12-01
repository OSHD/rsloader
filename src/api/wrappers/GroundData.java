








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class GroundData {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook blocks;
	private static FieldHook x;
	private static FieldHook y;
	public GroundData(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("GroundData");
			blocks = currentHook.getFieldHook("getBlocks");
			x = currentHook.getFieldHook("getX");
			y = currentHook.getFieldHook("getY");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		x=null;
		y=null;
	}
	public int[][] getBlocks(){
		if(blocks==null)
			blocks = currentHook.getFieldHook("getBlocks");
		if(blocks!=null){
			Object data = blocks.get(currentObject);
			if(data!=null)
				return (int[][])data;
		}
		return new int[][]{};
	}
	public int getX(){
		if(x==null)
			x = currentHook.getFieldHook("getX");
		if(x!=null){
			Object data = x.get(currentObject);
			if(data!=null)
				return (Integer)data * x.getIntMultiplier();
		}
		return -1;
	}
	public int getY(){
		if(y==null)
			y = currentHook.getFieldHook("getY");
		if(y!=null){
			Object data = y.get(currentObject);
			if(data!=null)
				return (Integer)data * y.getIntMultiplier();
		}
		return -1;
	}
}
