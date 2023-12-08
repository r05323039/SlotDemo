package com.example.slotdemo.service;

import java.util.List;

public class SlotScoreCalculator {

    private final PayTable baseGamePayTable;
    private final Reels baseGameReels;
    private final PayTable freeGamePayTable;
    private Reels freeGameReels;
    private int freeGameCount;
    private int freeGameBet;

    public SlotScoreCalculator(Reels baseGameReels, PayTable baseGamePayTable, Reels freeGameReels, PayTable freeGamePayTable) {
        this.baseGameReels = baseGameReels;
        this.baseGamePayTable = baseGamePayTable;
        this.freeGameReels = freeGameReels;
        this.freeGamePayTable = freeGamePayTable;
    }

    public SpinResult spinBase(int bet) throws WrongGameModeException {
        if (freeGameCount > 0) {
            throw new WrongGameModeException("wrong mode : Free Game");
        }

        baseGameReels.spin();
        Screen screen = baseGameReels.getScreen();
        int odd = baseGamePayTable.getOdd(screen);
        int win = bet * odd;
        tryTriggerFreeGame(screen, bet);
        return new SpinResult(win, screen);
    }

    private void tryTriggerFreeGame(Screen screen, int bet) {
        int sumA = 0;
        for (List<String> rawColumn : screen.rawScreen()) {
            long countA = rawColumn.stream()
                    .filter(s -> s.equals("A"))
                    .count();
            sumA += countA;
        }
        if (sumA >= 10) {
            freeGameCount += 3;
            freeGameBet = bet;
        }
    }

    public SpinResult spinFree() throws WrongGameModeException {
        if (freeGameCount <= 0) {
            throw new WrongGameModeException("wrong mode : Base Game");
        }

        freeGameReels.spin();
        Screen screen = freeGameReels.getScreen();
        int odd = freeGamePayTable.getOdd(screen);
        int win = freeGameBet * odd;
        tryDeactiveFreeGame();
        return new SpinResult(win, screen);
    }

    private void tryDeactiveFreeGame() {
        freeGameCount--;
    }

    public Screen getScreen() {
        if (freeGameCount > 0) {
            return freeGameReels.getScreen();
        } else {
            return baseGameReels.getScreen();
        }
    }


}
