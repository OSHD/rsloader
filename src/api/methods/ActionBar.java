package api.methods;


import api.wrappers.InterfaceChild;
import api.wrappers.InterfaceItem;

import java.awt.*;

/**
 * @author Spectrum/537065637472756d
 */
public class ActionBar {
	public static final int WIDGET_BAR = 640;
	public static final int WIDGET_CLOSED_BAR = 2;
	public static final int WIDGET_OPEN_TOGGLE = 3;
	public static final int WIDGET_OPEN_BAR = 4;
	public static final int WIDGET_CLOSE_TOGGLE = 30;
	public static final int WIDGET_TAB_INTERFACE = 22;
	public static final int WIDGET_TAB_PREVIOUS = 23;
	public static final int WIDGET_TAB_NEXT = 24;
	public static final int WIDGET_TAB_CURRENT = 25;
	public static final int WIDGET_LOCK = 26;
	public static final int WIDGET_SELECT_TARGET = 31;
	public static final int WIDGET_CURRENT_ADRENALINE = 13;
	public static final int SLOT_1 = 70;
	public static final int SLOT_2 = 75;
	public static final int SLOT_3 = 79;
	public static final int SLOT_4 = 83;
	public static final int SLOT_5 = 87;
	public static final int SLOT_6 = 91;
	public static final int SLOT_7 = 95;
	public static final int SLOT_8 = 99;
	public static final int SLOT_9 = 103;
	public static final int SLOT_0 = 107;
	public static final int SLOT_MINUS = 111;
	public static final int SLOT_EQUAL = 115;
	public static final int[] SLOTS = { SLOT_1, SLOT_2, SLOT_3, SLOT_4, SLOT_5,
			SLOT_6, SLOT_7, SLOT_8, SLOT_9, SLOT_0, SLOT_MINUS, SLOT_EQUAL };

	public static boolean isOpen() {
		InterfaceChild open = Interfaces.get(ActionBar.WIDGET_BAR, ActionBar.WIDGET_CLOSE_TOGGLE);
		if(open!=null){
			if(!open.isHidden())
				return true;
		}
		return false;
	}

	public static boolean open() {
		return (Interfaces.get(WIDGET_BAR, WIDGET_OPEN_TOGGLE) != null ? isOpen()
				|| Interfaces.get(WIDGET_BAR, WIDGET_OPEN_TOGGLE).doAction(
						"Expand") : false);
	}
	public static boolean close() {
		return (Interfaces.get(WIDGET_BAR, WIDGET_CLOSE_TOGGLE) != null ? !isOpen()
				|| Interfaces.get(WIDGET_BAR, WIDGET_CLOSE_TOGGLE).doAction(
						"Minimise") : false);
	}
	public static boolean isLocked() {
		return (Interfaces.get(WIDGET_BAR, WIDGET_LOCK) != null ? Interfaces.get(
				WIDGET_BAR, WIDGET_LOCK).getTextureID() == 14286 : false);

	}

	public static boolean lock() {
		if (isLocked())
			return true;
		return (isOpen() && Interfaces.get(WIDGET_BAR, WIDGET_LOCK) != null ? Interfaces
				.get(WIDGET_BAR, WIDGET_LOCK).doAction("Toggle Lock") : false);
	}

	public static boolean unlock() {
		if (!isLocked())
			return true;
		return (isOpen() && Interfaces.get(WIDGET_BAR, WIDGET_LOCK) != null ? Interfaces
				.get(WIDGET_BAR, WIDGET_LOCK).doAction("Toggle Lock") : false);
	}

	public static double adrenalinePercentage() {
		return Settings.get(679) / 10;
	}

	public static String getKeybind(int slot) {
		return (Interfaces.get(WIDGET_BAR, slot) != null ? Interfaces.get(WIDGET_BAR,
				slot).getText() : null);
	}

	public static void setKeybind(int slot, String key) {
		if (Interfaces.get(WIDGET_BAR, slot) != null) {
			Interfaces.get(WIDGET_BAR, slot).click();
			if (Menu.isOpen()) {
				if (Menu.click("Customise-keybind")) {
					try {
						Thread.sleep(750, 2000);
					} catch (InterruptedException e) {
					}
					Keyboard.sendKeys(key);
				}
			}
		}
	}

	public static boolean setSlot(final InterfaceItem item, final int slot) {
		if (item != null) {
			if (slot >= 0 && slot <= 27) {
				if (Inventory.getSelectedItem()!=null) {
					Inventory.getSelectedItem().click();
					return false;
				}
				InterfaceChild componentSlot = Interfaces.get(WIDGET_BAR, slot);
				if (componentSlot == null) {
					return false;
				}
				Rectangle componentRectangle = componentSlot.getBounds();
				Rectangle itemRectangle = item.getInterfaceChild().getBounds();
				if (componentRectangle.contains(itemRectangle)) {
					return true;
				}
				Mouse.move(item.getInterfaceChild().getRandomPoint());
				Point p = componentSlot.getRandomPoint();
				Mouse.drag(p.x, p.y);
				return true;
			}
		}
		return false;
	}

	public static void useSlot(int slot) {
		Keyboard.sendKeys(getKeybind(slot));
	}

	public static boolean clickSlot(int slot, boolean option) {
		InterfaceChild ic = Interfaces.get(WIDGET_BAR, slot);
		if(ic!=null){
			ic.click();
			return true;
		}
		return false;
	}

	public static boolean clickSlot(int slot, String action) {
		return (Interfaces.get(WIDGET_BAR, slot) != null ? Interfaces.get(WIDGET_BAR,
				slot).doAction(action) : false);
	}

	public static boolean clickSlot(String key, String action) {
		for (int i : SLOTS) {
			if (Interfaces.get(WIDGET_BAR, i) != null
					&& Interfaces.get(WIDGET_BAR, i).getText().equals(key)) {
				return Interfaces.get(WIDGET_BAR, i).doAction(action);
			}
		}
		return false;
	}

	public static int getTab() {
		return (Interfaces.get(WIDGET_BAR, WIDGET_TAB_CURRENT) != null ? Integer
				.parseInt(Interfaces.get(WIDGET_BAR, WIDGET_TAB_CURRENT).getText())
				: -1);
	}

	public static boolean nextTab() {
		return (Interfaces.get(WIDGET_BAR, WIDGET_TAB_NEXT) != null ? Interfaces.get(
				WIDGET_BAR, WIDGET_TAB_NEXT).doAction("Next") : false);
	}

	public static boolean previousTab() {
		return (Interfaces.get(WIDGET_BAR, WIDGET_TAB_PREVIOUS) != null ? Interfaces
				.get(WIDGET_BAR, WIDGET_TAB_PREVIOUS).doAction("Previous")
				: false);
	}
}