
package injection.transforms;


import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

public class Canvas {
    public static ClassNode transformClass(ClassNode cn) {
        if (cn.superName.equals("java/awt/Canvas")) {
            System.out.println("Injected canvas: " + cn.name);
            cn.superName = "injection/wrappers/Canvas";
            for (MethodNode mn : cn.methods) {
                if (mn.name.equals("<init>")) {
                    ListIterator<AbstractInsnNode> ili = mn.instructions.iterator();
                    while (ili.hasNext()) {
                        AbstractInsnNode ain = ili.next();
                        if (ain.getOpcode() == Opcodes.INVOKESPECIAL) {
                            MethodInsnNode min = (MethodInsnNode) ain;
                            min.owner = "injection/wrappers/Canvas";
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return cn;
    }
}
