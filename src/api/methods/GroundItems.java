
package api.methods;

import api.wrappers.*;

import java.util.ArrayList;

public class GroundItems {
	public static GroundItem[] getAll() {
		ArrayList<GroundItem> temp = new ArrayList<GroundItem>();
		Tile tile = Players.getMyPlayer().getLocation();
		int minX = Math.max(Client.getBaseX(), tile.getX() - 104), minY = Math.max(Client.getBaseY(), tile.getY() - 104);
		int maxX = Math.min(Client.getBaseX() + 104, tile.getX() + 104), maxY = Math.min(Client.getBaseY() + 104, tile.getY() + 104);
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				GroundItem[] items = getItemsAt(x, y);
				for (final GroundItem item : items) {
					if (item != null) {
						temp.add(item);
					}
				}
			}
		}
		return temp.toArray(new GroundItem[]{});
	}
	public static GroundItem[] getItemsAt(int x, int y){
		try{
			ArrayList<GroundItem> items = new ArrayList<GroundItem>();
			HashTable itemTable = Client.getItemHashTable();
			int index = x | y << 14 | Client.getPlane() << 28;
			NodeListCache cache = new NodeListCache(Nodes.lookup(itemTable, index).currentObject);
			NodeList nodeList = cache.getNodeList();
			Node tail = nodeList.getTail();
			for(Node curr = tail.getPrevious();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getPrevious()){	
				if(Item.isInstance(curr.currentObject)){
					items.add(new GroundItem(x, y, new Item(curr.currentObject)));
				}
			}	
			return items.toArray(new GroundItem[]{});
		}
		catch(Exception e){
		}
		return new GroundItem[]{};
	}
	public static GroundItem getNearestItemByID(int...ids) {
		GroundItem temp = null;
		double dist = Double.MAX_VALUE;
		for (GroundItem ao : getAll()) {
			int id = ao.item.getID();
			for (int i : ids) {
				if (i == id) {
					double distance = Calculations.distanceTo(ao.getLocationX(), ao.getLocationY());
					if (distance < dist) {
						dist = distance;
						temp = ao;
					}
				}
			}
		}
		return temp;
	}
	public static GroundItem getNearestItemByName(String...names) {
		GroundItem temp = null;
		double dist = Double.MAX_VALUE;
		for (GroundItem ao : getAll()) {
			String name = ao.item.getItemDef().getName();
			for (String i : names) {
				if (i.equals(name)) {
					double distance = Calculations.distanceTo(ao.getLocationX(), ao.getLocationY());
					if (distance < dist) {
						dist = distance;
						temp = ao;
					}
				}
			}
		}
		return temp;
	}
}
