package com.bondarenko.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList<T> implements List<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            first = last = newNode;
        } else if (index == 0) {
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        } else {
            Node<T> node = findByIndex(index);
            newNode.prev = node.prev;
            node.prev.next = node;
            node = newNode;
            newNode.next = node.next;
        }
        size++;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> current = findByIndex(index);
        if (size == 1) {
            first = last = null;
        } else if (index == 0) {
            first.next.prev = null;
            first = first.next;
        } else if (index == size - 1) {
            last.prev.next = null;
            last = last.prev;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return current.value;
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> current = findByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public int indexOf(T value) {
        Node<T> newNode = first;
        for (int index = 0; index < size; index++) {
            if (Objects.equals(newNode.value, value)) {
                return index;
            }
            newNode = newNode.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        Node<T> current = last;
        for (int index = size - 1; index > 0; index--) {
            if (Objects.equals(current.value, value)) {
                return index;
            }
            current = current.prev;
        }
        return -1;
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(",", "[", "]");
        for (T value : this) {
            result.add(String.valueOf(value));
        }
        return result.toString();
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (current == null) {
                throw new NoSuchElementException("No more elements");
            }
            T value = current.value;
            current = current.next;
            return value;
        }

        @Override
        public void remove() {
            if (current.next != null) {
                last = current.prev;
                current.prev.next = null;
            } else {
                throw new IllegalStateException("Element is not exist");
            }
            size--;
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " must be between [ " + 0 + "," + size + "]");
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index " + index + " must be between [ " + 0 + "," + (size - 1) + "]");
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value) {
            this.value = value;
        }
    }
}
