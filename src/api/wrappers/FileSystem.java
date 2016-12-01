








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.awt.*;

public class FileSystem {
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook referenceTable;
	private static FieldHook fileWorker;
	private static FieldHook entryBuffers;
	private static FieldHook childBuffers;
	private static FieldHook discardUnpacked;
	private static FieldHook discardEntryBuffers;
	public FileSystem(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("FileSystem");
			referenceTable=currentHook.getFieldHook("getReferenceTable");
			fileWorker=currentHook.getFieldHook("getFileWorker");
			entryBuffers=currentHook.getFieldHook("getEntryBuffers");
			childBuffers=currentHook.getFieldHook("getChildBuffers");
			discardUnpacked=currentHook.getFieldHook("getDiscardUnpacked");
			discardEntryBuffers=currentHook.getFieldHook("getDiscardEntryBuffers");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		referenceTable=null;
		fileWorker=null;
		entryBuffers=null;
		childBuffers=null;
		discardUnpacked=null;
		discardEntryBuffers=null;
	}
	public Object[][] getChildBuffers(){
		if(childBuffers==null)
			childBuffers=currentHook.getFieldHook("getChildBuffers");
		if(childBuffers!=null){
			Object data = childBuffers.get(currentObject);
			if(data!=null)
				return (Object[][])data;
		}
		return new Object[][]{};
	}
	public boolean getDiscardEntryBuffers(){
		if(discardEntryBuffers==null)
			discardEntryBuffers=currentHook.getFieldHook("getDiscardEntryBuffers");
		if(discardEntryBuffers!=null){
			Object data = discardEntryBuffers.get(currentObject);
			if(data!=null)
				return (Boolean)data;
		}
		return false;
	}
	public int getDiscardUnpacked(){
		if(discardUnpacked==null)
			discardUnpacked=currentHook.getFieldHook("getDiscardUnpacked");
		if(discardUnpacked!=null){
			Object data = discardUnpacked.get(currentObject);
			if(data!=null)
				return (Integer)data * discardUnpacked.getIntMultiplier();
		}
		return -1;
	}
	public Object[] getEntryBuffers(){
		if(entryBuffers==null)
			entryBuffers=currentHook.getFieldHook("getEntryBuffers");
		if(entryBuffers!=null){
			Object data = entryBuffers.get(currentObject);
			if(data!=null)
				return (Object[])data;
		}
		return new Object[]{};
	}
	public FileWorker getFileWorker(){
		if(fileWorker==null)
			fileWorker=currentHook.getFieldHook("getFileWorker");
		if(fileWorker!=null){
			Object data = fileWorker.get(currentObject);
			if(data!=null)
				return new FileWorker(data);
		}
		return null;
	}
	public ReferenceTable getReferenceTable(){
		if(referenceTable==null)
			referenceTable=currentHook.getFieldHook("getReferenceTable");
		if(referenceTable!=null){
			Object data = referenceTable.get(currentObject);
			if(data!=null)
				return new ReferenceTable(data);
		}
		return null;
	}
}
