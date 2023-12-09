package com.example.slotdemo.service;

import java.util.List;

public class AllStarPayTable implements PayTable {
    @Override
    public int getOdd(Screen screen) {
        List<List<String>> rawScreen = screen.rawScreen();

        int countA = 0;
        for (List<String> row : rawScreen) {
            if (row.get(0).equals("A")) {
                countA++;
            } else {
                break;
            }
        }

        if (countA ==5){
            return 400;
        }

        return 200;
    }
}
