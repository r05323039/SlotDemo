package com.example.slotdemo.service;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class CyclicRNG implements RandomNumberGenerator{
    private Queue<Integer> expectedValues = new ArrayDeque<>();

    public void resetExpectedValues(List<Integer> expected){
        this.expectedValues.clear();
        this.expectedValues.addAll(expected);
    }
    @Override
    public int nextInt(int bound) {
        Integer polled = expectedValues.remove();

        expectedValues.add(polled);

        return polled;
    }
}
