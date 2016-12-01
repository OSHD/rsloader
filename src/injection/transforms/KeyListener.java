
package injection.transforms;


import environment.Data;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

public class KeyListener {
	public static ClassNode transformClass(ClassNode cn){
		if(cn.access==49){
            for(MethodNode mn : cn.methods){
            	if(mn.name.startsWith("key") || mn.name.startsWith("focus")){
            		ClassNode superClass = Data.clientClasses.get(cn.superName);
            		if(superClass!=null && !superClass.superName.equals("injection/wrappers/KeyListener")){
            			superClass.superName="injection/wrappers/KeyListener";
            			//System.out.println("Changed superclass of : "+superClass.name+" = "+superClass.superName);
                        ListIterator<MethodNode> mli = superClass.methods.listIterator();
                        while (mli.hasNext()) {
                            MethodNode node = mli.next();
                            if (node.name.equals("<init>")) {
                                ListIterator<AbstractInsnNode> ili = node.instructions.iterator();
                                while (ili.hasNext()) {
                                    AbstractInsnNode ain = ili.next();
                                    if (ain.getOpcode() == Opcodes.INVOKESPECIAL) {
                                        MethodInsnNode min = (MethodInsnNode) ain;
                                        if(min.owner.equals("java/lang/Object")){
                                        	//System.out.println("Changed initialization owner.");
	                                        min.owner = "injection/wrappers/KeyListener";
	                                        break;
                                        }
                                    }
                                }
                                break;
                            }
                        }
            		}
            		mn.name="_"+mn.name;
            		//System.out.println("Hooked method : "+mn.name);
            	}
            }
		}
		return cn;
	}
}
