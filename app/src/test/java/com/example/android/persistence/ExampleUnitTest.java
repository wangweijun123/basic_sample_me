package com.example.android.persistence;

import android.util.ArrayMap;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        /*HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < 15; i++) {
            map.put("xxx"+i, "eeee "+i);
        }*/

        HashMap<Person, String> map = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            Person p = new Person();
            map.put(p, "i="+i);
        }
    }

    @Test
    public void testArrayMap() {
        ArrayMap<Integer, Integer> map = new ArrayMap<>();
        for (int i = 0; i < 3; i++) {
            map.put(i, i);
        }

    }

    class Person {
        int age;

        @Override
        public int hashCode() {
            return 100;
        }
    }
}