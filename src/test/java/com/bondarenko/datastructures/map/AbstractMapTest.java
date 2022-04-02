package com.bondarenko.datastructures.map;

import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractMapTest {

    private Map map = getMap();

    abstract Map getMap();

    @Test
    public void testPut() {
        assertNull(map.put("key1", "value1"));
        assertEquals(1, map.size());

        map.put("key2", "value2");
        assertEquals(2, map.size());

        map.put("key1", "value3");
        assertEquals("value3", map.put("key1", "value3"));
    }

    @Test
    public void testRemove() {
        assertNull(map.put("key1", "value1"));
        map.put("key2", "value2");
        assertEquals(2, map.size());

        map.remove("key1");
        assertEquals(1, map.size());

        map.remove("key2");
        assertEquals(0, map.size());
    }

    @Test
    public void testGet() {

        assertNull(map.get("key1"));

        map.put("key1", "value1");
        assertEquals("value1", map.get("key1"));

        map.put("key2", "value2");
        assertEquals("value2", map.get("key2"));
    }

    @Test
    public void testSize() {
        assertEquals(0, map.size());

        map.put("key2", "value2");
        assertEquals(1, map.size());

        map.put("key3", "value3");
        assertEquals(2, map.size());
    }

    @Test
    public void testContainsKey() {
        assertFalse(map.containsKey("key1"));

        map.put("key2", "value3");

        assertTrue(map.containsKey("key2"));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(map.isEmpty());

        map.put("key2", "value3");
        assertFalse(map.isEmpty());
    }

    @Test
    public void testIteratorHasNext() {
        map.put("key4", "value4");
        map.put("key5", "value5");
        map.put("key6", "value6");

    }
}
