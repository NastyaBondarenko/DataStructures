package com.bondarenko.datastructures.map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedHashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 5;
    private static final int DEFAULT_GROWTH_FACTOR = 2;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private Node<K, V>[] buckets;
    private int size;

    public LinkedHashMap() {
        buckets = new Node[DEFAULT_INITIAL_CAPACITY];
        if (buckets.length * DEFAULT_LOAD_FACTOR < size) {
            buckets[buckets.length] = buckets[DEFAULT_INITIAL_CAPACITY * DEFAULT_GROWTH_FACTOR];
        }
    }

    private int hash(final K key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    @Override
    public V put(K key, V value) {
        Node<K, V> newEntry = new Node<>(key, value);
        int index = newEntry.hash();
        V result;
        if (buckets[index] == null) {
            buckets[index] = new Node<>(null, null);
            buckets[index].getNodes().add(newEntry);
            size++;
            result = null;
        } else {
            List<Node<K, V>> bucket = buckets[index].getNodes();
            Node<K, V> oldValue = bucket.get(index);
            result = (V) oldValue.value;
            oldValue.value = value;
        }
        return result;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        V result = null;
        if (buckets[index] == null) {
            result = null;
        } else {
            List<Node<K, V>> nodeList = buckets[index].getNodes();
            for (Node<K, V> entry : nodeList) {
                if (key.equals(entry.getKey())) {
                    nodeList.remove(entry);
                    result = entry.value;
                }
            }
        }
        size--;
        return result;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        if (buckets[index] != null) {
            List<Node<K, V>> list = buckets[index].getNodes();
            for (Node<K, V> entry : list) {
                if (key.equals(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = hash(key);
        if (buckets[index] != null) {
            List<Node<K, V>> list = buckets[index].getNodes();
            for (Node<K, V> entry : list) {
                if (key.equals(entry.getKey())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0) ? true : false;
    }

    @Override
    public Iterator getIterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<V> {
        int counter;
        int valuesCounter;
        Iterator<Node<K, V>> iterator;

        @Override
        public boolean hasNext() {
            if (valuesCounter == size)
                return false;
            if (iterator == null || iterator.hasNext()) {
                if (moveToNextBucket()) {
                    iterator = buckets[counter].getNodes().iterator();
                } else {
                    return false;
                }
            }
            return iterator.hasNext();
        }

        private boolean moveToNextBucket() {
            counter++;
            while (counter < buckets.length && buckets[counter] == null) {
                counter++;
            }
            return counter < buckets.length && buckets[counter] != null;
        }

        @Override
        public V next() {
            valuesCounter++;
            return iterator.next().getValue();
        }

        @Override
        public void remove() {
            iterator.remove();
        }
    }

    private class Node<K, V> {
        private List<Node<K, V>> nodes;
        private int hash;
        private K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<>();
        }

        private List<Node<K, V>> getNodes() {
            return nodes;
        }

        private int hash() {//хеш-функція//hash
            return hashCode() % buckets.length;
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            hash = Math.abs(key.hashCode() % buckets.length);
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this == o) {
                return true;
            }
            if (o.getClass() == Node.class) {
                Node that = (Node) o;
                if (!key.equals(that.key)) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }
}
