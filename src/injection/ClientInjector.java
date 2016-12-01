
package injection;

import environment.Data;
import injection.transforms.Canvas;
import injection.transforms.KeyListener;
import injection.transforms.MouseListener;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class ClientInjector {
    private static long getChecksum(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            CRC32 crc = new CRC32();
            CheckedInputStream cis = new CheckedInputStream(fis, crc);
            byte[] buffer = new byte[(int) new File(file).length()];
            cis.read(buffer);
            return cis.getChecksum().getValue();
        } catch (Exception e) {

        }
        return -1;
    }

    public static void injectClient(String clientName) {
        try {
            HashMap<JarEntry, byte[]> clientData = new HashMap<>();
            Data.crcHash = getChecksum(clientName);
            System.out.println("Injecting into client : " + Data.crcHash);
            JarFile theJar = new JarFile(clientName);
            Enumeration<?> en = theJar.entries();
            while (en.hasMoreElements()) {
                JarEntry entry = (JarEntry) en.nextElement();
                if (entry.getName().startsWith("META"))
                    continue;
                byte[] buffer = new byte[1024];
                int read;
                InputStream is = theJar.getInputStream(entry);
                byte[] allByteData = new byte[0];
                while ((read = is.read(buffer)) != -1) {
                    byte[] tempBuff = new byte[read + allByteData.length];
                    for (int i = 0; i < allByteData.length; ++i)
                        tempBuff[i] = allByteData[i];
                    for (int i = 0; i < read; ++i)
                        tempBuff[i + allByteData.length] = buffer[i];
                    allByteData = tempBuff;
                }
                if (entry.getName().endsWith(".class")) {
                    ClassReader cr = new ClassReader(allByteData);
                    ClassNode cn = new ClassNode();
                    cr.accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                    Data.clientClasses.put(cn.name, cn);
                }
                clientData.put(entry, allByteData);
            }
            HashMap<String, ClassNode> newNodes = new HashMap<>();
            for (ClassNode cn : Data.clientClasses.values()) {
                Canvas.transformClass(cn);
               // MouseListener.transformClass(cn);
                //KeyListener.transformClass(cn);
                newNodes.put(cn.name, cn);
            }
            Data.clientClasses = newNodes;
            File newJar = new File(clientName);
            FileOutputStream stream = new FileOutputStream(newJar);
            JarOutputStream out = new JarOutputStream(stream);
            for (JarEntry je : clientData.keySet()) {
                byte[] entryData = clientData.get(je);
                if (je.getName().endsWith(".class")) {
                    ClassNode cn = Data.clientClasses.get(je.getName().substring(0, je.getName().indexOf(".class")));
                    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    cn.accept(cw);
                    entryData = cw.toByteArray();
                    JarEntry newEntry = new JarEntry(je.getName());
                    out.putNextEntry(newEntry);
                } else
                    out.putNextEntry(je);
                out.write(entryData);
            }
            out.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}