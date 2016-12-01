
package script;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * org.dynamac.bot.script
 * Date: 17/12/12
 * Time: 11:13
 */
public class ScriptLoader extends ClassLoader {
    private HashMap<String, byte[]> classes;

    public ScriptLoader(String path) {
        super(ScriptLoader.class.getClassLoader());

        this.classes = new HashMap<>();
        if (path.endsWith(".jar")) {
            try {
                JarFile jar = new JarFile(new File(path));
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        InputStream in = jar.getInputStream(entry);
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] data = new byte[4096];
                        int len;
                        while ((len = in.read(data)) != -1) {
                            out.write(data, 0, len);
                        }
                        System.out.println(entry.getName());
                        classes.put(entry.getName(), out.toByteArray());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (path.endsWith(".class")) {
            try {
                File clazz = new File(path);
                FileInputStream stream = new FileInputStream(clazz);
                byte[] bytes = new byte[stream.available()];
                stream.read(bytes);
                classes.put(clazz.getName(), bytes);
                stream.close();
            } catch (Exception ignored) {
            }
        }
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("Trying to load class : " + name);
        String entry = name.replace('.', '/') + ".class";
        if (this.classes.containsKey(entry)) {
            try {
                byte[] buf = this.classes.remove(entry);
                return defineClass(name, buf, 0, buf.length);
            } catch (Exception ignored) {
            }
        }
        try {
            return getClass().getClassLoader().loadClass(name);
        } catch (Exception ignored) {
        }
        return super.loadClass(name);
    }
}