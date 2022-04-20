package com.bondarenko.datastructures.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapTest {

    Map<String, String> map = new HashMap();
    Iterator<Map.Entry<String, String>> iterator = map.iterator();

    @Test
    @DisplayName("when Put By Not Null Key Then Value Should Be Returned By Inserted Key")
    public void whenPutByNotNullKey_ThenValueShouldBeReturned_ByInsertedKey() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("when Put Multiple Times By The Same Key then Value Should Be Updated To The Last")
    public void whenPutSeveralTimes_byTheSameKey_thenValueShouldBeUpdated_ToTheLast() {
        String key = "key";
        String value1 = "value1";
        String value2 = "value2";

        map.put(key, value1);
        map.put(key, value2);

        assertEquals(value2, map.get(key));
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("when Put In Pair Where Previous Value Was Null ThenNull Returned")
    public void whenPutInPair_WherePreviousValueWasNull_ThenNullReturned() {
        map.put("key1", null);
        assertNull(map.put("key1", "value1"));
    }

    @Test
    @DisplayName("when Remove Last Pair Then Size Should Be Zero")
    public void whenRemoveLastPair_ThenSizeShouldBeZero() {
        map.put("key1", "value1");

        map.remove("key1");

        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("when Remove Sequentially In Not Empty Map Then Size  Sequentially Decrease")
    public void whenRemoveSequentially_InNotEmptyMap_ThenSizeSequentiallyDecrease() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        assertEquals(2, map.size());

        map.remove("key1");
        assertEquals(1, map.size());

        map.remove("key2");
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("when Remove By Not Existing Key In Not Empty Map Then Size Not Change")
    public void whenRemoveByNotExistingKey_InNotEmptyMap_ThenSizeNotChange() {
        map.put("key1", "value1");
        assertEquals(1, map.size());

        map.remove("key2");
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("when Get By Not Existing Key the Null Return")
    public void whenGetByNotExistingKey_thenNullReturn() {
        map.put("key1", "value1");

        assertEquals(null, map.get("not existing key"));
    }

    @Test
    @DisplayName("when Put In Pair Where Previous Value Was Null Then Null Should Be Returned")
    public void whenRemovePair_WithNullValue_ThenNullShouldBeReturned() {
        map.put("key1", null);
        map.remove("key1");

        assertEquals(null, map.remove("key1"));
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
        Map<String, String> map = new HashMap<>();
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
    @DisplayName("when Exist Several Pairs Then Size Is Appropriate")
    public void whenExistSeveralPairs_ThenSizeIsAppropriate() {
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertEquals(2, map.size());
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
    @DisplayName("when Map Is Empty then Value is Not Exist")
    public void whenMapIsEmpty_thenValue_isNotExist() {
        assertFalse(map.iterator().hasNext());
    }

    @Test
    @DisplayName("when Add Value then Value Is Present")
    public void whenAddValue_thenValueIsPresent() {
        map.put("key1", "value1");
        assertTrue(iterator.hasNext());
    }

    @Test
    @DisplayName("when Remove Value then Value Is Removed")
    public void whenRemoveValue_thenValueIsRemoved() {
        map.put("key1", "value1");
        map.put("key2", "value2");

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

        assertTrue(iterator.hasNext());
        iterator.hasNext();
        assertTrue(iterator.hasNext());
    }

    @Test
    @DisplayName("when Iterator Next then Iterator Has Next Should Returned False")
    public void whenIteratorNext_thenIteratorHasNextShouldReturnedFalse() {
        map.put("key1", "value1");

        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    @DisplayName("when Put Several Values then Values Added()")
    public void whenPutSeveralValues_thenValuesAdded() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        assertTrue(iterator.hasNext());
        assertTrue(iterator.hasNext());
    }

    @Test
    @DisplayName("when Remove Without Next then Array Index Out Of Bounds Exception")
    public void whenRemoveWithoutNext_thenArrayIndexOutOfBoundsException() {
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            map.put("key1", "value1");
            assertEquals(1, map.size());

            iterator.remove();
        });
    }

    @Test
    @DisplayName("when Remove Called After Next then Size Should Be Decreased and Map Not Contains Key")
    public void whenRemoveCalledAfterNext_thenSizeShouldBeDecreased_andMapNotContainsKey() {
        map.put("key1", "value1");
        assertEquals(1, map.size());

        iterator.next();
        iterator.remove();
        assertFalse(map.containsKey("key1"));
        assertEquals(0, map.size());
    }
}