package com.example.slotdemo.service;

public class GameFlow {
    private Reels reels;
    private PayTable payTable;

    public GameFlow(Reels reels, PayTable payTable) {
        this.reels = reels;
        this.payTable = payTable;
    }

    public SpinResult runGameFlow(int bet) {
        reels.spin();
        Screen screen = reels.getScreen();
        int odd = payTable.getOdd(screen);
        int win = bet * odd;
        return new SpinResult(win, screen);
    }
}