package ztp.chinczyk.presenter;

import java.awt.Container;
import java.awt.Dimension;
import java.util.EnumMap;

import com.sun.org.apache.xml.internal.utils.Hashtree2Node;

import ztp.chinczyk.model.GameState;
import ztp.chinczyk.model.ModelFacade;
import ztp.chinczyk.model.Settings;
import ztp.chinczyk.model.pawn.IPawn;
import ztp.chinczyk.model.pawn.PawnRelative;
import ztp.chinczyk.model.pawn.PawnSet;
import ztp.chinczyk.model.pawn.PawnSetIterator;
import ztp.chinczyk.model.util.Colors;
import ztp.chinczyk.presenter.interfaces.GamePresenterInterface;
import ztp.chinczyk.view.GameView;
import ztp.chinczyk.view.PawnColor;
import ztp.util.iterator.Iterator;
import ztp.util.network.ClientConnectObserver;
import ztp.util.network.GameNetworkProvider;
import ztp.util.network.dataPackages.GameStateMessage;
import ztp.util.network.dataPackages.Message;
import ztp.util.network.dataPackages.MessageReceiver;
import ztp.util.network.dataPackages.StringMessage;

public class GamePresenter implements GamePresenterInterface {

	private class NetworkPresenter implements MessageReceiver, ClientConnectObserver{
		int playerCount;
		int maxPlayerCount;

		public NetworkPresenter(){
			playerCount = 1;
			maxPlayerCount = settings.getPlayerCount();
			if(asHost){
				modelFacade.newGame();
				onGameJoin("Host");
				localPlayer = "Host";
				networkProvider.init(settings.getHostPort(),maxPlayerCount-1, this, this);
			}
			else{
				gameView.hideStartButton();
				networkProvider.init(joinAddress, joinPort, this);
			}
		}

		@Override
		public void receiveMessage(Message message) {
			if(asHost){
				if(message.getHeadValue().equals("gameState")){
					modelFacade.setGameState((GameState) message.getBody());
					drawState();
					networkProvider.sendMessageToAllExceptLast(new GameStateMessage(localPlayer,"gameState",modelFacade.getGameState()));
					checkIfYourTurn();
				}
				if (message.getHeadValue().equals("iQuit")){
					onGameLeave((String) message.getBody());
					drawState();
					sendState();
				}
			}
			else {
				if(message.getHeadValue().equals("yourName")){
					localPlayer = (String) message.getBody();
				}
				else if(message.getHeadValue().equals("gameState")){
					modelFacade.setGameState((GameState) message.getBody());
					drawState();
					checkIfYourTurn();
				}
			}
		}

		@Override
		public void notifyAboutConnection(int id) {
			playerCount++;
			if(asHost){
				gameView.setStatus("Players connected "+playerCount+"/"+maxPlayerCount+".");
				String newPlayerName = "Player "+Integer.toString(id);
				onGameJoin(newPlayerName);
				networkProvider.sendMessageTo(id ,new StringMessage(localPlayer,"yourName",newPlayerName));
				if(playerCount == maxPlayerCount){
					gameView.setUnlocked(true);
				}
			}
		}
	}

	NetworkPresenter networkPresenter;
	GameView gameView;
	ModelFacade modelFacade;
	GameNetworkProvider networkProvider;
	Settings settings;
	boolean asHost;
	String joinAddress;
	int joinPort;
	String localPlayer;

	public GamePresenter(GameView gameView, ModelFacade modelFacade, GameNetworkProvider networkProvider,Settings settings, boolean asHost) {//// TODO: 12.01.2016
		this.gameView = gameView;
		this.modelFacade = modelFacade;
		this.networkProvider = networkProvider;
		this.asHost = asHost;
		this.settings = settings;

		em.put(Colors.GREEN, PawnColor.GREEN);
		em.put(Colors.RED, PawnColor.RED);
		em.put(Colors.YELLOW, PawnColor.YELLOW);
		em.put(Colors.BLUE, PawnColor.BLUE);
	}

	@Override
	public void run(Container c) {
		c.add(gameView);
	}

	@Override
	public void beforeStart(){
		gameView.setUnlocked(false);
		this.networkPresenter = new NetworkPresenter();
		if(asHost){
			gameView.setStatus("Players connected "+1+"/"+settings.getPlayerCount()+".");
		}
		else {
			gameView.setStatus("Wait for your turn.");
		}
	}

	@Override
	public void onStartGame() {
//////////////////////////////////////////////
		//onGameJoin("MyMyselfAndI");
		//onGameJoin("MyMyselfAndI2");
//////////////////////////////////////////////
		
		modelFacade.startRound();
		gameView.hideStartButton();

		drawState();
	}

	private void drawState() {
		if (modelFacade.isFinished() == false) {
			String currentPlayerName = modelFacade.getCurrentPlayerName();
			gameView.setCurrentPlayer(currentPlayerName + " " + em.get(modelFacade.getPlayerColor(currentPlayerName)));

			gameView.clearBoard();

			gameView.drawDice(modelFacade.getDice());

			for (String player : modelFacade.getAllPlayers()) {
				drawPlayerPawns(player);
			}

			if (modelFacade.noMove()) {
				gameView.showPassButton();
			} else {
				gameView.hidePassButton();
			}

		}
		if (modelFacade.winner()) {
			String player = modelFacade.getWinnerName();
			gameView.drawWinnerPrompt(player);
			gameView.showStartButton();
		}
	}

	EnumMap<Colors, PawnColor> em = new EnumMap<>(Colors.class);

	public void onGameJoin(String player) {
		modelFacade.addPlayer(player);
		gameView.addPlayer(player);

		drawPlayerPawns(player);
	}

	private void drawPlayerPawns(String player) {
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

	public void doMove(PawnView p) {
		if (modelFacade.isFinished() == false) {
			if (em.get(modelFacade.getPlayerColor(modelFacade.getCurrentPlayerName())) == p.getColor()) {
				modelFacade.doMove(p.getPawnNumber());
				drawState();
			}
		}
		checkIfYourTurn();
		sendState();
	}

	public void onGameLeave(String player) {
		modelFacade.removePlayer(player);
		gameView.removePlayer(player);
	}

	public void doPass() {
		modelFacade.doPass();
		drawState();
		checkIfYourTurn();
		sendState();
	}

	public int getJoinPort() {
		return joinPort;
	}

	public void setJoinPort(int joinPort) {
		this.joinPort = joinPort;
	}

	public String getJoinAddress() {
		return joinAddress;
	}

	public void setJoinAddress(String joinAddress) {
		this.joinAddress = joinAddress;
	}

	private void sendState(){
		if(asHost){
			networkProvider.sendMessage(new GameStateMessage(localPlayer,"gameState",modelFacade.getGameState()));
		}
		else{
			networkProvider.sendMessage(new GameStateMessage(localPlayer,"gameState", modelFacade.getGameState()));
		}
	}

	private void checkIfYourTurn(){
		if (modelFacade.getCurrentPlayerName().equals(localPlayer)){
			gameView.setUnlocked(true);
			gameView.setStatus("It's your turn.");
		}
		else {
			gameView.setUnlocked(false);
			gameView.setStatus("Wait for your turn.");
		}
	}
}
