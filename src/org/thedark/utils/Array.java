package org.thedark.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Array<T> implements Iterable<T>, Cloneable, RandomAccess, Serializable {

	private static final long serialVersionUID = 7753494750744059907l;

	public static final int MAXIMUM_SIZE = 268435452;
	public static final int INCREMENT_SIZE = 16;

	private Object[] items;
	private int length;
	private Random randomizer;

	/*
	 * 
	 * TODO:
	 * flat(depth)
	 * shift(places)
	 * remove(index, count)
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
	
	private int wrap(int index) {
		
		return (index < 0 ? length : 0) + index % length;
		
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

		return (T) items[wrap(index)];

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

	public void sort(Comparator<T> comparator) {

		List<T> arr = toList();
		arr.sort(comparator);

		length = arr.size();
		items = new Object[length];

		for (int i = 0; i < length; i++) items[i] = arr.get(i);

	}
	
	public <U> U reduce(BiFunction<T, U, U> accumulator, U initial) {
		
		if(length == 0) return initial;
		
		U result = initial;
		for(int i = 0; i < length; i++) result = accumulator.apply(get(i), result);
		
		return result;
		
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

	@SuppressWarnings("unchecked")
	public String join(String joiner) {

		if (length == 0) return "";

		String result = "";
		int l = length - 1;
		
		// length != 0 so items[0] exists
		boolean isArray = items[0].getClass().isArray();
		
		for (int i = 0; i < l; i++) {
			
			Object item = items[i];
			
			if(isArray) {
				
				result += (item == null ? "null" : new Array<T>((T[]) item).toString()) + joiner;
				
			} else {
				
				result += (item == null ? "null" : item.toString()) + joiner;
				
			}
			
		}
		result += items[l];

		return result;

	}

	public String join() {

		return join(", ");

	}

	public void repeat(int times) {

		if (times < 0) throw new IllegalArgumentException("Illegal negative repeat count " + times);

		if (times == 0) {

			clear();

		} else {

			T[] copy = toArray();

			length = length * times;
			items = new Object[length];

			for (int j = 0; j < times; j++) for (int i = 0; i < copy.length; i++) {

				items[j * copy.length + i] = copy[i];

			}

		}

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

	public Stream<T> toStream() {

		return toList().stream();

	}
	
	public LinkedList<T> toLinkedList() {
		
		return new LinkedList<T>(toList());
		
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
		
	@SuppressWarnings("unchecked")
	public Array<T> slice(int start, int end) {
		
		if(start >= length) throw new ArrayIndexOutOfBoundsException(end);
		if(end >= length) throw new ArrayIndexOutOfBoundsException(end);
		
		int wstart = wrap(start);
		int wend = wrap(end);
		
		if(wend < wstart) throw new IllegalArgumentException("Start index " + start + " is larger than end index " + end);
		
		Array<T> portion = new Array<T>();
				
		for(int i = wstart; i <= wend; i++) {
			
			portion.add(at(i));
			
		}
		
		return portion;
		
	}
	
	public Array<T> slice(int start) {
		
		return slice(start, length - 1);
		
	}
	
	@SuppressWarnings("unchecked")
	public Array<Array<T>> chunk(int size) {
		
		if(size < 0 || size > length) throw new IllegalArgumentException("Illegal chunk size " + size);
		
		int len = (int) Math.ceil((double) length / size);
		
		Array<Array<T>> chunked = new Array<Array<T>>();
		
		for(int j = 0; j < len; j++) {
			
			Array<T> arr = new Array<T>(size);
			
			int next = (j + 1) * len;
			if(next > length) next = length;
			
			for(int i = j * len; i < next; i++) arr.add(get(i));
			
			chunked.add(arr);
			
		}
		
		return chunked;
		
	}
	
	public void shuffle() {
		
		Object[] shuffled = new Object[length];
		
		// More efficient since most times you don't need to shuffle arrays
		if(randomizer == null) randomizer = new Random();
		
		for(int i = 0; i < length; i++) {
						
			int rand = randomizer.nextInt(length - i);
			
			Object result = items[rand];
			
			items[rand] = items[length - i - 1];
			shuffled[i] = result;
			
		}
		
		items = shuffled;
		
	}

	public Array<T> clone() {

		return new Array<T>(toArray());

	}

	public boolean equals(Array<T> other) {

		if (length != other.size()) return false;

		for (int i = 0; i < length; i++) if (!items[i].equals(other.get(i))) return false;

		return true;

	}

	public String toString() {

		if (length == 0) return "[ ]";

		return "[ " + join() + " ]";

	}

	@Override
	public Iterator<T> iterator() {

		return new ArrayIterator<T>(this);

	}

}