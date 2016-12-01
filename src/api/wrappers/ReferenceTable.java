








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReferenceTable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook childIdentityTables;
	private static FieldHook entryIdentityTable;
	private static FieldHook childIdentifiers;
	private static FieldHook childIndices;
	private static FieldHook entryChildCounts;
	private static FieldHook childIndexCounts;
	private static FieldHook entryVersions;
	private static FieldHook entryIdentifiers;
	private static FieldHook entryIndices;
	private static FieldHook entryCRCs;
	private static FieldHook cRC;
	private static FieldHook revision;
	private static FieldHook entryCount;
	private static FieldHook entryIndexCount;
	public ReferenceTable(Object o){
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ReferenceTable");
			childIdentityTables = currentHook.getFieldHook("getChildIdentityTables");
			entryIdentityTable = currentHook.getFieldHook("getEntryIdentityTable");
			childIdentifiers = currentHook.getFieldHook("getChildIdentifiers");
			childIndices = currentHook.getFieldHook("getChildIndices");
			entryChildCounts = currentHook.getFieldHook("getEntryChildCounts");
			childIndexCounts = currentHook.getFieldHook("getChildIndexCounts");
			entryVersions = currentHook.getFieldHook("getEntryVersions");
			entryIdentifiers = currentHook.getFieldHook("getEntryIdentifiers");
			entryIndices = currentHook.getFieldHook("getEntryIndices");
			entryCRCs = currentHook.getFieldHook("getEntryCRCs");
			cRC = currentHook.getFieldHook("getCRC");
			revision = currentHook.getFieldHook("getRevision");
			entryCount = currentHook.getFieldHook("getEntryCount");
			entryIndexCount = currentHook.getFieldHook("getEntryIndexCount");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		childIdentityTables=null;
		entryIdentityTable=null;
		childIdentifiers=null;
		childIndices=null;
		entryChildCounts=null;
		childIndexCounts=null;
		entryVersions=null;
		entryIdentifiers=null;
		entryIndices=null;
		entryCRCs=null;
		cRC=null;
		revision=null;
		entryCount=null;
		entryIndexCount=null;
	}
	public int[] getChildIndexCounts(){
		if(childIndexCounts==null)
			childIndexCounts = currentHook.getFieldHook("getChildIndexCounts");
		if(childIndexCounts!=null){
			Object data = childIndexCounts.get(currentObject);
			if(data!=null){
				return (int[])data;
			}
		}
		return new int[]{};
	}
	public int[][] getChildIndices(){
		if(childIndices==null)
			childIndices = currentHook.getFieldHook("getChildIndices");
		if(childIndices!=null){
			Object data = childIndices.get(currentObject);
			if(data!=null){
				return (int[][])data;
			}
		}
		return new int[][]{};
	}
	public int[][] getChildIdentifiers(){
		if(childIdentifiers==null)
			childIdentifiers = currentHook.getFieldHook("getChildIdentifiers");
		if(childIdentifiers!=null){
			Object data = childIdentifiers.get(currentObject);
			if(data!=null){
				return (int[][])data;
			}
		}
		return new int[][]{};
	}
	public LookupTable[] getChildIdentityTables(){
		if(childIdentityTables==null)
			childIdentityTables = currentHook.getFieldHook("getChildIdentityTables");
		ArrayList<LookupTable> list = new ArrayList<LookupTable>();
		if(childIdentityTables!=null){
			Object data = childIdentityTables.get(currentObject);
			if(data!=null){
				for(int i=0;i< Array.getLength(data);++i){
					Object dat = Array.get(data, i);
					if(dat!=null)
						list.add(new LookupTable(dat));
				}
			}
		}
		return list.toArray(new LookupTable[]{});
	}
	public int getCRC(){
		if(cRC==null)
			cRC = currentHook.getFieldHook("getCRC");
		if(cRC!=null){
			Object data = cRC.get(currentObject);
			if(data!=null){
				return ((Integer)data) * cRC.getIntMultiplier();
			}
		}
		return -1;
	}
	public int[] getEntryChildCounts(){
		if(entryChildCounts==null)
			entryChildCounts = currentHook.getFieldHook("getEntryChildCounts");
		if(entryChildCounts!=null){
			Object data = entryChildCounts.get(currentObject);
			if(data!=null){
				return (int[])data;
			}
		}
		return new int[]{};
	}
	public int getEntryCount(){
		if(entryCount==null)
			entryCount = currentHook.getFieldHook("getEntryCount");
		if(entryCount!=null){
			Object data = entryCount.get(currentObject);
			if(data!=null){
				return ((Integer)data) * entryCount.getIntMultiplier();
			}
		}
		return -1;
	}
	public int[] getEntryCRCs(){
		if(entryCRCs==null)
			entryCRCs = currentHook.getFieldHook("getEntryCRCs");
		if(entryCRCs!=null){
			Object data = entryCRCs.get(currentObject);
			if(data!=null){
				return (int[])data;
			}
		}
		return new int[]{};
	}
	public int[] getEntryIndices(){
		if(entryIndices==null)
			entryIndices = currentHook.getFieldHook("getEntryIndices");
		if(entryIndices!=null){
			Object data = entryIndices.get(currentObject);
			if(data!=null){
				return (int[])data;
			}
		}
		return new int[]{};
	}
	public int[] getEntryIdentifiers(){
		if(entryIdentifiers==null)
			entryIdentifiers = currentHook.getFieldHook("getEntryIdentifiers");
		if(entryIdentifiers!=null){
			Object data = entryIdentifiers.get(currentObject);
			if(data!=null){
				return (int[])data;
			}
		}
		return new int[]{};
	}
	public LookupTable getEntryIdentityTable(){
		if(entryIdentityTable==null)
			entryIdentityTable = currentHook.getFieldHook("getEntryIdentityTable");
		if(entryIdentityTable!=null){
			Object data = entryIdentityTable.get(currentObject);
			if(data!=null){
				return new LookupTable(data);
			}
		}
		return null;
	}
	public int[] getEntryVersions(){
		if(entryVersions==null)
			entryVersions = currentHook.getFieldHook("getEntryVersions");
		if(entryVersions!=null){
			Object data = entryVersions.get(currentObject);
			if(data!=null){
				return (int[])data;
			}
		}
		return new int[]{};
	}
	public int getIndex(){
		if(entryIndexCount==null)
			entryIndexCount = currentHook.getFieldHook("getEntryIndexCount");
		if(entryIndexCount!=null){
			Object data = entryIndexCount.get(currentObject);
			if(data!=null){
				return ((Integer)data) * entryIndexCount.getIntMultiplier();
			}
		}
		return -1;
	}
	public int getRevision(){
		if(revision==null)
			revision = currentHook.getFieldHook("getRevision");
		if(revision!=null){
			Object data = revision.get(currentObject);
			if(data!=null){
				return ((Integer)data) * revision.getIntMultiplier();
			}
		}
		return -1;
	}
}
