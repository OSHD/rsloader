








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class PlayerDef {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook equipment;
	private static FieldHook colors;
	private static FieldHook id;
	private static FieldHook male;
	private static FieldHook modelHash;
	public PlayerDef(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("PlayerDef");
			equipment = currentHook.getFieldHook("getEquipment");
			colors = currentHook.getFieldHook("getColors");
			id = currentHook.getFieldHook("getID");
			male = currentHook.getFieldHook("isMale");
			modelHash = currentHook.getFieldHook("getModelHash");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		equipment=null;
		colors=null;
		id=null;
		male=null;
		modelHash=null;
	}
	public int[] getEquipment(){
		if(equipment==null)
			equipment = currentHook.getFieldHook("getEquipment");
		if(equipment!=null){
			Object data = equipment.get(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public int[] getColors(){
		if(colors==null)
			colors = currentHook.getFieldHook("getColors");
		if(colors!=null){
			Object data = colors.get(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
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
	public boolean isMale(){
		if(male==null)
			male = currentHook.getFieldHook("isMale");
		if(male!=null){
			Object data = male.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public long getModelHash(){
		if(modelHash==null)
			modelHash = currentHook.getFieldHook("getModelHash");
		if(modelHash!=null){
			Object data = modelHash.get(currentObject);
			if(data!=null)
				return (Long)data * modelHash.getLongMultiplier();
		}
		return -1;
	}
}
