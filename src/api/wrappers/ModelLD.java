








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class ModelLD extends Model{//The reflection wrapper
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook verticesX;
	private static FieldHook verticesY;
	private static FieldHook verticesZ;
	private static FieldHook triangleX;
	private static FieldHook triangleY;
	private static FieldHook triangleZ;
	private static FieldHook triangleColor;
	public ModelLD(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ModelLD");
			verticesX = currentHook.getFieldHook("getVerticiesX");
			verticesY = currentHook.getFieldHook("getVerticiesY");
			verticesZ = currentHook.getFieldHook("getVerticiesZ");
			triangleX = currentHook.getFieldHook("getTriangleX");
			triangleY = currentHook.getFieldHook("getTriangleY");
			triangleZ = currentHook.getFieldHook("getTriangleZ");
			triangleColor = currentHook.getFieldHook("getTriangleColor");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		verticesX=null;
		verticesY=null;
		verticesZ=null;
		triangleX=null;
		triangleY=null;
		triangleZ=null;
		triangleColor=null;
	}
	public int[] getVerticiesX(){
		if(verticesX==null)
			verticesX = currentHook.getFieldHook("getVerticiesX");
		if(verticesX!=null){
			Object data = verticesX.get(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public int[] getVerticiesY(){
		if(verticesY==null)
			verticesY = currentHook.getFieldHook("getVerticiesY");
		if(verticesY!=null){
			Object data = verticesY.get(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public int[] getVerticiesZ(){
		if(verticesZ==null)
			verticesZ = currentHook.getFieldHook("getVerticiesZ");
		if(verticesZ!=null){
			Object data = verticesZ.get(currentObject);
			if(data!=null)
				return (int[])data;
		}
		return new int[]{};
	}
	public short[] getTriangleX(){
		if(triangleX==null)
			triangleX = currentHook.getFieldHook("getTriangleX");
		if(triangleX!=null){
			Object data = triangleX.get(currentObject);
			if(data!=null)
				return (short[])data;
		}
		return new short[]{};
	}
	public short[] getTriangleY(){
		if(triangleY==null)
			triangleY = currentHook.getFieldHook("getTriangleY");
		if(triangleY!=null){
			Object data = triangleY.get(currentObject);
			if(data!=null)
				return (short[])data;
		}
		return new short[]{};
	}
	public short[] getTriangleZ(){
		if(triangleZ==null)
			triangleZ = currentHook.getFieldHook("getTriangleZ");
		if(triangleZ!=null){
			Object data = triangleZ.get(currentObject);
			if(data!=null)
				return (short[])data;
		}
		return new short[]{};
	}
	public short[] getTriangleColor(){
		if(triangleColor==null)
			triangleColor = currentHook.getFieldHook("getTriangleColor");
		if(triangleColor!=null){
			Object data = triangleColor.get(currentObject);
			if(data!=null)
				return (short[])data;
		}
		return new short[]{};
	}
	public int[][] projectVertices(int localX, int localY) {
		if(Calculations.matrixCache.length==0)
			return new int[][]{{}};
		float[] data = Calculations.matrixCache;
		double locX = (localX+0.5)*512;
		double locY = (localY+0.5)*512;
		int numVertices = Math.min(getVerticiesX().length, Math.min(getVerticiesY().length, getVerticiesZ().length));
		int[][] screen = new int[numVertices][3];

		float xX = data[0];
		float yX = data[1];
		//2 same as 3 practically
		float zX = data[3];
		float xY = data[4];
		float yY = data[5];
		//6 same as 7 practically
		float zY = data[7];
		float xZ = data[8];
		float yZ = data[9];
		//10 same as 11
		float zZ = data[11];
		float xOff = data[12];
		float yOff = data[13];
		//14 same as 15
		float zOff = data[15];

		int height = Calculations.tileHeight((int)locX, (int)locY);
		for (int index = 0; index < numVertices; index++) {
			int vertexX = (int) (getVerticiesX()[index] + locX);
			int vertexY = getVerticiesY()[index] + height;
			int vertexZ = (int) (getVerticiesZ()[index] + locY);
			
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
}
