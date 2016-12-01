
package api.methods;

import api.wrappers.*;
import  environment.Data;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * 
 * @author  Steven10172
 * @notes   Open() won't click the correct spot if not using Fixed
 *          as the AbsoluteLocation Settings are incorrect
 *          
 *          If using Re-sizable setting user interface 746
 *
 *          findParentInferface() needs to be updated to read the
 *          current client display settings
 *
 */
public enum Tabs {
    COMBAT(116, "Combat Styles", KeyEvent.VK_F5),
    TASK_LIST(117, "Task List"),
    STATS(118, "Stats"),
    INVENTORY(120, "Inventory", KeyEvent.VK_F1),
    EQUIPMENT(121, "Worn Equipment", KeyEvent.VK_F2),
    PRAYER(122, "Prayer List", KeyEvent.VK_F3),
    MAGIC(123, "Magic Spellbook", KeyEvent.VK_F4),
	EXTRAS(84, "Extras"),
	FRIENDS_LIST(85, "Friends List"),
	FRIENDS_CHAT(86, "Friends Chat"),
	CLAN_CHAT(87, "Clan Chat"),
	OPTIONS(88, "Options"),
	EMOTES(89, "Emotes"),
	MUSIC(90, "Music Play"),
	NOTES(91, "Notes");

	private String description;
	private int fnKey;
	private int interfaceID;
	private static int parentInterface;

	Tabs(int interfaceID, String description) {
		this(interfaceID, description, -1);
	}

	Tabs(int interfaceID, String description, int fnKey) {
		this.description = description;
		this.fnKey = fnKey;
		this.interfaceID = interfaceID;
	}

	public String getDescription() {
		return description;
	}

	public int getFnKey() {
		return fnKey;
	}

	public int getInterfaceID() {
		return interfaceID;
	}

	public boolean hasFnKey() {
		return (fnKey != -1);
	}

	public boolean isOpen() {
		return (this == getCurrent());
	}

	public int getParentInterface() {
		return parentInterface;
	}

	private static int findParentInferface() {
		//return 746; // Resizeable client is being used
		return 548; // Fixed client is being used
	}

	public Point getClickLocation() {
		int height = Client.getInterfaceCache()[parentInterface].getChildren()[getInterfaceID()].getHeight();
		int width = Client.getInterfaceCache()[parentInterface].getChildren()[getInterfaceID()].getWidth();
		int absLocX = Client.getInterfaceCache()[parentInterface].getChildren()[getInterfaceID()].getAbsoluteX();
		int absLocY = Client.getInterfaceCache()[parentInterface].getChildren()[getInterfaceID()].getAbsoluteY();
		Random rand = new Random();

		return new Point(absLocX + rand.nextInt(width),
				absLocY + rand.nextInt(height));
	}

	public boolean open() {
		return open(false);
	}

	public boolean open(boolean useFnKey) {
		if(!isOpen()) {
			if(useFnKey && hasFnKey()) { //Use the Function Key
				Component keyboardTarget = Data.CLIENT_APPLET.getComponent(0);
				KeyEvent event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, getFnKey(), KeyEvent.CHAR_UNDEFINED);
				keyboardTarget.dispatchEvent(event); 
				event = new KeyEvent(keyboardTarget, KeyEvent.KEY_PRESSED, 0, 0, getFnKey(), KeyEvent.CHAR_UNDEFINED);
				keyboardTarget.dispatchEvent(event);
			} else { //Use the Mouse
				Mouse.move(getClickLocation());
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
				Mouse.click();
			}
		}
		return isOpen();
	}

	public static Tabs getCurrent() {
		parentInterface = findParentInferface();
		Interface tabsInterface = Client.getInterfaceCache()[parentInterface];
		if(tabsInterface != null && tabsInterface.getChildren().length > 0){
			InterfaceChild[] tabsChild = tabsInterface.getChildren();
			for(Tabs t : Tabs.values()) {
				if(tabsChild[t.getInterfaceID()].getTextureID() != -1) {
					return t;
				}
			}
		}
		return null;
	}
}