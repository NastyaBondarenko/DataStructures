package com.bondarenko.datastructures.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {
    private static final double GROWTH_FACTOR = 2.5;
    private static final double LOAD_FACTOR = 0.75;
    private static final int DEFAULT_INITIAL_CAPACITY = 5;
    private ArrayList<Entry<K, V>>[] buckets;
    private int size;

    private double growFactor;
    private double loadFactor;

    @SuppressWarnings("unchecked")
    public HashMap(int capacity, double growFactor, double loadFactor) {
        this.growFactor = growFactor;
        this.loadFactor = loadFactor;
        buckets = new ArrayList[capacity];
    }

    public HashMap() {
        this(DEFAULT_INITIAL_CAPACITY, LOAD_FACTOR, GROWTH_FACTOR);
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> oldEntry = getEntry(key);
        if (oldEntry != null) {
            V oldValue = oldEntry.getValue();
            oldEntry.setValue(value);
            return oldValue;
        } else if (size > buckets.length * loadFactor) {
            growCapacity();
        }
        addEntryToBucket(new Entry<>(key, value));
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

    private void addEntryToBucket(Entry<K, V> entry) {
        addEntryToBucket(entry, buckets);
    }

    private void addEntryToBucket(Entry<K, V> entry, ArrayList<Entry<K, V>>[] newBuckets) {
        K key = entry.getKey();
        int indexOfBucket = getIndexOfBucket(key);
        if (newBuckets[indexOfBucket] == null) {
            newBuckets[indexOfBucket] = new ArrayList<>();
        }
        newBuckets[indexOfBucket].add(entry);
    }

    private Entry<K, V> getEntry(K key) {
        int indexOfBucket = getIndexOfBucket(key);
        if (buckets[indexOfBucket] == null) {
            return null;
        }
        Iterator<Entry<K, V>> iterator = buckets[indexOfBucket].iterator();
        while (iterator().hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (Objects.equals(entry.getKey(), key)) {
                return entry;
            }
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
        int indexOfBucket = getIndexOfBucket(key);
        if (buckets[indexOfBucket] == null) {
            return null;
        }
        Iterator<Entry<K, V>> iterator = buckets[indexOfBucket].iterator();
        while (iterator().hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (Objects.equals(entry.getKey(), key)) {
                iterator.remove();
                size--;
                return entry;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void growCapacity() {
        ArrayList<Entry<K, V>>[] newBuckets = new ArrayList[(int) (buckets.length * growFactor)];
        for (ArrayList<Entry<K, V>> newBucket : newBuckets) {
            if (newBucket != null) {
                for (Entry<K, V> entry : newBucket) {
                    addEntryToBucket(entry, newBuckets);
                }
            }
        }
        buckets = newBuckets;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<Map.Entry<K, V>> {
        private int bucketIndex;
        private Iterator<Entry<K, V>> bucketIterator;
        private boolean changeOfBucketIndexIndicator;

        private HashMapIterator() {
            while (buckets[bucketIndex] == null) {
                bucketIndex++;
            }
            bucketIterator = buckets[bucketIndex].iterator();
        }

        @Override
        public boolean hasNext() {
            if (bucketIndex >= buckets.length) {
                return false;
            }
            while (buckets[bucketIndex] == null) {
                bucketIndex++;
                changeOfBucketIndexIndicator = true;
                if (bucketIndex >= buckets.length) {
                    return false;
                }
            }
            if (changeOfBucketIndexIndicator) {
                bucketIterator = buckets[bucketIndex].iterator();
            }
            if (!bucketIterator.hasNext()) {
                bucketIndex++;
                if (bucketIndex >= buckets.length) {
                    return false;
                }
                while ((buckets[bucketIndex] == null)) {
                    bucketIndex++;
                    if (bucketIndex >= buckets.length) {
                        return false;
                    }
                }
                bucketIterator = buckets[bucketIndex].iterator();
            }
            return bucketIterator.hasNext();
        }

        @Override
        public HashMap.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Next element is not exist");
            }
            changeOfBucketIndexIndicator = true;
            return bucketIterator.next();
        }

        @Override
        public void remove() {
            if (!changeOfBucketIndexIndicator) {
                throw new IllegalStateException("Method next() has not called before remove");
            }
            changeOfBucketIndexIndicator = false;
            bucketIterator.remove();
            size--;
        }
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

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
    }
}
