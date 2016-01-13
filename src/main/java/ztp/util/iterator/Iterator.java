package ztp.util.iterator;

public interface Iterator<E> {
	void first();

	void next();

	boolean isDone();

	E currentItem();
}