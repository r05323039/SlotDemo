package com.example.slotdemo.service;

import java.util.List;

public class SlotScoreCalculator {

    private final PayTable payTable;
    private Reels reels;
    private Reels freeGameReels;

    public SlotScoreCalculator(PayTable table, Reels reels) {
        this.payTable = table;
        this.reels = reels;
    }

    public SpinResult spinBase(int bet) {
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
        this.freeGameReels = freeGameReels;
    }

    public SpinResult spinFreeGame() {
        freeGameReels.spin();
        Screen screen = freeGameReels.getScreen();
        int odd = 500;
        int win = 10 * odd;
        return new SpinResult(win, screen);

    }
}
