package com.example.slotdemo.service;

import java.util.*;
import java.util.stream.Collectors;

public class SlotScoreCalculator {

    private final Map<Integer, Integer> odds = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0, 0),
            new AbstractMap.SimpleEntry<>(1, 10),
            new AbstractMap.SimpleEntry<>(2, 40),
            new AbstractMap.SimpleEntry<>(3, 100)
    );
    private List<List<String>> wheels;

    public SlotScoreCalculator(List<List<String>> wheel) {
        this.wheels = wheel;
    }

    public int calculate(int bet) {

//        for (List<String> wheel : wheels) {
//            wheel.subList()
//        }
        int lines = getLines();
        int odd = getOdd(lines);

        return bet * odd;
    }

    private int getLines() {
        int line = 0;
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Set<String> differentSymbols = wheels.stream()
                    .map(wheel -> wheel.get(finalI))
                    .collect(Collectors.toSet());

            if (differentSymbols.size() == 1)
                line++;
        }
        return line;
    }

    private int getOdd(int line) {
        int odd = odds.get(line);
        if (Objects.isNull(odd)) {
            throw new RuntimeException("Unsupported lines");
        }
        return odd;
    }
}
