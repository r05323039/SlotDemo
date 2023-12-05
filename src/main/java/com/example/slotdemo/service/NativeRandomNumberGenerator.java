package com.example.slotdemo.service;

import java.util.Random;

public class NativeRandomNumberGenerator implements RandomNumberGenerator {
    private final Random random;

    public NativeRandomNumberGenerator(Random random) {
        this.random = random;
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}