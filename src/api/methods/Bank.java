








package api.methods;

import api.wrappers.GameObject;
import api.wrappers.Interface;
import api.wrappers.InterfaceChild;
import api.wrappers.InterfaceItem;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Bank {
	public static int BANK_INTERFACE_ID = 762;
	public static int BANK_CLOSE_BUTTON_ID = 45;
	public static int BANK_SLOTS_INTERFACE_ID = 95;
	public static int BANK_DEPOSIT_INVENTORY_BUTTON_ID = 34;
	public static int BANK_DEPOSIT_EQUIPMENT_BUTTON_ID = 36;
	public static int BANK_DEPOSIT_BEAST_BUTTON_ID = 38;
	public static int BANK_SCROLLBAR = 116;
	public static final int[] BANKER_IDS = new int[]{
		44, 45, 166, 494, 495, 496, 497, 498, 499, 553, 909, 953, 958, 1036, 1360, 1702, 2163, 2164, 2354, 2355,
		2568, 2569, 2570, 2718, 2759, 3046, 3198, 3199, 3293, 3416, 3418, 3824, 4456, 4457, 4458, 4459, 4519, 4907,
		5257, 5258, 5259, 5260, 5488, 5776, 5777, 5901, 6200, 6362, 7049, 7050, 7605, 8948, 9710, 13932, 14923, 14924,
		14925, 15194
	};
	public static final int[] BANK_BOOTH_IDS = new int[]{
		782, 2012, 2015, 2019, 2213, 3045, 5276, 6084, 10517, 11338, 11758, 12759, 12798, 12799, 12800, 12801, 14369, 14370,
		16700, 19230, 20325, 20326, 20327, 20328, 22819, 24914, 25808, 26972, 29085, 34205, 34752, 35647,
		35648, 36262, 36786, 37474, 49018, 49019, 52397, 52589
	};
	public static final int[] BANK_CHEST_IDS = new int[]{
		2693, 4483, 8981, 12308, 14382, 20607, 21301, 27663, 42192, 57437, 62691
	};
	public static int BANK_TAB_SETTING = 110;
	public static int WITHDRAW_MODE_SETTING = 160;
	public static boolean close(){
		InterfaceChild ic = Interfaces.get(BANK_INTERFACE_ID, BANK_CLOSE_BUTTON_ID);
		if(ic!=null){
			ic.click();
			for(int i=0;i<20;++i){
				if(!isOpen())
					return true;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
		return false;
	}
	public static boolean depositInventory(){
		if(isOpen()){
			int invCount = Inventory.getCount();
			if(invCount>0){
				InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_INVENTORY_BUTTON_ID];
				button.click();
				for(int i=0;i<20;++i){
					if(invCount>Inventory.getCount())
						return true;
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
					}
				}
				return false;
			}
		}
		return false;
	}
	public static boolean depositItem(String name, int amount){
		InterfaceItem item = Inventory.getItemByName(name);
		if(item!=null)
			return depositItem(item.getID(), amount);
		return false;
	}
	public static boolean depositItem(int id, int amount){
		InterfaceItem item = Inventory.getItemByID(id);
		if(item!=null){
			String action = "";
			if(Inventory.getCount(id)>1 || item.getStackSize()>1){
				if(amount==0)
					action="Deposit-All";
				else if(amount==5 || amount==10)
					action="Deposit-"+amount;
				else{
					Point p = item.getInterfaceChild().getRandomPoint();
					Mouse.move(p);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					Mouse.rightClick();
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
					}
					if(!Menu.isOpen())
						return false;
					if(Menu.contains("Deposit-"+amount))
						return Menu.click("Deposit-"+amount);
					else{
						if(!Menu.click("Deposit-X"))
							return false;
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
					}
					Keyboard.sendKeys(""+amount);
					Keyboard.sendKey((char) KeyEvent.VK_ENTER);
					return true;
				}
			}
			else
				action="Deposit";
			return item.doAction(action);
		}
		return false;
	}
	public static boolean depositAllExcept(int... items) {
		if (isOpen()) {
			boolean deposit = true;
			int invCount = Inventory.getCount();
			outer: for (int i = 0; i < 28; i++) {
				InterfaceItem itemToDisplay=Inventory.getItemAt(i);
				if(itemToDisplay==null)return false;
				InterfaceChild item = itemToDisplay.getInterfaceChild();
				if (item != null && item.getComponentID() != -1) {
					for (int id : items) {
						if (item.getComponentID() == id) {
							continue outer;
						}
					}
					for (int tries = 0; tries < 5; tries++) {
						depositItem(item.getComponentID(), 0);
						for(int k=0;k<20;++k){
							try {
								Thread.sleep(new Random().nextInt(100)+50);
							} catch (Exception e) {
							}
							int cInvCount = Inventory.getCount();
							if (cInvCount < invCount) {
								invCount = cInvCount;
								continue outer;
							}
						}
					}
					deposit = false;
				}
			}
			return deposit;
		}
		return false;
	}
	public static boolean depositEquipment(){
		if(isOpen()){
			InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_EQUIPMENT_BUTTON_ID];
			button.click();
			return true;
		}
		return false;
	}
	public static boolean depositFamiliar(){
		if(isOpen()){
			InterfaceChild button = Client.getInterfaceCache()[BANK_INTERFACE_ID].getChildren()[BANK_DEPOSIT_BEAST_BUTTON_ID];
			button.click();
			return true;
		}
		return false;
	}
	public static boolean isOpen(){
		try{
			return Client.getInterfaceCache()[BANK_INTERFACE_ID]!=null;
		}
		catch(Exception e){
			
		}
		return getCurrentTab()>=0;
	}
	public static boolean isWithdrawNotedEnabled() {
		return Settings.get(WITHDRAW_MODE_SETTING) == 1;
	}
	public static int getCurrentTab(){
		return ((Settings.get(BANK_TAB_SETTING) >>> 24) - 136) / 8;
	}
	public static InterfaceItem getItem(int id){
		for(InterfaceItem item : getItems())
			if(item.getID()==id)
				return item;
		return null;
	}
	public static InterfaceItem[] getItems(){
		ArrayList<InterfaceItem> bankItems = new ArrayList<InterfaceItem>();
		Interface bank = Client.getInterfaceCache()[BANK_INTERFACE_ID];
		if(bank!=null){
			InterfaceChild slots = bank.getChildren()[BANK_SLOTS_INTERFACE_ID];
			if(slots!=null){
				for(InterfaceChild ic : slots.getChildren()){
					if(ic.getComponentID()!=-1)
						bankItems.add(new InterfaceItem(ic));
				}
			}
		}
		return bankItems.toArray(new InterfaceItem[]{});
	}
	public static int getItemCount(int id){
		InterfaceItem item = getItem(id);
		if(item!=null)
			return item.getStackSize();
		return 0;
	}
	public static boolean open(){
		GameObject object = Objects.getNearestObjectByID(BANK_BOOTH_IDS);
		if(object!=null){
			if(object.isOnScreen()){
				if(!object.doAction("Bank")){
					return false;
				}
				for(int i=0;i<20;++i){
					if(Bank.isOpen()){
						return true;
					}
					try {
						Thread.sleep(new Random().nextInt(100)+100);
					} catch (Exception e) {
					}
				}
				return Bank.isOpen();
			}
		}
		object=Objects.getNearestObjectByID(BANK_CHEST_IDS);
		if(object!=null){
			if(object.isOnScreen()){
				if(!object.doAction("Use")){//Check for top action
					return false;
				}
				for(int i=0;i<20;++i){
					if(Bank.isOpen()){
						return true;
					}
					try {
						Thread.sleep(new Random().nextInt(100)+100);
					} catch (Exception e) {
					}
				}
				return Bank.isOpen();
			}
		}
		/*NPC npcToDisplay=NPCs.getNearest(BANKER_IDS);
		if(npcToDisplay!=null){
			System.out.println("[Bank:open] Banker found.");
			if(npcToDisplay.isOnScreen()){
				if(!npcToDisplay.clickTile()){//doAction
					System.out.println("[Bank:open] Failed to click banker tile.");
					npcToDisplay=null;
					return false;
				}
				for(int i=0;i<20;++i){
					if(Bank.isOpen()){
						System.out.println("[Bank:open] Bank opened.");
						npcToDisplay=null;
						return true;
					}
					try {
						Thread.sleep(new Random().nextInt(100)+100);
					} catch (Exception e) {
					}
				}
				npcToDisplay=null;
				return true;
			}
			System.out.println("[Bank:open] Banker not on screen.");
			npcToDisplay=null;
		}*/
		return false;
	}
	public static boolean withdraw(int id, int amount){
		InterfaceItem item = getItem(id);
		if(item!=null){
			if(item.getInterfaceChild().getAbsoluteY()<312 && item.getInterfaceChild().getAbsoluteY()>140){
				if(amount==1){
					item.click();
					return true;
				}
				String action = "";
				if(amount==0)
					action="Withdraw-All";
				else if(amount==5 || amount==10)
					action="Withdraw-"+amount;
				else{
					Point p = item.getInterfaceChild().getRandomPoint();
					Mouse.move(p);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
					Mouse.rightClick();
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
					}
					if(!Menu.isOpen())
						return false;
					if(Menu.contains("Withdraw-"+amount))
						return Menu.click("Withdraw-"+amount);
					else{
						if(!Menu.click("Withdraw-X"))
							return false;
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
					}
					Keyboard.sendKeys(""+amount);
					Keyboard.sendKey((char) KeyEvent.VK_ENTER);
					return true;
				}
				int invCount = Inventory.getCount();
				item.doAction(action);
				for(int i=0;i<20;++i){
					try {
						if(Inventory.getCount()>invCount)
							return true;
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
			}
			else{
				InterfaceChild scrollBar=Interfaces.get(BANK_INTERFACE_ID, BANK_SCROLLBAR);
				if(scrollBar==null)
					return false;
				if(!Interfaces.scrollTo(item.getInterfaceChild(), scrollBar))
					return false;
				return withdraw(id, amount);
			}
		}
		return false;
	}
}
