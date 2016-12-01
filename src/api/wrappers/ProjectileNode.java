








package api.wrappers;

import api.methods.Calculations;
import api.methods.Client;
import api.methods.Menu;
import api.methods.Nodes;
import environment.Data;
import reflection.ClassHook;
import reflection.FieldHook;

public class ProjectileNode extends Animable{
	public Object currentObject;
	public static ClassHook currentHook;
	private static FieldHook projectile;
	public ProjectileNode(Object o){
		super(o);
		currentObject=o;
		if(currentHook==null){
			currentHook = Data.runtimeClassHooks.get("ProjectileNode");
			projectile = currentHook.getFieldHook("getProjectile");
		}
	}
	public static void resetHooks(){
		currentHook=null;
		projectile=null;
	}
	public Projectile getProjectile(){
		if(projectile==null)
			projectile = currentHook.getFieldHook("getProjectile");
		if(projectile!=null){
			Object data = projectile.get(currentObject);
			if(data!=null)
				return new Projectile(data);
		}
		return null;
	}
}
