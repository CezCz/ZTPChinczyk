package ztp.chinczyk.presenter;

import java.awt.Container;
import java.awt.Dimension;
import java.util.EnumMap;

import com.sun.org.apache.xml.internal.utils.Hashtree2Node;

import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.model.pawn.IPawn;
import ztp.chinczyk.model.pawn.PawnRelative;
import ztp.chinczyk.model.pawn.PawnSet;
import ztp.chinczyk.model.pawn.PawnSetIterator;
import ztp.chinczyk.model.util.Colors;
import ztp.chinczyk.presenter.interfaces.GamePresenterInterface;
import ztp.chinczyk.view.GameView;
import ztp.chinczyk.view.PawnColor;
import ztp.util.iterator.Iterator;

public class GamePresenter implements GamePresenterInterface {

	GameView gameView;
	ModelFacade modelFacade;

	public GamePresenter(GameView gameView, ModelFacade modelFacade) {
		this.gameView = gameView;
		this.modelFacade = modelFacade;
		em.put(Colors.GREEN, PawnColor.GREEN);
		em.put(Colors.RED, PawnColor.RED);
		em.put(Colors.YELLOW, PawnColor.YELLOW);
		em.put(Colors.BLUE, PawnColor.BLUE);
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

	EnumMap<Colors, PawnColor> em = new EnumMap<>(Colors.class);

	public void onGameJoin(String player) {
		modelFacade.addPlayer(player);
		gameView.addPlayer(player);

		PawnSet ps = modelFacade.getPlayerPawnSet(player);
		Iterator<IPawn<Integer>> psi = ps.createIterator();
		psi.first();
		while (!psi.isDone()) {

			IPawn<Integer> currentPawn = psi.currentItem();
			Colors playerColor = modelFacade.getPlayerColor(player);
			Integer currentElementNumber = ((PawnSetIterator) psi).getCurrentElementNumber();
			PawnColor translatedViewColor = em.get(playerColor);

			PawnView p = new PawnView(new PawnRelative(currentPawn, playerColor), currentElementNumber,
					translatedViewColor);

			gameView.drawPawn(p);
			psi.next();

		}
	}

	public void onGameLeave(String player) {
		modelFacade.removePlayer(player);
		gameView.removePlayer(player);
	}

}
