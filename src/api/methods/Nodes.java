
 
 
 
 
 
 
 
 
package api.methods;

import api.wrappers.*;

public class Nodes {
	public static Node lookup(HashTable nc, long id) {
		try {
			if (nc == null || nc.getBuckets() == null || id < 0) {
				return null;
			}
			for(Node node : nc.getBuckets()){
				for(Node in = node.getNext();in!=null && !in.currentObject.equals(node.currentObject);in=in.getNext()){
					try{
						if(in.getID()==id)
							return in;
					}
					catch(Exception e){}
				}
			}
		} catch (Exception e) {
		}
		return null;
	}
}
