package com.example.slotdemo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Random;


class SlotScoreCalculatorTest {

    private final Random random = Mockito.mock(Random.class);


    @Test
    void test01_lose() {
        List<List<String>> wheels = List.of(
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2")
        );

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1, 1, 1, 1, 2);//不轉動

        SlotScoreCalculator sut = new SlotScoreCalculator(new PayTable(), new Reels(wheels, new NativeRandomNumberGenerator(random)));

        SpinResult result = sut.calculate(10);
        int win = result.getValue();

        Assertions.assertThat(win).isEqualTo(0);
        Assertions.assertThat(result.getScreen()).isEqualTo(
                new Screen(
                        List.of(
                                List.of("1", "2", "A"),
                                List.of("1", "2", "A"),
                                List.of("1", "2", "A"),
                                List.of("1", "2", "A"),
                                List.of("2", "A", "1")
                        )
                )
        );

        Assertions.assertThat(sut.getScreen()).isEqualTo(
                new Screen(
                        List.of(
                                List.of("1", "2", "A"),
                                List.of("1", "2", "A"),
                                List.of("1", "2", "A"),
                                List.of("1", "2", "A"),
                                List.of("2", "A", "1")
                        )
                )
        );
    }

    @Test
    void test02_one_line() {
        List<List<String>> wheels = List.of(
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "2", "4")
        );
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);//不轉動

        SlotScoreCalculator sut = new SlotScoreCalculator(new PayTable(), new Reels(wheels, new NativeRandomNumberGenerator(random)));

        int win = sut.calculate(10).getValue();

        Assertions.assertThat(win).isEqualTo(100);
    }

    @Test
    void test03_two_line() {
        List<List<String>> wheels = List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "3")
        );
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);//不轉動

        SlotScoreCalculator sut = new SlotScoreCalculator(new PayTable(), new Reels(wheels, new NativeRandomNumberGenerator(random)));

        int win = sut.calculate(10).getValue();

        Assertions.assertThat(win).isEqualTo(400);
    }

    @Test
    void test04_three_line() {
        List<List<String>> wheels = List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2")
        );
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1);//不轉動

        SlotScoreCalculator sut = new SlotScoreCalculator(new PayTable(), new Reels(wheels, new NativeRandomNumberGenerator(random)));

        int win = sut.calculate(10).getValue();

        Assertions.assertThat(win).isEqualTo(1000);
    }

    @Test
    void test05_init() {
        List<List<String>> wheels = List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2")
        );
        SlotScoreCalculator sut = new SlotScoreCalculator(new PayTable(), new Reels(wheels, new NativeRandomNumberGenerator(random)));

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
