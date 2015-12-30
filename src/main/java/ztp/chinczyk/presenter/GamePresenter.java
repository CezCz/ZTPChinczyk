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
		onGameJoin("MyMyselfAndI");
		onGameJoin("MyMyselfAndI2");

		c.add(gameView);

	}

	@Override
	public void onStartGame() {

		// TODO Auto-generated method stub

	}

	public void onGameJoin(String player) {
		modelFacade.getGameLogic().getGs().addPlayer(player);
		gameView.addPlayer(player);
		gameView.drawPawns(modelFacade.getGameLogic().getGs().getPlayerColor(modelFacade.getGameLogic().getGs().getPlayerNumber(player)));
		
		// gameView.drawPawns();
	}

	public void onGameLeave(String player) {
		modelFacade.getGameLogic().getGs().removePlayer(player);
		gameView.removePlayer(player);
	}

}
