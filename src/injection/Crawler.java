package injection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Crawler {

    public static String get(String page) throws IOException {
        URLConnection uc = new URL(page).openConnection();
        uc.setRequestProperty("User-Agent", "Mozilla/4.0");
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        byte[] data = new byte[uc.getContentLength()];
        new DataInputStream(uc.getInputStream()).readFully(data);
        return new String(data);
    }
}
