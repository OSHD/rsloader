package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;
import api.methods.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GroundItem {
	public int locationX;
	public int locationY;
	public Item item;
	public GroundItem(int x, int y, Item i){
		locationX=x;
		locationY=y;
		this.item=i;
	}
	public boolean containsPoint(Point p){
		for(Polygon poly : getWireframe())
			if(poly.contains(p))
				return true;
		return false;
	}
	public boolean doAction(String action){
		if(!Menu.isOpen()){
			if(!isHovering() || !Menu.contains(action)){
				Point p = getRandomPoint();
				if(p.equals(new Point(-1, -1))){
					return false;
				}
				if(!containsPoint(p))
					return false;
				Mouse.move(p);
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
			}
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
		return Menu.click(action);
	}
	public int getLocalX(){
		return locationX-Client.getBaseX();
	}
	public int getLocalY(){
		return locationY-Client.getBaseY();
	}
	public Tile getLocation(){
		return new Tile(locationX, locationY);
	}
	public int getLocationX(){
		return locationX;
	}
	public int getLocationY(){
		return locationY;
	}
	public boolean hover(){
		if(isHovering())
			return true;
		Point p = getRandomPoint();
		if(p.equals(new Point(-1, -1))){
			return false;
		}
		if(!containsPoint(p))
			return false;
		Mouse.move(p);
		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}
		return isHovering();
	}
	public boolean isHovering(){
		return containsPoint(Mouse.getLocation());
	}
	public boolean isOnScreen(){
		return Calculations.isOnScreen(getLocationX(), getLocationY());
	}
	public Point getRandomPoint(){
		int[][] pts = projectVertices();
		if(pts.length>0){
			int i = new Random().nextInt(pts.length);
			return new Point(pts[i][0], pts[i][1]);
		}
		return new Point(-1, -1);
	}
	public Point getScreenPoint(){
		return Calculations.tileToScreen(getLocation());
	}
	public ModelLD getModelLD(){
		try{
			Node ref = Nodes.lookup(Client.getItemDefLoader().getModelCache().getTable(), item.getID());
			if(ref==null)
				return null;
			if (SoftReference.isInstance(ref.currentObject)){
				SoftReference sr = new SoftReference(ref.currentObject);
				Object def = sr.getSoftReference().get();
				return new ModelLD(def);
			}
			else if (HardReference.isInstance(ref.currentObject)) {
				HardReference hr = new HardReference(ref.currentObject);
				Object def = hr.getHardReference();
				return new ModelLD(def);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public Polygon[] getWireframe(){
		ModelLD model = getModelLD();
		if(model==null)
			return new Polygon[]{};
		ArrayList<Polygon> polys = new ArrayList<Polygon>();
		int[][] screenPoints = projectVertices();
		short[] trix = model.getTriangleX();
		short[] triy = model.getTriangleY();
		short[] triz = model.getTriangleZ();
		int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));;
		for (int i = 0; i < numTriangles; i++) {
			int index1 = trix[i];
			int index2 = triy[i];
			int index3 = triz[i];

			int point1X = screenPoints[index1][0];
			int point1Y = screenPoints[index1][1];
			int point2X = screenPoints[index2][0];
			int point2Y = screenPoints[index2][1];
			int point3X = screenPoints[index3][0];
			int point3Y = screenPoints[index3][1];
			if(point1X==-1 || point1Y==-1 ||
					point2X==-1 || point2Y==-1 ||
					point3X==-1 || point3Y==-1)
				continue;

			Polygon p = new Polygon();
			p.addPoint(point1X, point1Y);
			p.addPoint(point2X, point2Y);
			p.addPoint(point3X, point3Y);

			polys.add(p);
		}
		return polys.toArray(new Polygon[]{});
	}
	public int[][] projectVertices() {
		float[] data = Calculations.matrixCache;
		ModelLD model = getModelLD();
		if(model==null){
			return new int[][]{{-1, -1, -1}};
		}
		try{
			double locX = (getLocalX()+0.5)*512;
			double locY = (getLocalY()+0.5)*512;
			int numVertices = Math.min(model.getVerticiesX().length, Math.min(model.getVerticiesY().length, model.getVerticiesZ().length));
			int[][] screen = new int[numVertices][3];

			float xOff = data[12];
			float yOff = data[13];
			float zOff = data[15];
			float xX = data[0];
			float xY = data[4];
			float xZ = data[8];
			float yX = data[1];
			float yY = data[5];
			float yZ = data[9];
			float zX = data[3];
			float zY = data[7];
			float zZ = data[11];

			int height = Calculations.tileHeight((int)locX, (int)locY);
			for (int index = 0; index < numVertices; index++) {
				int vertexX = (int) (model.getVerticiesX()[index] + locX);
				int vertexY = model.getVerticiesY()[index] + height;
				int vertexZ = (int) (model.getVerticiesZ()[index] + locY);

				float _z = (zOff + (zX * vertexX + zY * vertexY + zZ * vertexZ));
				float _x = (xOff + (xX * vertexX + xY * vertexY + xZ * vertexZ));
				float _y = (yOff + (yX * vertexX + yY * vertexY + yZ * vertexZ));

				float fx = ((float)256.0 + ((float)256.0 * _x) / _z);
				float fy = ((float)166.0 + ((float)167.0 * _y) / _z);
				if(fx<520 && fx>0 && fy<390 && fy>50){
					screen[index][0] = (int)fx;
					screen[index][1] = (int)fy;
					screen[index][2] = 1;
				}
				else{
					screen[index][0] = -1;
					screen[index][1] = -1;
					screen[index][2] = 0;
				}
			}
			return screen;
		}
		catch(Exception e){}
		return new int[][]{{}};
	}
}
