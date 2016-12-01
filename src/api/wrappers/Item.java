package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class Item {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook id;
	private static FieldHook stackSize;
	public Item(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Item");
			id = currentHook.getFieldHook("getID");
			stackSize = currentHook.getFieldHook("getStackSize");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		id=null;
		stackSize=null;
	}
	public int getID(){
		if(id==null)
			id = currentHook.getFieldHook("getID");
		if(id!=null){
			Object data = id.get(currentObject);
			if(data!=null)
				return (Integer)data * id.getIntMultiplier();
		}
		return -1;
	}
	public int getStackSize(){
		if(stackSize==null)
			stackSize = currentHook.getFieldHook("getStackSize");
		if(stackSize!=null){
			Object data = stackSize.get(currentObject);
			if(data!=null)
				return (Integer)data * stackSize.getIntMultiplier();
		}
		return -1;
	}
	public ItemDef getItemDef(){
		try{
			Node ref = Nodes.lookup(Client.getItemDefLoader().getDefCache().getTable(), getID());
			if(ref==null)
				return null;
			if (SoftReference.isInstance(ref.currentObject)){
				SoftReference sr = new SoftReference(ref.currentObject);
				Object def = sr.getSoftReference().get();
				return new ItemDef(def);
			}
			else if (HardReference.isInstance(ref.currentObject)) {
				HardReference hr = new HardReference(ref.currentObject);
				Object def = hr.getHardReference();
				return new ItemDef(def);
			}
		}
		catch(Exception e){}
		return null;
	}
	public static boolean isInstance(Object o){
		if(currentHook==null)
			currentHook = Data.runtimeClassHooks.get("HardReference");
		if(currentHook!=null)
			return o.getClass().getName().equals(currentHook.getClassName());
		return false;
	}
}
