package ztp.util.network.lowLevel;

import ztp.util.network.dataPackages.Message;
import ztp.util.network.dataPackages.MessageReceiver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client implements Runnable{
    private class NotConnectedException extends RuntimeException{
        NotConnectedException(String message){
            super(message);
        }
    }

    private interface ClientState{
        void disconnect();
        void send(Message message);
    }

    private class ConnectedState implements ClientState{

        @Override
        public void disconnect() {
            isOn.set(false);
            try {
                socket.close();
                currentState = disconnectedState;
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void send(Message message) {
            try {
                System.out.println("Wysylam do serwera");
                outputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class DisconnectedState implements ClientState{

        @Override
        public void disconnect() {
            throw new NotConnectedException("Tried to disconnect already disconnected Client!");
        }

        @Override
        public void send(Message message) {
            throw new NotConnectedException("Tried to disconnect already disconnected Client!");
        }
    }

    private int port;
    private String address;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private List<MessageReceiver> messageReceivers;
    private ConnectedState connectedState;
    private DisconnectedState disconnectedState;
    private ClientState currentState;
    private AtomicBoolean isOn;

    public Client(String address, int port){
        isOn = new AtomicBoolean(true);
        connectedState = new ConnectedState();
        disconnectedState = new DisconnectedState();
        currentState = disconnectedState;
        messageReceivers = Collections.synchronizedList(new LinkedList<>());
        this.address = address;
        this.port = port;
    }

    private void connect(){
        try {
            socket = new Socket(address, port);
            System.out.println("Polaczono z serwerem");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            currentState = connectedState;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyReceivers(Message message){
        for(MessageReceiver receiver : messageReceivers){
            receiver.receiveMessage(message);
        }
    }

    public void registerReceiver(MessageReceiver receiver){
        messageReceivers.add(receiver);
    }

    public void send(Message message){
        currentState.send(message);
    }

    public boolean connected(){
        return socket!=null && socket.isConnected();
    }

    public void disconnect(){
        currentState.disconnect();
    }

    @Override
    public void run() {
        connect();
        while (isOn.get()){
            try {
                if(inputStream.available() > 0){
                    System.out.println("Dane z serwera");
                    notifyReceivers((Message) inputStream.readObject());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
