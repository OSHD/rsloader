
package reflection;


import environment.Data;
import jdk.internal.org.objectweb.asm.tree.FieldNode;

import java.lang.reflect.Field;

public class FieldHook {
    private String refactoredName = "";
    private Field reflectedField = null;
    private FieldNode bytecodeField;
    private int intMultiplier = 1;
    private long longMultiplier = 1;

    public FieldHook(String name, FieldNode fn) {
        refactoredName = name;
        bytecodeField = fn;
    }

    /**
     * Gets the field value.
     * Static field use only.
     *
     * @return Field data
     */
    public Object get() {
        try {
            boolean isAccessible = reflectedField.isAccessible();
            if (!isAccessible)
                reflectedField.setAccessible(true);
            Object data = reflectedField.get(Data.clientBootClass);
            if (!isAccessible)
                reflectedField.setAccessible(false);
            return data;
        } catch (Exception e) {
            //Should not happen.
            return null;
        }
    }

    /**
     * Gets the field value.
     * For non-static fields.
     *
     * @param Parent instance
     * @return Field data
     */
    public Object get(Object parent) {
        try {
            boolean isAccessible = reflectedField.isAccessible();
            if (!isAccessible)
                reflectedField.setAccessible(true);
            Object data = reflectedField.get(parent);
            if (!isAccessible)
                reflectedField.setAccessible(false);
            return data;
        } catch (Exception e) {
            //Should not happen in wrappers.
            return null;
        }
    }

    public FieldNode getBytecodeField() {
        return bytecodeField;
    }

    public int getIntMultiplier() {
        return intMultiplier;
    }

    public long getLongMultiplier() {
        return longMultiplier;
    }

    public String getName() {
        return bytecodeField.name;
    }

    public Field getReflectedField() {
        return reflectedField;
    }

    public String getRefactoredName() {
        return refactoredName;
    }

    public void setHook(Field f) {
        reflectedField = f;
    }

    public void setMultiplier(int i) {
        intMultiplier = i;
    }

    public void setMultiplier(long l) {
        longMultiplier = l;
    }
}
