package ztp.chinczyk.model.pawn;

import java.util.ArrayList;

import ztp.chinczyk.model.util.Colors;

public class PawnSetPool {

	private static ArrayList<PawnSet> pawnPool = new ArrayList<PawnSet>();
	private static int count = 0;

	static {
		for (int i = 0; i < 4; i++) {
			pawnPool.add(new PawnSet(PawnSetPool.getPawnColor()));
			count++;
		}
		count = 0;
	}

	public static PawnSet getPawnSet() throws Exception {
		if (count < 4) {
			return pawnPool.get(count++);
		} else {
			throw new Exception("No more free pawn sets!");
		}
	}

	private static Colors getPawnColor() {
		switch (count) {
		case 0:
			return Colors.GREEN;
		case 1:
			return Colors.YELLOW;
		case 2:
			return Colors.BLUE;
		case 3:
			return Colors.RED;
		default:
			return Colors.GREEN;
		}
	}

	public static void putBack() {
		count -= 1;
	}

}