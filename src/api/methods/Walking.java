








package api.methods;

import  api.walking.TheWalker;
import api.wrappers.*;

import java.awt.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;


public class Walking {
	/**
	 * Generates a straight path to a target.
	 * 
	 * @param target
	 *            The tile to generate a path to.
	 * 
	 * @return The path.
	 */
	public static Tile[] generateStraightPath(Tile target) {
		ArrayList<Tile> path = new ArrayList<Tile>();

		Tile myTile = new Tile(Client.getMyPlayer().getLocationX(), Client.getMyPlayer().getLocationY(), Client.getMyPlayer().getPlane());
		int distance = (int) Calculations.distanceTo(target.getX(), target.getY());
		if (distance <= 15
				|| Calculations.worldToMap(target.getX(), target.getY()).equals(
						new Point(-1, -1)))
			return new Tile[] { target };
		int points = (int) Math.ceil((distance / 15) + 0.4999D);
		int xDist = (target.getX() > myTile.getX() ? target.getX() - myTile.getX() : myTile.getX()
				- target.getX());
		int yDist = (target.getY() > myTile.getY() ? target.getY() - myTile.getY() : myTile.getY()
				- target.getY());
		int xStep = xDist / points;
		int yStep = yDist / points;

		for (int i = 1; i <= points; i++) {
			int x = (target.getX() > myTile.getX() ? myTile.getX() + (xStep * i) : myTile.getX()
					- (xStep * i));
			int y = (target.getY() > myTile.getY() ? myTile.getY() + (yStep * i) : myTile.getY()
					- (yStep * i));
			path.add(new Tile(x, y, Client.getPlane()));
		}

		return path.toArray(new Tile[path.size()]);
	}


	/**
	 * Generates a straight path to a BoundaryObject.
	 * 
	 *
	 * 
	 * @return The path.
	 */
	public static Tile[] generateStraightPath(BoundaryObject bo) {
		return generateStraightPath(new Tile(bo.getLocationX(), bo.getLocationY(), bo.getPlane()));
	}
	/**
	 * Generates a straight path to a target to be walked using the screen,
	 * instead of the minimap
	 * 
	 * @param target
	 *            The tile to generate a path to.
	 * 
	 * @return The path.
	 */
	public static Tile[] generateStraightScreenPath(Tile target) {
		ArrayList<Tile> path = new ArrayList<Tile>();

		Tile myTile = new Tile(Client.getMyPlayer().getLocationX(), Client.getMyPlayer().getLocationY(), Client.getMyPlayer().getPlane());
		int distance = (int) Calculations.distanceTo(target.getX(), target.getY());
		if (distance <= 5
				|| Calculations.isOnScreen(target.getX(), target.getY()))
			return new Tile[] { target };

		int points = (int) Math.ceil((distance / 5) + 0.4999D);
		int xDist = (target.getX() > myTile.getX() ? target.getX() - myTile.getX() : myTile.getX()
				- target.getX());
		int yDist = (target.getY() > myTile.getY() ? target.getY() - myTile.getY() : myTile.getY()
				- target.getY());
		int xStep = xDist / points;
		int yStep = yDist / points;

		for (int i = 1; i <= points; i++) {
			int x = (target.getX() > myTile.getX() ? myTile.getX() + (xStep * i) : myTile.getX()
					- (xStep * i));
			int y = (target.getY() > myTile.getY() ? myTile.getY() + (yStep * i) : myTile.getY()
					- (yStep * i));
			path.add(new Tile(x, y, Client.getPlane()));
		}

		return path.toArray(new Tile[path.size()]);
	}
	public static Tile getDestination(){
		return Client.getDestination();
	}
	public static int getEnergy(){
		try {
			return Integer.parseInt(Interfaces.get(750, 6).getText());
		} catch (Exception ignored) {
			return -1;
		}
	}
	public static boolean hasDestination(){
		return !getDestination().equals(new Tile(-1, -1));
	}
	/**
	 * Inverts a path.
	 * 
	 * @param path
	 *            The path to be inverted.
	 * @return The inverted path.
	 */
	public static Tile[] invertPath(Tile[] path) {
		Tile[] invertedPath = new Tile[path.length];
		for (int i = 0; i < path.length; i++) {
			invertedPath[i] = path[path.length - 1 - i];
		}
		return invertedPath;
	}
	public static boolean isPathLocal(Tile[] path){
		for(Tile t : path)
			if(t.isOnMap())
				return true;
		return false;
	}
	public static boolean isRunEnabled(){
		return Settings.get(463)==1;
	}
	
