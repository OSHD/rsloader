








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class SkillInfo {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook maxLevel;
	private static FieldHook experience;
	private static FieldHook currentLevel;
	public SkillInfo(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("SkillInfo");
			maxLevel = currentHook.getFieldHook("getMaxLevel");
			experience = currentHook.getFieldHook("getExperience");
			currentLevel = currentHook.getFieldHook("getCurrentLevel");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		maxLevel=null;
		experience=null;
		currentLevel=null;
	}
	public int getCurrentLevel(){
		if(currentLevel==null)
			currentLevel = currentHook.getFieldHook("getCurrentLevel");
		if(currentLevel!=null){
			Object data = currentLevel.get(currentObject);
			if(data!=null)
				return (Integer)data * currentLevel.getIntMultiplier();
		}
		return -1;
	}
	public int getExperience(){
		if(experience==null)
			experience = currentHook.getFieldHook("getExperience");
		if(experience!=null){
			Object data = experience.get(currentObject);
			if(data!=null)
				return (Integer)data * experience.getIntMultiplier();
		}
		return -1;
	}
	public int getMaxLevel(){
		if(maxLevel==null)
			maxLevel = currentHook.getFieldHook("getData");
		if(maxLevel!=null){
			Object data = maxLevel.get(currentObject);
			if(data!=null)
				return (Integer)data * maxLevel.getIntMultiplier();
		}
		return -1;
	}
}
