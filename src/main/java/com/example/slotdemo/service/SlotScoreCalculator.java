package com.example.slotdemo.service;

public class SlotScoreCalculator {

    private final PayTable payTable;
    private Reels reels;

    public SlotScoreCalculator(PayTable table, Reels reels) {
        this.payTable = table;
        this.reels = reels;
    }

    public int calculate(int bet) {
        Screen screen = reels.reelsToScreen();
        int odd = payTable.getOdd(screen);
        return bet * odd;
    }
}
