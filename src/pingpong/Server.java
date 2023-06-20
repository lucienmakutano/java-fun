package pingpong;

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

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String ping = reader.readLine();

                if (ping.equals("ping")) {
                    OutputStream output = socket.getOutputStream();
                    PrintWriter writer = new PrintWriter(output, true);

                    writer.println("pong");
                }
            }
        }
    }
}
