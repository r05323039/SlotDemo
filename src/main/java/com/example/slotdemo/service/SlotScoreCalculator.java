package com.example.slotdemo.service;

import java.util.*;
import java.util.stream.Stream;

public class SlotScoreCalculator {

    private final Random random;
    private final PayTable payTable;
    private List<List<String>> reels;

    public SlotScoreCalculator(List<List<String>> reels, Random random, PayTable table) {
        this.reels = reels;
        this.random = random;// 從建構子注入，測試才能控制行為
        this.payTable = table;
    }

    public int calculate(int bet) {
        Screen screen = reelsToScreen();
        int odd = payTable.getOdd(screen);
        return bet * odd;
    }

    private Screen reelsToScreen() {
        List<List<String>> rawScreen = reels.stream()
                .map(reel -> {
                    int firstSymbolIndex = random.nextInt(reel.size());
                    return Stream.concat(reel.stream(), reel.stream())
                            .toList()
                            .subList(firstSymbolIndex, firstSymbolIndex + 3);
                }).toList();
        return new Screen(rawScreen);
    }
}
