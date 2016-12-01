
 
 
 
 
 
 
 
 
package api.methods;

import api.wrappers.*;

import java.util.ArrayList;

public class NPCs {
	
	public static NPC getNearest(int...ids) {
		NPC temp = null;
		double dist = Double.MAX_VALUE;
		for (NPC npc : getNPCArray()) {
			if(npc==null)
				continue;
			int id = npc.getNPCDef().getID();
			for (int i : ids) {
				if (i == id) {
					double distance = Calculations.distanceTo(npc.getLocationX(), npc.getLocationY());
					if (distance < dist) {
						dist = distance;
						temp = npc;
					}
				}
			}
		}
		if(temp != null)
			return temp;
		return null;
	}

	
	public static NPC getNearestOnScreen(int...ids) {
		NPC temp = null;
		double dist = Double.MAX_VALUE;
		for (NPC npc : getNPCArray()) {
			if(npc != null && npc.getNPCDef() != null && npc.getNPCDef().getID() > 1) {
				int id = npc.getNPCDef().getID();
				for (int i : ids) {
					if (i == id) {
						double distance = Calculations.distanceTo(npc.getLocationX(), npc.getLocationY());
						if (distance < dist) {
							dist = distance;
							temp = npc;
						}
					}
				}
			}
		}
		if(temp != null)
			if(temp.isOnScreen())
				return temp;
		return getNearestOnScreen(ids);
	}

	
	public static NPC[] getNPCsByID(int... npcIDs) {
		ArrayList<NPC> matchedNPCs = new ArrayList<NPC>();
		for (NPC npc : getNPCArray()) {
			if(npc==null)
				continue;
			for (int currentID : npcIDs) {
				if (npc.getNPCDef().getID() == currentID) {
					matchedNPCs.add(npc);
				}
			}
		}
		return matchedNPCs.toArray(new NPC[]{});
	}

	public static NPC[] getNPCsByName(String... npcNames) {
		ArrayList<NPC> matchedNPCs = new ArrayList<NPC>();
		for (NPC npc : getNPCArray()) {
			if(npc==null)
				continue;
			for (String currentName : npcNames) {
				if (npc.getNPCName().toLowerCase().equals(currentName.toLowerCase())) {
					matchedNPCs.add(npc);
				}
			}
		}
		return matchedNPCs.toArray(new NPC[]{});
	}

	public static NPC[] getNPCArray(){
		ArrayList<NPC> npcs = new ArrayList<NPC>();
		for(NPCNode node : Client.getNPCNodeArray()){
			if(node!=null){
				NPC npc = node.getNPC();
				if(npc==null)
					continue;
				if(!npc.getNPCName().equals("null") && !npc.getNPCName().equals(""))
					npcs.add(npc);
			}
		}
		return npcs.toArray(new NPC[]{});
	}

	public static NPC getNPCAt(Tile t){
		if(t==null)
			return null;
		return getNPCAt(t.getX(), t.getY());
	}

	public static NPC getNPCAt(int x, int y){
		for(NPC npc : getNPCArray()){
			if(npc.getLocation().equals(new Tile(x, y, Client.getPlane())))
				return npc;
		}
		return null;
	}

}
