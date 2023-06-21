package dictionary;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private final HashMap<String, String> dictionary;
    private final int port;

    public Server(HashMap<String, String> dictionary, int port) {
        this.dictionary = dictionary;
        this.port = port;
    }

    public void run() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Server running on " + this.port);
            System.out.println("Search any word and I will define it for you");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client connected");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String ping = reader.readLine();

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                writer.println(this.search(ping));
            }
        }
    }

    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    private String search(String word) {
        return this.dictionary.get(word);
    }

    public static void main(String[] args) {
        HashMap<String, String> dictionary = new HashMap<>() {{
            put("programming", "Computer programming is the process of performing particular computations");
            put("noun", "A noun is word that generally functions as the name of a specific object");
        }};
        Server server = new Server(dictionary, 8082);

        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
