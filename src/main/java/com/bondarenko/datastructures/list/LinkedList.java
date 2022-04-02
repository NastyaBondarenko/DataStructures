package com.bondarenko.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class LinkedList<T> implements List<T> {
    private Node first;
    private Node last;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node(value);
        if (size == 0) {
            first = newNode;
            newNode.next = last;
            last = newNode;
        } else if (size > 0) {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index);
        Node newNode = new Node(value);

        if (index == 0) {
            first.previous = newNode;
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        } else if (size == 0) {
            first = last = newNode;
        } else {
            Node node = getNode(index);//getNode(value)
            newNode.previous = node.previous;
            node.previous.next = node;
            node = newNode;
            newNode.next = node.next;
        }
        size++;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Object result = first.current;

        if (size == 1) {
            first = last = null;
        } else if (index == size - 1) {
            result = last.current;
            last = last.previous;
            last.next = null;
        } else if (index == 0) {
            first = first.next;
            first.previous = null;
        } else {
            Node node = getNode(index);
            node.previous.next = node.next;
        }
        size--;
        return (T) result;
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return (T) getNode(index).current;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node node = getNode(index);
        Object oldValue = node.current;
        node.current = value;
        return (T) oldValue;
    }

    @Override
    public int indexOf(T value) {
        Node newNode = first;
        for (int index = 0; index < size; index++) {
            if (newNode.current.equals(value)) {
                return index;
            }
            newNode = newNode.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        Node newNode = last;
        for (int index = size - 1; index > 0; index--) {
            if (newNode.current.equals(value)) {
                return index;
            }
            newNode = newNode.previous;
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
        Node newNode = first;////current instead newNode
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        while (newNode != null) {
            stringJoiner.add(String.valueOf(newNode.current));
            newNode = newNode.next;
        }
        return stringJoiner.toString();
    }

    @Override
    public boolean contains(T value) {
        Node node = new Node(value);
        if (node.current.equals(value))
            return true;
        else return false;
    }

    @Override
    public Iterator getIterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator {
        private Node node = first;
        private boolean okToCallNext;

        @Override
        public boolean hasNext() {
            if (node.next != null) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (node == last) {
                throw new NoSuchElementException("No more  elements are avalible");
            }
            okToCallNext = true;
            if (node.next != null) {
                node = node.next;
            } else {
                node = first;
            }
            return node.current;
        }

        @Override
        public void remove() {
            if (node.next != null) {
                last = node.previous;
                node.previous.next = null;
            } else {
                throw new IllegalStateException("The method next() not used previously ");
            }
            size--;
        }
    }

    public Node getNode(int index) {
        Node newNode = first;
        if (index < size) {
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;
            }
        }
        return newNode;
    }

    private void validateIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index" + index + " must be between null and size = " + size);
        }
    }

    private static class Node {
        private Object current;
        private Node next;
        private Node previous;

        private Node(Object current) {
            this.current = current;
        }
    }
}
