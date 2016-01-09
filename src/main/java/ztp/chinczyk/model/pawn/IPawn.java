package ztp.chinczyk.model.pawn;

import java.io.Serializable;

public interface IPawn<K> extends Serializable{

	public K getPosition();

	public void setPosition(K position);

	public void resetPawn();

}