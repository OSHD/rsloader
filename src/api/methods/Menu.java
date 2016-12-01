








package api.methods;

import api.wrappers.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Menu {
	public static boolean click(String action){
		int index = getIndex(action);
		if(index!=-1)
			return clickMain(getItems(), index);
		return false;
	}
	public static boolean click(String action, String option){
		int index = getIndex(action, option);
		if(index!=-1)
			return clickMain(getItems(), index);
		return false;
	}
	public static boolean clickIndex(int i) {
		if (!isOpen()) {
			return false;
		}
		String[] items = getItems();
		if (items.length <= i) {
			return false;
		}
		return clickMain(items, i);
	}
	public static boolean clickMain(String[] items, int i) {
		try{
			if(items.length<i || !isOpen())
				return false;
			Point menu = getLocation();
			int xOff = new Random().nextInt(getWidth());
			int yOff = 21 + 16 * i + new Random().nextInt(15);
			Mouse.move(new Point(menu.x + xOff, menu.y + yOff));
			if (isOpen()) {
				Mouse.click();
				return true;
			}
		}
		catch(Exception e){}
		return false;
	}
	public static boolean clickSub(String[] items, int mIdx, int sIdx) {
		Point menuLoc = getLocation();
		int x = new Random().nextInt((items[mIdx].length() * 4)-4)+4;
		int y = 21 + 16 * mIdx + new Random().nextInt(9)+3;
		Mouse.move(menuLoc.x + x, menuLoc.y + y);
		try {
			Thread.sleep(new Random().nextInt(25)+125);
		} catch (Exception e) {
		}
		if (isOpen()) {
			//TODO Not yet finished
		}
		return false;
	}
	public static boolean contains(String action){
		return getIndex(action)!=-1;
	}
	public static boolean contains(String action, String option){
		return getIndex(action, option)!=-1;
	}
	public static String[] getActions(){
		NodeList items = Client.getMenuItems();
		ArrayList<String> options = new ArrayList<String>();
		Node tail = items.getTail();
		for(Node curr = tail.getPrevious();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getPrevious()){
			if(curr instanceof MenuItemNode){
				MenuItemNode item = (MenuItemNode) curr;
				options.add(item.getAction());
			}
		}
		return options.toArray(new String[]{});
	}
	public static int getHeight(){
		return Client.getMenuHeight();
	}
	public static int getIndex(String string){
		String[] items = getItems();
		for(int i=0;i<items.length;++i)
			if(items[i].contains(string))
				return i;
		return -1;
	}
	public static int getIndex(String action, String option){
		if(!isOpen())
			return -1;
		String[] actions = getActions();
		String[] options = getOptions();
		for(int i=0;i<Math.min(actions.length, options.length);++i)
			if(actions[i].contains(action) && options[i].contains(option))
				return i;
		return -1;
	}
	public static String[] getItems(){
		NodeList items = Client.getMenuItems();
		ArrayList<String> options = new ArrayList<String>();
		Node tail = items.getTail();
		for(Node curr = tail.getPrevious();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getPrevious()){
			if(curr instanceof MenuItemNode){
				MenuItemNode item = (MenuItemNode) curr;
				options.add(item.getAction()+" "+item.getOption());
			}
		}
		return options.toArray(new String[]{});
	}
	public static int getLength(){
		return getItems().length;
	}
	public static Point getLocation(){
		if(isOpen())
			return new Point(Client.getMenuX(), Client.getMenuY());
		else return Mouse.getLocation();
	}
	public static String[] getOptions(){
		NodeList items = Client.getMenuItems();
		ArrayList<String> options = new ArrayList<String>();
		Node tail = items.getTail();
		for(Node curr = tail.getPrevious();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getPrevious()){
			if(curr instanceof MenuItemNode){
				MenuItemNode item = (MenuItemNode) curr;
				options.add(item.getOption());
			}
		}
		return options.toArray(new String[]{});
	}
	public static int getSubHeight(){
		return Client.getSubMenuHeight();
	}
	public static String[] getSubItems(){
		NodeSubQueue items = Client.getCollapsedMenuItems();
		ArrayList<String> options = new ArrayList<String>();
		Node tail = items.getTail();
		for(Node curr = tail.getNext();curr!=null && !curr.currentObject.equals(tail.currentObject);curr=curr.getNext()){
			if(curr instanceof MenuItemNode){
				MenuItemNode item = (MenuItemNode) curr;
				options.add(item.getAction()+" "+item.getOption());
			}
		}
		return options.toArray(new String[]{});
	}
	public static Point getSubLocation(){
		return new Point(Client.getSubMenuX(), Client.getSubMenuY());
	}
	public static int getSubWidth(){
		return Client.getSubMenuWidth();
	}
	public static int getWidth(){
		return Client.getMenuWidth();
	}
	public static boolean isCollapsed(){
		return Client.isMenuCollapsed();
	}
	public static boolean isOpen(){
		return Client.isMenuOpen();
	}
}
