package org.thedark.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class Array<T> implements Iterable<T> {

	public static final int INCREMENT_SIZE = 10;

	private Object[] items;
	private int length;

	/*
	 * 
	 * TODO:
	 * sort()
	 * sort(comparator)
	 * repeat(count)
	 * 
	 * Add JavaDoc
	 * 
	 */

	public Array() {

		items = new Object[INCREMENT_SIZE];
		length = 0;

	}

	public Array(int initialSize) {

		if (initialSize < 0) throw new IllegalArgumentException("Illegal initial size " + initialSize);

		items = new Object[initialSize];
		length = 0;

	}

	public Array(T[] arr) {

		length = arr.length;
		items = new Object[length];

		for (int i = 0; i < length; i++) items[i] = arr[i];

	}

	public Array(Array<T> arr) {

		length = arr.size();
		items = new Object[length];

		for (int i = 0; i < length; i++) items[i] = arr.get(i);

	}

	public Array(Iterable<T> iterable) {

		ArrayList<T> arr = new ArrayList<T>();
		for (T val : iterable) arr.add(val);

		length = arr.size();
		items = new Object[length];

		for (int i = 0; i < length; i++) items[i] = arr.get(i);

	}

	private void expand() {

		Object[] expanded = new Object[items.length + INCREMENT_SIZE];
		for (int i = 0; i < items.length; i++) expanded[i] = items[i];

		items = expanded;

	}

	private void checkIndex(int index) {

		if (index < 0 || index >= length) throw new ArrayIndexOutOfBoundsException(index);

	}

	@SuppressWarnings("unchecked")
	public void add(T... arr) {

		for (T val : arr) {

			if (++length >= items.length) expand();

			items[length - 1] = val;

		}

	}

	@SuppressWarnings("unchecked")
	public T get(int index) {

		checkIndex(index);

		return (T) items[index];

	}

	public void set(T item, int index) {

		checkIndex(index);

		items[index] = item;

	}

	@SuppressWarnings("unchecked")
	public T at(int index) {

		return (T) items[(index < 0 ? length : 0) + index % length];

	}

	public void insert(T item, int index) {

		checkIndex(index);

		Object[] arr = new Object[length + 1];

		for (int i = 0; i < index; i++) arr[i] = items[i];
		for (int i = index; i < length; i++) arr[i + 1] = items[i];
		arr[index] = item;
		
		items = arr;
		length++;

	}

	@SuppressWarnings("unchecked")
	public T remove(int index) {

		checkIndex(index);

		Object[] arr = new Object[length - 1];

		Object deleted = items[index];

		for (int i = 0; i < index; i++) arr[i] = items[i];
		for (int i = index + 1; i < length; i++) arr[i - 1] = items[i];

		items = arr;
		length--;

		return (T) deleted;

	}

	public T remove(T item) {

		int idx = indexOf(item);
		if (idx == - 1) return null;

		return remove(idx);

	}

	public void removeAll(T item) {

		int idx;

		while ((idx = indexOf(item)) != - 1) remove(idx);

	}

	public void clear() {

		items = new Object[INCREMENT_SIZE];
		length = 0;

	}

	public void concat(Iterable<T> iterable) {

		for (T val : iterable) {

			if (++length >= items.length) expand();

			items[length - 1] = val;

		}

	}

	public void forEach(Consumer<? super T> consumer) {

		for (int i = 0; i < length; i++) consumer.accept(get(i));

	}

	public void forEach(Function<T, ?> function) {

		for (int i = 0; i < length; i++) function.apply(get(i));

	}

	public void every(Function<T, Boolean> function) {

		for (int i = 0; i < length; i++) if (function.apply(get(i)) == false) break;

	}

	@SuppressWarnings("unchecked")
	public Array<T> filter(Function<T, Boolean> function) {

		Array<T> arr = new Array<T>();

		for (int i = 0; i < length; i++) if (function.apply(get(i)) == true) arr.add(get(i));

		return arr;

	}

	public boolean some(Function<T, Boolean> function) {

		for (int i = 0; i < length; i++) if (function.apply(get(i)) == true) return true;

		return false;

	}

	public boolean contains(T item) {

		for (int i = 0; i < length; i++) if (items[i].equals(item)) return true;

		return false;

	}

	public boolean containsAll(Iterable<T> iterable) {

		for (T val : iterable) if (!contains(val)) return false;

		return true;

	}

	public boolean containsAny(Iterable<T> iterable) {

		for (T val : iterable) if (contains(val)) return true;

		return false;

	}

	public int count(T item) {

		int found = 0;

		for (int i = 0; i < length; i++) if (items[i].equals(item)) found++;

		return found;

	}

	public void fill(T item, int count) {

		if (count < 0) throw new IllegalArgumentException("Illegal fill count " + count);

		items = new Object[count];
		length = count;

		for (int i = 0; i < count; i++) items[i] = item;

	}

	public void reverse() {

		Object[] reversed = new Object[length];

		for (int i = 0; i < length; i++) reversed[length - i - 1] = items[i];

		items = reversed;

	}

	public int indexOf(T item, int skip) {

		if (skip < 0) throw new IllegalArgumentException("Illegal skip count " + skip);

		int found = 0;
		for (int i = 0; i < length; i++) if (items[i].equals(item)) {

			found++;
			if (found > skip) return i;

		}

		return - 1;

	}

	public int indexOf(T item) {

		return indexOf(item, 0);

	}

	public int lastIndexOf(T item, int skip) {

		if (skip < 0) throw new IllegalArgumentException("Illegal skip count " + skip);

		int found = 0;
		for (int i = length - 1; i >= 0; i--) if (items[i].equals(item)) {

			found++;
			if (found > skip) return i;

		}

		return - 1;

	}

	public int lastIndexOf(T item) {

		return lastIndexOf(item, 0);

	}

	public int[] indicesOf(T item) {

		ArrayList<Integer> indices = new ArrayList<Integer>();

		for (int i = 0; i < length; i++) if (items[i].equals(item)) indices.add(i);

		int[] arr = new int[indices.size()];

		for (int i = 0; i < indices.size(); i++) arr[i] = indices.get(i);

		return arr;

	}

	public int size() {

		return length;

	}

	public boolean isEmpty() {

		return length == 0;

	}

	public String join(String joiner) {

		String result = "";
		int l = length - 1;

		for (int i = 0; i < l; i++) result += items[i].toString() + joiner;
		result += items[l];

		return result;

	}

	public String join() {

		return join(", ");

	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {

		Object[] arr = new Object[length];

		for (int i = 0; i < length; i++) arr[i] = items[i];

		return (T[]) arr;

	}

	public List<T> toList() {

		List<T> list = new ArrayList<T>();

		for (int i = 0; i < length; i++) list.add(get(i));

		return list;

	}

	public Map<Integer, T> toMap() {

		Map<Integer, T> map = new HashMap<Integer, T>();

		for (int i = 0; i < length; i++) map.putIfAbsent(map.size(), get(i));

		return map;

	}

	public Set<T> toSet() {

		Set<T> set = new HashSet<T>();

		for (int i = 0; i < length; i++) set.add(get(i));

		return set;

	}

	public void trim(int newLength, int index) {

		if (newLength < 0 || newLength > length) throw new IllegalArgumentException("Illegal trim length " + newLength);

		Object[] arr = new Object[newLength];

		int len = newLength;
		if (index + newLength > length) len = length - index;
		for (int i = 0; i < len; i++) arr[i] = items[index + i];

		items = arr;
		length = len;

	}

	public void trim(int newLength) {

		trim(newLength, 0);

	}

	public Array<T> copy() {

		Array<T> arr = new Array<T>(length);

		for (int i = 0; i < length; i++) arr.set(arr.get(i), i);

		return arr;

	}

	public boolean equals(Array<T> other) {

		if (length != other.size()) return false;

		for (int i = 0; i < length; i++) if (!items[i].equals(other.get(i))) return false;

		return true;

	}

	public String toString() {

		return "[ " + join() + " ]";

	}

	@Override
	public Iterator<T> iterator() {

		return new ArrayIterator<T>(this);

	}

}