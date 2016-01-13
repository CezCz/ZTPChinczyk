package ztp.chinczyk.model;

import java.util.ArrayList;

import ztp.chinczyk.model.pawn.PawnSet;
import ztp.chinczyk.model.util.Colors;

public class ModelFacade {
	
	private GameLogic g = new GameLogic();
	
	public ModelFacade() {
		g = new GameLogic();
	}
	
	public void addPlayer(String name) {
		g.addPlayer(name);
	}

	public void removePlayer(String name) {
		g.removePlayer(name);
	}
	
	public Colors getPlayerColor(String name) {
		return g.getGs().getPlayerColor(g.getGs().getPlayerNumber(name));
	}
	
	public PawnSet getPlayerPawnSet(String name) {
		return g.getGs().getPlayersPawns(g.getGs().getPlayerNumber(name));
	}
	
	public void doMove(int pawnNumber) {
		g.doMove(pawnNumber);
	}
	
	public int getDice() {
		return g.getGs().getDiceRoll();
	}
	
	public void startRound() {
		g.prePlayerMove();
	}

	public ArrayList<String> getAllPlayers() {
		return g.getGs().getPlayersList();
	}

	public boolean noMove() {
		return g.getGs().getAnyMovable() == false;
	}

	public boolean winner() {
		return g.getGs().getFinished();
	}

	public String getWinnerName() {
		return g.getGs().getPlayersList().get(g.getGs().getWinner());
	}

	public void doPass() {
		g.doPass();
	}

	public String getCurrentPlayerName() {
		return g.getGs().getPlayersList().get(g.getGs().getCurrentPlayer());
	}

	public boolean isFinished() {
		return g.getGs().getFinished();
	}

	public void newGame() {
		g.newGame();
	}

	public GameState getGameState(){
		return g.getGs();
	}

	public void setGameState(GameState gameState){
		g.setGs(gameState);
	}

}
