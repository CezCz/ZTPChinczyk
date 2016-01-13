package ztp.util.network.lowLevel;

import ztp.util.network.ClientConnectObserver;
import ztp.util.network.dataPackages.Message;
import ztp.util.network.dataPackages.MessageReceiver;
import ztp.util.network.dataPackages.StringMessage;
import ztp.util.network.dataPackages.WrappedMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server implements Runnable{
    int port;
    ServerSocket[] serverSockets;
    Socket[] sockets;
    ObjectOutputStream[] outputStreams;
    ObjectInputStream[] inputStreams;
    List<MessageReceiver> messageReceivers;
    List<ClientConnectObserver> connectionObservers;
    AtomicBoolean isOn;
    int lastSender;

    public Server(int port, int connectionsCount){
        isOn = new AtomicBoolean(true);
        this.port = port;
        messageReceivers = Collections.synchronizedList(new LinkedList<MessageReceiver>());
        connectionObservers = Collections.synchronizedList(new LinkedList<>());
        outputStreams = new ObjectOutputStream[connectionsCount];
        inputStreams = new ObjectInputStream[connectionsCount];
        sockets = new Socket[connectionsCount];
        serverSockets = new ServerSocket[connectionsCount];
    }

    private void host(){
        for(int i = 0; i < sockets.length; i++){
            try {
                serverSockets[i] = new ServerSocket(port);
                sockets[i] = serverSockets[i].accept();
                serverSockets[i] = null;
                outputStreams[i] = new ObjectOutputStream(sockets[i].getOutputStream());
                inputStreams[i] = new ObjectInputStream(sockets[i].getInputStream());
                notifyObservers(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerReceiver(MessageReceiver receiver){
        messageReceivers.add(receiver);
    }

    public void registerConnectionObserver(ClientConnectObserver observer){
        connectionObservers.add(observer);
    }

    public void send(int id, Message message){
        try {
            outputStreams[id].reset();
            outputStreams[id].writeObject(message);
            System.out.println("Wiadomość do "+id+ " " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(Message message){
        for(int i = 0; i < outputStreams.length; i++){
            send(i, message);
        }
    }

    public void sendToAllExceptLast(Message message){
        for (int i = 0; i < outputStreams.length; i++){
            if(i != lastSender){
                send(i, message);
            }
        }
    }

    public void shutDown(){
        isOn.set(false);
        for (int i = 0; i < sockets.length; i++){
            sockets[i] = null;
            outputStreams[i] = null;
            inputStreams[i] = null;
        }
    }

    @Override
    public void run() {
        System.out.println("Uruchomiono serwer w porcie " + port);
        host();
        while (isOn.get()){
            for(int i = 0; i < inputStreams.length; i++){
                /*
                try {
                    if(inputStreams[i].available() > 0){// TODO sprawdzic czy dziala

                        notifyReceivers((Message) inputStreams[i].readObject());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                */
                try {
                    Message receivedMessage = (Message) inputStreams[i].readObject();
                    System.out.println("Dane od "+receivedMessage);
                    //WrappedMessage wrappedMessage = new WrappedMessage("fromClientID",Integer.toString(i), (Message) inputStreams[i].readObject());
                    lastSender = i;
                    notifyReceivers(receivedMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void notifyReceivers(Message message){
        for(MessageReceiver receiver : messageReceivers){
            receiver.receiveMessage(message);
        }
    }

    private void notifyObservers(int id){
        for(ClientConnectObserver observer: connectionObservers){
            observer.notifyAboutConnection(id);
        }
    }
}
