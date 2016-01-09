package ztp.util.network.dataPackages;

import ztp.chinczyk.model.GameState;

import java.io.Serializable;

public class GameStateMessage implements Message, Serializable{
    private String headKey;
    private String headValue;
    private GameState gameState;

    public GameStateMessage(String headKey, String headValue, GameState gameState){
        this.headKey = headKey;
        this.headValue = headValue;
        this.gameState = gameState;
    }

    @Override
    public String getHeadKey() {
        return headKey;
    }

    @Override
    public String getHeadValue() {
        return headValue;
    }

    @Override
    public Object getBody() {
        return gameState;
    }
}
