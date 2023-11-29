package com.example.slotdemo.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SlotScoreCalculator {

    private List<List<String>> wheels;

    public SlotScoreCalculator(List<List<String>> wheel) {
        this.wheels = wheel;
    }

    public int calculate(int bet) {
        int line = 0;
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Set<String> symbols = wheels.stream()
                    .map(wheel -> wheel.get(finalI))
                    .collect(Collectors.toSet());

            if (symbols.size() == 1)
                line++;
        }
        int odd = 0;
        if (line == 1) {
            odd = 40;
        }

        return bet * odd;
    }
}
