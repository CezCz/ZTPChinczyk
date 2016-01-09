package ztp.chinczyk.presenter.interfaces;

import ztp.chinczyk.model.Settings;

import java.util.HashMap;

public interface SettingsPresenterInterface extends Presenter{

    void onAccept(HashMap<String,String> rawSettings);
    void onCancel();
}
