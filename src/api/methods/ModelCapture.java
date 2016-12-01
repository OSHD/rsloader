package api.methods;

import api.wrappers.*;

public class ModelCapture {
	public static ModelLD lastCapture = null;
	//The static capture method
	public static CapturedModel captureModel(final Object data, final CapturedModel oldCap){//also made final to be sure I dont overwrite the instance
		//System.out.println(data+":"+oldCap);//------------------------------------------
		/*if(data!=null){
			//final ModelLD temp = new ModelLD(data);//Get model instance we want data from //here?
			if(oldCap==null){//If it hasnt been captured yet
				final CapturedModel captured = new CapturedModel(new ModelLD(data));//Capture the data and initialize the wrapper
				return captured;
			}
			else{
				final CapturedModel recaptured = oldCap; 
				recaptured.updateData();//otherwise update the current data
				return recaptured;
			}
		}*/
		return null;
	}
}
