








package api.methods;

import api.wrappers.Player;
import api.wrappers.PlayerDef;

import java.util.ArrayList;


public class Players {
	public static String[] getAllPlayerNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(Player p : getPlayerArray()){
			if(p!=null && !p.getPlayerName().equals("")){
				names.add(p.getPlayerName());
			}
		}
		return names.toArray(new String[]{});
	}
	public static Player getMyPlayer(){
		return Client.getMyPlayer();
	}
	public static Player[] getPlayerArray(){
		ArrayList<Player> players = new ArrayList<Player>();
		for(Player p : Client.getPlayerArray())
			if(p!=null)
				players.add(p);
		return players.toArray(new Player[]{});
	}
	public static Player getPlayerByID(int id){
		for(Player p : getPlayerArray()){
			PlayerDef def = p.getPlayerDef();
			if(def==null)
				continue;
			if(def.getID()==id)
				return p;
		}
		return null;
	}
	public static Player getPlayerByName(String name){
		for(Player p : getPlayerArray())
			if(p.getPlayerName().equals(name))
				return p;
		return null;
	}
}
