package com.bondarenko.datastructures.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapTest {

    Map<String, String> map = new HashMap();

    @Test
    @DisplayName("given Not Null Key When Put Then Size Should Be Equal To One And Value Should Be Equal To Inserted")
    public void givenNotNullKey_WhenPutThenSize_ShouldBeEqualToOne_AndValueShouldBeEqual_ToInserted() {
        map.put("key", "value");

        assertEquals(1, map.size());
        assertEquals("value", map.get("key"));
    }

    @Test
    @DisplayName("When Put Then Size Should Be Equal To Size Of Keys And Get By Key Returns Corresponding Value")
    public void givenMultipleNotNullKeys_WhenPutThenSizeShouldBeEqualToSizeOfKeys_AndGetByKey_ReturnsCorrespondingValue() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        assertEquals(2, map.size());
        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
    }

    @Test
    @DisplayName("when put values by different keys then get by key returns corresponding value")
    public void givenMultipleNodes_InSameBucket_WhenGetByExistingKey_ThenGetByKeyReturnsCorrespondingValue() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        assertEquals(3, map.size());
        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
        assertEquals("value3", map.get("key3"));
    }

    @Test
    @DisplayName("update value when put by the same key")
    public void givenNotNullKey_WhenPutMultipleTimesWithSameKey_ThenSizeShouldBeEqualToOne_AndValueShouldBeOverwrittenWithLast() {
        map.put("key", "value1");
        map.put("key", "value2");
        map.put("key", "value3");

        assertEquals(1, map.size());
        assertEquals("value3", map.get("key"));
    }

    @Test
    @DisplayName("when map is Empty then size equal  to zero")
    public void whenMapIsEmpty_thenSizeIsZero() {
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("given Empty Map When Remove Then Size Should Be Equal To Zero")
    public void givenEmptyMap_WhenRemove_ThenSizeShouldBeEqual_ToZero() {
        map.remove("key");
        assertEquals(0, map.size());

    }

    @Test
    @DisplayName("given Not Empty Map When Remove Then Size Should Be Equal ToZero")
    public void givenNotEmptyMap_WhenRemove_ThenSizeShould_BeEqualToZero() {
        map.put("key", "value");
        assertEquals(1, map.size());

        map.remove("key");
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("when Remove One By One Then Size Should Decrease After Each Removal ByOne")
    public void givenNotEmptyMap_WhenRemoveOneByOne_ThenSizeShouldDecreaseAfterEachRemovalByOne() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        assertEquals(3, map.size());

        map.remove("key1");
        assertEquals(2, map.size());

        map.remove("key2");
        assertEquals(1, map.size());

        map.remove("key3");
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("When Remove First Node Then Size Should Decrease By One")
    public void givenNotEmptyMap_AndObjectsInSameBucket_WhenRemoveFirstNode_ThenSizeShouldDecreaseByOne() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");

        assertEquals(4, map.size());

        map.remove("key2");
        assertEquals(3, map.size());
    }

    @Test
    @DisplayName("When Remove Last Node Then Size Should Decrease By One")
    public void givenNotEmptyMap_AndObjectsInSameBucket_WhenRemoveLastNodeThenSizeShouldDecreaseByOne() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");

        assertEquals(4, map.size());

        map.remove("key4");
        assertEquals(3, map.size());
    }

    @Test
    @DisplayName("When Remove Node In The Middle Then Size Should Decrease By One")
    public void givenNotEmptyMapAndObjectsInSameBucket_WhenRemoveNodeInTheMiddle_ThenSizeShouldDecreaseByOne() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");

        assertEquals(4, map.size());

        map.remove("key3");
        assertEquals(3, map.size());
    }

    @Test
    @DisplayName("given Empty Map When Contains Null Key Then False Should Be Returned")
    public void givenEmptyMap_WhenContainsNullKey_ThenFalseShouldBeReturned() {
        assertFalse(map.containsKey(null));
    }

    @Test
    @DisplayName("given Empty Map When Contains Not Null Key Then False Should Be Returned")
    public void givenEmptyMap_WhenContainsNotNullKey_ThenFalseShouldBeReturned() {
        assertFalse(map.containsKey("key"));
    }

    @Test
    @DisplayName("given Map With Existing Null Key When Contains Null Key Then True Should Be Returned")
    public void givenMapWithExistingNullKey_WhenContainsNullKey_ThenTrueShouldBeReturned() {
        map.put(null, "value");

        assertTrue(map.containsKey(null));
    }

    @Test
    @DisplayName("given Map With Not Existing Null Key When Contains Null Key Then False Should Be Returned")
    public void givenMapWithNotExistingNullKey_WhenContainsNullKey_ThenFalseShouldBeReturned() {
        map.put("key", "value");

        assertFalse(map.containsKey(null));
    }

    @Test
    @DisplayName("given Not Existing Key When Contains Key Then False Should Be Returned")
    public void givenNotExistingKey_WhenContainsKey_ThenFalseShouldBeReturned() {
        map.put("key", "value");

        assertTrue(map.containsKey("key"));
    }

    @Test
    @DisplayName("given Not Empty Map When Iterator Has Next Then Should Return True")
    public void givenNotEmptyMap_WhenIteratorHasNext_ThenShouldReturnTrue() {
        map.put("key", "value");

        Iterator<Map.Entry<String, String>> iterator = map.iterator();
        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());

        map.remove("key");
        assertFalse(iterator.hasNext());

        map.put("key", "value");
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("given Map With One Element When Iterator Next Then Iterator Has Next Should Return False")
    public void givenMapWithOneElement_WhenIteratorNext_ThenIteratorHasNext_ShouldReturnFalse() {
        map.put("key", "value");

        Iterator<Map.Entry<String, String>> iterator = map.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("given Iterator When Remove Called After Next Then Size Should Be Decreased By One And Map Should NotContainKey")
    public void givenIterator_WhenRemoveCalledAfterNext_ThenSizeShouldBeDecreased_ByOne_AndMapShouldNotContainKey() {
        String key = "key";
        map.put(key, "value");
        assertEquals(1, map.size());

        Iterator<Map.Entry<String, String>> iterator = map.iterator();
        iterator.next();
        iterator.remove();

        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("when Add Value then Value Is Present")
    public void whenAddValue_thenValueIsPresent() {
        map.put("key1", "value1");
        Iterator<Map.Entry<String, String>> iterator = map.iterator();

        assertTrue(iterator.hasNext());
    }

    @Test
    @DisplayName("when Map Is Empty Then Size Is Zero")
    public void whenMapIsEmpty_ThenSizeIsZero() {
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("when Map Is Empty Then Size Is Zero")
    public void whenMapHasOnlyOnePair_ThenSizeIsOne() {
        map.put("key1", "value1");
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("when Map Is Empty then Return True")
    public void whenMapIsEmpty_thenReturnTrue() {
        assertTrue(map.isEmpty());
    }

    @Test
    @DisplayName("when Map Is Not Empty then Return False")
    public void whenMapIsNotEmpty_thenReturnFalse() {
        map.put("key2", "value3");
        assertFalse(map.isEmpty());
    }

    @Test
    @DisplayName("when Remove In Empty Map Then Size Should Be Zero")
    public void whenRemoveInEmptyMap_ThenSizeShouldBeZero() {
        map.remove("key1");

        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("when Get By Key In Pair With Null Value Then Null Should Be Returned")
    public void whenGetByKey_InPairWithNullValueThenNullShouldBeReturned() {
        map.put("key1", null);

        map.get("key1");

        assertNull(map.get("key1"));
    }

    @Test
    @DisplayName("when Get By Key In Empty Map Then Null Should Be Returned")
    public void whenGetByKey_InEmptyMap_ThenNullShouldBeReturned() {
        map.get("key1");

        assertNull(map.get("key1"));
    }

    @Test
    @DisplayName("when Remove By Not Existing Key then Size Is Not Change ")
    public void whenRemoveByNotExistingKey_thenSizeIsNotChange() {
        map.put("key1", "value1");

        assertEquals(1, map.size());

        map.remove("key2");
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("when Map Is Empty then Contains Key Return False")
    public void whenMapIsEmpty_thenContainsKey_ReturnFalse() {
        assertFalse(map.containsKey("key1"));
    }

    @Test
    @DisplayName("when Contains Existing Key then Return True")
    public void whenContainsExistingKey_thenReturnTrue() {
        map.put("key1", "value");

        assertTrue(map.containsKey("key1"));
    }

    @Test
    @DisplayName("when Contains Not Existing Key then Return False")
    public void whenContainsNotExistingKey_thenReturnFalse() {
        map.put("key1", "value1");

        assertFalse(map.containsKey("key3"));
    }

    @Test
    @DisplayName("whenGet By Existing Key then Get By Key Returns Corresponding Value()")
    public void whenGetByExistingKey_thenGetByKeyReturnsCorrespondingValue() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
        assertEquals("value3", map.get("key3"));
        assertEquals(3, map.size());
    }

    @Test
    @DisplayName("when Remove Value then Value Is Removed")
    public void whenRemoveValue_thenValueIsRemoved() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        Iterator<Map.Entry<String, String>> iterator = map.iterator();

        Map.Entry<String, String> e1 = iterator.next();
        Map.Entry<String, String> e2 = iterator.next();
        iterator.remove();

        assertEquals("key1", e1.getKey());
        assertEquals("key2", e2.getKey());
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("when Iterate To Next Value then Next Value Is Returned")
    public void whenIterateToNextValue_thenNextValueIsReturned() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<Map.Entry<String, String>> iterator = map.iterator();

        Map.Entry<String, String> e1 = iterator.next();
        Map.Entry<String, String> e2 = iterator.next();

        assertEquals("key1", e1.getKey());
        assertEquals("key2", e2.getKey());
    }

    @Test
    @DisplayName("when Iterator Next then Iterator Has Next Should Returned True")
    public void whenIteratorNext_thenIteratorHasNextShouldReturnedTrue() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<Map.Entry<String, String>> iterator = map.iterator();

        assertTrue(iterator.hasNext());
        iterator.hasNext();
        assertTrue(iterator.hasNext());
    }

    @Test
    @DisplayName("when Iterator Next then Iterator Has Next Should Returned False")
    public void whenIteratorNext_thenIteratorHasNextShouldReturnedFalse() {
        map.put("key1", "value1");

        Iterator<Map.Entry<String, String>> iterator = map.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("when Put Several Values then Values Added()")
    public void whenPutSeveralValues_thenValuesAdded() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<Map.Entry<String, String>> iterator = map.iterator();

        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
    }

    @Test
    @DisplayName("when Remove Without Next then Array Index Out Of Bounds Exception")
    public void whenRemoveWithoutNext_thenArrayIndexOutOfBoundsException() {
        Assertions.assertThrows(IllegalStateException.class, () -> {

            map.put("key1", "value1");

            assertEquals(1, map.size());

            Iterator<Map.Entry<String, String>> iterator = map.iterator();

            iterator.remove();
        });
    }

    @Test
    @DisplayName("when Remove Called After Next then Size Should Be Decreased and Map Not Contains Key")
    public void whenRemoveCalledAfterNext_thenSizeShouldBeDecreased_andMapNotContainsKey() {
        map.put("key1", "value1");

        Iterator<Map.Entry<String, String>> iterator = map.iterator();

        assertEquals(1, map.size());

        iterator.next();
        iterator.remove();
        assertFalse(map.containsKey("key1"));
        assertEquals(0, map.size());
    }
}








