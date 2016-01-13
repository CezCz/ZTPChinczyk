package ztp.chinczyk.model.pawn;

import java.util.EnumMap;
import java.util.Map;

import jdk.nashorn.internal.ir.TernaryNode;
import ztp.chinczyk.model.util.Colors;

public class PawnRelative implements IPawn<Integer> {

	private static Map<Colors, Integer> relativeOffsetMap = new EnumMap<Colors, Integer>(Colors.class);

	static {
		relativeOffsetMap.put(Colors.GREEN, 0);
		relativeOffsetMap.put(Colors.BLUE, 10);
		relativeOffsetMap.put(Colors.YELLOW, 20);
		relativeOffsetMap.put(Colors.RED, 30);
	}

	private IPawn<Integer> p;
	private Colors c;

	public Colors getColor() {
		return c;
	}

	public PawnRelative(IPawn<Integer> p, Colors c) {
		this.p = p;
		this.c = c;
	}

	@Override
	public Integer getPosition() {
		int k = ((int) p.getPosition() + relativeOffsetMap.get(c));
		if (k > 40) {
			return (k % 41) + 1;
		}
		return k % 41;
	}

	@Override
	public void setPosition(Integer relPosition) {
		int absPos = ((((relPosition - relativeOffsetMap.get(c)) % 41) + 41) % 41);

		absPos = (absPos == 0) ? 1 : absPos;

		p.setPosition(absPos);
	}

	@Override
	public void resetPawn() {
		p.resetPawn();
	}

	public Integer getAbsolutePosition() {
		return p.getPosition();
	}

	@Override
	public boolean isInFinish() {
		return p.isInFinish();
	}

}