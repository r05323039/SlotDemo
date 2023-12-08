package com.example.slotdemo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Random;


class SlotScoreCalculatorTest {

    private final Random random = Mockito.mock(Random.class);

    private SlotScoreCalculator sut;

    private SpinResult spinResult;

    // 建立假設
    private SlotScoreCalculator assume_sut(List<List<String>> rawReels) {
        SlotScoreCalculator sut = new SlotScoreCalculator(new PayTable(), new Reels(rawReels, new NativeRandomNumberGenerator(random)));
        return sut;
    }

    // 執行
    private void spin(int bet) {
        spinResult = sut.calculate(bet);
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
        sut = assume_sut(List.of(
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1, 1, 1, 1, 2);

        spin(10);

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
        sut = assume_sut(List.of(
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "2", "4")
        ));
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        spin(10);

        assertWin(100);
    }

    @Test
    void test03_two_line() {
        sut = assume_sut(List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "3")
        ));
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        spin(10);

        assertWin(400);
    }

    @Test
    void test04_three_line() {
        sut = assume_sut(List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1);

        spin(10);

        assertWin(1000);
    }

    @Test
    void test05_init() {
        sut = assume_sut(List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2")
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
}
