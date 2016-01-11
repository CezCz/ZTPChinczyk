package ztp.util.network.dataPackages;

public class WrappedMessage implements Message{
    private String headKey;
    private String headValue;
    private Message message;

    public WrappedMessage(String headKey, String headValue, Message message){
        this.headKey = headKey;
        this.headValue = headValue;
        this.message = message;
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
        return message;
    }
}