	public static boolean isWalking(){
		return Players.getMyPlayer().isMoving();
	}
	public static void paintPath(Graphics g){
		TheWalker.drawMap(g);
	}
	public static Tile[] randomizePath(Tile[] path, int xDist, int yDist) {
		Tile[] newPath = new Tile[path.length];
		for (int i = 0; i < path.length; i++) {
			newPath[i] = new Tile(path[i].getX() + new Random().nextInt(-xDist)+ xDist,
					path[i].getY() + new Random().nextInt(-yDist)+yDist, Client.getPlane());
		}
		return newPath;
	}
	public static void setRun(boolean enable){
		if(isRunEnabled()==enable)
			return;
		InterfaceChild ic = Interfaces.get(750, 2);
		if(ic!=null){
			ic.click();
			for(int i=0;i<20;++i){
				if(isRunEnabled()==enable)
					break;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	public static boolean walkPath(Tile[] path) {
		if (!Players.getMyPlayer().isMoving() || Calculations.distanceTo(new Tile(Client.getDestinationX(), Client.getDestinationY())) <= 5)
			return new TheWalker().walkTo(path, true);
		return false;
	}
	/**
	 * Walks to every tile on a path and waits for a complete stop after every
	 * tile has been walked to.
	 * 
	 * @param path
	 *            The path to be walked.
	 */
	public static void walkTiles(Tile[] path) {
		try {
			for (Tile tile : path) {
				Point p = Calculations.worldToMap(tile.getX(), tile.getY());
				if (!p.equals(new Point(-1, -1))) {
					Mouse.move(p);
					Thread.sleep(new Random().nextInt(100) + 50);
					Mouse.click();
					Thread.sleep(1000);
					while (Client.getMyPlayer().isMoving())
						Thread.sleep(100);
					Thread.sleep(new Random().nextInt(250) + 500);
				} else {
					Tile[] path2 = generateStraightPath(tile);
					for (Tile tile2 : path2) {
						Point p2 = Calculations.worldToMap(tile2.getX(), tile2.getY());
						if (!p2.equals(new Point(-1, -1))) {
							Mouse.move(p2);
							Thread.sleep(new Random().nextInt(100) + 50);
							Mouse.click();
							Thread.sleep(1000);
							while (Client.getMyPlayer().isMoving())
								Thread.sleep(100);
							Thread.sleep(new Random().nextInt(250) + 500);
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Walks the path determined in the paramters and will kill once theTile
	 * is on screen.  Needs tweaking.
	 * 
	 * @param path
	 * @param theTile
	 * 
	 */
	public static void walkPathUntilOnScreen(Tile[] path, Tile theTile) {

		while(Calculations.isOnScreen(theTile.getX(), theTile.getY()))
			break;
		try {
			for (Tile tile : path) {
				Point p = Calculations.worldToMap(tile.getX(), tile.getY());
				if (!p.equals(new Point(-1, -1))) {
					Mouse.move(p);
					Thread.sleep(new Random().nextInt(100) + 50);
					Mouse.click();
					Thread.sleep(1000);
					while (Client.getMyPlayer().isMoving()) {
						if(Calculations.isOnScreen(tile.getX(), tile.getY()))
							continue;
						if(Calculations.isOnScreen(theTile.getX(), theTile.getY()))
							break;
						Thread.sleep(100);
					}
				} else {
					Tile[] path2 = generateStraightPath(tile);
					for (Tile tile2 : path2) {
						Point p2 = Calculations.worldToMap(tile2.getX(), tile2.getY());
						if (!p2.equals(new Point(-1, -1))) {
							Mouse.move(p2);
							Thread.sleep(new Random().nextInt(100) + 50);
							Mouse.click();
							Thread.sleep(1000);
							while (Client.getMyPlayer().isMoving()) {
								if(Calculations.isOnScreen(tile2.getX(), tile2.getY()))
									continue;
								if(Calculations.isOnScreen(theTile.getX(), theTile.getY()))
									break;
								Thread.sleep(100);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean walkTile(Tile t){
		if(t.isOnScreen())
			return t.doAction("Walk here");
		else if(t.isOnMap())
			return Walking.walkTileOnMap(t);
		return false;
	}
	public static boolean walkTileOnMap(Tile t){
		Point p = Calculations.tileToMap(t);
		if(!p.equals(new Point(-1, -1))){
			Mouse.move(p);
			try {
				Thread.sleep(new Random().nextInt(100) + 50);
			} catch (Exception e1) {
			}
			Mouse.click();
			for(int i=0;i<20;++i){
				try {
					if(hasDestination())
						return true;
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			}
		}
		return false;
	}
}

