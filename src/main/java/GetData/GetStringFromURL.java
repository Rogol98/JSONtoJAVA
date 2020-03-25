package GetData;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class GetStringFromURL {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        String jsonText = "";
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            jsonText = readAll(rd);
            return jsonText;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return jsonText;
    }

}
