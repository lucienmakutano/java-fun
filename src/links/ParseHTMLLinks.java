package links;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class ParseHTMLLinks {
    public void run (String url) {
        try {
            URL _url = new URI(url).toURL();
            URLConnection connection = _url.openConnection();
            InputStream stream = connection.getInputStream();

            byte[] b = stream.readAllBytes();
            String data = new String(b);
            Arrays.stream(data.split(">"))
                    .filter(d -> d.strip().startsWith("<a "))
                    .toList()
                    .forEach(item -> {
                        String s = item.strip().split("href=")[1];
                        System.out.println(s);
                    });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ParseHTMLLinks parser = new ParseHTMLLinks();
        System.out.println("Google=============================================");
        parser.run("https://www.google.com");

        System.out.println();
        System.out.println("Geeks for geeks=====================================");
        parser.run("https://www.geeksforgeeks.org/stream-reduce-java-examples/");
    }
}
