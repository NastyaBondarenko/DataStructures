package com.bondarenko.datastructures.map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {
    private static final double GROWTH_FACTOR = 2.5;
    private static final double LOAD_FACTOR = 0.75;
    private static final int DEFAULT_INITIAL_CAPACITY = 5;
    private Entry<K, V>[] buckets;
    private int size;
    private double loadFactor;

    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public HashMap(int capacity, double loadFactor) {
        this.loadFactor = loadFactor;
        this.buckets = new Entry[capacity];
    }

    @Override
    public V put(K key, V value) {
        growCapacity();
        int index = getIndexOfBucket(key);
        if (buckets[index] == null) {
            buckets[index] = new Entry<>(key, value);
            size++;
            return null;
        }
        Entry<K, V> currentEntry = buckets[index];
        while (currentEntry != null) {
            if (Objects.equals(currentEntry.key, key)) {
                V oldValue = currentEntry.getValue();
                currentEntry.setValue(value);
                return oldValue;
            }
            currentEntry = currentEntry.next;
        }
        currentEntry.next = new Entry<>(key, value);
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        Entry<K, V> entry = removeEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Override
    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry != null) {
            return entry.getValue();
        } else {
            return null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Entry<K, V> getEntry(K key) {
        int index = getIndexOfBucket(key);
        if (buckets[index] == null) {
            return null;
        }
        Entry<K, V> currentEntry = buckets[index];
        while (currentEntry != null) {
            if (Objects.equals(currentEntry.key, key)) {
                return currentEntry;
            }
            currentEntry = currentEntry.next;
        }
        return null;
    }

    private int getIndexOfBucket(K key) {
        if (key == null) {
            return 0;
        }
        int hashCode = key.hashCode();
        if (hashCode == Integer.MIN_VALUE) {
            return 0;
        }
        return Math.abs(key.hashCode() % buckets.length);
    }

    private Entry<K, V> removeEntry(K key) {
        int index = getIndexOfBucket(key);
        Entry<K, V> currentEntry = buckets[index];
        Entry<K, V> previousEntry = null;
        while (currentEntry != null) {
            if (Objects.equals(currentEntry.key, key)) {
                if (currentEntry == buckets[index]) {
                    buckets[index] = currentEntry.next;
                } else if (currentEntry.next != null) {
                    previousEntry.next = currentEntry.next;
                } else {
                    previousEntry.next = null;
                }
                size--;
                return currentEntry;
            }
            previousEntry = currentEntry;
            currentEntry = currentEntry.next;
        }
        return null;
    }

    private void growCapacity() {
        if (buckets.length * LOAD_FACTOR < size) {
            HashMap<K, V> hashMap = new HashMap<>(buckets.length * (int) GROWTH_FACTOR, loadFactor);
            for (Map.Entry<K, V> entry : this) {
                hashMap.put(entry.getKey(), entry.getValue());
            }
            buckets = hashMap.buckets;
        }
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<Map.Entry<K, V>> {
        private Entry<K, V> current = buckets[0];
        private boolean remove;
        private int index;
        private Entry<K, V> prevEntry;

        @Override
        public boolean hasNext() {
            for (int i = index; i < buckets.length; i++) {
                current = buckets[i];
                if (current != null) return true;
            }
            return false;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Next element is not exist");
            }
            prevEntry = current;
            current = current.next;
            if (current != null) {
                index++;
            }
            remove = true;
            return current;
        }

        @Override
        public void remove() {
            if (!remove) {
                throw new IllegalStateException("The method next() not used previously");
            }
            HashMap.this.remove(prevEntry.key);
            remove = false;
        }
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (object.getClass() == Entry.class) {
                Entry<K, V> that = (Entry<K, V>) object;
                return key.equals(that.key);
            }
            return false;
        }
    }
}





