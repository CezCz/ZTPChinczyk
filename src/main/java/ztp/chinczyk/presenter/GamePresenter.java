package ztp.chinczyk.presenter;

import java.awt.Container;

import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.presenter.interfaces.GamePresenterInterface;
import ztp.chinczyk.view.GameView;

public class GamePresenter implements GamePresenterInterface {

	GameView gameView;
	ModelFacade modelFacade;

	public GamePresenter(GameView gameView, ModelFacade modelFacade) {
		this.gameView = gameView;
		this.modelFacade = modelFacade;
	}

	@Override
	public void run(Container c) {

		c.add(gameView);
		
	}

	@Override
	public void onStartGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResignGame() {
		// TODO Auto-generated method stub

	}

	

}
