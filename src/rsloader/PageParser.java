package rsloader; 

import environment.Data;

import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParser {
    private String HTML;
    public static String jarLocation;

    public PageParser() {
        long start = System.currentTimeMillis();
        System.out.println("[ - Parameter Parser - ]");
        if (!parseParams()) {
            System.out.println("Failed to parse parameters.");
            return;
        }
        System.out.println("Succesfully parsed parameters in : " + (System.currentTimeMillis() - start) + "ms");
        //new ClientDownloader(jarLocation);
    }

    private String getContent(String link) {
        try {
            URL url = new URL(link);
            String referer = url.toExternalForm();
            URLConnection uc = url.openConnection();
            uc
                    .addRequestProperty(
                            "Accept",
                            "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
            uc.addRequestProperty("Accept-Charset",
                    "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            uc.addRequestProperty("Accept-Encoding", "gzip,deflate");
            uc.addRequestProperty("Accept-Language", "en-gb,en;q=0.5");
            uc.addRequestProperty("Connection", "keep-alive");
            uc.addRequestProperty("Host", "www.runescape.com");
            uc.addRequestProperty("Keep-Alive", "300");
            if (referer != null)
                uc.addRequestProperty("Referer", referer);
            uc
                    .addRequestProperty(
                            "User-Agent",
                            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.8.0.6) Gecko/20060728 Firefox/1.5.0.6");
            DataInputStream di = new DataInputStream(uc.getInputStream());
            byte[] buffer = new byte[uc.getContentLength()];
            di.readFully(buffer);
            di.close();
            Thread.sleep(250 + (int) (Math.random() * 500));
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getUrl() throws Exception {
        return Data.BASE_LINK + ext("archive=", " ", HTML);
    }

    private String ext(String from, String to, String str1) {
        int p = 0;
        p = str1.indexOf(from, p) + from.length();
        return str1.substring(p, str1.indexOf(to, p));
    }

    private String getNewBaseLink() {
        int[] worlds = {27};
        return "http://oldschool" + worlds[new Random().nextInt(worlds.length)] + ".runescape.com/";
    }

    private String remove(String str) {
        return str.replaceAll("\"", "");
    }

    private boolean parseParams() {
        try {
            System.out.println("Parsing parameters...");
            Data.BASE_LINK = getNewBaseLink();
            System.out.println("Base Link : " + Data.BASE_LINK);
            HTML = getContent(Data.BASE_LINK);
            Pattern regex = Pattern.compile("<param name=\"?([^\\s]+)\"?\\s+value=\"?([^>]*)\"?>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher regexMatcher = regex.matcher(HTML);
            while (regexMatcher.find())
                if (!Data.PARAMETERS.containsKey(regexMatcher.group(1))) {
                    Data.PARAMETERS.put(remove(regexMatcher.group(1)), remove(regexMatcher.group(2)));
                }
            jarLocation = getUrl();
            System.out.println(jarLocation);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
