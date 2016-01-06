package ztp.chinczyk.model.pawn;

public interface IPawn<K> {

	public K getPosition();

	public void setPosition(K position);

	public void resetPawn();

}