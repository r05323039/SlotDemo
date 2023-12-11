package com.example.slotdemo.service;

import java.util.List;

public class MyFreeGameTriggerringRules implements FreeGameTriggerringRules {
    @Override
    public int addFreeGameCount() {
        return 3;
    }

    @Override
    public boolean isFreeGameTriggered(Screen screen) {
        int sumA = 0;
        for (List<String> rawColumn : screen.rawScreen()) {
            long countA = rawColumn.stream().filter(s -> s.equals("A")).count();
            sumA += countA;
        }
        return sumA >= 10;
    }
}