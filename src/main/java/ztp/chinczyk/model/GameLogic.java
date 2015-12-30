package ztp.chinczyk.model;

public class GameLogic {

	private GameState gs;
	private final static int lastSquare = 40;
	private final static int house = 0;

	public GameLogic() {
		gs = new GameState();
	}

	public void newGame() {
		gs = new GameState();
	}

	public void addPlayer(String name) {
		gs.addPlayer(name);
	}

	public void nextPlayer() {
		gs.setCurrentPlayer();
		prePlayerMove();
	}

	public void startRound() {
		gs.setDiceRoll(Dice.rollDice());
	}

	public void doMove(int pawnNumber) {
		PawnSet ps = gs.getCurrentPlayerPawns();
		IPawn p = ps.getPawn(pawnNumber);
		boolean movable = false;
		if (isInHouse(p) && gs.getDiceRoll() == 6) {
			movable = true;
		}

		if (!movable) {
			if (isMovable(p)) {
				if (!isCollided(p, ps)) {
					movable = true;
				}
			}
		}

		if (movable) {
			p.setPosition(p.getPosition() + gs.getDiceRoll());
			sendOtherToHouse(p);
			checkWin();
			nextPlayer();
		} else {
			System.out.println("not possible");
		}
	}

	private void sendOtherToHouse(IPawn p) {
		int allPlayers = gs.getPlayerCount();
		PawnRelative myPawnRel = new PawnRelative(p, gs.getPlayerColor(gs.getCurrentPlayer()));
		for (int i = 0; i < allPlayers; i++) {
			if (i != gs.getCurrentPlayer()) {
				PawnSet ps = gs.getPlayersPawns(i);
				Iterator<IPawn> psi = ps.createIterator();
				psi.first();
				while (!psi.isDone()) {
					IPawn p2 = new PawnRelative(psi.currentItem(), gs.getPlayerColor(i));
					
					if (p2.getPosition() == myPawnRel.getPosition()) {
						p2.resetPawn();
						return;
					}
					psi.next();
				}
			}
		}
	}

	public void checkWin() {
		PawnSet ps = gs.getCurrentPlayerPawns();
		Iterator<IPawn> it = ps.createIterator();
		it.first();
		boolean winner = true;
		while (!it.isDone()) {
			if (it.currentItem().getPosition() < 40) {
				winner = false;
			}
			it.next();
		}
		System.out.println("winner?");
		if (winner) {
			System.out.println("Winner set");
			gs.setWinner();
			gs.setFinished(true);
		}
	}

	public void setGs(GameState gs) {
		this.gs = gs;
	}

	public void prePlayerMove() {
		int roll = Dice.rollDice();
		gs.setDiceRoll(roll);
		checkIfSix();
		checkIfAnyMovePossible();
	}

	private void checkIfAnyMovePossible() {
		PawnSet pw = gs.getCurrentPlayerPawns();
		gs.setAnyMovable(false);
		Iterator<IPawn> p = pw.createIterator();
		p.first();
		while (!p.isDone()) {
			if (p.currentItem().getPosition() == house && gs.getDiceRoll() == 6) {
				gs.setAnyMovable(true);
				break;
			}
			if (!isInHouse(p.currentItem())) {
				if (isMovable(p.currentItem())) {
					if (!isCollided(p.currentItem(), pw)) {
						gs.setAnyMovable(true);
						break;
					}
				}
			}
			p.next();
		}
	}

	private boolean isInHouse(IPawn p) {
		return p.getPosition() == house;
	}

	private boolean isMovable(IPawn p) {
		return p.getPosition() + gs.getDiceRoll() < 45;
	}

	private boolean isCollided(IPawn p, PawnSet pw) {
		Iterator<IPawn> k = pw.createIterator();
		k.first();
		boolean isCollided = false;
		while (!k.isDone()) {
			if (p != k.currentItem()) {
				if (p.getPosition() + gs.getDiceRoll() == k.currentItem().getPosition()) {
					isCollided = true;
					break;
				}
			}
			k.next();
		}
		return isCollided;
	}

	private void checkIfSix() {
		if (gs.getDiceRoll() == 6) {
			gs.setRepeat(true);
		} else {
			gs.setRepeat(false);
		}
	}

	public GameState getGs() {
		return gs;
	}

	public void doPass() {
		nextPlayer();
	}

}
