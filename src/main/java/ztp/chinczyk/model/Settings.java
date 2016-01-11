package ztp.chinczyk.model;

public class Settings {
    private int hostPort;

    public Settings setHostPort(int port){
        this.hostPort = port;
        return this;
    }
    public int getHostPort(){
        return hostPort;
    }

    @Override
    public String toString(){
        return "Settings: {hostPort:" + this.hostPort +"}";
    }
}
