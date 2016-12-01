package api.wrappers;

import api.methods.*;
import api.methods.Menu;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;
import api.methods.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameObject {
	@SuppressWarnings("unused")
	private int minx, miny, maxx, maxy;
	private byte plane;
	private ModelLD model;
	private int id;
	private int orientation=0;
	private Object reference;
	public GameObject(AnimableObject ao){
		minx=ao.getMinX();
		miny=ao.getMinY();
		maxx=ao.getMaxX();
		maxy=ao.getMaxY();
		plane=ao.getPlane();
		model=ao.getLDModel();
		id=ao.getID();
		reference=ao;
	}
	public GameObject(AnimatedObject ao){
		Interactable inter = ao.getInteractable();
		if(inter!=null){
			Animable anim = new Animable(inter.currentObject);
			minx=anim.getMinX();
			miny=anim.getMinY();
			maxx=anim.getMaxX();
			maxy=anim.getMaxY();
			plane=anim.getPlane();
		}
		else{
			minx=ao.getLocalX();
			maxx=ao.getLocalX();
			miny=ao.getLocalY();
			maxy=ao.getLocalY();
		}
		model=ao.getLDModel();
		id=ao.getID();
		orientation=ao.getOrientation();
		reference=ao;
	}
	public GameObject(BoundaryObject ao){
		minx=(int) ao.getLocalX();
		miny=(int) ao.getLocalY();
		maxx=(int) ao.getLocalX();
		maxy=(int) ao.getLocalY();
		plane=ao.getPlane();
		model=ao.getLDModel();
		id=ao.getID();
		reference=ao;
	}
	public GameObject(FloorObject ao){
		minx=(int) ao.getLocalX();
		miny=(int) ao.getLocalY();
		maxx=(int) ao.getLocalX();
		maxy=(int) ao.getLocalY();
		plane=ao.getPlane();
		model=ao.getLDModel();
		id=ao.getID();
		reference=ao;
	}
	public GameObject(WallObject ao){
		minx=(int) ao.getLocalX();
		miny=(int) ao.getLocalY();
		maxx=(int) ao.getLocalX();
		maxy=(int) ao.getLocalY();
		plane=ao.getPlane();
		model=ao.getLDModel();
		id=ao.getID();
		reference=ao;
	}

	public boolean containsPoint(Point p){
		Polygon[] ps = getWireframe();
		for(int i=0;i<ps.length;++i)
			if(ps[i].contains(p)){
				return true;
			}
		return false;
	}
	public boolean doAction(String action){
		if(!Menu.isOpen()){
			if(!isHovering() || !Menu.contains(action)){
				Point p = getRandomPoint();
				if(p.equals(new Point(-1, -1))){
					System.out.println("Point was not valid!");
					return false;
				}
				if(!containsPoint(p) || !Calculations.pointOnScreen(p)){
					if(!containsPoint(p))
						System.out.println("Did not contain point!");
					if(!Calculations.pointOnScreen(p)){
						System.out.println("Point not on screen!");
					}
					return false;
				}
				while(!Mouse.getLocation().equals(p)){
					Mouse.move(p);
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
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
		if(Menu.isOpen())
			return Menu.click(action);
		return false;
	}
	public int getID(){
		return id;
	}
	public int getLocalX(){
		return minx;
	}
	public int getLocalY(){
		return miny;
	}
	public Tile getLocation(){
		return new Tile(getLocationX(), getLocationY(), 0);
	}

	public int getLocationX(){
		try{
			return minx+Client.getBaseX();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocationY(){
		try{
			return miny+Client.getBaseY();
		}
		catch(Exception e){
			return -1;
		}
	}
	public ModelLD getModel(){
		return model;
	}
	public String getName(){
		ObjectDef def = getObjectDef();
		if(def!=null)
			return def.getName();
		return "null";
	}
	public ObjectDef getObjectDef(){
		try{
			Node ref = Nodes.lookup(Client.getRSData().getObjectDefLoaders().getDefCache().getTable(), (long)getID());
			if(ref==null)
				return null;
			if (SoftReference.isInstance(ref.currentObject)){
				SoftReference sr = new SoftReference(ref.currentObject);
				Object def = sr.getSoftReference().get();
				return new ObjectDef(def);
			}
			else if (HardReference.isInstance(ref.currentObject)) {
				HardReference hr = new HardReference(ref.currentObject);
				Object def = hr.getHardReference();
				return new ObjectDef(def);
			}
		}
		catch(Exception e){
		}
		return null;
	}
	public int getOrientation(){
		return orientation;
	}
	public Point[] getModelPoints(){
		if(model==null)
			return new Point[]{};
		ArrayList<Point> pts = new ArrayList<Point>();
		int[][] screenPoints = projectVertices();
		for(int[] i : screenPoints){
			Point pt = new Point(i[0], i[1]);
			if(Calculations.pointOnScreen(pt))
				pts.add(pt);
		}
		return pts.toArray(new Point[]{});
	}
	public int getPlane(){
		return plane;
	}
	public Point getRandomPoint(){
		try{
			Point[] pts = getModelPoints();
			for(int i=0;i<pts.length;++i){
				Point curr = pts[new Random().nextInt(pts.length)];
				if(containsPoint(curr)){
					return curr;
				}
			}
			//if(pts.length>0)
				//return pts[new Random().nextInt(pts.length)];
		}
		catch(Exception e){			
			e.printStackTrace();
		}
		return new Point(-1, -1);
	}
	public Polygon[] getWireframe(){
		ModelLD model = getModel();
		if(model==null)
			return new Polygon[]{};
		ArrayList<Polygon> polys = new ArrayList<Polygon>();
		int[][] screenPoints = projectVertices();
		short[] trix = model.getTriangleX();
		short[] triy = model.getTriangleY();
		short[] triz = model.getTriangleZ();
		int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));
		for (int i = 0; i < numTriangles; i++) {
			try{
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
			catch(Exception e){}
		}
		return polys.toArray(new Polygon[]{});
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
		if(Bank.isOpen())
			return false;
		Point p = Calculations.locationToScreen(getLocationX(), getLocationY());
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}
	
	public int[][] projectVertices() {
		float[] data = Calculations.matrixCache;
		ModelLD model = getModel();
		if(model==null){
			return new int[][]{{-1, -1, -1}};
		}
		try{
			double locX = (getLocalX()+0.5)*512;
			double locY = (getLocalY()+0.5)*512;
			if(reference instanceof AnimatedObject){
				Interactable inter = ((AnimatedObject)reference).getInteractable();
				if(inter!=null && inter.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("AnimatedAnimableObject").getClassName())){
					Animable anim = new Animable(inter.currentObject);
					locX = (anim.getMinX()+0.5)*512;
					locY = (anim.getMinY()+0.5)*512;
					if(anim.getMinX()!=anim.getMaxX()){
						locX+=(anim.getMaxX()+0.5)*512;
						locX/=2;
					}
					if(anim.getMinY()!=anim.getMaxY()){
						locY+=(anim.getMaxY()+0.5)*512;
						locY/=2;
					}
				}
			}
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
