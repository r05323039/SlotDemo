package com.example.slotdemo.service;

public class SlotScoreCalculator {

    private final PayTable payTable;
    private Reels reels;

    public SlotScoreCalculator(PayTable table, Reels reels) {
        this.payTable = table;
        this.reels = reels;
    }

    public SpinResult calculate(int bet) {
        Screen screen = reels.spin();
        int odd = payTable.getOdd(screen);
        int win = bet * odd;
        return new SpinResult(win,screen);
    }

    public Screen getScreen() {
        return reels.getScreen();
    }
}
