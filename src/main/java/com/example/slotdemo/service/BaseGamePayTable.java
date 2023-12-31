package com.example.slotdemo.service;

import java.util.*;

public class BaseGamePayTable implements PayTable {
    final Map<Integer, Integer> odds = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0, 0),
            new AbstractMap.SimpleEntry<>(1, 10),
            new AbstractMap.SimpleEntry<>(2, 40),
            new AbstractMap.SimpleEntry<>(3, 100)
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