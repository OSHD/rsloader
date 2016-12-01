








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class ObjectDefLoader {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook defCache;
	private static FieldHook modelCache;
	private static FieldHook composite;
	private static FieldHook groundActions;
	public ObjectDefLoader(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ObjectDefLoader");
			defCache = currentHook.getFieldHook("getDefCache");
			modelCache = currentHook.getFieldHook("getModelCache");
			composite = currentHook.getFieldHook("getComposite");
			groundActions = currentHook.getFieldHook("getGroundActions");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		defCache=null;
		modelCache=null;
		composite=null;
		groundActions=null;
	}
	public Cache getDefCache(){
		if(defCache==null)
			defCache = currentHook.getFieldHook("getDefCache");
		if(defCache!=null){
			Object data = defCache.get(currentObject);
			if(data!=null)
				return new Cache(data);
		}
		return null;
	}
	public Cache getModelCache(){
		if(modelCache==null)
			modelCache = currentHook.getFieldHook("getModelCache");
		if(modelCache!=null){
			Object data = modelCache.get(currentObject);
			if(data!=null)
				return new Cache(data);
		}
		return null;
	}
	public ObjectComposite getComposite(){
		if(composite==null)
			composite = currentHook.getFieldHook("getComposite");
		if(composite!=null){
			Object data = composite.get(currentObject);
			if(data!=null)
				return new ObjectComposite(data);
		}
		return null;
	}
	public String[] getGroundActions(){
		if(groundActions==null)
			groundActions = currentHook.getFieldHook("getGroundActions");
		if(groundActions!=null){
			Object data = groundActions.get(currentObject);
			if(data!=null)
				return (String[])data;
		}
		return new String[]{};
	}
	public ObjectDef getObjectDef(int id){
		Cache cache = getDefCache();
		if(cache!=null){
			Node node = Nodes.lookup(cache.getTable(), id);
			if(node!=null){
				ObjectDef def = new ObjectDef(node.currentObject);
				return def;
			}
		}
		return null;
	}
}
