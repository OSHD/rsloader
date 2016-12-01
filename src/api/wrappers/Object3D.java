package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;
import java.awt.*;

public class Object3D {

	int xLoc, yLoc;

	int startX = -32;
	int stopX = 32;
	int startY = -32;
	int stopY = 32;
	int startHeight = 0;
	int stopHeight = 50;

	private Polygon objectPoly;

	public Object3D(int xLoc, int yLoc) {
		//startHeight = new Tile(xLoc, yLoc).getPlane();
		//stopHeight = startHeight + 20;
		xLoc = transform(xLoc);
		yLoc = transform(yLoc);
	}

	public static int transform(int x) {
		return (x - Client.getBaseX()) * 128 + 64;
	}
	/**
	 * Essentially will return an initial boundary object
	 * More of a testing method than anything.
	 * @author Greg
	 * @param xLoc
	 * @param yLoc
	 * @return
	 */
	public Polygon getBounds(int xLoc, int yLoc) {
		return setBounds(xLoc, yLoc, startX, stopX, startY, stopY, startHeight, stopHeight);
	}

	public Polygon getPolygon() {
		if(this.objectPoly != null)
			return getBounds(xLoc, yLoc);
		return null;
	}

	private Polygon setBounds(int xLoc, int yLoc, int startX, int stopX,
			int startY, int stopY, int startHeight, int stopHeight) {

		Point p = Calculations.worldToScreen(xLoc + startX, yLoc + startY, startHeight);
		Point p1 = Calculations.worldToScreen(xLoc + startX, yLoc + stopY, startHeight);
		Point p2 = Calculations.worldToScreen(xLoc + stopX, yLoc + startY, startHeight);
		Point p3 = Calculations.worldToScreen(xLoc + stopX, yLoc + stopY, startHeight);

		Point p4 = Calculations.worldToScreen(xLoc + startX, yLoc + startY, stopHeight);
		Point p5 = Calculations.worldToScreen(xLoc + startX, yLoc + stopY, stopHeight);
		Point p6 = Calculations.worldToScreen(xLoc + stopX, yLoc + startY, stopHeight);
		Point p7 = Calculations.worldToScreen(xLoc + stopX, yLoc + stopY, stopHeight);

		if (p.x == -1 || p1.x == -1 || p2.x == -1 || p3.x == -1 || p4.x == -1
				|| p5.x == -1 || p6.x == -1 || p7.x == -1) {
			if (Calculations.locationToScreen(xLoc, yLoc, (startHeight + stopHeight) / 2).x == -1) {
				System.out.println("null");
				return null;
			}
			System.out.println("RETURNING POLYGON");
			return setBounds(xLoc, yLoc, startHeight - 2, stopX - 2, startY - 2,
					stopY - 2, startHeight - 2, stopHeight - 2);
		}
		int xp[] = {p.x, p1.x, p3.x, p2.x, p.x, p4.x, p5.x, p1.x, p5.x, p7.x,
				p3.x, p7.x, p6.x, p2.x, p6.x, p4.x};
		int yp[] = {p.y, p1.y, p3.y, p2.y, p.y, p4.y, p5.y, p1.y, p5.y, p7.y,
				p3.y, p7.y, p6.y, p2.y, p6.y, p4.y};

		return new Polygon(xp, yp, xp.length);
	}

	/**
	 * Set the bounds of an object3D to your fitting
	 * @author Greg
	 * @param xLoc
	 * @param yLoc
	 * @param startX
	 * @param stopX
	 * @param startY
	 * @param stopY
	 * @param startHeight
	 * @param stopHeight
	 * @return
	 */
	public Polygon setObjectDimensions(int xLoc, int yLoc, int startX, int stopX,
			int startY, int stopY, int startHeight, int stopHeight) {
		return objectPoly = setBounds(xLoc, yLoc, startX, stopX,
				startY, stopY, startHeight, stopHeight);
	}

	public int getxLoc() {
		return xLoc;
	}

	public void setxLoc(int xLoc) {
		this.xLoc = xLoc;
	}

	public int getyLoc() {
		return yLoc;
	}

	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}

	public int getStartX() {
		return startX;
	}


	public int getStopX() {
		return stopX;
	}


	public int getStartY() {
		return startY;
	}


	public int getStopY() {
		return stopY;
	}

	public int getStartHeight() {
		return startHeight;
	}

	public int getStopHeight() {
		return stopHeight;
	}

}
