package com.example.slotdemo.service;

import java.util.*;
import java.util.stream.Collectors;

public class PayTable {
    final Map<Integer, Integer> odds = Map.ofEntries(
            new AbstractMap.SimpleEntry<Integer, Integer>(0, 0),
            new AbstractMap.SimpleEntry<Integer, Integer>(1, 10),
            new AbstractMap.SimpleEntry<Integer, Integer>(2, 40),
            new AbstractMap.SimpleEntry<Integer, Integer>(3, 100)
    );

    public int getOdd(List<List<String>> screen) {
        int lines = getLines(screen);
        int odd = odds.get(lines);
        if (Objects.isNull(odd)) {
            throw new RuntimeException("Unsupported lines");
        }
        return odd;
    }
    private int getLines(List<List<String>> screen) {
        int line = 0;
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Set<String> differentSymbols = screen.stream()
                    .map(wheel -> wheel.get(finalI))
                    .collect(Collectors.toSet());

            if (differentSymbols.size() == 1)
                line++;
        }
        return line;
    }
}