package ztp.util.network;

import ztp.util.network.dataPackages.Message;
import ztp.util.network.dataPackages.MessageReceiver;
import ztp.util.network.lowLevel.Client;
import ztp.util.network.lowLevel.Server;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameNetworkProvider implements MessageReceiver{
    private static GameNetworkProvider gameNetworkProvider;

    private int clientID;
    boolean clientMode;
    private Thread clientThread;
    private Thread serverThread;
    private Client client;
    private Server server;
    private List<MessageReceiver> messageReceivers;

    public static GameNetworkProvider getGameNetworkProvider(){
        if(gameNetworkProvider == null){
            gameNetworkProvider = new GameNetworkProvider();
        }
        return gameNetworkProvider;
    }

    public GameNetworkProvider(){
        messageReceivers = Collections.synchronizedList(new LinkedList<>());
        clientMode = false;
    }

    public void init(String address, int port){
        client = new Client(address, port);
        clientThread = new Thread(client);
        clientMode = true;
    }

    public void init(int port, int clientsCount){
        server = new Server(port, clientsCount);
        serverThread = new Thread(server);
        clientMode = false;
    }

    public void close(){
        if (isRunning()) {
            if(clientMode){
                client.disconnect();
                try {
                    clientThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client = null;
                clientThread = null;
            }
            else{
                server.shutDown();
                try {
                    serverThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                server = null;
                serverThread = null;
            }
        }
    }

    public void passMessages(Message message){
        for(MessageReceiver receiver: messageReceivers){
            receiver.receiveMessage(message);
        }
    }

    public void registerReceiver(MessageReceiver receiver){
        messageReceivers.add(receiver);
    }

    @Override
    public void receiveMessage(Message message) {
        passMessages(message);
    }

    public void sendMessage(Message message){
        if (isRunning()) {
            if(clientMode){
                client.send(message);
            }
            else{
                server.sendToAll(message);
            }
        }
    }

    public void sendMessageTo(int clientID, Message message){
        if(isRunning()&& !clientMode){
            server.send(clientID, message);
        }
    }

    private boolean isRunning(){
        return clientThread != null || serverThread != null;
    }

    public boolean isClientMode() {
        return clientMode;
    }
}
