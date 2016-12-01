
 
 
 
 
 
 
 
 
package api.wrappers;
import api.methods.Mouse;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Character{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook gender;
	private static FieldHook level;
	private static FieldHook def;
	private static FieldHook name;
	private static FieldHook prayIcon;
	private static FieldHook skullIcon;
	private static FieldHook team;
	private static FieldHook title;

	private int[][] modelTriangles = new int[][]{new int[]{4, 5, 6, 7},
			new int[]{0, 1, 2, 3},
			new int[]{0, 1, 4, 5},
			new int[]{2, 3, 6, 7},
			new int[]{0, 3, 5, 7},
			new int[]{1, 2, 4, 6}};
	private int[] verticesX = new int[]{-75, -75, 75, 75, -75, -75, 75, 75};//tile x
	private int[] verticesY = new int[]{0, 0, 0, 0, -750, -750, -750, -750};//height
	private int[] verticesZ = new int[]{75, -75, -75, 75, -75, 75, -75, 75};
	public Player(Object o){
		super(o);
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Player");
			gender = currentHook.getFieldHook("getGender");
			level = currentHook.getFieldHook("getLevel");
			def = currentHook.getFieldHook("getPlayerDef");
			name = currentHook.getFieldHook("getPlayerName");
			prayIcon = currentHook.getFieldHook("getPrayerIcon");
			skullIcon = currentHook.getFieldHook("getSkullIcon");
			team = currentHook.getFieldHook("getTeam");
			title = currentHook.getFieldHook("getTitle");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		gender=null;
		level=null;
		def=null;
		name=null;
		prayIcon=null;
		skullIcon=null;
		team=null;
		title=null;
	}
	public boolean containsPoint(Point p){
		for(Polygon poly : getBounds())
			if(poly.contains(p))
				return true;
		return false;
	}
	public Polygon[] getBounds(){
		ArrayList<Polygon> polys = new ArrayList<Polygon>();
		int[][] screenPoints = projectBoundsVertices();
		if(screenPoints==new int[][]{})
			return new Polygon[]{};
		int[] trix = modelTriangles[0];
		int[] triy = modelTriangles[1];
		int[] triz = modelTriangles[2];
		int numTriangles = Math.min(trix.length, Math.min(triy.length, triz.length));;
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
	public byte getGender(){
		if(gender==null)
			gender = currentHook.getFieldHook("getGender");
		if(gender!=null){
			Object data = gender.get(currentObject);
			if(data!=null)
				return (Byte)data;
		}
		return -1;
	}
	public int getLevel(){
		if(level==null)
			level = currentHook.getFieldHook("getLevel");
		if(level!=null){
			Object data = level.get(currentObject);
			if(data!=null)
				return ((Integer)data) * level.getIntMultiplier();	
		}
		return -1;
	}
	public CapturedModel getCapturedModel(){
		for(Field f : currentObject.getClass().getFields()){
			if(f.getName().equals("model")){
				try {
					Object data = f.get(currentObject);
					if(data!=null){
						return (CapturedModel)data;
					}
				} catch (Exception e) {
				}
				break;
			}
		}
		return null;
	}
	public ModelLD getModel(){
		PlayerDef def = getPlayerDef();
		if(def!=null){	
			Cache models = Client.getPlayerModels();
			HashTable table = models.getTable();
			for(Node n : table.getBuckets()){
				for(Node in = n.getNext();in!=null && !in.currentObject.equals(n.currentObject);in=in.getNext()){
					if (SoftReference.isInstance(in.currentObject)){
						SoftReference sr = new SoftReference(in.currentObject);
						java.lang.ref.SoftReference<?> realRef = sr.getSoftReference();
						if(realRef!=null){
							if(in.getID()==def.getModelHash()){
								return new ModelLD(realRef.get());
							}
						}
						else if (HardReference.isInstance(in.currentObject)) {
							HardReference hr = new HardReference(in.currentObject);
							Object ref = hr.getHardReference();
							if(ref!=null){
								if(in.getID()==def.getModelHash())
									return new ModelLD(ref);
							}
						}
					}
				}
			}
		}
		return null;
	}
	public PlayerDef getPlayerDef(){
		if(def==null)
			def = currentHook.getFieldHook("getPlayerDef");
		if(def!=null){
			Object data = def.get(currentObject);
			if(data!=null)
				return new PlayerDef(data);
		}
		return null;
	}
	public String getPlayerName(){
		if(name==null)
			name = currentHook.getFieldHook("getPlayerName");
		if(name!=null){
			Object data = name.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public int getPrayerIcon(){
		if(prayIcon==null)
			prayIcon = currentHook.getFieldHook("getPrayerIcon");
		if(prayIcon!=null){
			Object data = prayIcon.get(currentObject);
			if(data!=null)
				return ((Integer)data) * prayIcon.getIntMultiplier();	
		}
		return -1;
	}
	public Point getRandomPoint(){
		try{
			int[][] pts = projectBoundsVertices();
			if(pts.length>0){
				int i = new Random().nextInt(pts.length);
				return new Point(pts[i][0], pts[i][1]);
			}
		}
		catch(Exception e){			
		}
		return new Point(-1, -1);
	}
	public int getSkullIcon(){
		if(skullIcon==null)
			skullIcon = currentHook.getFieldHook("getSkullIcon");
		if(skullIcon!=null){
			Object data = skullIcon.get(currentObject);
			if(data!=null)
				return ((Integer)data) * skullIcon.getIntMultiplier();	
		}
		return -1;
	}
	public int getTeam(){
		if(team==null)
			team = currentHook.getFieldHook("getTeam");
		if(team!=null){
			Object data = team.get(currentObject);
			if(data!=null)
				return ((Integer)data) * team.getIntMultiplier();
		}
		return -1;	
	}
	public String getTitle(){
		if(title==null)
			title = currentHook.getFieldHook("getTitle");
		if(title!=null){
			Object data = title.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
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
	public int[][] projectVertices() {
		float[] data = Calculations.matrixCache;
		ModelLD model = getModel();
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

				float fx = (float)(377.5 + (346.0 * _x) / _z);
				float fy = (float)(297.5 + (257.0 * _y) / _z);
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
	public int[][] projectBoundsVertices() {
		try{
			float[] data = Calculations.matrixCache;
			double locX = (this.getMinX()+0.5)*512;
			double locY = (this.getMinY()+0.5)*512;
			if(this.getMinX()!=this.getMaxX()){
				locX+=(this.getMaxX()+0.5)*512;
				locX/=2;
			}
			if(this.getMinY()!=this.getMaxY()){
				locY+=(this.getMaxY()+0.5)*512;
				locY/=2;
			}
			int numVertices = Math.min(verticesX.length, Math.min(verticesY.length, verticesZ.length));
			if(numVertices<1)
				return new int[][]{};
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
				int vertexX = (int) (verticesX[index] + locX);
				int vertexY = verticesY[index] + height;
				int vertexZ = (int) (verticesZ[index] + locY);

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
		catch(Exception e){
			return new int[][]{};
		}
	}
}
