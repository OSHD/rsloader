import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import environment.Data;
import gui.AppletFrame;
import injection.ClientInjector;
import injection.XteaInjector;
import injection.wrappers.RuneHD;
import reflection.cache.Loader;
import rsloader.PageParser;

public class Boot {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, MalformedURLException, IOException {
        /**
         * Obtaining all page PARAMETERS
         */
        new PageParser();
        /**
         * Loading all ClassNodes
         * Injecting Canvas into the client
         * Saving injected Client
         */
        File gamepack = new File("gamepack.jar");
        if(!gamepack.exists())
        {
        	System.out.println("Downloading gamepack");
        	FileUtils.copyURLToFile(new URL(PageParser.jarLocation), gamepack);
        }
        ClientInjector.injectClient("gamepack.jar");
        /**
         * Starting client GUI + APPLET
         * */
        new Thread(new RuneHD()).start();
        Data.appletFrame = new AppletFrame();
        /**
         * Loading our hooked classes and fields
         * */
        Loader.loadCache();
        
    }

}
