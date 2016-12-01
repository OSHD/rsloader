
 
 
 
 
 
 
 
 
package api.wrappers;

import api.methods.Client;
import api.methods.Interfaces;
import api.methods.Menu;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;
import api.methods.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class InterfaceChild {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook getAnimator;
	private static FieldHook getRelativeX;
	private static FieldHook getRelativeY;
	private static FieldHook getWidth;
	private static FieldHook getHeight;
	private static FieldHook getID;
	private static FieldHook getTextureID;
	private static FieldHook getParentID;
	private static FieldHook getComponentID;
	private static FieldHook getModelID;
	private static FieldHook getModelZoom;
	private static FieldHook getTextColor;
	private static FieldHook getVerticalScrollbarSize;
	private static FieldHook getHorizontalScrollbarSize;
	private static FieldHook getVerticalScrollbarPosition;
	private static FieldHook getHorizontalScrollbarPosition;
	private static FieldHook getVerticalScrollbarThumbSize;
	private static FieldHook getHorizontalScrollbarThumbSize;
	private static FieldHook getComponentStackSize;
	private static FieldHook getBoundsArrayIndex;
	private static FieldHook getYRotation;
	private static FieldHook getXRotation;
	private static FieldHook getZRotation;
	private static FieldHook getModelType;
	private static FieldHook getShadowColor;
	private static FieldHook getBorderThickness;
	private static FieldHook getComponentIndex;
	private static FieldHook getType;
	private static FieldHook getSpecialType;
	private static FieldHook getChildren;
	private static FieldHook getActions;
	private static FieldHook getSelectedActionName;
	private static FieldHook getComponentName;
	private static FieldHook getText;
	private static FieldHook getTooltip;
	private static FieldHook isHidden;
	private static FieldHook isHovering;
	private static FieldHook isVisible;
	private static FieldHook getDisplayTime;
	private static FieldHook isVerticallyFlipped;
	private static FieldHook isHorizontallyFlipped;
	private static FieldHook isInventoryInterface;
	public Interface parentInterface;
	public InterfaceChild parentInterfaceChild;
	public int index;
	public long displayTime;
	public InterfaceChild getParentInterfaceChild(){
		return parentInterfaceChild;
	}
	public Interface getParentInterface(){
		return parentInterface;
	}
	public InterfaceChild(Object o, Interface i, int idx){
		parentInterface = i;
		index=idx;
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("InterfaceChild");
			getAnimator = currentHook.getFieldHook("getAnimator");
			getRelativeX = currentHook.getFieldHook("getRelativeX");
			getRelativeY = currentHook.getFieldHook("getRelativeY");
			getWidth = currentHook.getFieldHook("getWidth");
			getHeight = currentHook.getFieldHook("getHeight");
			getID = currentHook.getFieldHook("getID");
			getTextureID = currentHook.getFieldHook("getTextureID");
			getParentID = currentHook.getFieldHook("getParentID");
			getComponentID = currentHook.getFieldHook("getComponentID");
			getModelID = currentHook.getFieldHook("getModelID");
			getModelZoom = currentHook.getFieldHook("getModelZoom");
			getTextColor = currentHook.getFieldHook("getTextColor");
			getVerticalScrollbarSize = currentHook.getFieldHook("getVerticalScrollbarSize");
			getHorizontalScrollbarSize = currentHook.getFieldHook("getHorizontalScrollbarSize");
			getVerticalScrollbarPosition = currentHook.getFieldHook("getVerticalScrollbarPosition");
			getHorizontalScrollbarPosition = currentHook.getFieldHook("getHorizontalScrollbarPosition");
			getVerticalScrollbarThumbSize = currentHook.getFieldHook("getVerticalScrollbarThumbSize");
			getHorizontalScrollbarThumbSize = currentHook.getFieldHook("getHorizontalScrollbarThumbSize");
			getComponentStackSize = currentHook.getFieldHook("getComponentStackSize");
			getBoundsArrayIndex = currentHook.getFieldHook("getBoundsArrayIndex");
			getYRotation = currentHook.getFieldHook("getYRotation");
			getXRotation = currentHook.getFieldHook("getXRotation");
			getZRotation = currentHook.getFieldHook("getZRotation");
			getModelType = currentHook.getFieldHook("getModelType");
			getShadowColor = currentHook.getFieldHook("getShadowColor");
			getBorderThickness = currentHook.getFieldHook("getBorderThickness");
			getComponentIndex = currentHook.getFieldHook("getComponentIndex");
			getType = currentHook.getFieldHook("getType");
			getSpecialType = currentHook.getFieldHook("getSpecialType");
			getChildren = currentHook.getFieldHook("getChildren");
			getActions = currentHook.getFieldHook("getActions");
			getSelectedActionName = currentHook.getFieldHook("getSelectedActionName");
			getComponentName = currentHook.getFieldHook("getComponentName");
			getText = currentHook.getFieldHook("getText");
			getTooltip = currentHook.getFieldHook("getTooltip");
			isHidden = currentHook.getFieldHook("isHidden");
			isHovering = currentHook.getFieldHook("isHovering");
			isVisible = currentHook.getFieldHook("isVisible");
			getDisplayTime = currentHook.getFieldHook("getDisplayTime");
			isVerticallyFlipped = currentHook.getFieldHook("isVerticallyFlipped");
			isHorizontallyFlipped = currentHook.getFieldHook("isHorizontallyFlipped");
			isInventoryInterface = currentHook.getFieldHook("isInventoryInterface");
		}
		displayTime=getDisplayTime()-1;
	}
	public InterfaceChild(Object o, InterfaceChild ic, int idx){
		parentInterfaceChild=ic;
		index=idx;
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("InterfaceChild");
			getAnimator = currentHook.getFieldHook("getAnimator");
			getRelativeX = currentHook.getFieldHook("getRelativeX");
			getRelativeY = currentHook.getFieldHook("getRelativeY");
			getWidth = currentHook.getFieldHook("getWidth");
			getHeight = currentHook.getFieldHook("getHeight");
			getID = currentHook.getFieldHook("getID");
			getTextureID = currentHook.getFieldHook("getTextureID");
			getParentID = currentHook.getFieldHook("getParentID");
			getComponentID = currentHook.getFieldHook("getComponentID");
			getModelID = currentHook.getFieldHook("getModelID");
			getModelZoom = currentHook.getFieldHook("getModelZoom");
			getTextColor = currentHook.getFieldHook("getTextColor");
			getVerticalScrollbarSize = currentHook.getFieldHook("getVerticalScrollbarSize");
			getHorizontalScrollbarSize = currentHook.getFieldHook("getHorizontalScrollbarSize");
			getVerticalScrollbarPosition = currentHook.getFieldHook("getVerticalScrollbarPosition");
			getHorizontalScrollbarPosition = currentHook.getFieldHook("getHorizontalScrollbarPosition");
			getVerticalScrollbarThumbSize = currentHook.getFieldHook("getVerticalScrollbarThumbSize");
			getHorizontalScrollbarThumbSize = currentHook.getFieldHook("getHorizontalScrollbarThumbSize");
			getComponentStackSize = currentHook.getFieldHook("getComponentStackSize");
			getBoundsArrayIndex = currentHook.getFieldHook("getBoundsArrayIndex");
			getYRotation = currentHook.getFieldHook("getYRotation");
			getXRotation = currentHook.getFieldHook("getXRotation");
			getZRotation = currentHook.getFieldHook("getZRotation");
			getModelType = currentHook.getFieldHook("getModelType");
			getShadowColor = currentHook.getFieldHook("getShadowColor");
			getBorderThickness = currentHook.getFieldHook("getBorderThickness");
			getComponentIndex = currentHook.getFieldHook("getComponentIndex");
			getType = currentHook.getFieldHook("getType");
			getSpecialType = currentHook.getFieldHook("getSpecialType");
			getChildren = currentHook.getFieldHook("getChildren");
			getActions = currentHook.getFieldHook("getActions");
			getSelectedActionName = currentHook.getFieldHook("getSelectedActionName");
			getComponentName = currentHook.getFieldHook("getComponentName");
			getText = currentHook.getFieldHook("getText");
			getTooltip = currentHook.getFieldHook("getTooltip");
			isHidden = currentHook.getFieldHook("isHidden");
			isHovering = currentHook.getFieldHook("isHovering");
			isVisible = currentHook.getFieldHook("isVisible");
			getDisplayTime = currentHook.getFieldHook("getDisplayTime");
			isVerticallyFlipped = currentHook.getFieldHook("isVerticallyFlipped");
			isHorizontallyFlipped = currentHook.getFieldHook("isHorizontallyFlipped");
			isInventoryInterface = currentHook.getFieldHook("isInventoryInterface");
		}
		displayTime=getDisplayTime()-1;
	}
	public static void resetHooks(){
		currentHook=null;
		getAnimator=null;
		getRelativeX=null;
		getRelativeY=null;
		getWidth=null;
		getHeight=null;
		getID=null;
		getTextureID=null;
		getParentID=null;
		getComponentID=null;
		getModelID=null;
		getModelZoom=null;
		getTextColor=null;
		getVerticalScrollbarSize=null;
		getHorizontalScrollbarSize=null;
		getVerticalScrollbarPosition=null;
		getHorizontalScrollbarPosition=null;
		getVerticalScrollbarThumbSize=null;
		getHorizontalScrollbarThumbSize=null;
		getComponentStackSize=null;
		getBoundsArrayIndex=null;
		getYRotation=null;
		getXRotation=null;
		getZRotation=null;
		getModelType=null;
		getShadowColor=null;
		getBorderThickness=null;
		getComponentIndex=null;
		getType=null;
		getSpecialType=null;
		getChildren=null;
		getActions=null;
		getSelectedActionName=null;
		getComponentName=null;
		getText=null;
		getTooltip=null;
		isHidden=null;
		isHovering=null;
		isVisible=null;
		getDisplayTime=null;
		isVerticallyFlipped=null;
		isHorizontallyFlipped=null;
		isInventoryInterface=null;
	}
	public void click(){
		Point p = getRandomPoint();
		if(getBounds().contains(Mouse.getLocation()))
			Mouse.click();
		else if(!p.equals(new Point(-1, -1))){
			Mouse.move(p);
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			Mouse.click();
		}
	}
	public boolean containsPoint(Point p){
		return getBounds().contains(p);
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
			if(!isHovering())
				return false;
			if(Menu.getIndex(action)==0){
				Mouse.click();
				return true;
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
	public Point getRandomPoint(){
		try{
			Rectangle r = getBounds();
			return new Point(new Random().nextInt(r.width)+r.x, new Random().nextInt(r.height)+r.y);
		}
		catch(Exception e){}
		return new Point(-1, -1);
	}
	public Point getAbsoluteLocation() {
		int parentId = getParentID();
		int x = 0, y = 0;
		if (parentId != -1) {
			InterfaceChild child = Interfaces.getChild(parentId);
			if(child!=null){
				Point p = child.getAbsoluteLocation();
				x+=p.x;
				y+=p.y;
			}
		} 
		else {
			Rectangle[] bounds = Client.getInterfaceBoundsArray();
			int index = getBoundsArrayIndex();
			if (bounds != null && index > 0 && index < bounds.length && bounds[index] != null) {
				x+=bounds[index].x;
				y+=bounds[index].y;
				return new Point(x, y);
			}
		}
		if (parentId != -1) {
			InterfaceChild child = Interfaces.getChild(parentId);
			if(child!=null){
				int h = child.getHorizontalScrollbarSize(), v = child.getVerticalScrollbarSize();
				if (v > 0 || h > 0) {
					x -= child.getHorizontalScrollbarPosition();
					y -= child.getVerticalScrollbarPosition();
				}
			}
		}
		x += getRelativeX();
		y += getRelativeY();
		return new Point(x, y);
	}
	public int getAbsoluteX() {
		return getAbsoluteLocation().x;
	}
	public int getAbsoluteY(){
		return getAbsoluteLocation().y;
	}
	public Animator getAnimator(){
		if(getAnimator!=null)
			getAnimator = currentHook.getFieldHook("getAnimator");
		if(getAnimator!=null){
			Object data = getAnimator.get(currentObject);
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
	public Rectangle getBounds(){
		return new Rectangle(getAbsoluteX(), getAbsoluteY(), getHorizontalScrollbarThumbSize(), getVerticalScrollbarThumbSize());
	}
	public int getRelativeX(){
		if(getRelativeX==null)
			getRelativeX=currentHook.getFieldHook("getRelativeX");
		if(getRelativeX!=null){
			Object data = getRelativeX.get(currentObject);
			if(data!=null)
				return (Integer)data * getRelativeX.getIntMultiplier();
		}
		return -1;
	}
	public int getRelativeY(){
		if(getRelativeY==null)
			getRelativeY=currentHook.getFieldHook("getRelativeY");
		if(getRelativeY!=null){
			Object data = getRelativeY.get(currentObject);
			if(data!=null)
				return (Integer)data * getRelativeY.getIntMultiplier();
		}
		return -1;
	}
	public int getWidth(){
		if(getWidth==null)
			getWidth=currentHook.getFieldHook("getWidth");
		if(getWidth!=null){
			Object data = getWidth.get(currentObject);
			if(data!=null)
				return (Integer)data * getWidth.getIntMultiplier();
		}
		return -1;
	}
	public int getHeight(){
		if(getHeight==null)
			getHeight=currentHook.getFieldHook("getHeight");
		if(getHeight!=null){
			Object data = getHeight.get(currentObject);
			if(data!=null)
				return (Integer)data * getHeight.getIntMultiplier();
		}
		return -1;
	}
	public int getID(){
		if(getID==null)
			getID=currentHook.getFieldHook("getID");
		if(getID!=null){
			Object data = getID.get(currentObject);
			if(data!=null)
				return (Integer)data * getID.getIntMultiplier();
		}
		return -1;
	}
	public int getTextureID(){
		if(getTextureID==null)
			getTextureID=currentHook.getFieldHook("getTextureID");
		if(getTextureID!=null){
			Object data = getTextureID.get(currentObject);
			if(data!=null)
				return (Integer)data * getTextureID.getIntMultiplier();
		}
		return -1;
	}
	public int getParentID(){
		if(getParentID==null)
			getParentID=currentHook.getFieldHook("getParentID");
		if(getParentID!=null){
			Object data = getParentID.get(currentObject);
			if(data!=null){
				int id = (Integer)data * getParentID.getIntMultiplier();
				if(id!=-1)
					return id;
				else{
					int mainID = getID() >> 0x10;
					HashTable nc = Client.getInterfaceNodeCache();
					for(Node start : nc.getBuckets()){
						for(Node in = start.getNext();in!=null && !in.currentObject.equals(start.currentObject);in=in.getNext()){
							try{
								InterfaceNode curr = new InterfaceNode(in.currentObject);
								if(mainID==curr.getMainID())
									return (int)curr.getID();
							}
							catch(Exception e){}
						}
					}
				}
			}
		}
		return -1;
	}
	public int getComponentID(){
		if(getComponentID==null)
			getComponentID=currentHook.getFieldHook("getComponentID");
		if(getComponentID!=null){
			Object data = getComponentID.get(currentObject);
			if(data!=null)
				return (Integer)data * getComponentID.getIntMultiplier();
		}
		return -1;
	}
	public int getModelID(){
		if(getModelID==null)
			getModelID=currentHook.getFieldHook("getModelID");
		if(getModelID!=null){
			Object data = getModelID.get(currentObject);
			if(data!=null)
				return (Integer)data * getModelID.getIntMultiplier();
		}
		return -1;
	}
	public int getModelZoom(){
		if(getModelZoom==null)
			getModelZoom=currentHook.getFieldHook("getModelZoom");
		if(getModelZoom!=null){
			Object data = getModelZoom.get(currentObject);
			if(data!=null)
				return (Integer)data * getModelZoom.getIntMultiplier();
		}
		return -1;
	}
	public int getTextColor(){
		if(getTextColor==null)
			getTextColor=currentHook.getFieldHook("getTextColor");
		if(getTextColor!=null){
			Object data = getTextColor.get(currentObject);
			if(data!=null)
				return (Integer)data * getTextColor.getIntMultiplier();
		}
		return -1;
	}
	public int getVerticalScrollbarSize(){
		if(getVerticalScrollbarSize==null)
			getVerticalScrollbarSize=currentHook.getFieldHook("getVerticalScrollbarSize");
		if(getVerticalScrollbarSize!=null){
			Object data = getVerticalScrollbarSize.get(currentObject);
			if(data!=null)
				return (Integer)data * getVerticalScrollbarSize.getIntMultiplier();
		}
		return -1;
	}
	public int getHorizontalScrollbarSize(){
		if(getHorizontalScrollbarSize==null)
			getHorizontalScrollbarSize=currentHook.getFieldHook("getHorizontalScrollbarSize");
		if(getHorizontalScrollbarSize!=null){
			Object data = getHorizontalScrollbarSize.get(currentObject);
			if(data!=null)
				return (Integer)data * getHorizontalScrollbarSize.getIntMultiplier();
		}
		return -1;
	}
	public int getVerticalScrollbarPosition(){
		if(getVerticalScrollbarPosition==null)
			getVerticalScrollbarPosition=currentHook.getFieldHook("getVerticalScrollbarPosition");
		if(getVerticalScrollbarPosition!=null){
			Object data = getVerticalScrollbarPosition.get(currentObject);
			if(data!=null)
				return (Integer)data * getVerticalScrollbarPosition.getIntMultiplier();
		}
		return -1;
	}
	public int getHorizontalScrollbarPosition(){
		if(getHorizontalScrollbarPosition==null)
			getHorizontalScrollbarPosition=currentHook.getFieldHook("getHorizontalScrollbarPosition");
		if(getHorizontalScrollbarPosition!=null){
			Object data = getHorizontalScrollbarPosition.get(currentObject);
			if(data!=null)
				return (Integer)data * getHorizontalScrollbarPosition.getIntMultiplier();
		}
		return -1;
	}
	public int getVerticalScrollbarThumbSize(){
		if(getVerticalScrollbarThumbSize==null)
			getVerticalScrollbarThumbSize=currentHook.getFieldHook("getVerticalScrollbarThumbSize");
		if(getVerticalScrollbarThumbSize!=null){
			Object data = getVerticalScrollbarThumbSize.get(currentObject);
			if(data!=null)
				return (Integer)data * getVerticalScrollbarThumbSize.getIntMultiplier();
		}
		return -1;
	}
	public int getHorizontalScrollbarThumbSize(){
		if(getHorizontalScrollbarThumbSize==null)
			getHorizontalScrollbarThumbSize=currentHook.getFieldHook("getHorizontalScrollbarThumbSize");
		if(getHorizontalScrollbarThumbSize!=null){
			Object data = getHorizontalScrollbarThumbSize.get(currentObject);
			if(data!=null)
				return (Integer)data * getHorizontalScrollbarThumbSize.getIntMultiplier();
		}
		return -1;
	}
	public int getComponentStackSize(){
		if(getComponentStackSize==null)
			getComponentStackSize=currentHook.getFieldHook("getComponentStackSize");
		if(getComponentStackSize!=null){
			Object data = getComponentStackSize.get(currentObject);
			if(data!=null)
				return (Integer)data * getComponentStackSize.getIntMultiplier();
		}
		return -1;
	}
	public int getBoundsArrayIndex(){
		if(getBoundsArrayIndex==null)
			getBoundsArrayIndex=currentHook.getFieldHook("getBoundsArrayIndex");
		if(getBoundsArrayIndex!=null){
			Object data = getBoundsArrayIndex.get(currentObject);
			if(data!=null)
				return (Integer)data * getBoundsArrayIndex.getIntMultiplier();
		}
		return -1;
	}
	public int getYRotation(){
		if(getYRotation==null)
			getYRotation=currentHook.getFieldHook("getYRotation");
		if(getYRotation!=null){
			Object data = getYRotation.get(currentObject);
			if(data!=null)
				return (Integer)data * getYRotation.getIntMultiplier();
		}
		return -1;
	}
	public int getXRotation(){
		if(getXRotation==null)
			getXRotation=currentHook.getFieldHook("getXRotation");
		if(getXRotation!=null){
			Object data = getXRotation.get(currentObject);
			if(data!=null)
				return (Integer)data * getXRotation.getIntMultiplier();
		}
		return -1;
	}
	public int getZRotation(){
		if(getZRotation==null)
			getZRotation=currentHook.getFieldHook("getZRotation");
		if(getZRotation!=null){
			Object data = getZRotation.get(currentObject);
			if(data!=null)
				return (Integer)data * getZRotation.getIntMultiplier();
		}
		return -1;
	}
	public int getModelType(){
		if(getModelType==null)
			getModelType=currentHook.getFieldHook("getModelType");
		if(getModelType!=null){
			Object data = getModelType.get(currentObject);
			if(data!=null)
				return (Integer)data * getModelType.getIntMultiplier();
		}
		return -1;
	}
	public int getShadowColor(){
		if(getShadowColor==null)
			getShadowColor=currentHook.getFieldHook("getShadowColor");
		if(getShadowColor!=null){
			Object data = getShadowColor.get(currentObject);
			if(data!=null)
				return (Integer)data * getShadowColor.getIntMultiplier();
		}
		return -1;
	}
	public int getBorderThickness(){
		if(getBorderThickness==null)
			getBorderThickness=currentHook.getFieldHook("getBorderThickness");
		if(getBorderThickness!=null){
			Object data = getBorderThickness.get(currentObject);
			if(data!=null)
				return (Integer)data * getBorderThickness.getIntMultiplier();
		}
		return -1;
	}
	public int getComponentIndex(){
		if(getComponentIndex==null)
			getComponentIndex=currentHook.getFieldHook("getComponentIndex");
		if(getComponentIndex!=null){
			Object data = getComponentIndex.get(currentObject);
			if(data!=null)
				return (Integer)data * getComponentIndex.getIntMultiplier();
		}
		return -1;
	}
	public int getType(){
		if(getType==null)
			getType=currentHook.getFieldHook("getType");
		if(getType!=null){
			Object data = getType.get(currentObject);
			if(data!=null)
				return (Integer)data * getType.getIntMultiplier();
		}
		return -1;
	}
	public int getSpecialType(){
		if(getSpecialType==null)
			getSpecialType=currentHook.getFieldHook("getSpecialType");
		if(getSpecialType!=null){
			Object data = getSpecialType.get(currentObject);
			if(data!=null)
				return (Integer)data * getSpecialType.getIntMultiplier();
		}
		return -1;
	}
	public InterfaceChild[] getChildren(){
		if(getChildren==null)
			getChildren=currentHook.getFieldHook("getChildren");
		if(getChildren!=null){
			Object data = getChildren.get(currentObject);
			if(data!=null){
				ArrayList<InterfaceChild> array = new ArrayList<InterfaceChild>();
				int i=0;
				for(Object o : (Object[])data){
					array.add(new InterfaceChild(o, this, i));
					i++;
				}
				return array.toArray(new InterfaceChild[]{});
			}
		}
		return new InterfaceChild[]{};
	}
	public String[] getActions(){
		if(getActions==null)
			getActions=currentHook.getFieldHook("getActions");
		if(getActions!=null){
			Object data = getActions.get(currentObject);
			if(data!=null)
				return (String[])data;
		}
		return new String[]{};
	}
	public String getSelectedActionName(){
		if(getSelectedActionName==null)
			getSelectedActionName=currentHook.getFieldHook("getSelectedActionName");
		if(getSelectedActionName!=null){
			Object data = getSelectedActionName.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public String getComponentName(){
		if(getComponentName==null)
			getComponentName=currentHook.getFieldHook("getComponentName");
		if(getComponentName!=null){
			Object data = getComponentName.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public String getText(){
		if(getText==null)
			getText=currentHook.getFieldHook("getText");
		if(getText!=null){
			Object data = getText.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
	}
	public String getTooltip(){
		if(getTooltip==null)
			getTooltip=currentHook.getFieldHook("getTooltip");
		if(getTooltip!=null){
			Object data = getTooltip.get(currentObject);
			if(data!=null)
				return data.toString();
		}
		return "";
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
	public boolean isHidden(){
		if(isHidden==null)
			isHidden=currentHook.getFieldHook("isHidden");
		if(isHidden!=null){
			Object data = isHidden.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public boolean isHovering(){
		if(isHovering==null)
			isHovering=currentHook.getFieldHook("isHovering");
		if(isHovering!=null){
			Object data = isHovering.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public boolean isVisible(){
		if(isVisible==null)
			isVisible=currentHook.getFieldHook("isVisible");
		if(isVisible!=null){
			Object data = isVisible.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public int getDisplayTime(){
		if(getDisplayTime==null)
			getDisplayTime=currentHook.getFieldHook("getDisplayTime");
		if(getDisplayTime!=null){
			Object data = getDisplayTime.get(currentObject);
			if(data!=null)
				return (Integer)data * getDisplayTime.getIntMultiplier();
		}
		return -1;
	}
	public boolean isDisplayed(){
		return !isHidden();
	}
	public boolean isVerticallyFlipped(){
		if(isVerticallyFlipped==null)
			isVerticallyFlipped=currentHook.getFieldHook("isVerticallyFlipped");
		if(isVerticallyFlipped!=null){
			Object data = isVerticallyFlipped.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public boolean isHorizontallyFlipped(){
		if(isHorizontallyFlipped==null)
			isHorizontallyFlipped=currentHook.getFieldHook("isHorizontallyFlipped");
		if(isHorizontallyFlipped!=null){
			Object data = isHorizontallyFlipped.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public boolean isInventoryInterface(){
		if(isInventoryInterface==null)
			isInventoryInterface=currentHook.getFieldHook("isInventoryInterface");
		if(isInventoryInterface!=null){
			Object data = isInventoryInterface.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
}
