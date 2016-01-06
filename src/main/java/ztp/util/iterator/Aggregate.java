package ztp.util.iterator;

public interface Aggregate<E> {
	Iterator<E> createIterator();
}