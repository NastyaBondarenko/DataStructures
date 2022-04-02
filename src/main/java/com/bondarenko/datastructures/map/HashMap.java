package com.bondarenko.datastructures.map;

import java.util.ArrayList;
import java.util.Iterator;

public class HashMap<K, V> implements Map<K, V> {
    private static final int GROWTH_FACTOR = 2;
    private static final double LOAD_FACTOR = 0.75;
    private static final int DEFAULT_INITIAL_CAPACITY = 5;
    private ArrayList<Entry<K, V>>[] buckets;
    private int size;

    public HashMap() {
        buckets = new ArrayList[DEFAULT_INITIAL_CAPACITY];
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            buckets[i] = new ArrayList<>();
            if (buckets.length * LOAD_FACTOR < size) {
                buckets[buckets.length] = buckets[DEFAULT_INITIAL_CAPACITY * GROWTH_FACTOR];
            }
        }
    }

    @Override
    public V put(K key, V value) {
        ArrayList<Entry<K, V>> bucket = buckets[getIndexOfBucket(key)];
        Entry<K, V> newEntry = new Entry<>(key, value);
        int index = bucket.indexOf(newEntry);
        V result;
        if (index == -1) {
            bucket.add(newEntry);
            result = null;
            size++;
        } else {
            Entry<K, V> oldValue = bucket.get(index);
            result = oldValue.value;
            oldValue.value = value;
        }
        return result;
    }

    @Override
    public V remove(K key) {
        ArrayList<Entry<K, V>> bucket = buckets[getIndexOfBucket(key)];
        Entry<K, V> newEntry = new Entry<>(key, null);
        int index = bucket.indexOf(newEntry);
        if (index == -1) {
            return null;
        } else {
            V result;
            result = (V) bucket.get(index);
            bucket.remove(index);
            size--;
            return result;
        }
    }

    @Override
    public V get(K key) {
        ArrayList bucket = buckets[getIndexOfBucket(key)];
        Entry<K, V> newEntry = new Entry<>(key, null);
        int index = bucket.indexOf(newEntry);
        V result;
        if (index == -1) {
            return null;
        } else {
            Entry oldValue = (Entry) bucket.get(index);
            result = (V) oldValue.value;
            oldValue.value = oldValue;
        }
        return result;
    }

    @Override
    public boolean containsKey(K key) {// тернарний оператор
        ArrayList bucket = buckets[getIndexOfBucket(key)];
        Entry<K, V> newEntry = new Entry<>(key, null);
        int index = bucket.indexOf(newEntry);
        if (index == -1) {
            return false;
        } else
            return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0) ? true : false;
    }

    public V putIfAbsent(K key, V value) {
        Entry<K, V> result = findBucketByKey(key);
        if (result == null) {
            addBucket(key, value);
            return null;
        }
        return result.value;
    }

    private int getIndexOfBucket(K key, ArrayList[] buckets) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode() % buckets.length);
    }

    private int getIndexOfBucket(K key) {
        return getIndexOfBucket(key, buckets);
    }

    private Entry findBucketByKey(K key) {
        int index = getIndexOfBucket(key);
        if (buckets[index] != null) {
            for (Entry<K, V> bucket : buckets[index]) {
                if (key.equals(bucket.key)) {
                    return bucket;
                }
            }
        }
        return null;
    }

    private void addBucket(K key, V value) {
        add(key, value, buckets);
        size++;
    }

    private void add(K key, V value, ArrayList<Entry<K, V>>[] newBuckets) {
        int index = getIndexOfBucket(key, newBuckets);
        if (newBuckets[index] == null) {
            newBuckets[index] = new ArrayList<>();
        }
        newBuckets[index].add(new Entry<>(key, value));
    }

    @Override
    public Iterator<Entry<K, V>> getIterator() {
        return new HashMapIterator();
    }

    public class HashMapIterator implements Iterator<Entry<K, V>> {
        private HashMap<K, V> map;
        private Iterator<HashMap.Entry<K, V>> iterator;
        private HashMap.Entry<K, V> entry;

        public HashMapIterator(HashMap<K, V> map, Iterator<Entry<K, V>> iterator) {
            this.map = map;
            this.iterator = iterator;
        }

        public HashMapIterator() {
        }

        @Override
        public boolean hasNext() {
            if (iterator == null) {
                return false;
            }
            return iterator.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            if (iterator == null)
                return null;
            entry = iterator.next();
            return entry;
        }

        @Override
        public void remove() {
            if (iterator == null || entry == null) {
                return;
            }
            map.remove(entry.getKey());
            iterator.remove();
        }
    }

    private static class Entry<K, V> {// private
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
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
                Entry that = (Entry) object;
                if (!key.equals(that.key)) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }
}
