
package rsloader;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class RSClassLoader extends ClassLoader {
    public Hashtable<String, Class<?>> classes = new Hashtable<>();
    public HashMap<String, byte[]> classBytes = new HashMap<>();

    public RSClassLoader(File file) {
        super(RSClassLoader.class.getClassLoader());

        try {
            JarFile jar = new JarFile(file);
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                byte[] buffer = new byte[1024];
                int read;
                InputStream is = jar.getInputStream(entry);
                byte[] allByteData = new byte[0];
                while ((read = is.read(buffer)) != -1) {
                    byte[] tempBuff = new byte[read + allByteData.length];
                    System.arraycopy(allByteData, 0, tempBuff, 0, allByteData.length);
                    System.arraycopy(buffer, 0, tempBuff, allByteData.length, read);
                    allByteData = tempBuff;
                }
                classBytes.put(entry.getName(), allByteData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        String entryName = name.replace('.', '/') + ".class";
        if (classes.containsKey(name))
            return classes.get(name);
        if (this.classBytes.containsKey(entryName)) {
            byte[] buf = this.classBytes.get(entryName);
            Class<?> clazz = defineClass(name, buf, 0, buf.length);
            if (clazz != null) {
                classes.put(name, clazz);
                return clazz;
            }
        }
        return super.loadClass(name);
    }
}
