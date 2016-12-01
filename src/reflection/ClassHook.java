
package reflection;


import environment.Data;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ClassHook {
    private ClassNode bytecodeClass;
    private Class<?> reflectedClass;
    private String refactoredName = "";
    private HashMap<String, FieldHook> fieldHooks = new HashMap<>();

    public ClassHook(String name, ClassNode cn) {
        refactoredName = name;
        bytecodeClass = cn;
    }

    public boolean addFieldHook(FieldHook fh) {
        return fieldHooks.put(fh.getRefactoredName(), fh) != null;
    }

    public FieldHook[] getAllFieldHooks() {
        return fieldHooks.values().toArray(new FieldHook[]{});
    }

    public String getClassName() {
        return reflectedClass.getName();
    }

    public FieldHook getFieldHook(String refName) {
        return fieldHooks.get(refName);
    }

    public String getRefactoredName() {
        return refactoredName;
    }

    public void loadRuntime() {
        try {
            reflectedClass = Data.LOADER.loadClass(bytecodeClass.name);
            if (reflectedClass == null) {
                System.out.println("ClassLoader failed to load class : " + bytecodeClass.name);
                return;
            }
            System.out.println("Loaded runtime class hook : " + refactoredName);
            System.out.println("Loading " + fieldHooks.size() + " field hooks...");
            HashMap<String, FieldHook> staticFields = new HashMap<>();
            for (FieldHook fh : fieldHooks.values()) {
                for (Field f : reflectedClass.getDeclaredFields()) {
                    if (f.getName().equals(fh.getName())) {
                        fh.setHook(f);
                        if (fh.getBytecodeField().access == Opcodes.ACC_STATIC) {
                            staticFields.put(fh.getRefactoredName(), fh);
                            System.out.println("Loaded runtime static hook : " + fh.getRefactoredName());
                        } else
                            System.out.println("Loaded runtime field hook : " + fh.getRefactoredName());
                    }
                }
            }
            for (FieldHook fh : staticFields.values()) {
                Data.staticFieldHooks.put(fh.getRefactoredName(), fh);
                fieldHooks.remove(fh.getRefactoredName());
            }
        } catch (Exception e) {
            System.out.println("Failed to load runtime class hook : " + refactoredName);
            e.printStackTrace();
        }
    }
}
