package api.wrappers;

import java.awt.*;

public class TileArea {

	Polygon polygon;
	int plane = -1;

	public TileArea(final Tile t1, final Tile t2) {
		this(
				new Tile(Math.min(t1.getX(), t2.getX()), Math.min(t1.getY(), t2.getY()), t1.getPlane()),
				new Tile(Math.max(t1.getX(), t2.getX()), Math.min(t1.getY(), t2.getY()), t1.getPlane()),
				new Tile(Math.max(t1.getX(), t2.getX()), Math.max(t1.getY(), t2.getY()), t2.getPlane()),
				new Tile(Math.min(t1.getX(), t2.getX()), Math.max(t1.getY(), t2.getY()), t2.getPlane())
				);
	}

	public TileArea(final Tile... bounds) {
		polygon = new Polygon();
		for (Tile tile : bounds) {
			if (plane != -1 && tile.getPlane() != plane) {
				throw new RuntimeException("area does not support 3d");
			}
			plane = tile.getPlane();
			addTile(tile);
		}
	}

	public void addTile(Tile t) {
		addTile(t.getX(), t.getY());
	}

	public void addTile(int tilex, int tiley) {
		polygon.addPoint(tilex, tiley);
	}

	public boolean contains(GameObject go) {
		if(go!= null)
			return contains(go.getLocation());
		return false;
	}

	public boolean contains(NPC npc) {
		if(npc != null)
			return contains(npc.getLocation());
		return false;
	}

	public boolean contains(int tilex, int tiley) {
		if(tilex > 0 || tilex != -1 || tiley >0 || tiley != -1 )
			return polygon.contains(tilex, tiley);
		return false;
	}

	public boolean contains(Tile tile) {
		if(tile != null) 
			return contains(tile.getX(), tile.getY());
		return false;
	}
	
	public int[] getAreaXPoints() {
		return polygon.xpoints;
	}
	
	
	public int getAreaNPoints() {
		return polygon.npoints;
	}
	
	public int[] getAreaYPoints() {
		return polygon.ypoints;
	}
	
	public Rectangle getBounds() {
		return polygon.getBounds();
	}
}
