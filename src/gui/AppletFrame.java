
package gui;


import environment.Data;
import rsloader.RSClassLoader;
import script.ScriptDef;
import script.ScriptLoader;
import injection.XteaInjector;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

public class AppletFrame extends JFrame implements AppletStub {

    private MenuBar menuBar;
    private Menu fileMenu;
    public MenuItem startScriptOption;
    public MenuItem pauseScriptOption;
    private Menu viewMenu;

    public static CheckboxMenuItem hdCheck;

    public AppletFrame() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        long start = System.currentTimeMillis();
        System.out.println("\n[ - Applet Loader - ]");
        System.out.println("Starting Runescape applet...");
        Data.LOADER = new RSClassLoader(new File("gamepack.jar"));
        new XteaInjector(null,"gamepack");
        Data.clientBootClass = Data.LOADER.loadClass("client").newInstance();
        Data.CLIENT_APPLET = (Applet) Data.clientBootClass;
        Data.CLIENT_APPLET.setStub(this);
        Data.CLIENT_APPLET.init();
        Data.CLIENT_APPLET.start();
        Data.CLIENT_APPLET.setBounds(5, 5, 765, 503);
        this.setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("RuneHD - OS");
        getContentPane().add(Data.CLIENT_APPLET, BorderLayout.CENTER);
        setSize(765, 503);
        System.out.println("Succesfully started applet in : " + (System.currentTimeMillis() - start) + "ms");

        menuBar = new MenuBar();
        fileMenu = new Menu("File");

        startScriptOption = new MenuItem(Data.currentScript == null ? "Start Script" : "Stop Script");
        startScriptOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startScriptOptionActionPerformed(evt);
            }
        });
        fileMenu.add(startScriptOption);
        pauseScriptOption = new MenuItem((Data.currentScript != null && Data.currentScript.isPaused) ? "Resume Script" : "Pause Script");
        pauseScriptOption.setEnabled(Data.currentScript != null);
        pauseScriptOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseScriptOptionActionPerformed(evt);
            }
        });
        fileMenu.add(pauseScriptOption);
        menuBar.add(fileMenu);

        viewMenu = new Menu("View");

        hdCheck = new CheckboxMenuItem("HD");
        hdCheck.setState(false);
        Data.hd = hdCheck.getState();
        viewMenu.add(hdCheck);

        menuBar.add(viewMenu);

        setMenuBar(menuBar);

        setVisible(true);//Data.hd ? false : true);

        for (int i = 0; i < 10000; ) {
            try {
                long curr = System.currentTimeMillis();
                Thread.sleep(1000);
                i += System.currentTimeMillis() - curr;
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void appletResize(int arg0, int arg1) {

    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(Data.BASE_LINK);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public URL getDocumentBase() {
        try {
            return new URL(Data.BASE_LINK);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getParameter(String s) {
        return Data.PARAMETERS.get(s);
    }

    public void writeToRegistry(String key, String value) {
        Preferences userPref = Preferences.userRoot();
        userPref.put(key, value);
    }

    public String readRegistry(String key) {
        Preferences userPref = Preferences.userRoot();
        String s = userPref.get(key, "null");
        return s;
    }

    public String getCurrentDirectory() {
        if (readRegistry("LastScriptLocation").equals("null")) {
            writeToRegistry("LastScriptLocation", getDefaultDirectory());
        }
        return readRegistry("LastScriptLocation");
    }

    public String getDefaultDirectory() {
        try {
            return new File(".").getCanonicalPath();
        } catch (IOException e) {
            return System.getProperty("user.dir");
        }
    }

    public void pauseScriptOptionActionPerformed(java.awt.event.ActionEvent evt) {
        if (pauseScriptOption.getLabel().contains("Pause")) {
            pauseScriptOption.setLabel("Resume Script");
            Data.currentScript.pause();
        } else {
            pauseScriptOption.setLabel("Pause Script");
            Data.currentScript.unpause();
        }
    }

    @SuppressWarnings("deprecation")
    private void startScriptOptionActionPerformed(java.awt.event.ActionEvent evt) {
        if (Data.currentScript == null) {
            JFileChooser c = new JFileChooser();
            c.setCurrentDirectory(new File(getCurrentDirectory()));
            c.setDialogTitle("Select script...");
            c.setFileSelectionMode(JFileChooser.FILES_ONLY);
            c.setFileFilter(new FileFilter() {
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".class") ||
                            f.getName().toLowerCase().endsWith(".jar")
                            || f.isDirectory();
                }

                public String getDescription() {
                    return "Dynamac Scripts";
                }
            });
            c.setAcceptAllFileFilterUsed(false);
            if (c.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                writeToRegistry("LastScriptLocation", c.getSelectedFile().toString());
                try {
                    ScriptLoader loader = null;
                    loader = new ScriptLoader(c.getSelectedFile().getPath());
                    if (loader != null) {
                        Class<?> scriptClass = loader.loadClass(c.getSelectedFile().getName().substring(0, c.getSelectedFile().getName().indexOf(".")));
                        Object scriptObject = scriptClass.newInstance();
                        if (scriptObject instanceof ScriptDef) {
                            Data.currentScript = (ScriptDef) scriptObject;
                            Data.currentScript.start();
                            startScriptOption.setLabel("Stop Script");
                            pauseScriptOption.setLabel("Pause Script");
                            pauseScriptOption.setEnabled(true);

                        } else {
                            System.out.println("Selected file is not a valid script!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            startScriptOption.setLabel("Start Script");
            pauseScriptOption.setLabel("Pause Script");
            pauseScriptOption.setEnabled(false);
            Data.currentScript.stop();
            Data.currentScript = null;
            Runtime.getRuntime().gc();
        }
    }


}
