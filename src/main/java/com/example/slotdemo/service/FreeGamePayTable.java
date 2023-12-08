package com.example.slotdemo.service;

public class FreeGamePayTable implements PayTable {

    public int getOdd(Screen screen) {
        int odd = 0;
        int line = screen.countStraightLines();
        if (line == 3) {
            odd = 500;
        } else if (line == 2) {
            odd = 300;
        } else if (line == 1) {
            odd = 100;
        }
        return odd;
    }
}