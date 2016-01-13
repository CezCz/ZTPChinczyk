package ztp.chinczyk.model.pawn;

import java.io.Serializable;

public interface IPawn<K> extends Serializable{

	K getPosition();

	void setPosition(K position);

	void resetPawn();
	
	boolean isInFinish();

}