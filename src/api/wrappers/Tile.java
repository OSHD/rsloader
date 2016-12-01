package api.wrappers;
import api.methods.*;
import api.methods.Mouse;

import api.methods.Menu;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Tile {
	private static Character c = new Character(null);
	private int localX;
	private int localY;
	private int x;
	private int y;
	private int plane;
	public Tile(int x, int y){
		this.plane=Client.getPlane();
		if(x<=104 || y<=104){
			this.localX=x;
			this.localY=y;
			this.x=x+Client.getBaseX();
			this.y=y+Client.getBaseY();
		}
		else{
			this.x=x;
			this.y=y;
			this.localX=x-Client.getBaseX();
			this.localY=y-Client.getBaseY();
		}
	}
	public Tile(int x, int y, int plane){
		this.plane=plane;
		if(x<=104 || y<=104){
			this.localX=x;
			this.localY=y;
			this.x=x+Client.getBaseX();
			this.y=y+Client.getBaseY();
		}
		else{
			this.x=x;
			this.y=y;
			this.localX=x-Client.getBaseX();
			this.localY=y-Client.getBaseY();
		}
	}
	public boolean clickTile(){
		Polygon p = Calculations.getTilePolygon(getLocalX(), getLocalY());
		Rectangle r = p.getBounds();
		Point pt = new Point(new Random().nextInt(r.width)+r.x, new Random().nextInt(r.height)+r.y);
		if(pt.x>0 && pt.x<515 && pt.y>54 && pt.y<388){
			Mouse.move(pt);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			Mouse.click();
			return true;
		}

		return false;
	}
	public boolean clickMap(){
		if(isOnMap()){
			Mouse.move(getTileOnMap());
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			Mouse.click();
			return true;
		}
		return false;
	}

	/**
	 * Creates an area out of a base tile of your choosing.
	 * 
	 * @Autor Greg
	 * @param height
	 * @param width
	 * @return
	 */
	public TileArea computeArea(int height, int width) {
		int genH = height / 2;
		int genW = width / 2;
		int A = getX() - genW;
		int B = getY() + genH;
		int C = getX() + genW;
		int D = getY() - genH;
		return new TileArea(new Tile(A,B, getPlane()), new Tile(C,D, getPlane()));
	}

	public boolean containsGameObject() {
		for(GameObject go : Objects.getAllObjects())
			if(go.getLocationX() == x && go.getLocationY() == y) {
				return true;
			} 
		return false;
	}

	public boolean containsNPC() {
		for(NPC npc : NPCs.getNPCArray())
			if(npc.getLocationX() == x && npc.getLocationY() == y) {
				return true;
			} 
		return false;
	}

	public boolean containsGroundItem() {
		GroundItem[] items = GroundItems.getItemsAt(x, y);
		if(items!=null && items.length>0)
			return true;
		return false;
	}
	public boolean containsPoint(Point p){
		return getPolygon().contains(p);
	}
	public boolean doAction(String action){
		if(!Menu.isOpen()){
			if(!isHovering() || !Menu.contains(action)){
				Point p = getRandomPoint();
				if(p.equals(new Point(-1, -1))){
					return false;
				}
				if(!containsPoint(p) || !Calculations.pointOnScreen(p))
					return false;
				Mouse.move(p);
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
			}
			if(!isHovering())
				return false;
			if(Menu.getIndex(action)==0){
				Mouse.click();
				for(int i=0;i<20;++i){
					if(Client.getMouseCrosshairState()==2)
						return true;
					if(Client.getMouseCrosshairState()==1)
						return false;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
				return false;
			}
			if(Menu.getIndex(action)>0){
				Mouse.rightClick();
				for(int i=0;i<10;++i){
					if(Menu.isOpen())
						break;
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
			}
		}
		if(Menu.isOpen())
			return Menu.click(action);
		return false;
	}
	public boolean equals(Tile t) {
		return x == t.getX() && y == t.getY() && t.getPlane() == getPlane();
	}
	public Rectangle getBounds(){
		return getPolygon().getBounds();
	}
	public Tile getClosestTile(final Tile tile) {
		final Tile player = new Tile(c.getLocationX(),c.getLocationY(), getPlane());
		final Tile close = new Tile((player.getX() + tile.getLocalX()) / 2,(player.getY() + tile.getLocalY()) / 2, getPlane());
		if (!close.isOnMap()) {
			return getClosestTile(close);
		}
		return close;
	}
	public Tile getLastTile(final Tile... tiles) {
		return tiles[tiles.length - 1];
	}
	public int getLocalX(){
		return localX;
	}
	public int getLocalY(){
		return localY;
	}
	public Tile getNext(final Tile... tiles) {
		for (int i = tiles.length - 1; i > 0; i -= 1) {
			if (tiles[i].isOnMap()) {
				return tiles[i];
			}
		}
		return null;
	}
	public int getPlane(){
		return plane;
	}
	public Polygon getPolygon(){
		return Calculations.getTilePolygon(x, y);
	}
	public Point getRandomPoint(){
		Point center = getScreenLocation();
		Polygon tile = getPolygon();
		for(int i=0;i<5;++i){
			Point newPt = new Point(Calculations.random(center.x-5, center.x+5), Calculations.random(center.y-5, center.y+5));
			if(tile.contains(newPt))
				return newPt;
		}
		return new Point(-1, -1);
	}
	public Point getScreenLocation(){
		return Calculations.tileToScreen(this);
	}
	public Tile[] getSurrounding(final Tile t) {
		final Vector<Tile> neighbors = new Vector<Tile>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (j != i || i != 0) {
					neighbors.add(new Tile(t.x + i, t.y + j, getPlane()));
				}
			}
		} return neighbors.toArray(new Tile[neighbors.size()]);
	}
	public Point getTileOnMap(){
		return Calculations.worldToMap(x, y);
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public boolean isHovering(){
		return containsPoint(Mouse.getLocation());
	}
	public boolean isOnMap(){
		return !getTileOnMap().equals(new Point(-1, -1));
	}	
	public boolean isOnScreen(){
		Point p = Calculations.locationToScreen(getX(), getY());
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}
	@Override
	public String toString(){
		return "("+x+", "+y+", "+plane+")";
	}
}
