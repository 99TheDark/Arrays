package org.thedark.Array;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

	private Array<T> array;
	private int cursor;

	public ArrayIterator(Array<T> array) {

		this.array = array;

		cursor = 0;

	}

	@Override
	public boolean hasNext() {

		return cursor < array.size() - 1;

	}

	@Override
	public T next() {

		cursor++;
		return array.get(cursor);

	}

}