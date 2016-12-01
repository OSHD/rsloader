package api.methods;

import api.wrappers.*;

public class Settings {
	public static int[] getAll(){
		Facade fac = Client.getFacade();
		if(fac!=null){
			api.wrappers.Settings sets = fac.getSettings();
			if(sets!=null)
				return sets.getData();
		}
		return new int[]{};
	}
	public static int get(int index){
		int[] all = getAll();
		if(all.length>index)
			return all[index];
		return -1;
	}
	public static int get(int index, int mask){
		return get(index) & mask;
	}
	public static int get(int index, int shift, int mask){
		return (get(index)>>shift)&mask;
	}
}
