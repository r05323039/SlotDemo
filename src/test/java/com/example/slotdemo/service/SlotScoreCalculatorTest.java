package com.example.slotdemo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;


class SlotScoreCalculatorTest {
    private final RandomNumberGenerator random = Mockito.mock(NativeRandomNumberGenerator.class);
    private SlotScoreCalculator sut;
    private SpinResult spinResult;

    // 建立假設
    private void assume_sut(List<List<String>> baseGameRawReels, List<List<String>> freeGameRawReels) {
        sut = new SlotScoreCalculator(
                new PayTable(),
                new Reels(baseGameRawReels, random),
                new Reels(freeGameRawReels, random));
    }

    // 執行
    private void spin_base(int bet) {
        spinResult = sut.spinBase(bet);
    }

    // 驗證
    private void assertWin(int win) {
        Assertions.assertThat(spinResult.getValue()).isEqualTo(win);
    }

    private void assertScreen(List<List<String>> rawScreen) {
        Assertions.assertThat(spinResult.getScreen()).isEqualTo(new Screen(rawScreen));
    }

    @Test
    void test01_lose() {
        assume_sut(List.of(
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1, 1, 1, 1, 2);

        spin_base(10);

        assertWin(0);
        assertScreen(List.of(
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("2", "A", "1")
        ));
    }


    @Test
    void test02_one_line() {
        assume_sut(List.of(
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "2", "4")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ));
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        spin_base(10);

        assertWin(100);
    }

    @Test
    void test03_two_line() {
        assume_sut(List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "3")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ));
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        spin_base(10);

        assertWin(400);
    }

    @Test
    void test04_three_line() {
        assume_sut(List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1);

        spin_base(10);

        assertWin(1000);
    }

    @Test
    void test05_init() {
        assume_sut(List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ));

        Screen screen = sut.getScreen();

        Assertions.assertThat(screen).isEqualTo(
                new Screen(List.of(
                        List.of("A", "4", "2"),
                        List.of("A", "4", "2"),
                        List.of("A", "4", "2"),
                        List.of("A", "4", "2"),
                        List.of("A", "4", "2")
                )));
    }

    @Test
    void test06_free_game() {
        assume_sut(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        Reels freeGameReels = new Reels(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ), random);

        sut.setFreeGameReels(freeGameReels);

        spin_base(10);

        spin_free();

        assertWin(5000);
        assertScreen(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ));
    }

    private void spin_free() {
        spinResult = sut.spinFreeGame();
    }
}
