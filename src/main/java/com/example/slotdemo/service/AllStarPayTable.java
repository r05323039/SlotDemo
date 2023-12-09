package com.example.slotdemo.service;

public class AllStarPayTable implements PayTable {
    @Override
    public int getOdd(Screen screen) {
        return 400;
    }
}
