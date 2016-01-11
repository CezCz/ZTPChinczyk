package ztp.chinczyk.presenter;

import ztp.chinczyk.presenter.interfaces.HelpPresenterInterface;
import ztp.chinczyk.presenter.interfaces.WelcomePresenterInterface;
import ztp.chinczyk.view.HelpView;

import java.awt.*;

public class HelpPresenter implements HelpPresenterInterface{
    private HelpView helpView;
    private WelcomePresenterInterface parent;

    public HelpPresenter(HelpView helpView, WelcomePresenterInterface parent){
        this.helpView = helpView;
        this.helpView.registerPresenter(this);
        this.parent = parent;
    }

    @Override
    public void onExit() {
        parent.onHelpClose();
    }

    @Override
    public void run(Container c) {
        c.add(helpView);
    }
}
