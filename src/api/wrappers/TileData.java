
package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class TileData {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook heights;
	public TileData(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("TileData");
			heights = currentHook.getFieldHook("getHeights");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		heights=null;
	}
	public int[][] getHeights(){
		if(heights==null)
			heights = currentHook.getFieldHook("getHeights");
		if(heights!=null){
			Object data = heights.get(currentObject);
			if(data!=null)
				return (int[][])data;
		}
		return new int[][]{{}};		
	}
}
