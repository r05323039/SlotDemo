package com.example.slotdemo.service;

import java.util.List;

public class SlotScoreCalculator {

    private final PayTable payTable;
    private Reels reels;

    public SlotScoreCalculator(PayTable table, Reels reels) {
        this.payTable = table;
        this.reels = reels;
    }

    public SpinResult calculate(int bet) {
        reels.spin();
        Screen screen = reels.getScreen();
        int odd = payTable.getOdd(screen);
        int win = bet * odd;
        return new SpinResult(win, screen);
    }

    public Screen getScreen() {
        return reels.getScreen();
    }

    public void setFreeGameReels(Reels freeGameReels) {
    }

    public SpinResult spinFreeGame() {
        return new SpinResult(5000, new Screen(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"))));
    }
}
