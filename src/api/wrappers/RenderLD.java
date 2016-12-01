
 
 
 
 
 
 
 
 
package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class RenderLD extends Render{
	public Object currentObject;
	public static ClassHook currentHook;
	private static float absoluteX=(float) 260.0;
	private static float absoluteY=(float) 171.0;
	private static float xMultiplier=(float) 256.0;
	private static float yMultiplier=(float) 167.0;
	private static FieldHook viewport;
	public RenderLD(Object o){
		super(o);
		currentObject = o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("RenderLD");
			viewport = currentHook.getFieldHook("getViewport");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		viewport=null;
	}
	public float getAbsoluteX(){
		return absoluteX;
	}
	public float getAbsoluteY(){
		return absoluteY;
	}
	public float getXMultiplier(){
		return xMultiplier;
	}
	public float getYMultiplier(){
		return yMultiplier;
	}
	public Viewport getViewport(){
		if(viewport==null)
			viewport = currentHook.getFieldHook("getViewport");
		if(viewport!=null){
			Object data = viewport.get(currentObject);
			if(data!=null)
				return new Viewport(data);
		}
		return null;
	}
}
