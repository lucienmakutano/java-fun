package upper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }
    public void run () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Server running on " + this.port);
            System.out.println("Ready to turn your word to upper case");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client connected");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String ping = reader.readLine();

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println(ping.toUpperCase());
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8081);

        try {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
