
package api.walking;


import api.methods.Calculations;
import api.methods.Client;
import api.methods.Players;
import api.methods.Walking;
import api.wrappers.Player;
import api.wrappers.Tile;
import environment.Data;

import java.awt.*;

public class TheWalker {
	private static Thread walker = null;
	private static Tile[] path = null;
	public boolean isPathLocal(Tile[] path){
		for(Tile t : path)
			if(Calculations.tileOnMap(t))
				return true;
		return false;
	}
	private void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
		}
	}		
	public void wait(int toSleep) {
		try {
			long start = System.currentTimeMillis();
			Thread.sleep(toSleep);
			long now;
			while (start + toSleep > (now = System.currentTimeMillis())) {
				Thread.sleep(start + toSleep - now);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public boolean waitToMove(int timeout){
		long start = System.currentTimeMillis();
		Player myPlayer = Players.getMyPlayer();
		while (System.currentTimeMillis() - start < timeout) {
			if (myPlayer.isMoving() || !Client.getDestination().equals(new Tile(-1, -1)))
				return true;
			wait(15);
		}
		return false;
	}
	public static void drawMap(final Graphics g) {
		if (walker != null && walker.isAlive()) {
			g.setColor(Color.RED);
			Tile loc = Players.getMyPlayer().getLocation();
			Point myTile = Calculations.worldToMap(loc.getX(), loc.getY());
			Point center = new Point(myTile.x + 2, myTile.y + 2);
			g.drawOval(center.x - 70, center.y - 70, 140, 140);
			if (path == null) return;
			for (int i = 0; i < path.length; i++) {
				Tile tile = path[i];
				Point p = Calculations.worldToMap(tile.getX(), tile.getY());
				if (p.x != -1 && p.y != -1) {
					g.setColor(Color.BLACK);
					g.fillRect(p.x + 1, p.y + 1, 3, 3);
					if (i > 0) {
						final Point p1 = Calculations.worldToMap(path[i - 1].getX(), path[i - 1].getY());
						g.setColor(Color.ORANGE);
						if (p1.x != -1 && p1.y != -1)
							g.drawLine(p.x + 2, p.y + 2, p1.x + 2, p1.y + 2);
					}
				}
			}
			Tile next = nextTile(path);
			Point tile = Calculations.worldToMap(next.getX(), next.getY());
			g.setColor(Color.RED);
			if (tile.x != -1 && tile.y != -1) {
				g.fillRect(tile.x + 1, tile.y + 1, 3, 3);
			}
			g.setColor(Color.BLACK);
		}
	}
	public boolean walkTo(Tile[] path, boolean waitUntilDest){
		if(!isPathLocal(path)){
			System.out.println("[Walker] Path not local. Not walking.");
			return false;
		}
		Walker walkto = new Walker(path, 5, 10000);
		walker = new Thread(walkto);
		if(walker==null)return false;
		walker.start();
		waitToMove(Calculations.random(800, 1200));
		if (waitUntilDest) {
			while (walker.isAlive()) {
				sleep(Calculations.random(300, 600));
			}
			walker=null;
			return walkto.done;
		} 
		else{
			walker=null;
			return true;
		}
	}
	public Tile getClosestTileOnMap(Tile tile) {
		if (Client.isLoggedIn() && !tile.isOnMap()) {
			try {
				Tile loc = Players.getMyPlayer().getLocation();
				Tile walk = new Tile((loc.getX() + tile.getX()) / 2, (loc.getY() + tile.getY()) / 2);
				return walk.isOnMap() ? walk : getClosestTileOnMap(walk);
			} catch (final Exception e) { }
		}
		return tile;
	}
	public static Tile nextTile(Tile[] path) {
		for (int i = path.length - 1; i >= 0; i--) {
			if (path[i].isOnMap()) {
				return path[i];
			}
		}
		return new Tile(-1,-1);
	}
	class Walker implements Runnable {
		Tile tile = null;
		boolean done = false;
		int movementTimer = 10000;
		int distanceTo = 6;
		Walker(final Tile[] userpath) {
			this.tile = userpath[userpath.length - 1];
			path = userpath;
		}
		Walker(final Tile[] userpath, final int distanceTo, final int movementTimer) {
			this.tile = userpath[userpath.length - 1];
			this.movementTimer = movementTimer;
			this.distanceTo = distanceTo;
			path = userpath;
		}
		public void run() {
			long timer = System.currentTimeMillis();
			Tile lastTile = Players.getMyPlayer().getLocation();
			int randomReturn = Calculations.random(5, 8);
			while (Client.isLoggedIn() && Calculations.distanceTo(tile) > distanceTo && Data.currentScript!=null) {
				if(Data.currentScript.isPaused){
					sleep(500);
					continue;
				}
				if(!Walking.isRunEnabled() && Walking.getEnergy()>50){
					Walking.setRun(true);
					for(int i=0;i<20;++i){
						if(Walking.isRunEnabled())
							break;
						sleep(Calculations.random(150,200));
					}
				}
				if (!Players.getMyPlayer().isMoving() || (Client.getDestinationX() == -1 || Client.getDestinationY()==-1) || Calculations.distanceTo(new Tile(Client.getDestinationX(), Client.getDestinationY())) < randomReturn) {
					Tile nextTile = nextTile(path);
					if ((Client.getDestinationX() != -1 && Client.getDestinationY()!=-1) && Calculations.distanceBetween(new Tile(Client.getDestinationX(), Client.getDestinationY()), nextTile) <= distanceTo) {
						continue;
					}
					nextTile.clickMap();
					if(nextTile.equals(tile))
						done = true;
					if(!waitToMove(Calculations.random(800, 1200))){
						System.out.println("[Walker] Failed to walk to tile "+nextTile.toString());
						return;
					}
					randomReturn = Calculations.random(5, 8);
				}
				Tile myLoc = Players.getMyPlayer().getLocation();
				if(!myLoc.equals(lastTile)){
					timer = System.currentTimeMillis();
					lastTile = myLoc;
				}
				if (System.currentTimeMillis() - timer > movementTimer) {
					System.out.println("[Walker] Timeout exit.");
					return;
				}
				sleep(Calculations.random(20, 40));
			}
			if (Calculations.distanceTo(tile) <= distanceTo) {
				done = true;
			}
		}
	}
}