package com.bondarenko.datastructures.list;

import org.junit.jupiter.api.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractListTest<T> {

    List<String> list = getList();

    abstract List<String> getList();

    @BeforeEach
    public void initEach() {
        list.add("A");
        list.add("B");
        list.add("C");
    }

    @AfterEach
    public void cleanUp() {
        list.clear();
    }

    @Test
    @DisplayName("test Add To The End")
    public void testAddToTheEnd() {
        //when
        list.add("D");

        //then
        assertEquals(4, list.size());
        assertEquals("D", list.get(3));
    }

    @Test
    @DisplayName("test Add By Index Null")
    public void testAddByIndexNull() {
        //when
        list.add("D", 0);

        //then
        assertEquals(4, list.size());
        assertEquals("D", list.get(0));
        assertEquals("A", list.get(1));
        assertEquals("B", list.get(2));
        assertEquals("C", list.get(3));
    }

    @Test
    @DisplayName("test Remove By Null")
    public void testRemoveByNull() {
        //when
        list.remove(0);

        //than
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));
        assertEquals("C", list.get(1));
        assertFalse(list.contains("A"));
    }

    @Test
    @DisplayName("test Remove By Last Index")
    public void testRemoveByLastIndex() {
        //when
        list.remove(2);

        //than
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertFalse(list.contains("C"));
    }

    @Test
    @DisplayName("test Remove By Index In The Middle")
    public void testRemoveByIndexInTheMiddle() {
        //when
        list.remove(1);

        //than
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));
        assertFalse(list.contains("B"));
    }

    @Test
    @DisplayName("when Remove By Index Then Removed Value Returned")
    public void whenRemoveByIndex_ThenRemovedValueReturned() {
        //when
        Object removedValue = list.remove(0);

        //then
        assertEquals("A", removedValue);
    }

    @Test
    @DisplayName("test Get By Index")
    public void testGetByIndex() {
        //when
        list.get(0);

        //then
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    @DisplayName("test Set Value By Index")
    public void testSetValueByIndex() {
        //when
        list.set("C", 0);

        //than
        assertEquals(3, list.size());
        assertEquals("C", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    @DisplayName("when Set By Index Then Old Value Returned")
    public void whenSetByIndex_ThenOldValueReturned() {
        //when
        Object oldValue = list.set("C", 0);

        //than
        assertEquals("A", oldValue);
    }

    @Test
    @DisplayName("test Contains Value")
    public void testContains() {
        //than
        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertTrue(list.contains("C"));
        assertFalse(list.contains("D"));
    }

    @Test
    @DisplayName("test Contains Null")
    public void testContainsNull() {
        //when
        list.add(null);
        //than
        assertTrue(list.contains(null));
    }

    @Test
    @DisplayName("receive Index Of The First Occurrence Of Value")
    public void receiveIndexOfTheFirstOccurrenceOfValue() {
        //when
        list.add("A");

        //than
        assertEquals(0, list.indexOf("A"));
    }

    @Test
    @DisplayName("test receive Index Of The First  Occurrence Of Null")
    public void receiveIndexOfTheFirstOccurrenceOfNull() {
        //when
        list.add(null);
        list.add(null);

        //than
        assertEquals(3, list.indexOf(null));
    }

    @Test
    @DisplayName("when The First Occurrence Of Value Is Not Exist Then Receive Minus One")
    public void whenTheFirstOccurrenceOfValue_IsNotExist_ThenReceiveMinusOne() {
        //when
        int result = list.indexOf("D");

        //than
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("receive Index Of The Last Occurrence Of Value")
    public void receiveIndexOfTheLastOccurrenceOfValue() {
        //when
        list.add("A");

        //than
        assertEquals(3, list.lastIndexOf("A"));
    }

    @Test
    @DisplayName("when The Last Occurrence Of Value Is Not Exist Then Given Minus One")
    public void whenTheLastOccurrenceOfValue_IsNotExist_ThenGivenMinusOne() {
        //when
        int result = list.lastIndexOf("D");

        //than
        assertEquals(-1, result);
    }

    @Test
    @DisplayName("test To String")
    public void testToString() {
        //when
        list.toString();

        //then
        assertEquals("[A,B,C]", list.toString());
    }

    @Test
    @DisplayName("test Clear")
    public void testClear() {
        //when
        list.clear();

        //than
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("test Size")
    public void testSize() {
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("test Is Empty")
    public void testIsEmpty() {
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("test Iterator Has Next")
    public void testIteratorHasNext() {
        Iterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
    }

    @Test
    @DisplayName("test Next Iterator")
    public void testNextIterator() {

        Iterator<String> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();
    }

    @Test
    @DisplayName("test Iterator Remove")
    public void testIteratorRemove() {

        Iterator<String> iterator = list.iterator();
        assertEquals(3, list.size());
        assertTrue(iterator.hasNext());
        iterator.next();

        iterator.remove();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("when Capacity Of Array Is Not Enough Then Grow Capacity Of Array")
    public void whenCapacityOfArray_IsNotEnough_ThenGrowCapacityOfArray() {
        //prepare
        ArrayList<Integer> arrayList = new ArrayList<>(10);
        assertEquals(10, arrayList.getCapacity());

        //when
        for (int i = 0; i < 11; i++) {
            arrayList.add(i);
        }

        //then
        assertEquals(25, arrayList.getCapacity());
    }

    @Test
    @DisplayName("when Add By Index Which Less Then Zero Then Throw Exception")
    public void whenAddByIndexWhichLes_ThenZeroThenThrowException() {

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("D", -1);
        });
    }

    @Test
    @DisplayName("when Add By Index Which More Then Size Then Throw Exception")
    public void whenAddByIndexWhichMore_ThenSizeThenThrowException() {

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("D", list.size() + 1);
        });
    }

    @Test
    @DisplayName("when Remove By Index Which Less Then Zero Then Throw Exception")
    public void whenRemoveByIndexWhichLess_ThenZeroThenThrowException() {

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });
    }

    @Test
    @DisplayName("when Remove By Index Which More Then Size Minus One Then Throw Exception")
    public void whenRemoveByIndexWhichMore_ThenSizeMinusOneThenThrowException() {

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(list.size());
        });
    }

    @Test
    @DisplayName("when Get By Index Which More Then Size Minus One Then Throw Exception")
    public void whenGetByIndexWhichMore_ThenSizeMinusOneThenThrowException() {

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(list.size());
        });
    }

    @Test
    @DisplayName("when Get B yIndex Which Less Then Zero Then Throw Exception")
    public void whenGetByIndexWhichLess_ThenZeroThenThrowException() {

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
    }

    @Test
    @DisplayName("when Set Value By Index Which More Then Size Minus One Then Throw Exception")
    public void whenSetValueByIndexWhichMore_ThenSizeMinusOneThenThrowException() {

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("D", list.size());
        });
    }

    @Test
    @DisplayName("when Set Value By Index Which Less Then Zero Then Throw Exception")
    public void whenSetValueByIndexWhichLess_ThenZeroThenThrowException() {

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("D", -1);
        });
    }
}


