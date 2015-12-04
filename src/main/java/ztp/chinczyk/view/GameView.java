package ztp.chinczyk.view;

import javax.swing.JPanel;

import ztp.chinczyk.presenter.GamePresenter;
import ztp.chinczyk.view.interfaces.GameViewInterface;
import ztp.chinczyk.view.interfaces.View;

public class GameView extends JPanel implements GameViewInterface {
	
	GamePresenter gamePresenter;
	
	private static class Factory extends ViewFactory {
		protected View create() {
			return new GameView();
		}
	}
	
	static {
		ViewFactory.addFactory("GameView", new Factory());
	}

	@Override
	public void registerPresenter(GamePresenter p) {
		gamePresenter = p;

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
