package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class InterfaceItem {
	private String itemName;
	private int itemID;
	private int itemStackSize;
	private InterfaceChild itemInterface;
	public InterfaceItem(InterfaceChild ic){
		itemName = ic.getComponentName();
		itemID = ic.getComponentID();
		itemStackSize = ic.getComponentStackSize();
		itemInterface = ic;
	}
	public int getID(){
		return itemID;
	}
	public int getStackSize(){
		return itemStackSize;
	}
	public String getName(){
		return itemName;
	}
	public InterfaceChild getInterfaceChild(){
		return itemInterface;
	}
	public int getIndex(){
		return itemInterface.index;
	}
	public boolean isSelected(){
		return itemInterface.getBorderThickness()==2;
	}
	public ItemDef getItemDef(){
		try{
			Node ref = Nodes.lookup(Client.getItemDefLoader().getDefCache().getTable(), getID());
			if(ref==null)
				return null;
			if (SoftReference.isInstance(ref.currentObject)){
				SoftReference sr = new SoftReference(ref.currentObject);
				Object def = sr.getSoftReference().get();
				return new ItemDef(def);
			}
			else if (HardReference.isInstance(ref.currentObject)) {
				HardReference hr = new HardReference(ref.currentObject);
				Object def = hr.getHardReference();
				return new ItemDef(def);
			}
		}
		catch(Exception e){}
		return null;
	}
	public void click(){
		itemInterface.click();
	}
	public boolean doAction(String action){
		return itemInterface.doAction(action);
	}
}
