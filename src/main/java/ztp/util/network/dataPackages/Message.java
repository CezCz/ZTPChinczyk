package ztp.util.network.dataPackages;

public interface Message{
    String getHeadKey();
    String getHeadValue();
    Object getBody();
    String toString();
}
