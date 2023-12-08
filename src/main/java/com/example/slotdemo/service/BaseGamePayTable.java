package com.example.slotdemo.service;

import java.util.*;

public class BaseGamePayTable implements PayTable {
    final Map<Integer, Integer> odds = Map.ofEntries(
            new AbstractMap.SimpleEntry<Integer, Integer>(0, 0),
            new AbstractMap.SimpleEntry<Integer, Integer>(1, 10),
            new AbstractMap.SimpleEntry<Integer, Integer>(2, 40),
            new AbstractMap.SimpleEntry<Integer, Integer>(3, 100)
    );

    @Override
    public int getOdd(Screen screen) {
        int lines = screen.countStraightLines();

        int odd = odds.get(lines);
        if (!odds.containsKey(lines)) {
            throw new RuntimeException("Unsupported lines");
        }
        return odd;
    }
}