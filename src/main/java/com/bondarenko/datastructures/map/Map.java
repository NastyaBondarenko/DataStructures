package com.bondarenko.datastructures.map;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {

    V put(K key, V value);

    V remove(K key);

    V get(K key);

    boolean containsKey(K key);

    int size();

    boolean isEmpty();

    interface Entry<K, V> {
        K getKey();

        V getValue();

        void setValue(V value);
    }
}
