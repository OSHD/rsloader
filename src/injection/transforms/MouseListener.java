
package injection.transforms;


import environment.Data;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

public class MouseListener {
	public static ClassNode transformClass(ClassNode cn){
        for(MethodNode mn : cn.methods){
            if(mn.name.startsWith("mouse")){
                ClassNode superClass = Data.clientClasses.get(cn.superName);
                if(superClass!=null && !superClass.superName.equals("injection/wrappers/MouseListener")){
                    superClass.superName="injection/wrappers/MouseListener";
                    System.out.println("Changed superclass of : "+superClass.name+" = "+superClass.superName);
                    for (MethodNode node : superClass.methods) {
                        if (node.name.equals("<init>")) {
                            ListIterator<AbstractInsnNode> ili = node.instructions.iterator();
                            while (ili.hasNext()) {
                                AbstractInsnNode ain = ili.next();
                                if (ain.getOpcode() == Opcodes.INVOKESPECIAL) {
                                    MethodInsnNode min = (MethodInsnNode) ain;
                                    if (min.owner.equals("java/lang/Object")) {
                                        System.out.println("Changed initialization owner.");
                                        min.owner = "injection/wrappers/MouseListener";
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                mn.name="_"+mn.name;
                System.out.println("Hooked method : "+mn.name);
            }
        }
		return cn;
	}
}
