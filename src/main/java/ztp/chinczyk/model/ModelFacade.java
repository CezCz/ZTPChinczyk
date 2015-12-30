package ztp.chinczyk.model;

public class ModelFacade {
	
	private GameLogic g = new GameLogic();
	
	public ModelFacade() {
		g = new GameLogic();
	}
	
	public GameLogic getGameLogic() {
		return g;
	}
	

}
