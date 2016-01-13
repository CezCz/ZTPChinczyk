package ztp.util.network;

import com.sun.istack.internal.Nullable;
import ztp.util.network.dataPackages.Message;
import ztp.util.network.dataPackages.MessageReceiver;
import ztp.util.network.lowLevel.Client;
import ztp.util.network.lowLevel.Server;


public class GameNetworkProvider{
    private static GameNetworkProvider gameNetworkProvider;

    private int clientID;
    boolean clientMode;
    private Thread clientThread;
    private Thread serverThread;
    private Client client;
    private Server server;

    public static GameNetworkProvider getGameNetworkProvider(){
        if(gameNetworkProvider == null){
            gameNetworkProvider = new GameNetworkProvider();
        }
        return gameNetworkProvider;
    }

    public GameNetworkProvider(){
        clientMode = false;
    }

    public void init(String address, int port, @Nullable MessageReceiver receiver){
        clientMode = true;
        client = new Client(address, port);
        if(receiver != null){
            registerReceiver(receiver);
        }
        clientThread = new Thread(client);
        clientThread.start();
    }

    public void init(int port, int clientsCount, @Nullable ClientConnectObserver observer, @Nullable MessageReceiver receiver){
        clientMode = false;
        server = new Server(port, clientsCount);
        if(observer != null){
            registerConnectionObserver(observer);
        }
        if(receiver != null){
            registerReceiver(receiver);
        }
        serverThread = new Thread(server);
        serverThread.start();
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

    public void registerReceiver(MessageReceiver receiver){
        if(clientMode){
            client.registerReceiver(receiver);
        }
        else {
            server.registerReceiver(receiver);
        }
    }

    public void registerConnectionObserver(ClientConnectObserver observer){
        if(!clientMode){
            server.registerConnectionObserver(observer);
        }
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

    public void sendMessageToAllExceptLast(Message message){
        if(isRunning() && !clientMode){
            server.sendToAllExceptLast(message);
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
