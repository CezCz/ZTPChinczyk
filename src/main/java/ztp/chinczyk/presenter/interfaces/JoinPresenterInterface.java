package ztp.chinczyk.presenter.interfaces;

public interface JoinPresenterInterface extends Presenter{

    void onConnect(String address, String port);

    void onCancel();
}
