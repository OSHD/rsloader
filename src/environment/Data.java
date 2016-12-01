
package environment;


import gui.AppletFrame;
import injection.XteaInjector;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import reflection.ClassHook;
import reflection.FieldHook;
import rsloader.RSClassLoader;
import script.ScriptDef;

import java.applet.Applet;
import java.util.HashMap;

public class Data {
    public static HashMap<String, String> PARAMETERS = new HashMap<>();
    public static HashMap<String, ClassNode> clientClasses = new HashMap<>();
    public static Object clientBootClass = null;
    public static RSClassLoader LOADER;
    public static HashMap<String, ClassHook> runtimeClassHooks = new HashMap<String, ClassHook>();
	public static HashMap<String, FieldHook> staticFieldHooks = new HashMap<String, FieldHook>();
    public static Applet CLIENT_APPLET;
    public static long crcHash;
    public static ScriptDef currentScript;
    public static AppletFrame appletFrame;
    public static String BASE_LINK = "http://oldschool27.runescape.com/j1/";
    public static boolean hd = false;
    public static boolean debugFrame = true;
}
