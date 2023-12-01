package com.example.slotdemo.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public record Reels(List<List<String>> rawReels, Random random) {
    public Screen reelsToScreen() {
        List<List<String>> rawScreen = this.rawReels.stream()
                .map(reel -> {
                    int firstSymbolIndex = this.random.nextInt(reel.size());
                    return Stream.concat(reel.stream(), reel.stream())
                            .toList()
                            .subList(firstSymbolIndex, firstSymbolIndex + 3);
                }).toList();
        return new Screen(rawScreen);
    }
}