package api.methods;


import api.wrappers.Interface;
import api.wrappers.InterfaceChild;
import api.wrappers.InterfaceItem;

import java.util.ArrayList;
import java.util.Random;


/**
 * Equipment related operations.
 */
public class Equipment{
	public static final int ITEM_SLOTS = 13;
	public static final int INTERFACE_EQUIPMENT = 387;
	public static final int HELMET = 7;
	public static final int CAPE = 10;
	public static final int NECK = 13;
	public static final int WEAPON = 16;
	public static final int BODY = 19;
	public static final int SHIELD = 22;
	public static final int LEGS = 25;
	public static final int HANDS = 28;
	public static final int FEET = 31;
	public static final int RING = 34;
	public static final int POCKET = 37;
	public static final int AMMO = 39;
	public static final int AURA = 48;
	public static final int[] childItemSlots = new int[]{HELMET, CAPE, NECK, WEAPON, BODY, SHIELD, LEGS, HANDS, FEET, RING, POCKET, AMMO, AURA};

	/**
	 * Gets the equipment interface.
	 *
	 * @return the equipment interface
	 */
	public static Interface getInterface() {
		if (Tabs.getCurrent() != Tabs.EQUIPMENT) {
			if (Bank.isOpen()) {
				Bank.close();
			}
			Mouse.move(Tabs.EQUIPMENT.getClickLocation());
			Mouse.click();
			try {
				Thread.sleep(new Random().nextInt(600)+900);
			} catch (InterruptedException e) {
			}
		}
		return Interfaces.get(INTERFACE_EQUIPMENT);
	}

	/**
	 * Gets the equipment array.
	 *
	 * @return An array containing all equipped items
	 */
	public static InterfaceItem[] getItems() {
		final InterfaceChild[] equip = getInterface().getChildren();
		ArrayList<InterfaceItem> items = new ArrayList<InterfaceItem>();
		for(int index : childItemSlots){
			InterfaceChild ic = equip[index];
			if(ic.getComponentID()!=-1)
				items.add(new InterfaceItem(ic));
		}
		return items.toArray(new InterfaceItem[]{});
	}
	/**
	 * Gets the cached equipment array (i.e. does not open the interface).
	 *
	 * @return The items equipped as seen when the equipment tab was last
	 *         opened.
	 */
	public static InterfaceItem[] getCachedItems() {
		final Interface equipment = Interfaces.get(INTERFACE_EQUIPMENT);
		final InterfaceChild[] equip = equipment.getChildren();
		ArrayList<InterfaceItem> items = new ArrayList<InterfaceItem>();
		for(int index : childItemSlots){
			InterfaceChild ic = equip[index];
			if(ic.getComponentID()!=-1)
				items.add(new InterfaceItem(ic));
		}
		return items.toArray(new InterfaceItem[]{});
	}

	/**
	 * Gets the equipment item at a given index.
	 *
	 * @param index The item index.
	 * @return The equipped item.
	 */
	public static InterfaceItem getItem(final int index) {
		return new InterfaceItem(getInterface().getChildren()[index]);
	}

	/**
	 * Returns the number of items equipped excluding stack sizes.
	 *
	 * @return Amount of items currently equipped.
	 */
	public static int getCount() {
		return ITEM_SLOTS - getCount(-1);
	}

	/**
	 * Returns the number of items matching a given ID equipped excluding stack
	 * sizes.
	 *
	 * @param itemID The item ID to count. Same as the equipment/item id in the
	 *               inventory.
	 * @return Amount of specified item currently equipped.
	 * @see #getItems()
	 */
	public static int getCount(final int itemID) {
		int count = 0;
		for (final InterfaceItem item : getItems()) {
			if (item.getID() == itemID) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Checks whether the player has all of the given items equipped.
	 *
	 * @param items The item ID to check for. Same as the equipment/item id in the
	 *              inventory.
	 * @return <tt>true</tt> if specified item is currently equipped; otherwise
	 *         <tt>false</tt>.
	 * @see #getItems()
	 */
	public static boolean containsAll(final int... items) {
		final InterfaceItem[] equips = getItems();
		int count = 0;
		for (final int item : items) {
			for (final InterfaceItem equip : equips) {
				if (equip.getID() == item) {
					count++;
					break;
				}
			}
		}
		return count == items.length;
	}

	/**
	 * Checks if the player has one (or more) of the given items equipped.
	 *
	 * @param items The IDs of items to check for.
	 * @return <tt>true</tt> if the player has one (or more) of the given items
	 *         equipped; otherwise <tt>false</tt>.
	 */
	public static boolean containsOneOf(final int... items) {
		for (final InterfaceItem item : getItems()) {
			for (final int id : items) {
				if (item.getID() == id) {
					return true;
				}
			}
		}
		return false;
	}
}
