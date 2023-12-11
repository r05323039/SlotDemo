package com.example.slotdemo.service;

import java.util.List;

public class SlotScoreCalculator {
    private final GameFlow baseGameFlow;
    private final GameFlow freeGameFlow;
    private int freeGameCount;
    private int freeGameBet;
    private int sumA;

    public SlotScoreCalculator(GameFlow baseGameFlow, GameFlow freeGameFlow) {
        this.baseGameFlow = baseGameFlow;
        this.freeGameFlow = freeGameFlow;
    }

    public SpinResult spinBase(int bet) throws WrongGameModeException {
        if (isFreeGame()) {
            throw new WrongGameModeException("wrong mode : Free Game");
        }
        SpinResult spinResult = baseGameFlow.runGameFlow(bet);
        tryTriggerFreeGame(spinResult.getScreen(), bet);
        return new SpinResult(spinResult.getWin(), spinResult.getScreen());
    }

    public SpinResult spinFree() throws WrongGameModeException {
        if (!isFreeGame()) {
            throw new WrongGameModeException("wrong mode : Base Game");
        }
        SpinResult spinResult = freeGameFlow.runGameFlow(freeGameBet);
        tryDeactiveFreeGame();
        return spinResult;
    }

    private void tryTriggerFreeGame(Screen screen, int bet) {
        boolean isFreeGameTriggered = isFreeGameTriggered(screen);
        if (isFreeGameTriggered) {
            freeGameCount += addFreeGameCount();
            freeGameBet = bet;
        }
    }

    private int addFreeGameCount() {
        return 3;
    }

    private boolean isFreeGameTriggered(Screen screen) {
        int sumA = 0;
        for (List<String> rawColumn : screen.rawScreen()) {
            long countA = rawColumn.stream().filter(s -> s.equals("A")).count();
            sumA += countA;
        }
        return sumA >= 10;
    }

    private void tryDeactiveFreeGame() {
        freeGameCount--;
    }

    public Screen getScreen() {
        if (isFreeGame()) {
            return freeGameFlow.getScreen();
        } else {
            return baseGameFlow.getScreen();
        }
    }

    public boolean isFreeGame() {
        return freeGameCount > 0;
    }

    public Memento toMemento() {
        return new Memento(
                baseGameFlow.getPositions(),
                freeGameFlow.getPositions(),
                freeGameCount);
    }

    public void restore(Memento memento) {
        baseGameFlow.restore(memento.getBaseGamePositions());
        freeGameFlow.restore(memento.getFreeGamePositions());
        freeGameCount = memento.getFreeGameCount();
    }

    public int getFreeGameCount() {
        return freeGameCount;
    }
}
