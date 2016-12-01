








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
import java.lang.reflect.Array;

public class Facade {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook skillArray;
	private static FieldHook settings;
	public Facade(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Facade");
			skillArray=currentHook.getFieldHook("getSkillArray");
			settings=currentHook.getFieldHook("getSettings");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		skillArray=null;
		settings=null;
	}
	public Settings getSettings(){
		if(settings==null)
			settings=currentHook.getFieldHook("getSettings");
		if(settings!=null){
			Object data = settings.get(currentObject);
			if(data!=null)
				return new Settings(data);
		}
		return null;
	}
	public SkillInfo[] getSkillArray(){
		if(skillArray==null)
			skillArray=currentHook.getFieldHook("getEntryBuffers");
		if(skillArray!=null){
			Object data = skillArray.get(currentObject);
			if(data!=null){
				SkillInfo[] skills = new SkillInfo[Array.getLength(data)];
				for(int i=0;i<skills.length;++i){
					Object indDat = Array.get(data, i);
					if(indDat!=null)
						skills[i]=new SkillInfo(indDat);
				}
				return skills;
			}
		}
		return new SkillInfo[]{};
	}
}
