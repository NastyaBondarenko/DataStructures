package com.bondarenko.datastructures.list;

public class ArrayListTest<T> extends AbstractListTest<T> {

    @Override
    List<String> getList() {
        return new ArrayList<>();
    }
}
