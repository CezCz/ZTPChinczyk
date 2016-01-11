package ztp.chinczyk.model.pawn;

import ztp.util.iterator.Iterator;

public class PawnSetIterator<N> implements Iterator<IPawn<N>> {

	PawnSet ps;
	public int currentElement = 0;

	public PawnSetIterator(PawnSet ps) {
		this.ps = ps;
	}

	public int getCurrentElementNumber() {
		return currentElement;
	}

	@Override
	public void first() {
		currentElement = 0;
	}

	@Override
	public void next() {
		currentElement++;
	}

	@Override
	public boolean isDone() {
		return currentElement > 3;
	}

	@Override
	public IPawn currentItem() {
		return ps.getPawn(currentElement);
	}

}