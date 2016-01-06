package ztp.chinczyk.model;

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

}
