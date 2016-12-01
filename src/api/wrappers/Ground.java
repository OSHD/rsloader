








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Ground {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook floorDecoration;
	private static FieldHook plane;
	private static FieldHook boundary1;
	private static FieldHook boundary2;
	private static FieldHook wallDecoration1;
	private static FieldHook wallDecoration2;
	private static FieldHook animableList;
	public Ground(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Ground");
			floorDecoration = currentHook.getFieldHook("getFloorDecoration");
			plane = currentHook.getFieldHook("getPlane");
			boundary1 = currentHook.getFieldHook("getBoundary1");
			boundary2 = currentHook.getFieldHook("getBoundary2");
			wallDecoration1 = currentHook.getFieldHook("getWallDecoration1");
			wallDecoration2 = currentHook.getFieldHook("getWallDecoration2");
			animableList = currentHook.getFieldHook("getAnimableList");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		floorDecoration=null;
		plane=null;
		boundary1=null;
		boundary2=null;
		wallDecoration1=null;
		wallDecoration2=null;
		animableList=null;
	}
	public FloorDecoration getFloorDecoration(){
		if(floorDecoration==null)
			floorDecoration = currentHook.getFieldHook("getFloorDecoration");
		if(floorDecoration!=null){
			Object data = floorDecoration.get(currentObject);
			if(data!=null)
				return new FloorDecoration(data);
		}
		return null;		
	}
	public byte getPlane(){
		if(plane==null)
			plane = currentHook.getFieldHook("getPlane");
		if(plane!=null){
			Object data = plane.get(currentObject);
			if(data!=null)
				return (Byte)data;
		}
		return -1;		
	}
	public Boundary getBoundary1(){
		if(boundary1==null)
			boundary1 = currentHook.getFieldHook("getBoundary1");
		if(boundary1!=null){
			Object data = boundary1.get(currentObject);
			if(data!=null)
				return new Boundary(data);
		}
		return null;		
	}
	public Boundary getBoundary2(){
		if(boundary2==null)
			boundary2 = currentHook.getFieldHook("getBoundary2");
		if(boundary2!=null){
			Object data = boundary2.get(currentObject);
			if(data!=null)
				return new Boundary(data);
		}
		return null;		
	}
	public WallDecoration getWallDecoration1(){
		if(wallDecoration1==null)
			wallDecoration1 = currentHook.getFieldHook("getWallDecoration1");
		if(wallDecoration1!=null){
			Object data = wallDecoration1.get(currentObject);
			if(data!=null)
				return new WallDecoration(data);
		}
		return null;		
	}
	public WallDecoration getWallDecoration2(){
		if(wallDecoration2==null)
			wallDecoration2 = currentHook.getFieldHook("getWallDecoration2");
		if(wallDecoration2!=null){
			Object data = wallDecoration2.get(currentObject);
			if(data!=null)
				return new WallDecoration(data);
		}
		return null;		
	}
	public AnimableNode getAnimableList(){
		if(animableList==null)
			animableList = currentHook.getFieldHook("getAnimableList");
		if(animableList!=null){
			Object data = animableList.get(currentObject);
			if(data!=null)
				return new AnimableNode(data);
		}
		return null;		
	}
}
