package ztp.chinczyk.presenter;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;

import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.presenter.interfaces.WelcomePresenterInterface;
import ztp.chinczyk.view.GameView;
import ztp.chinczyk.view.ViewFactory;
import ztp.chinczyk.view.WelcomeView;

public class WelcomePresenter implements WelcomePresenterInterface {

	WelcomeView loginView;
	ModelFacade modelFacade;

	public WelcomePresenter(WelcomeView loginView, ModelFacade modelFacade) {
		this.loginView = loginView;
		this.modelFacade = modelFacade;
	}

	@Override
	public void run(Container c) {

		c.add((Component) loginView);
	}

	@Override
	public void onStartNewGame() {
		GameView gameView = (GameView) ViewFactory.getView("GameView");
		GamePresenter gamePresenter = new GamePresenter(gameView, modelFacade);
		gameView.registerPresenter(gamePresenter);

		JFrame gameFrame = new JFrame();
		gamePresenter.run(gameFrame);
		gameFrame.setVisible(true);
		gameFrame.pack();
		
	}

	@Override
	public void onJoinGame() {
		
	}

	@Override
	public void onSettings() {
	}

	@Override
	public void onHelp() {
	}

	@Override
	public void onExit() {
		System.exit(1);
	}

}
