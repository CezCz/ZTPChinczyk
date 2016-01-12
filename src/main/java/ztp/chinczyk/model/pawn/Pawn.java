package ztp.chinczyk.model.pawn;

public class Pawn implements IPawn<Integer> {
	private int position;
	private boolean isInHouse;

	public Pawn() {
		resetPawn();
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public void resetPawn() {
		position = 0;
	}

	@Override
	public boolean isInFinish() {
		if(position > 40) {
			return true;
		} else {
			return false;
		}
	}

}