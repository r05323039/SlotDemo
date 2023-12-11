package com.example.slotdemo.service;

import java.util.List;

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

    public Screen getScreen() {
        return reels.getScreen();
    }

    public List<Integer> getPositions() {
        return reels.getPositions();
    }

    public void restore(List<Integer> positions) {
        reels.setPositions(positions);
    }
}