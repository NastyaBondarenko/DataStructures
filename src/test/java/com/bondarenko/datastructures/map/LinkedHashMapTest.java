package com.bondarenko.datastructures.map;

public class LinkedHashMapTest extends AbstractMapTest {
    @Override
    Map getMap() {
        Map map = new LinkedHashMap();
        return map;
    }
}