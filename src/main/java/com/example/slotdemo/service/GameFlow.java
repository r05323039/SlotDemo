package com.example.slotdemo.service;

public class GameFlow {
    public GameFlow() {
    }

    public SpinResult runGameFlow(int bet, Reels reels, PayTable payTable) {
        reels.spin();
        Screen screen = reels.getScreen();
        int odd = payTable.getOdd(screen);
        int win = bet * odd;
        return new SpinResult(win, screen);
    }
}