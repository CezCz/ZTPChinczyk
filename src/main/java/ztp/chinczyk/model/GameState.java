package ztp.chinczyk.model;

import java.io.Serializable;
import java.util.ArrayList;

import ztp.chinczyk.model.pawn.PawnSet;
import ztp.chinczyk.model.pawn.PawnSetPool;
import ztp.chinczyk.model.util.Colors;

public class GameState implements Serializable{

	private ArrayList<String> playersList;
	private ArrayList<PawnSet> playersPawns;
	private int currentPlayer;
	private int diceRoll;
	private boolean finished;
	private boolean anyMovable;
	private int currentPlayerMoves;
	private boolean isSix;
	private int winner;

	public GameState() {
		playersList = new ArrayList<>();
		playersPawns = new ArrayList<>();
		currentPlayer = 0;
		currentPlayerMoves = 0;
		finished = false;
		anyMovable = false;
		isSix = false;
	}
	
	public void setCurrentPlayerMoves(int currentPlayerMoves) {
		this.currentPlayerMoves = currentPlayerMoves;
	}
	
	public int getCurrentPlayerMoves() {
		return currentPlayerMoves;
	}
	
	
	
	public Colors getPlayerColor(int whos) {
		return playersPawns.get(whos).getPawnColor();
	}

	public void setCurrentPlayer() {
		this.currentPlayer = (currentPlayer + 1) % playersList.size();
	}

	public int getPlayerCount() {
		return playersList.size();
	}

	public void setAnyMovable(boolean anyMovable) {
		this.anyMovable = anyMovable;
	}

	public boolean getAnyMovable() {
		return anyMovable;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean getFinished() {
		return finished;
	}

	public void setRepeat(boolean b) {
		this.isSix = b;
	}
	
	public boolean getRepeat() {
		return this.isSix;
	}

	public void setWinner() {
		this.winner = currentPlayer;
	}

	public int getWinner() {
		return winner;
	}
	
	public void setDiceRoll(int diceRoll) {
		this.diceRoll = diceRoll;
	}

	public int getDiceRoll() {
		return diceRoll;
	}

	public ArrayList<String> getPlayersList() {
		return playersList;
	}

	public PawnSet getPlayersPawns(int whichSet) {
		return playersPawns.get(whichSet);
	}

	public PawnSet getCurrentPlayerPawns() {
		return getPlayersPawns(currentPlayer);
	}

	public void addPlayer(String name) {
		playersList.add(name);
		try {
			playersPawns.add(PawnSetPool.getPawnSet());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removePlayer(String name) {
		playersList.remove(playersList.indexOf(name));
		PawnSetPool.putBack();
		playersPawns.remove(playersPawns.size()-1);
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public int getPlayerNumber(String player) {
		return playersList.indexOf(player);
	}

}
