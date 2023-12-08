package com.example.slotdemo.service;

import java.util.List;

public class SlotScoreCalculator {

    private final PayTable payTable;
    private final Reels reels;
    private Reels freeGameReels;
    private int freeGameCount;

    public SlotScoreCalculator(PayTable table, Reels reels, Reels freeGameReels) {
        this.payTable = table;
        this.reels = reels;
        this.freeGameReels = freeGameReels;
    }

    public SpinResult spinBase(int bet) {
        if (freeGameCount > 0) {
            throw new RuntimeException("wrong mode : Free Game");
        }


        reels.spin();
        Screen screen = reels.getScreen();
        int odd = payTable.getOdd(screen);
        int win = bet * odd;

        int sumA = 0;
        for (List<String> rawColumn : screen.rawScreen()) {
            long countA = rawColumn.stream()
                    .filter(s -> s.equals("A"))
                    .count();
            sumA += countA;
        }
        if (sumA >= 10) {
            freeGameCount += 3;
        }

        return new SpinResult(win, screen);
    }

    public Screen getScreen() {
        return reels.getScreen();
    }

    public void setFreeGameReels(Reels freeGameReels) {
        this.freeGameReels = freeGameReels;
    }

    public SpinResult spinFreeGame() {
        freeGameReels.spin();
        Screen screen = freeGameReels.getScreen();

        int odd = 0;
        int line = screen.countStraightLines();
        if (line == 3) {
            odd = 500;
        } else if (line == 2) {
            odd = 300;
        } else if (line == 1) {
            odd = 100;
        }

        int win = 10 * odd;
        return new SpinResult(win, screen);

    }
}
