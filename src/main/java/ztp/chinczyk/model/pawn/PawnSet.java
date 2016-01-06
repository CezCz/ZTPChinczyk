package ztp.chinczyk.model.pawn;

import java.util.ArrayList;

import ztp.chinczyk.model.util.Colors;
import ztp.util.iterator.Aggregate;
import ztp.util.iterator.Iterator;

public class PawnSet implements Aggregate<IPawn<Integer>> {
	private ArrayList<IPawn> pawns;
	private Colors c;
	
	public PawnSet(Colors c) {
		this.c = c;
		pawns = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			pawns.add(new Pawn());
		}
	}

	public IPawn getPawn(int k) {
		return pawns.get(k);
	}

	public Iterator<IPawn<Integer>> createIterator() {
		return (Iterator<IPawn<Integer>>) new PawnSetIterator(this);
	}

	@Override
	public String toString() {
		return pawns.get(0).getPosition() + " " + pawns.get(1).getPosition() + " " + pawns.get(2).getPosition() + " "
				+ pawns.get(3).getPosition();
	}
	
	public Colors getPawnColor() {
		return c;
	}

}