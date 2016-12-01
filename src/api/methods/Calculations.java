
package api.methods;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import api.wrappers.Player;
import api.wrappers.Tile;
import api.wrappers.TileData;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class Calculations {	
	public static float[] matrixCache = new float[]{};
	public static int angleToTile(Tile t) {
		Tile me = Players.getMyPlayer().getLocation();
		int angle = (int) Math.toDegrees(Math.atan2(t.getY() - me.getY(), t.getX() - me.getX()));
		return angle >= 0 ? angle : 360 + angle;
	}
	public static double distanceBetween(Point pt1, Point pt2){
		return distance(pt1.x, pt2.x, pt1.y, pt2.y);
	}
	public static double distanceBetween(Tile t1, Tile t2){
		return distance(t1.getX(), t2.getX(), t1.getY(), t2.getY());
	}
	public static double distance(int x1, int x2, int y1, int y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	public static double distanceTo(Tile t){
		return distanceTo(t.getX(), t.getY());
	}
	public static double distanceTo(int x, int y) {
		Player p = Client.getMyPlayer();
		if(p!=null){
			int x2 = p.getLocationX();
			int y2 = p.getLocationY();
			return Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
		}
		return -1.0;
	}
	public static Tile[] getSurrounding(final Tile t) {
        final Vector<Tile> neighbors = new Vector<Tile>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (j != i || i != 0) {
                    neighbors.add(new Tile(t.getX() + i, t.getY() + j, t.getPlane()));
                }
            }
        } return neighbors.toArray(new Tile[neighbors.size()]);
    }
	
	public static Polygon getTilePolygon(int x, int y){
		Polygon p = new Polygon();
		Point center = Calculations.locationToScreen(x, y);
		Point n = Calculations.locationToScreen(x, y+1);
		Point s = Calculations.locationToScreen(x, y-1);
		Point e = Calculations.locationToScreen(x+1, y);
		Point w = Calculations.locationToScreen(x-1, y);
		p.addPoint((center.x+n.x+w.x)/3, (center.y+n.y+w.y)/3);
		p.addPoint((center.x+s.x+w.x)/3, (center.y+s.y+w.y)/3);
		p.addPoint((center.x+s.x+e.x)/3, (center.y+s.y+e.y)/3);
		p.addPoint((center.x+n.x+e.x)/3, (center.y+n.y+e.y)/3);
		return p;
	}
	public static Point groundToScreen(int x, int y) {
		try{
			int z = tileHeight(x, y);
			return worldToScreen(x, z, y);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new Point(-1, -1);
	}
	public static Point groundToScreen(int x, int y, int height) {
		try{
			int z = tileHeight(x, y)-height;
			return worldToScreen(x, z, y);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new Point(-1, -1);
	}
	public static boolean isOnScreen(Tile t){
		return isOnScreen(t.getX(), t.getY());
	}
	public static boolean isOnScreen(int x, int y){
		Point p = Calculations.locationToScreen(x, y);
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}
	public static Point locationToScreen(int x, int y, int height){
		x = x-Client.getRSData().getBaseInfo().getX();
		y = y-Client.getRSData().getBaseInfo().getY();
		return groundToScreen((int) ((x + 0.5) * 512), (int) ((y + 0.5) * 512), height);
	}
	public static Point locationToScreen(int x, int y){
		try{
			if(!Bank.isOpen()){
				x = x-Client.getBaseX();
				y = y-Client.getBaseY();
				return groundToScreen((int) ((x + 0.5) * 512), (int) ((y + 0.5) * 512));
			}
		}
		catch(Exception e){}
		return new Point(-1, -1);
	}
	public static boolean pointOnScreen(Point p){
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}
	public static int random(int min, int max){
		return new Random().nextInt(max-min)+min;
	}
	public static int tileHeight(int x, int y) {
		try{
			int p = Client.getPlane();
			int x1 = x >> 9;
			int y1 = y >> 9;
			byte[][][] settings = Client.getRSData().getGroundBytes().getBytes();
			if (settings != null && x1 >= 0 && x1 < 512 && y1 >= 0 && y1 < 512) {
				if (p <= 3 && (settings[1][x1][y1] & 2) != 0) {
					++p;
				}
				TileData[] planes = Client.getRSData().getGroundInfo().getTileData();
				if (planes != null && p < planes.length && planes[p] != null) {
					int[][] heights = planes[p].getHeights();
					if (heights != null) {
						int x2 = x & 0x200 - 1;
						int y2 = y & 0x200 - 1;
						int start_h = heights[x1][y1] * (0x200 - x2) + heights[x1 + 1][y1] * x2 >> 9;
						int end_h = heights[x1][1 + y1] * (0x200 - x2) + heights[x1 + 1][y1 + 1] * x2 >> 9;
			return start_h * (512 - y2) + end_h * y2 >> 9;
					}
				}
			}
		}
		catch(Exception e){
		}
		return 0;
	}	
	public static boolean tileOnMap(Tile t){
		if(t==null)
			return false;
		return !worldToMap(t.getX(), t.getY()).equals(new Point(-1, -1));
	}
	public static Point tileToScreen(Tile t){
		return locationToScreen(t.getX(), t.getY());
	}	
	public static Point tileToMap(Tile t){
		Player p = Players.getMyPlayer();
		if(p!=null){
			Point center = worldToMap(p.getLocationX(), p.getLocationY());
			Point tile = worldToMap(t.getX(), t.getY());
			if(Calculations.distanceBetween(center, tile)<70)
				return worldToMap(t.getX(), t.getY());
		}
		return new Point(-1, -1);
	}
	public static Point worldToMap(int x, int y){	
		try{
			x-= Client.getBaseX();	
			y-= Client.getBaseY();	
			int regionTileX = Client.getMyPlayer().getLocationX() - Client.getBaseX();
			int regionTileY = Client.getMyPlayer().getLocationY() - Client.getBaseY();
			if(x>104 || x<0 || y>104 || y<0)
				return new Point(-1, -1);
			final int cX = (int) (x * 4 + 2) - (regionTileX << 9) / 0x80;		
			final int cY = (int) (y * 4 + 2) - (regionTileY << 9) / 0x80;		
			final int actDistSq = cX * cX + cY * cY;	
			final int mmDist = Math.max(154 / 2, 154 / 2) + 10;	
			if (mmDist * mmDist >= actDistSq) {		
				int angle = 0x3fff & (int)Client.getMinimapAngle();		
				final boolean mmsettingis4 = Client.getMinimapSetting()==4;		
				if (!mmsettingis4){			
					angle = 0x3fff & Client.getMinimapOffset() + (int) Client.getMinimapAngle();	
				}	
				int sin = SIN_TABLE[angle];	
				int cos = COS_TABLE[angle];		
				if (!mmsettingis4) {		
					final int fact = 0x100 + Client.getMinimapScale();		
					sin = 0x100 * sin / fact;			
					cos = 0x100 * cos / fact;		
				}	
				Point ret = new Point((cos * cX + sin * cY >> 0xf) + 550 + 154 / 2, -(cos * cY - sin * cX >> 0xf) + 58 + 154 / 2);
				if(x==regionTileX && y==regionTileY)
					return ret;
				else if(Calculations.distanceBetween(ret, Players.getMyPlayer().getLocation().getTileOnMap())<70)
					return ret;	
			}	
		}
		catch(Exception e){}
		return new Point(-1, -1);
	}
	public static Point worldToScreen(final int x, final int y, final int z) {
		if(matrixCache.length==0)
			return new Point(-1, -1);
		float[] data = matrixCache;
		final float _z = z * data[11] + (data[15] + x * data[3] + y * data[7]);
		final float _x = data[8] * z + (data[4] * y + (data[12] + data[0] * x));
		final float _y = data[9] * z + (data[5] * y + (data[1] * x + data[13]));	
		return new Point(			
			Math.round((int)(260.0 + (256.0 * _x) / _z)),	
			Math.round((int)(171.0 + (167.0 * _y) / _z))		
		);		
	}								
	public static final int[] SIN_TABLE = new int[0x4000];	
	public static final int[] COS_TABLE = new int[0x4000];	
	static {
		final double d = 0.00038349519697141029D;	
		for (int i = 0; i < 0x4000; i++) {		
			Calculations.SIN_TABLE[i] = (int) (32768D * Math.sin(i * d));	
			Calculations.COS_TABLE[i] = (int) (32768D * Math.cos(i * d));	
		}	
	}
}
