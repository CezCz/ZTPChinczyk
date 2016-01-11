package ztp.chinczyk.presenter;

import ztp.chinczyk.model.Settings;
import ztp.chinczyk.presenter.interfaces.SettingsPresenterInterface;
import ztp.chinczyk.presenter.interfaces.WelcomePresenterInterface;
import ztp.chinczyk.view.SettingsView;

import java.awt.*;
import java.util.HashMap;

public class SettingsPresenter implements SettingsPresenterInterface {
    private SettingsView settingsView;
    private WelcomePresenterInterface parent;

    public SettingsPresenter(SettingsView settingsView, WelcomePresenterInterface parent){
        this.settingsView = settingsView;
        this.settingsView.registerPresenter(this);
        this.parent = parent;
    }

    @Override
    public void onAccept(HashMap<String, String> rawSettings) {
        Settings settings = new Settings();
        settings.setHostPort(Integer.parseInt(rawSettings.get("port")));
        parent.onSettingsClose(settings);
    }

    @Override
    public void onCancel() {
        parent.onSettingsClose(null);
    }

    @Override
    public void run(Container c) {
        c.add(settingsView);
    }
}
