package ztp.chinczyk.presenter;

import java.awt.Point;
import java.util.HashMap;
import java.util.Set;

import ztp.chinczyk.model.pawn.IPawn;
import ztp.chinczyk.model.pawn.PawnRelative;
import ztp.chinczyk.model.util.Colors;
import ztp.chinczyk.view.PawnColor;

public class PawnView implements IPawn<Point> {

	private IPawn<Integer> p;
	private int pawnNumber;
	private PawnColor pc;
	
	public PawnColor getColor() {
		return pc;
	}

	PawnView(IPawn<Integer> p, int pawnNumber, PawnColor pc) {
		this.pawnNumber = pawnNumber;
		this.p = p;
		this.pc = pc;
	}

	static HashMap<Integer, Point> pointMap = new HashMap<>();

	static HashMap<Integer, Point> greenHouseMap = new HashMap<>();
	static HashMap<Integer, Point> blueHouseMap = new HashMap<>();
	static HashMap<Integer, Point> yellowHouseMap = new HashMap<>();
	static HashMap<Integer, Point> redHouseMap = new HashMap<>();

	static HashMap<Integer, Point> greenFinishMap = new HashMap<>();
	static HashMap<Integer, Point> blueFinishMap = new HashMap<>();
	static HashMap<Integer, Point> yellowFinishMap = new HashMap<>();
	static HashMap<Integer, Point> redFinishMap = new HashMap<>();

	static {

		pointMap.put(1, new Point(200, 500));
		pointMap.put(2, new Point(200, 450));
		pointMap.put(3, new Point(200, 400));
		pointMap.put(4, new Point(200, 350));
		pointMap.put(5, new Point(200, 300));
		pointMap.put(6, new Point(150, 300));
		pointMap.put(7, new Point(100, 300));
		pointMap.put(8, new Point(50, 300));
		pointMap.put(9, new Point(0, 300));
		pointMap.put(10, new Point(0, 250));
		pointMap.put(11, new Point(0, 200));
		pointMap.put(12, new Point(50, 200));
		pointMap.put(13, new Point(100, 200));
		pointMap.put(14, new Point(150, 200));
		pointMap.put(15, new Point(200, 200));
		pointMap.put(16, new Point(200, 150));
		pointMap.put(17, new Point(200, 100));
		pointMap.put(18, new Point(200, 50));
		pointMap.put(19, new Point(200, 0));
		pointMap.put(20, new Point(250, 0));
		pointMap.put(21, new Point(300, 0));
		pointMap.put(22, new Point(300, 50));
		pointMap.put(23, new Point(300, 100));
		pointMap.put(24, new Point(300, 150));
		pointMap.put(25, new Point(300, 200));
		pointMap.put(26, new Point(350, 200));
		pointMap.put(27, new Point(400, 200));
		pointMap.put(28, new Point(450, 200));
		pointMap.put(29, new Point(500, 200));
		pointMap.put(30, new Point(500, 250));
		pointMap.put(31, new Point(500, 300));
		pointMap.put(32, new Point(450, 300));
		pointMap.put(33, new Point(400, 300));
		pointMap.put(34, new Point(350, 300));
		pointMap.put(35, new Point(300, 300));
		pointMap.put(36, new Point(300, 350));
		pointMap.put(37, new Point(300, 400));
		pointMap.put(38, new Point(300, 450));
		pointMap.put(39, new Point(300, 500));
		pointMap.put(40, new Point(250, 500));

		greenHouseMap.put(0, new Point(0, 500));
		greenHouseMap.put(1, new Point(50, 500));
		greenHouseMap.put(2, new Point(0, 450));
		greenHouseMap.put(3, new Point(50, 450));

		blueHouseMap.put(0, new Point(0, 50));
		blueHouseMap.put(1, new Point(50, 50));
		blueHouseMap.put(2, new Point(0, 0));
		blueHouseMap.put(3, new Point(50, 0));

		yellowHouseMap.put(0, new Point(450, 50));
		yellowHouseMap.put(1, new Point(500, 50));
		yellowHouseMap.put(2, new Point(450, 0));
		yellowHouseMap.put(3, new Point(500, 0));

		redHouseMap.put(0, new Point(450, 500));
		redHouseMap.put(1, new Point(500, 500));
		redHouseMap.put(2, new Point(450, 450));
		redHouseMap.put(3, new Point(500, 450));

		greenFinishMap.put(41, new Point(250, 450));
		greenFinishMap.put(42, new Point(250, 400));
		greenFinishMap.put(43, new Point(250, 350));
		greenFinishMap.put(44, new Point(250, 300));

		blueFinishMap.put(41, new Point(50, 250));
		blueFinishMap.put(42, new Point(100, 250));
		blueFinishMap.put(43, new Point(150, 250));
		blueFinishMap.put(44, new Point(200, 250));

		yellowFinishMap.put(41, new Point(250, 50));
		yellowFinishMap.put(42, new Point(250, 100));
		yellowFinishMap.put(43, new Point(250, 150));
		yellowFinishMap.put(44, new Point(250, 200));

		redFinishMap.put(41, new Point(300, 250));
		redFinishMap.put(42, new Point(350, 250));
		redFinishMap.put(43, new Point(400, 250));
		redFinishMap.put(44, new Point(450, 250));

	}

	@Override
	public Point getPosition() {

		Colors c = ((PawnRelative) p).getColor();
		int pos = (int) p.getPosition();

		if (pos == 0) {
			switch (c) {
			case GREEN:
				return greenHouseMap.get(pawnNumber);
			case BLUE:
				return blueHouseMap.get(pawnNumber);
			case YELLOW:
				return yellowHouseMap.get(pawnNumber);
			case RED:
				return redHouseMap.get(pawnNumber);
			}
		} else if (pos < 41) {
			return pointMap.get(pos);
		} else {
			switch (c) {
			case GREEN:
				return greenFinishMap.get(pos);
			case BLUE:
				return blueFinishMap.get(pos);
			case YELLOW:
				return yellowFinishMap.get(pos);
			case RED:
				return redFinishMap.get(pos);
			}
		}

		return null;
	}

	@Override
	public void setPosition(Point position) {

		Colors c = ((PawnRelative) p).getColor();

		// logic of this is quite complex but i hope it works
		if ((position.x == 250 || position.y == 250)
				&& (position.x < 500 && position.x > 50 && position.y > 50 && position.y < 500)) {
			Set<Integer> ks;
			switch (c) {
			case GREEN:
				ks = greenFinishMap.keySet();
				for (Integer i : ks) {
					if (greenFinishMap.get(i).equals(position)) {
						p.setPosition(i);
					}
				}
				break;
			case BLUE:
				ks = blueFinishMap.keySet();
				for (Integer i : ks) {
					if (blueFinishMap.get(i).equals(position)) {
						p.setPosition(i);
					}
				}
				break;
			case YELLOW:
				ks = yellowFinishMap.keySet();
				for (Integer i : ks) {
					if (yellowFinishMap.get(i).equals(position)) {
						p.setPosition(i);
					}
				}
				break;
			case RED:
				ks = redFinishMap.keySet();
				for (Integer i : ks) {
					if (redFinishMap.get(i).equals(position)) {
						p.setPosition(i);
					}
				}
				break;
			}
		} else {
			Set<Integer> ks = pointMap.keySet();
			for(Integer i : ks) {
				if(pointMap.get(i).equals(position)) {
					p.setPosition(i);
				}
			}
		}

	}

	@Override
	public void resetPawn() {
		p.resetPawn();
	}

}