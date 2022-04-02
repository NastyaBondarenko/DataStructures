package com.bondarenko.datastructures.map;

import java.util.Iterator;

public interface Map<K, V> {

    V put(K key, V value);

    V remove(K key);

    V get(K key);

    boolean containsKey(K key);

    int size();

    boolean isEmpty();

    default Iterator getIterator() {
        return null;
    }
}
