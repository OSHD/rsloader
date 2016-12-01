
package api.methods;

import  environment.Data;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Keyboard {	
	public static int getLocation(final char ch) {
		if (ch >= KeyEvent.VK_SHIFT && ch <= KeyEvent.VK_ALT) {
			return new Random().nextInt((KeyEvent.KEY_LOCATION_RIGHT + 1)-KeyEvent.KEY_LOCATION_LEFT)+KeyEvent.KEY_LOCATION_LEFT;
		}
		return KeyEvent.KEY_LOCATION_STANDARD;
	}
	public static void pressKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = Data.CLIENT_APPLET.getComponent(0);
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, Keyboard.getLocation(s));
		keyboardTarget.dispatchEvent(event); 
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
		keyboardTarget.dispatchEvent(event);
	}
	public static void releaseKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = Data.CLIENT_APPLET.getComponent(0);
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, Keyboard.getLocation(s));
		keyboardTarget.dispatchEvent(event);
	}
	public static void sendKey(char s){
		int code = s;
		if (s >= 'a' && s <= 'z') {
			code -= 32;
		}
		Component keyboardTarget = Data.CLIENT_APPLET.getComponent(0);
		KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, code, s, Keyboard.getLocation(s));
		keyboardTarget.dispatchEvent(event); 
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_TYPED, 0, 0, KeyEvent.VK_UNDEFINED, s, 0);
		keyboardTarget.dispatchEvent(event);
		event = new KeyEvent(keyboardTarget, KeyEvent.KEY_RELEASED, 0, 0, code, s, Keyboard.getLocation(s));
		keyboardTarget.dispatchEvent(event);
	}
	public static void sendKeys(String str){
		for(int i=0;i<str.length();++i){
			sendKey(str.charAt(i));
			try{
				Thread.sleep(new Random().nextInt(100));
			}
			catch(Exception e){}
		}
	}
}
