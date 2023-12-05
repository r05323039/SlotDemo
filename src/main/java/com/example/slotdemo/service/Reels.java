package com.example.slotdemo.service;

import java.util.List;
import java.util.stream.Stream;

public final class Reels {
    private final List<List<String>> rawReels;
    private final RandomNumberGenerator randomNumberGenerator;

    public Reels(List<List<String>> rawReels, RandomNumberGenerator randomNumberGenerator) {
        this.rawReels = rawReels;
        this.randomNumberGenerator = randomNumberGenerator;
    }


    public Screen spin() {
        List<List<String>> rawScreen = this.rawReels.stream()
                .map(reel -> {
                    int firstSymbolIndex = randomNumberGenerator.nextInt(reel.size());
                    return Stream.concat(reel.stream(), reel.stream())
                            .toList()
                            .subList(firstSymbolIndex, firstSymbolIndex + 3);
                }).toList();
        return new Screen(rawScreen);
    }
}