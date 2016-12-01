








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
import java.lang.reflect.Array;
import java.util.Random;

public class Character extends Animable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook animator;
	private static FieldHook direction;
	private static FieldHook height;
	private static FieldHook movementSpeed;
	private static FieldHook models;
	private static FieldHook locX;
	private static FieldHook locY;
	private static FieldHook interactiveIndex;
	public Character(Object o){
		super(o);
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("Character");
			animator = currentHook.getFieldHook("getAnimator");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		animator=null;
		direction=null;
		height=null;
		movementSpeed=null;
		models=null;
		locX=null;
		locY=null;
		interactiveIndex=null;
	}

	
	public boolean clickTile(){
		if(isOnScreen()){
			Polygon p = Calculations.getTilePolygon(getLocationX(), getLocationY());
			Rectangle r = p.getBounds();
			Point pt = new Point(new Random().nextInt(r.width)+r.x, new Random().nextInt(r.height)+r.y);
			if(pt.x>0 && pt.x<515 && pt.y>54 && pt.y<388){
				api.methods.Mouse.move(pt);
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
	
	
	public Animator getAnimator(){
		if(animator==null)
			animator = currentHook.getFieldHook("getAnimator");
		if(animator!=null){
			Object data = animator.get(currentObject);
			if(data!=null)
				return new Animator(data);
		}
		return null;
	}
	public int getAnimationID(){
		Animator animator = getAnimator();
		if(animator!=null){
			Animation animation = animator.getAnimation();
			if(animation!=null)
				return animation.getID();
		}
		return -1;
	}
	public int getDirection(){
		if(direction==null)
			direction = currentHook.getFieldHook("getDirection");
		if(direction!=null){
			Object data = direction.get(currentObject);
			if(data!=null){
				double deg = ((Integer)data) * direction.getIntMultiplier();
				deg = deg/45;
				deg = deg-(deg%45);
				return (int)deg;
			}
		}
		return -1;		
	}
	public int getLocationX(){
//		try{
//			return getMaxX()+Client.getRSData().getBaseInfo().getX();
//		}
//		catch(Exception e){
//			return -1;
//		}
		if(locX==null)
			locX = currentHook.getFieldHook("getPlayerX");
		if(locX!=null){
			Object data = locX.get(currentObject);
			if(data!=null)
				return ((Integer)data) * locX.getIntMultiplier();
		}
		return -1;		
	}
	public int getLocationY(){
//		try{
//			return getMaxY()+Client.getRSData().getBaseInfo().getY();
//		}
//		catch(Exception e){
//			return -1;
//		}
		if(locY==null)
			locY = currentHook.getFieldHook("getPlayerY");
		if(locY!=null){
			Object data = locY.get(currentObject);
			if(data!=null)
				return ((Integer)data) * locY.getIntMultiplier();
		}
		return -1;		
	}
	
	public Tile getLocation() {
		return new Tile(getLocationX(), getLocationY(), getPlane());
	}
	public int getLocalX(){
		try{
			return getMaxX();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getLocalY(){
		try{
			return getMinY();
		}
		catch(Exception e){
			return -1;
		}
	}
	public int getHeight(){
//		if(height==null)
//			direction = currentHook.getFieldHook("getHeight");
//		if(height!=null){
//			Object data = height.get(currentObject);
//			if(data!=null)
//				return ((Integer)data) * height.getIntMultiplier();
//		}
		return 0;		
	}
	public int getMovementSpeed(){
		if(movementSpeed==null)
			movementSpeed = currentHook.getFieldHook("getMovementSpeed");
		if(movementSpeed!=null){
			Object data = movementSpeed.get(currentObject);
			if(data!=null)
				return ((Integer)data) * movementSpeed.getIntMultiplier();
		}
		return -1;		
	}
	public ModelLD[] getLDModels(){
		if(models==null)
			movementSpeed = currentHook.getFieldHook("getModels");
		if(models!=null){
			Object array = models.get(currentObject);
			ModelLD[] models = new ModelLD[Array.getLength(array)];
			for(int i=0;i<models.length;++i){
				Object data = Array.get(array, i);
				if(data!=null)
					models[i] = new ModelLD(data);
				else
					models[i] = null;
			}
			return models;
		}
		return new ModelLD[]{};
	}
	public boolean isIdle(){
		return !isMoving() && getAnimationID()==-1;
	}
	public boolean isMoving(){
		return getMovementSpeed()>0;
	}
	public boolean isOnScreen(){
		Point p = Calculations.locationToScreen(getLocationX(), getLocationY());
		return (p.x>0 && p.x<515 && p.y>54 && p.y<388);
	}
}
