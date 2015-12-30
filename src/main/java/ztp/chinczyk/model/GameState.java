package ztp.chinczyk.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class GameState {

	private ArrayList<String> playersList;
	private ArrayList<PawnSet> playersPawns;
	private int currentPlayer;
	private int diceRoll;
	private boolean finished;
	private boolean anyMovable;
	private boolean isSix;
	private int winner;

	public GameState() {
		playersList = new ArrayList<>();
		playersPawns = new ArrayList<>();
		currentPlayer = 0;
		finished = false;
		anyMovable = false;
		isSix = false;
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
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

}

interface IPawn {

	public int getPosition();

	public void setPosition(int position);

	public void resetPawn();

}

class Pawn implements IPawn {
	private int position;

	public Pawn() {
		resetPawn();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void resetPawn() {
		position = 0;
	}

}

enum Colors {
	GREEN, BLUE, YELLOW, RED
}

class PawnRelative implements IPawn {

	// offset needs to be mod 40
	private static Map<Colors, Integer> relativeOffsetMap = new EnumMap<Colors, Integer>(Colors.class);

	static {
		relativeOffsetMap.put(Colors.GREEN, 0);
		relativeOffsetMap.put(Colors.BLUE, 10);
		relativeOffsetMap.put(Colors.YELLOW, 20);
		relativeOffsetMap.put(Colors.RED, 30);
	}

	private IPawn p;
	public Colors c;

	public PawnRelative(IPawn p, Colors c) {
		this.p = p;
		this.c = c;
	}

	@Override
	public int getPosition() {
		return (p.getPosition() + relativeOffsetMap.get(c)) % 40;
	}

	@Override
	public void setPosition(int relPosition) {

		int absPos = ((((relPosition - relativeOffsetMap.get(c)) % 40) + 40) % 40);
		p.setPosition(absPos);

	}
	
	@Override
	public void resetPawn() {
		p.resetPawn();
	}

}

interface Aggregate<E> {
	Iterator<E> createIterator();
}

class PawnSet implements Aggregate<IPawn> {
	private ArrayList<IPawn> pawns;

	public PawnSet(Colors c) {
		pawns = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			pawns.add(new PawnRelative(new Pawn(), c));
		}
	}

	public IPawn getPawn(int k) {
		return pawns.get(k);
	}

	public Iterator<IPawn> createIterator() {
		return (Iterator<IPawn>) new PawnSetIterator(this);
	}

	@Override
	public String toString() {
		return pawns.get(0).getPosition() + " " + pawns.get(1).getPosition() + " " + pawns.get(2).getPosition() + " "
				+ pawns.get(3).getPosition() + " Color: " + ((PawnRelative) pawns.get(0)).c ;
	}

}

interface Iterator<E> {
	public void first();

	public void next();

	public boolean isDone();

	public E currentItem();
}

class PawnSetIterator implements Iterator<IPawn> {

	PawnSet ps;
	public int currentElement = 0;

	public PawnSetIterator(PawnSet ps) {
		this.ps = ps;
	}

	public int getCurrentElement() {
		return currentElement;
	}

	@Override
	public void first() {
		currentElement = 0;
	}

	@Override
	public void next() {
		currentElement++;
	}

	@Override
	public boolean isDone() {
		return currentElement > 3;
	}

	@Override
	public IPawn currentItem() {
		return ps.getPawn(currentElement);
	}

}

class PawnSetPool {

	private static ArrayList<PawnSet> pawnPool = new ArrayList<PawnSet>();
	private static int count = 0;

	static {
		pawnPool.add(new PawnSet(Colors.GREEN));
		pawnPool.add(new PawnSet(Colors.YELLOW));
		pawnPool.add(new PawnSet(Colors.BLUE));
		pawnPool.add(new PawnSet(Colors.RED));
	}

	public static PawnSet getPawnSet() throws Exception {
		if (count < 4) {
			return pawnPool.get(count++);
		} else {
			throw new Exception("No more free pawn sets!");
		}
	}

	public static void putBack() {
		count--;
	}

}
