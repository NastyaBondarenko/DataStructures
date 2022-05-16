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
            Node<T> node = getNode(index);
            newNode.prev = node.prev;
            node.prev.next = node;
        }
        size++;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> current = getNode(index);
        removeNode(current);
        return current.value;
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> current = getNode(index);
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
        for (int index = size - 1; index >= 0; index--) {
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
        private boolean remove;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Next element is not exist");
            }
            T value = current.value;
            current = current.next;
            remove = true;
            return value;
        }

        @Override
        public void remove() {
            if (!remove) {
                throw new IllegalStateException("The method next() not used previously");
            }
            removeNode(current);
            remove=false;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void removeNode(Node<T> node) {
        if (size == 1) {
            first = last = null;
        } else if (node == first) {
            first.prev = null;
            first = node.next;
        } else if (node == last) {
            last = node.prev;
            last.next = null;
        } else {
            Node<T> prevNode = node.prev;
            Node<T> nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
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
