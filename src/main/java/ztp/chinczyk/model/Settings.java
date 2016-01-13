package ztp.chinczyk.model;

public class Settings {
    private int hostPort;
    private int playerCount;

    public Settings setHostPort(int port){
        this.hostPort = port;
        return this;
    }

    public int getHostPort(){
        return hostPort;
    }

    public Settings setPlayerCount(int playerCount){
        this.playerCount = playerCount;
        return this;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public String toString(){
        return "Settings: {hostPort:" + this.hostPort +", playerCount:" + this.playerCount+"}";
    }
}
