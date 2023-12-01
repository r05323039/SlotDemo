package com.example.slotdemo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class SlotScoreCalculatorTest {

    @Test
    void test01_lose() {
        List<List<String>> wheel_lose = List.of(
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("4", "2", "4")
        );

        SlotScoreCalculator sut = new SlotScoreCalculator(wheel_lose);

        int win = sut.calculate(10);

        Assertions.assertThat(win).isEqualTo(0);
    }

    @Test
    void test02_one_line() {
        List<List<String>> wheel_one = List.of(
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "3", "2"),
                List.of("A", "2", "4")
        );
        SlotScoreCalculator sut = new SlotScoreCalculator(wheel_one);

        int win = sut.calculate(10);

        Assertions.assertThat(win).isEqualTo(100);
    }

    @Test
    void test03_two_line() {
        List<List<String>> wheel_one = List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "3")
        );
        SlotScoreCalculator sut = new SlotScoreCalculator(wheel_one);

        int win = sut.calculate(10);

        Assertions.assertThat(win).isEqualTo(400);
    }

    @Test
    void test04_three_line() {
        List<List<String>> wheel_one = List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2")
        );
        SlotScoreCalculator sut = new SlotScoreCalculator(wheel_one);

        int win = sut.calculate(10);

        Assertions.assertThat(win).isEqualTo(1000);
    }
    @Test
    void tt(){
        List<List<String>> wheel_one = List.of(
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2"),
                List.of("A", "4", "2")
        );
        SlotScoreCalculator sut = new SlotScoreCalculator(wheel_one);


        boolean result = sut.tea("0000");
        Assertions.assertThat(result).isEqualTo(false);

        boolean result1 = sut.tea("0001");
        Assertions.assertThat(result1).isEqualTo(true);

        boolean result2 = sut.tea(null);
        Assertions.assertThat(result2).isEqualTo(false);

    }
}
