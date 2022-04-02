package com.bondarenko.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final int DEFAULT_GROWTH_FACTOR = 2;
    private T array[];
    int size;

    public ArrayList() {
        array = (T[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index);
        checkCapacity();
        T[] newArray = (T[]) new Object[array.length * DEFAULT_GROWTH_FACTOR];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
            System.arraycopy(array, index, newArray, index + 1, size);
        }
        array = newArray;
        array[index] = value;
        size++;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        checkCapacity();
        for (int i = 0; i < array.length - 1; i++) {
            if (i == index) {
                array[index] = array[index + 1];
                T[] newArray = (T[]) new Object[array.length];
                newArray[i] = array[i];
                System.arraycopy(newArray, index, array, index + 1, size);
            }
        }
        size--;
        return (T) array[index];
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        checkCapacity();
        T value = array[index];
        return value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        checkCapacity();
        T oldValue = array[index];
        array[index] = value;
        return oldValue;
    }

    @Override
    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            return array[i].equals(value) ? true : false;
        }
        return true;
    }

    @Override
    public int indexOf(T value) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].equals(value)) {
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

    @Override//
    public boolean isEmpty() {
        for (int i = 0; i < array.length - 1; i++) {
            return Objects.equals(array[i], null) ? true : false;
        }
        return true;
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
        if (index > array.length - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + "has to be between 0 and size " + size);
        }
    }

    private void checkCapacity() {//+++ але провірить ленг -1 чи просто ленг
        if (size == array.length) {
            T[] newArray = (T[]) new Object[(int) array.length * DEFAULT_GROWTH_FACTOR + 1];
            System.arraycopy(array, 0, newArray, 0, array.length - 1);
            array = newArray;
        }
    }

    @Override
    public Iterator<T> getIterator() {
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
            if (array[index] == null) {
                throw new NoSuchElementException("Next element is not exist");
            }
            return array[index++];
        }

        @Override
        public void remove() {
            if (array[index] == null) {
                throw new IllegalArgumentException("Element is not avalible");
            } else if (array[index] != null) {//???
                array[index] = null;
                size--;
            }
        }
    }
}
