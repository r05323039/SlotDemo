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

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1,1,1,1,2);//不轉動

        SlotScoreCalculator sut = new SlotScoreCalculator(new PayTable(), new Reels(wheels,  new NativeRandomNumberGenerator(random)));

        int win = sut.calculate(10);

        Assertions.assertThat(win).isEqualTo(0);
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

        SlotScoreCalculator sut = new SlotScoreCalculator(new PayTable(), new Reels(wheels,  new NativeRandomNumberGenerator(random)));

        int win = sut.calculate(10);

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

        int win = sut.calculate(10);

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

        int win = sut.calculate(10);

        Assertions.assertThat(win).isEqualTo(1000);
    }

}
