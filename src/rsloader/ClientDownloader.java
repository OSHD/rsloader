package rsloader; 

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ClientDownloader {
    public ClientDownloader(String jarLocation) {
        long start = System.currentTimeMillis();
        System.out.println("\n[ - Client Downloader - ]");
        if (!jarLocation.equals("")) {
            System.out.println("JAR Location : " + jarLocation);
            System.out.println("Downloading runescape client... ");
            if (downloadFile(jarLocation)) {
                System.out.println("Succesfully downloaded client in : " + (System.currentTimeMillis() - start) + "ms");
            } else
                System.out.println("Failed to download client.");
        } else
            System.out.println("Invalid JAR Location!");

    }

    @SuppressWarnings("unused")
    private boolean downloadFile(final String link) {
        try {
            URL url = new URL(link);
            url.openConnection();
            InputStream reader = url.openStream();
            FileOutputStream writer = new FileOutputStream("gamepack.jar");
            byte[] buffer = new byte[153600];
            int read;
            while ((read = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, read);
                buffer = new byte[153600];
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
