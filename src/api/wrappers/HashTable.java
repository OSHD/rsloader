








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

public class HashTable {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook buckets;
	public HashTable(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("HashTable");
			buckets = currentHook.getFieldHook("getBuckets");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		buckets=null;
	}
	public Node[] getBuckets(){
		if(buckets==null)
			buckets = currentHook.getFieldHook("getBuckets");
		if(buckets!=null){
			Object data = buckets.get(currentObject);
			Node[] nodes = new Node[Array.getLength(data)];
			for(int i=0;i<nodes.length;++i)
				nodes[i]=new Node(Array.get(data, i));
			return nodes;
		}			
		return new Node[]{};
	}
}
