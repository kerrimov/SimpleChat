package chat.server;

import chat.network.TCPConnection;
import chat.network.TCPConnetionListener;

import java.util.ArrayList;

abstract class ChatServerAbstract implements TCPConnetionListener {
    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    ChatServerAbstract(){
        runServer();
    }

    protected abstract void runServer();

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String value){
        System.out.println(value);
        for (int i = 0; i < connections.size(); i++){
            connections.get(i).sendString(value);
        }
    }
}
