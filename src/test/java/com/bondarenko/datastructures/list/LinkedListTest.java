package com.bondarenko.datastructures.list;

public class LinkedListTest<T> extends AbstractListTest<T> {

    @Override
    List<String> getList() {
        return new LinkedList<>();
    }
}
