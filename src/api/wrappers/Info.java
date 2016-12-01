








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Info {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook groundInfo;
	private static FieldHook baseInfo;
	private static FieldHook groundBytes;
	private static FieldHook groundData;
	private static FieldHook objectDefLoader;
	public Info(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Info");
			groundInfo = currentHook.getFieldHook("getGroundInfo");
			baseInfo = currentHook.getFieldHook("getBaseInfo");
			groundBytes = currentHook.getFieldHook("getGroundBytes");
			groundData = currentHook.getFieldHook("getGroundData");
			objectDefLoader = currentHook.getFieldHook("getObjectDefLoaders");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		groundInfo=null;
		baseInfo=null;
		groundBytes=null;
		groundData=null;
		objectDefLoader=null;
	}
	public GroundInfo getGroundInfo(){
		if(groundInfo==null)
			groundInfo = currentHook.getFieldHook("getGroundInfo");
		if(groundInfo!=null){
			Object data = groundInfo.get(currentObject);
			if(data!=null)
				return new GroundInfo(data);
		}
		return null;		
	}
	public BaseInfo getBaseInfo(){
		if(baseInfo==null)
			baseInfo = currentHook.getFieldHook("getBaseInfo");
		if(baseInfo!=null){
			Object data = baseInfo.get(currentObject);
			if(data!=null)
				return new BaseInfo(data);
		}
		return null;		
	}
	public GroundBytes getGroundBytes(){
		if(groundBytes==null)
			groundBytes = currentHook.getFieldHook("getGroundBytes");
		if(groundBytes!=null){
			Object data = groundBytes.get(currentObject);
			if(data!=null)
				return new GroundBytes(data);
		}
		return null;		
	}
	public GroundData getGroundData(){
		if(groundData==null)
			groundData = currentHook.getFieldHook("getGroundData");
		if(groundData!=null){
			Object data = groundData.get(currentObject);
			if(data!=null)
				return new GroundData(data);
		}
		return null;		
	}
	public ObjectDefLoader getObjectDefLoaders(){
		if(objectDefLoader==null)
			objectDefLoader = currentHook.getFieldHook("getObjectDefLoaders");
		if(objectDefLoader!=null){
			Object data = objectDefLoader.get(currentObject);
			if(data!=null)
				return new ObjectDefLoader(data);
		}
		return null;		
	}
}
