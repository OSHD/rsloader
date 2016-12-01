








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;
import api.methods.Mouse;

import java.awt.*;
import java.lang.reflect.Array;

public class GroundInfo {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook tileData;
	private static FieldHook groundArray;
	public GroundInfo(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("GroundInfo");
			tileData = currentHook.getFieldHook("getTileData");
			groundArray = currentHook.getFieldHook("getGroundArray");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		tileData=null;
		groundArray=null;
	}
	public TileData[] getTileData(){
		if(tileData==null)
			tileData = currentHook.getFieldHook("getTileData");
		if(tileData!=null){
			Object array = tileData.get(currentObject);
			TileData[] data = new TileData[Array.getLength(array)];
			for(int i=0;i<data.length;++i){
				Object indData = Array.get(array, i);
				if(indData!=null)
					data[i] = new TileData(indData);
				else
					data[i] = null;
			}
			return data;
		}
		return new TileData[]{};
	}
	public Ground[][][] getGroundArray(){
		if(groundArray==null)
			groundArray = currentHook.getFieldHook("getGroundArray");
		if(groundArray!=null){
			Object data = groundArray.get(currentObject);
			if(data!=null){
				Ground[][][] tempArray = new Ground[][][]{};
				int x = Array.getLength(data);
				tempArray = new Ground[x][][];
				for(int x2=0;x2<x;++x2){
					Object curr = Array.get(data, x2);
					if(curr==null)
						continue;
					int y = Array.getLength(curr);
					tempArray[x2] = new Ground[y][];
					for(int y2=0;y2<y;++y2){
						Object curr2 = Array.get(curr, y2);
						if(curr2==null)
							continue;
						int z = Array.getLength(curr2);
						tempArray[x2][y2] = new Ground[z];
						for(int z2=0;z2<z;++z2){
							Object curr3 = Array.get(curr2, z2);
							if(curr3==null)
								continue;
							tempArray[x2][y2][z2] = new Ground(curr3);
						}
					}
				}
				return tempArray;
			}
		}
		return new Ground[][][]{};
	}
}
