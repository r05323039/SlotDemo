package com.example.slotdemo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

public class RtpTest {
    private SlotScoreCalculator sut;

    private void assume_sut(List<List<String>> baseGameRawReels, List<List<String>> freeGameRawReels, RandomNumberGenerator random) {
        final Reels baseGameReels = new Reels(baseGameRawReels, random);
        final BaseGamePayTable baseGamePayTable = new BaseGamePayTable();
        final Reels freeGameReels = new Reels(freeGameRawReels, random);
        final FreeGamePayTable freeGamePayTable = new FreeGamePayTable();
        sut = new SlotScoreCalculator(
                new GameFlow(baseGameReels, baseGamePayTable), new GameFlow(freeGameReels, freeGamePayTable), new MyFreeGameTriggerringRules());
    }

    @Test
    void Rtp() throws WrongGameModeException {
        NativeRandomNumberGenerator randomNumberGenerator = new NativeRandomNumberGenerator(new Random());

        assume_sut(List.of(
                List.of("A", "A", "A", "A"),
                List.of("A", "A", "1", "A"),
                List.of("A", "A", "A"),
                List.of("A", "A", "A"),
                List.of("A", "A", "A")
        ), List.of(
                List.of("A", "2", "A", "A", "3"),
                List.of("A", "2", "A"),
                List.of("A", "A", "3")
        ), randomNumberGenerator);

        double totalWin = 0;
        double totalBet = 0;
        for (int i = 0; i < 100000; i++) {
            if (sut.isFreeGame()) {
                SpinResult spinResult = sut.spinFree();
                totalBet += 10;
                totalWin += spinResult.getWin();
            } else {
                SpinResult spinResult = sut.spinBase(10);
                totalBet += 10;
                totalWin += spinResult.getWin();
            }
        }

        double rtp = (totalWin) / totalBet;
        System.out.println(rtp);
        Assertions.assertThat(Math.abs(rtp - 80) < 1).isTrue();
    }
}