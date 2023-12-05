package com.example.slotdemo.service;

import java.util.List;
import java.util.stream.Stream;

public class Reel {
    private final List<String> grids;
    private final RandomNumberGenerator randomNumberGenerator;
    private int position;
    public Reel(List<String> grids, RandomNumberGenerator randomNumberGenerator) {
        this.grids = grids;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void roll() {
        position = randomNumberGenerator.nextInt(grids.size());
    }

    public List<String> getScreenColumn(int columnSize) {
        return Stream.concat(grids.stream(), grids.stream())
                .toList()
                .subList(position, position + columnSize);
    }
}
