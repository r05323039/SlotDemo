package com.example.slotdemo.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SlotScoreCalculator {

    private final Map<Integer, Integer> odds = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0, 0),
            new AbstractMap.SimpleEntry<>(1, 10),
            new AbstractMap.SimpleEntry<>(2, 40),
            new AbstractMap.SimpleEntry<>(3, 100)
    );
    private final Random random;
    private List<List<String>> reels;

    public SlotScoreCalculator(List<List<String>> reels, Random random) {
        this.reels = reels;
        this.random = random;// 從建構子注入，測試才能控制行為
    }

    public int calculate(int bet) {
        List<List<String>> screen = getScreen();
        int lines = getLines(screen);
        int odd = getOdd(lines);
        return bet * odd;
    }

    private List<List<String>> getScreen() {
        return reels.stream()
                .map(reel -> {
                    int firstSymbolIndex = random.nextInt(reel.size());
                    return Stream.concat(reel.stream(), reel.stream())
                            .toList()
                            .subList(firstSymbolIndex, firstSymbolIndex + 3);
                }).toList();
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

    private int getOdd(int line) {
        int odd = odds.get(line);
        if (Objects.isNull(odd)) {
            throw new RuntimeException("Unsupported lines");
        }
        return odd;
    }
}
