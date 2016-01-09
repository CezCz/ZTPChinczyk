package ztp.util.network.dataPackages;

import java.io.Serializable;

public class StringMessage implements Message, Serializable{
    String headKey;
    String headValue;
    String body;

    public StringMessage(String headKey, String headValue, String body){
        this.headKey = headKey;
        this.headValue = headValue;
        this.body = body;
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
        return body;
    }

    @Override
    public String toString(){
        return "{"+headKey+":"+headValue+"-"+body+"}";
    }
}
