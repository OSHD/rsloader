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

public class AnimatedObject{
	public Object currentObject;
	public static ClassHook currentHook;
	private int localX;
	private int localY;
	private static FieldHook id;
	private static FieldHook interactable;
	private static FieldHook objectDefLoader;
	private static FieldHook orientation;
	public AnimatedObject(Object data, short x, short y) {
		localX=x;
		localY=y;
		currentObject = data;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("AnimatedObject");
			id=currentHook.getFieldHook("getID");
			interactable=currentHook.getFieldHook("getInteractable");
			objectDefLoader=currentHook.getFieldHook("getObjectDefLoader");
			orientation=currentHook.getFieldHook("getOrientation");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		id=null;
		interactable=null;
		objectDefLoader=null;
		orientation=null;
	}
	public boolean clickTile(){
		if(isOnScreen()){
			Polygon p = Calculations.getTilePolygon(getLocationX(), getLocationY());
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
		}
		return false;
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
	public boolean containsPoint(Point p){
		for(Polygon poly : getWireframe())
			if(poly.contains(p))
				return true;
		return false;
	}
	public Point getRandomPoint(){
		try{
			int[][] pts = projectVertices();
			if(pts.length>0){
				int i = new Random().nextInt(pts.length);
				return new Point(pts[i][0], pts[i][1]);
			}
		}
		catch(Exception e){			
		}
		return new Point(-1, -1);
	}
	public int getID(){
		if(id==null)
			id = currentHook.getFieldHook("getID");
		if(id!=null){
			Object data = id.get(currentObject);
			if(data!=null)
				return (Integer)data * id.getIntMultiplier();
		}
		return -1;
	}
	public Interactable getInteractable(){
		if(interactable==null)
			interactable = currentHook.getFieldHook("getInteractable");
		if(interactable!=null){
			Object data = interactable.get(currentObject);
			if(data!=null)
				return new Interactable(data);
		}
		return null;
	}
	public ObjectDef getObjectDef(){
		try{
			Node ref = Nodes.lookup(Client.getRSData().getObjectDefLoaders().getDefCache().getTable(), (long)getID());
			if(ref==null)
				return null;
			if (ref.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("SoftReference").getClassName())) {
				SoftReference sr = new SoftReference(ref.currentObject);
				Object def = sr.getSoftReference().get();
				return new ObjectDef(def);
			}
			else if (ref.currentObject.getClass().getName().equals(Data.runtimeClassHooks.get("HardReference").getClassName())) {
				HardReference hr = new HardReference(ref.currentObject);
				Object def = hr.getHardReference();
				return new ObjectDef(def);
			}
		}
		catch(Exception e){
		}
		return null;
	}
	public Tile getLocation(){
		return new Tile(getLocationX(), getLocationY());
	}
	public int getLocationX(){
		try{
			return localX+Client.getBaseX();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocationY(){
		try{
			return localY+Client.getBaseY();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocalX(){
		return localX;
	}
	public int getLocalY(){
		return localY;
	}
	public ObjectDefLoader getObjectDefLoader(){
		if(objectDefLoader==null)
			objectDefLoader = currentHook.getFieldHook("getObjectDefLoader");
		if(objectDefLoader!=null){
			Object data = objectDefLoader.get(currentObject);
			if(data!=null)
				return new ObjectDefLoader(data);
		}
		return null;
	}
	public ModelLD getLDModel(){
		ObjectDefLoader loader = getObjectDefLoader();
		if(loader!=null){
			Cache modelCache = loader.getModelCache();
			if(modelCache!=null){
				Node model = Nodes.lookup(modelCache.getTable(), getModelHash());
				if(model!=null){
					Object def=null;
					if (SoftReference.isInstance(model.currentObject)){
						SoftReference sr = new SoftReference(model.currentObject);
						java.lang.ref.SoftReference<?> realRef = sr.getSoftReference();
						if(realRef!=null)
							def = realRef.get();
					}
					else if (HardReference.isInstance(model.currentObject)) {
						HardReference hr = new HardReference(model.currentObject);
						def = hr.getHardReference();
					}
					if(def!=null){
						return new ModelLD(def);
					}
				}
			}
		}
		return null;
	}	
	public long getModelHash(){
		long modelHash = (getID()<<10) + (10 << 3) + getOrientation();
		modelHash |= 0 << 29;
		ObjectDefLoader loader = getObjectDefLoader();
		if(loader!=null){
			Cache modelCache = loader.getModelCache();
			if(modelCache!=null){
				modelHash = (getID()<<10) + (4 << 3) + getOrientation();
				modelHash |= 0 << 29;
				Node model = Nodes.lookup(modelCache.getTable(), modelHash);
				if(model!=null)
					return modelHash;
				modelHash = (getID()<<10) + (10 << 3) + getOrientation();
				modelHash |= 0 << 29;
				return modelHash;
			}
		}
		return modelHash;
	}
	public Point[] getModelPoints(){
		ArrayList<Point> pts = new ArrayList<Point>();
		int[][] screenPoints = projectVertices();
		for(int[] i : screenPoints){
			Point pt = new Point(i[0], i[1]);
			if(Calculations.pointOnScreen(pt))
				pts.add(pt);
		}
		return pts.toArray(new Point[]{});
	}
	public int getOrientation(){
		if(orientation!=null){
			Object data = orientation.get(currentObject);
			if(data!=null)
				return (Integer)data * orientation.getIntMultiplier();
		}
		return 0;
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
		Point p = Calculations.locationToScreen(getLocationX(), getLocationY());
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}
	public Polygon[] getWireframe(){
		ModelLD model = getLDModel();
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
	public int[][] projectVertices() {
		float[] data = Calculations.matrixCache;
		ModelLD model = getLDModel();
		if(model==null){
			return new int[][]{{-1, -1, -1}};
		}
		try{
			double locX = (localX+0.5)*512;
			double locY = (localY+0.5)*512;
			Interactable inter = getInteractable();
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
				if(fx<515 && fx>0 && fy<390 && fy>50){
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
