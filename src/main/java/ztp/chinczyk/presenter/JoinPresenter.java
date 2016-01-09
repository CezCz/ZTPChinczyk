package ztp.chinczyk.presenter;

import ztp.chinczyk.presenter.interfaces.JoinPresenterInterface;
import ztp.chinczyk.presenter.interfaces.WelcomePresenterInterface;
import ztp.chinczyk.view.JoinView;

import java.awt.*;

public class JoinPresenter implements JoinPresenterInterface{
    private JoinView joinView;
    private WelcomePresenterInterface parent;

    JoinPresenter(JoinView joinView, WelcomePresenterInterface parent){
        this.joinView = joinView;
        this.joinView.registerPresenter(this);
        this.parent = parent;
    }

    @Override
    public void onConnect(String address, String port) {//TODO
        //connect
        parent.onJoinClose();
    }

    @Override
    public void onCancel() {
        parent.onJoinClose();
    }

    @Override
    public void run(Container c) {
        c.add(joinView);
    }
}
