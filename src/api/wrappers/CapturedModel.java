package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
import java.util.Arrays;

public class CapturedModel {
	private ModelLD cappedModel=null;
	private int[] vertex_x=new int[]{};
	private int[] vertex_y=new int[]{};
	private int[] vertex_z=new int[]{};
	private short[] triangle_x=new short[]{};
	private short[] triangle_y=new short[]{};
	private short[] triangle_z=new short[]{};
	private short[] triangle_colors=new short[]{};
	private int numVertices=0;
	private int numFaces=0;
	public CapturedModel(final ModelLD model){
		cappedModel=model;
		final int[] verticies_x = model.getVerticiesX();
		final int[] verticies_y = model.getVerticiesY();
		final int[] verticies_z = model.getVerticiesZ();
		final short[] tri_x = model.getTriangleX();
		final short[] tri_y = model.getTriangleY();
		final short[] tri_z = model.getTriangleZ();
		final short[] tri_colors = model.getTriangleColor();
		final int numVertices = Math.min(verticies_x.length, Math.min(verticies_y.length, verticies_z.length));
		final int numFaces = Math.min(tri_x.length, Math.min(tri_y.length, tri_z.length));
		this.numVertices = numVertices;
		this.numFaces = numFaces;
		this.vertex_x = Arrays.copyOf(verticies_x, this.numVertices);
		this.vertex_y = Arrays.copyOf(verticies_y, this.numVertices);
		this.vertex_z = Arrays.copyOf(verticies_z, this.numVertices);
		this.triangle_x = Arrays.copyOf(tri_x, this.numFaces);
		this.triangle_y = Arrays.copyOf(tri_y, this.numFaces);
		this.triangle_z = Arrays.copyOf(tri_z, this.numFaces);
		this.triangle_colors = tri_colors.clone();
	}
	public void updateData(){
		final int[] verticies_x = cappedModel.getVerticiesX();
		final int[] verticies_y = cappedModel.getVerticiesY();
		final int[] verticies_z = cappedModel.getVerticiesZ();
		final short[] tri_x = cappedModel.getTriangleX();
		final short[] tri_y = cappedModel.getTriangleY();
		final short[] tri_z = cappedModel.getTriangleZ();
		final short[] tri_colors = cappedModel.getTriangleColor();
		final int numVertices = Math.min(verticies_x.length, Math.min(verticies_y.length, verticies_z.length));
		final int numFaces = Math.min(tri_x.length, Math.min(tri_y.length, tri_z.length));
		if(numVertices>this.numVertices){
			this.numVertices = numVertices;
			this.vertex_x = Arrays.copyOf(verticies_x, this.numVertices);
			this.vertex_y = Arrays.copyOf(verticies_y, this.numVertices);
			this.vertex_z = Arrays.copyOf(verticies_z, this.numVertices);
		}
		else{
			this.numVertices = numVertices;
			System.arraycopy(verticies_x, 0, this.vertex_x, 0, this.numVertices);
			System.arraycopy(verticies_y, 0, this.vertex_y, 0, this.numVertices);
			System.arraycopy(verticies_z, 0, this.vertex_z, 0, this.numVertices);
		}
		if(numFaces>this.numFaces){
			this.numFaces = numFaces;
			this.triangle_x = Arrays.copyOf(tri_x, this.numFaces);
			this.triangle_y = Arrays.copyOf(tri_y, this.numFaces);
			this.triangle_z = Arrays.copyOf(tri_z, this.numFaces);
		}
		else{
			this.numFaces = numFaces;
			System.arraycopy(tri_x, 0, this.triangle_x, 0, this.numFaces);
			System.arraycopy(tri_y, 0, this.triangle_y, 0, this.numFaces);
			System.arraycopy(tri_z, 0, this.triangle_z, 0, this.numFaces);
		}
		//this.triangle_colors = tri_colors.clone();
	}
	public int[] getVerticiesX(){
		return vertex_x;
	}
	public int[] getVerticiesY(){
		return vertex_y;
	}
	public int[] getVerticiesZ(){
		return vertex_z;
	}
	public short[] getTriangleX(){
		return triangle_x;
	}
	public short[] getTriangleY(){
		return triangle_y;
	}
	public short[] getTriangleZ(){
		return triangle_z;
	}
	public short[] getTriangleColor(){
		return triangle_colors;
	}
	public int[][] projectVertices(int localX, int localY) {
		if(Calculations.matrixCache.length==0)
			return new int[][]{{}};
		float[] data = Calculations.matrixCache;
		double locX = (localX+0.5)*512;
		double locY = (localY+0.5)*512;
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
