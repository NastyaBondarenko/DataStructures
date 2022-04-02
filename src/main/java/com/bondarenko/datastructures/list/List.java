package com.bondarenko.datastructures.list;

import java.util.Iterator;

public interface List<T> {
    void add(T value);

    void add(T value, int index);

    T remove(int index);

    T get(int index);

    T set(T value, int index);

    boolean contains(T value);

    int indexOf(T value);

    int lastIndexOf(T value);

    void clear();

    int size();

    boolean isEmpty();

    String toString();

    Iterator getIterator();
}
