package chat.server;

import chat.network.TCPConnection;
import chat.network.TCPConnetionListener;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends ChatServerAbstract implements TCPConnetionListener {
    public static void main(String[] args) {

        ChatServerAbstract Server = new Server();
        Server.runServer();
    }

    protected void runServer() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8190)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept()); // ждать пока кто-нибудь не подключиться
                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}