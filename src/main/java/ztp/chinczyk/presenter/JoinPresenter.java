package ztp.chinczyk.presenter;

import ztp.chinczyk.presenter.interfaces.JoinPresenterInterface;
import ztp.chinczyk.presenter.interfaces.WelcomePresenterInterface;
import ztp.chinczyk.view.GameView;
import ztp.chinczyk.view.JoinView;
import ztp.chinczyk.view.ViewFactory;

import javax.swing.*;
import java.awt.*;

public class JoinPresenter implements JoinPresenterInterface{
    private JoinView joinView;
    private WelcomePresenter parent;

    JoinPresenter(JoinView joinView, WelcomePresenterInterface parent){
        this.joinView = joinView;
        this.joinView.registerPresenter(this);
        this.parent = (WelcomePresenter) parent;
    }

    @Override
    public void onConnect(String address, String port) {//TODO
        GameView gameView = (GameView) ViewFactory.getView("GameView");
        GamePresenter gamePresenter = new GamePresenter(gameView, parent.getModelFacade(), parent.getGameNetworkProvider(),parent.getSettings() ,false);
        gameView.registerPresenter(gamePresenter);

        JFrame gameFrame = new JFrame();
        gameFrame.setSize(900, 600);
        gameFrame.setResizable(false);
        gamePresenter.run(gameFrame);
        gameFrame.setVisible(true);
        gamePresenter.setJoinAddress(address);
        gamePresenter.setJoinPort(Integer.parseInt(port));
        gamePresenter.beforeStart();
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
