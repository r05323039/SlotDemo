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

        SpinResult spinResult = runGameFlow(bet, baseGameReels, baseGamePayTable);
        tryTriggerFreeGame(spinResult.getScreen(), bet);
        return new SpinResult(spinResult.getValue(), spinResult.getScreen());
    }

    private SpinResult runGameFlow(int bet, Reels reels, PayTable payTable) {
        reels.spin();
        Screen screen = reels.getScreen();
        int odd = payTable.getOdd(screen);
        int win = bet * odd;
        return new SpinResult(win, screen);
    }


    private void tryTriggerFreeGame(Screen screen, int bet) {
        int sumA = 0;
        for (List<String> rawColumn : screen.rawScreen()) {
            long countA = rawColumn.stream().filter(s -> s.equals("A")).count();
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

        SpinResult spinResult = runGameFlow(freeGameBet, freeGameReels, freeGamePayTable);
        tryDeactiveFreeGame();
        return spinResult;
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
