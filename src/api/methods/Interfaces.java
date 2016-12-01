
package api.methods;

import api.wrappers.*;

import java.awt.*;
import java.util.Random;

public class Interfaces {
	public static boolean canContinue() {
		InterfaceChild ic = getContinueComponent(); 
		return ic != null && !ic.isHidden();
	}
	public static boolean clickContinue(){
		InterfaceChild button = getContinueComponent();
		if(button!=null)
			return button.doAction("Continue");
		return false;
	}
	public static Interface get(int index){
		Interface[] faces = getInterfaces();
		if(faces.length>index)
			return faces[index];
		return null;
	}
	public static InterfaceChild get(int index1, int index2){
		Interface i = get(index1);
		if(i!=null){
			if(i.getChildren().length>index2){
				return i.getChildren()[index2];
			}
		}
		return null;
	}
	public static InterfaceChild getChild(final int id) {
		final int y = id >> 0x10;
		final int x = id & 0xffff;
		return get(x, y);
	}
	public static InterfaceChild getContinueComponent(){
		InterfaceChild button = get(1184, 18);//NPC talking with our player
		if(button!=null)
			return button;
		button = get(1191, 18);//Our player talking with an NPC
		if(button!=null)
			return button;
		button = get(1186, 10);
		if(button!=null)
			return button;
		return null;
	}
	public static Interface[] getInterfaces(){
		Interface[] interfaces = Client.getInterfaceCache();
		for(int i=0;i<interfaces.length;++i){
			boolean[] valid = Client.getValidInterfaceArray();
			if(valid.length>i && !valid[i])
				interfaces[i]=null;
		}
		return interfaces;
	}
	public static boolean scrollTo(InterfaceChild component, InterfaceChild scrollBar) {
		if (component == null || scrollBar == null || scrollBar.getChildren().length!=6) {
			return false;
		}
		InterfaceChild scrollableArea = component;
		for (int i=0;i<10 && scrollableArea.getVerticalScrollbarSize() == 0
				&& scrollableArea.getParentID() != -1;++i) {
			scrollableArea = Interfaces.getChild(scrollableArea.getParentID());
		}
		if (scrollableArea.getVerticalScrollbarSize() == 0) {
			return false;
		}
		int areaY = scrollableArea.getAbsoluteY();
		int areaHeight = scrollableArea.getVerticalScrollbarThumbSize();
		if (component.getAbsoluteY() >= areaY
				&& component.getAbsoluteY() <= areaY + areaHeight
				- component.getVerticalScrollbarThumbSize()) {
			return true;
		}
		InterfaceChild scrollBarArea = scrollBar.getChildren()[0];
		int contentHeight = scrollableArea.getVerticalScrollbarSize();
		int pos = (int) ((float) scrollBarArea.getVerticalScrollbarThumbSize() / contentHeight * (component.getRelativeY() + new Random().nextInt(areaHeight / 2 - component.getVerticalScrollbarThumbSize())+-areaHeight / 2));
		if (pos < 0){
			pos = 0;
		} 
		else if (pos >= scrollBarArea.getVerticalScrollbarThumbSize()) {
			pos = scrollBarArea.getVerticalScrollbarThumbSize() - 1;
		}
		Point p = new Point(scrollBarArea.getAbsoluteX() + new Random().nextInt(scrollBarArea.getWidth()-2)+2, scrollBarArea.getAbsoluteY() + pos);
		Mouse.move(p);
		for(int i=0;i<3;++i){
			int curr = component.getAbsoluteY();
			Mouse.click();
			try {
				Thread.sleep(new Random().nextInt(150)+100);
			} catch (InterruptedException e) {
			}
			if(component.getAbsoluteY()!=curr)
				break;
		}
		try {
			Thread.sleep(new Random().nextInt(200)+200);
		} catch (InterruptedException e) {
		}
		if(component.getAbsoluteY() >= areaY && component.getAbsoluteY() <= areaY + areaHeight - component.getVerticalScrollbarThumbSize())
			return true;
		final boolean scrollUp = component.getAbsoluteY() < areaY;
		p = scrollBar.getChildren()[scrollUp ? 4 : 5].getRandomPoint();
		Mouse.move(p);
		try {
			Thread.sleep(new Random().nextInt(150));
		} catch (InterruptedException e) {
		}
		Mouse.press(Mouse.LEFT_BUTTON);
		for(int i=0;i<20;++i){
			if(component.getAbsoluteY() >= areaY && component.getAbsoluteY() <= areaY + areaHeight - component.getVerticalScrollbarThumbSize())
				break;
			int curr = component.getAbsoluteY();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			if(curr!=component.getAbsoluteY())
				i=0;
		}
		Mouse.release(Mouse.LEFT_BUTTON);
		return component.getAbsoluteY() >= areaY && component.getAbsoluteY() <= areaY + areaHeight - component.getVerticalScrollbarThumbSize();
	}
}
