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
        if (key == null) {
            throw new NullPointerException("Key is not exist");
        }
        Entry<K, V> oldEntry = getEntry(key);
        if (oldEntry != null) {
            V oldValue = oldEntry.getValue();
            oldEntry.setValue(value);
            return oldValue;
        } else if (size > buckets.length * loadFactor) {
            growCapacity();
            addEntryToBucket(new Entry<>(key, value));
            size++;
            return null;
        }
        addEntryToBucket(new Entry<>(key, value));
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("Key is not exist");
        }
        Entry<K, V> entry = getEntry(key, true);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Key is not exist");
        }
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
        return getEntry(key, false);
    }

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

    private void addEntryToBucket(Entry<K, V> entry) {
        addEntryToBucket(entry, buckets);
    }

    private void addEntryToBucket(Entry<K, V> entry, ArrayList<Entry<K, V>>[] newBuckets) {
        int indexOfBucket = Math.abs(entry.getKey().hashCode() % buckets.length);
        if (newBuckets[indexOfBucket] == null) {
            newBuckets[indexOfBucket] = new ArrayList<>();
        }
        newBuckets[indexOfBucket].add(entry);
    }

    private Entry<K, V> getEntry(K key, boolean remove) {
        int indexOfBucket = Math.abs(key.hashCode() % buckets.length);
        if (buckets[indexOfBucket] == null) {
            return null;
        } else {
            Iterator<Entry<K, V>> iterator = buckets[indexOfBucket].iterator();
            while (iterator().hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (Objects.equals(entry.getKey(), key)) {
                    if (remove) {
                        iterator.remove();
                        size--;
                    }
                    return entry;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    public class HashMapIterator implements Iterator<Map.Entry<K, V>> {
        private int currentBucketIndex;
        private int currentEntry;

        @Override
        public boolean hasNext() {
            for (int i = currentBucketIndex; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    if (buckets[i].size() > currentEntry) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public HashMap.Entry<K, V> next() {
            if (size == 0 || currentBucketIndex == buckets.length) {
                throw new NoSuchElementException("Next element is not exist ");
            }
            ArrayList<HashMap.Entry<K, V>> nextBucketArray = buckets[currentBucketIndex];
            if (nextBucketArray != null) {
                HashMap.Entry<K, V> result = nextBucketArray.get(currentEntry);
                currentEntry++;
                if (nextBucketArray.size() == currentEntry) {
                    currentEntry = 0;
                    currentBucketIndex++;
                }
                return result;
            } else {
                currentEntry = 0;
                currentBucketIndex++;
                for (int i = currentBucketIndex; i < buckets.length; i++) {
                    if (buckets[i] != null) {
                        currentBucketIndex = i;
                        HashMap.Entry<K, V> result = buckets[i].get(currentEntry);
                        currentEntry++;
                        if (buckets[i].size() == currentEntry) {
                            currentEntry = 0;
                            currentBucketIndex++;
                        }
                        return result;
                    }
                }
            }
            return null;
        }

        @Override
        public void remove() {
            ArrayList<HashMap.Entry<K, V>> nextBucketArray = buckets[currentBucketIndex - 1];
            if (nextBucketArray != null) {
                nextBucketArray.remove(currentEntry);
                size--;
                if (nextBucketArray.size() == 0) {
                    buckets[currentBucketIndex - 1] = null;
                    currentBucketIndex--;
                }
            }
        }
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
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
