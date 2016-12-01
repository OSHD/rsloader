








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class LookupTable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook identityTable;
	public LookupTable(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("LookupTable");
			identityTable = currentHook.getFieldHook("getIdentityTable");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		identityTable=null;
	}
	public int[] getIdentityTable(){
		if(identityTable==null)
			identityTable = currentHook.getFieldHook("getIdentityTable");
		if(identityTable!=null){
			Object data = identityTable.get(currentObject);
			if(data!=null)
				return ((int[])data);	
		}
		return new int[]{};
	}
}
