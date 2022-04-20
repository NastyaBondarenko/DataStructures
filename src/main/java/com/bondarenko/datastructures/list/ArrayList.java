package com.bondarenko.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final double DEFAULT_GROWTH_FACTOR = 2.5;
    private T[] array;
    private int size;

    private double loadFactor;

    public ArrayList() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_GROWTH_FACTOR);
    }

    public ArrayList(int initialCapacity) {
        this(initialCapacity, DEFAULT_GROWTH_FACTOR);
    }

    public ArrayList(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor < 1 ? DEFAULT_GROWTH_FACTOR : loadFactor;
        this.array = (T[]) new Object[initialCapacity];
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        ensureCapacity();
        T value = array[index];
        System.arraycopy(array, index + 1, array, index, array.length - index - 1);
        array[size - 1] = null;
        size--;
        return value;
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        ensureCapacity();
        return array[index];
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        ensureCapacity();
        T oldValue = array[index];
        array[index] = value;
        return oldValue;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        for (int i = array.length - 1; i > 0; i--) {
            if (Objects.equals(array[i], value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        for (int i = 0; i < size; i++) {
            stringJoiner.add(String.valueOf(array[i]));
        }
        return stringJoiner.toString();
    }

    private void validateIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " must be between [ " + 0 + "," + (size - 1) + "]");
        }
    }

    private void validateIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " must be between [ " + 0 + "," + size + "]");
        }
    }

    private void ensureCapacity() {
        if (size == getCapacity()) {
            T[] newArray = (T[]) new Object[(int) (array.length * loadFactor)];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    int getCapacity() {
        return array.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        int index;

        @Override
        public boolean hasNext() {
            return index < array.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Next element is not exist");
            }
            return array[index++];
        }

        @Override
        public void remove() {
            if (array[index] == null) {
                throw new IllegalArgumentException("Element is not exist");
            } else if (array[index] != null) {//???
                array[index] = null;
                size--;
            }
        }
    }
}

