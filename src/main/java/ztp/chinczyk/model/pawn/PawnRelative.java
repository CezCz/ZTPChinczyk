package ztp.chinczyk.model.pawn;

import java.util.EnumMap;
import java.util.Map;

import ztp.chinczyk.model.util.Colors;

public class PawnRelative implements IPawn<Integer> {

	// offset needs to be mod 40
	private static Map<Colors, Integer> relativeOffsetMap = new EnumMap<Colors, Integer>(Colors.class);

	static {
		relativeOffsetMap.put(Colors.GREEN, 0);
		relativeOffsetMap.put(Colors.BLUE, 10);
		relativeOffsetMap.put(Colors.YELLOW, 20);
		relativeOffsetMap.put(Colors.RED, 30);
	}

	private IPawn p;
	private Colors c;
	
	public Colors getColor() {
		return c;
	}

	public PawnRelative(IPawn p, Colors c) {
		this.p = p;
		this.c = c;
	}

	@Override
	public Integer getPosition() {
		return ((int)p.getPosition() + relativeOffsetMap.get(c)) % 40;
	}

	@Override
	public void setPosition(Integer relPosition) {

		int absPos = ((((relPosition - relativeOffsetMap.get(c)) % 40) + 40) % 40);
		p.setPosition(absPos);

	}

	@Override
	public void resetPawn() {
		p.resetPawn();
	}

}