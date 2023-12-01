package com.example.slotdemo.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record Screen(List<List<String>> rawScreen) {

    public int countStraightLines() {
        int line = 0;
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Set<String> differentSymbols = this.rawScreen.stream()
                    .map(wheel -> wheel.get(finalI))
                    .collect(Collectors.toSet());

            if (differentSymbols.size() == 1)
                line++;
        }
        return line;
    }
}