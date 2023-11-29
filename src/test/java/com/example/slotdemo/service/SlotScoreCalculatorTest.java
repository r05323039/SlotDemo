package com.example.slotdemo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


class SlotScoreCalculatorTest {

    @Test
    void test01_lose() {
        List<List<String>> wheel_lose = List.of(
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("x", "2", "1")
        );

        SlotScoreCalculator sut = new SlotScoreCalculator(wheel_lose);

        int win = sut.calculate(10);

        Assertions.assertThat(win).isEqualTo(0);

    }

    @Test
    void test02_one_line() {
        List<List<String>> wheel_one = List.of(
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "2", "1")
        );
        SlotScoreCalculator sut = new SlotScoreCalculator(wheel_one);

        int win = sut.calculate(10);

        Assertions.assertThat(win).isEqualTo(400);

    }

//    @Test
//    void method_test(){
//
//        List<List<String>> a = List.of(
//                List.of("A", "1", "2"),
//                List.of("A", "1", "2"),
//                List.of("A", "1", "2"),
//                List.of("A", "1", "2"),
//                List.of("A", "1", "2")
//                );
//        System.out.println(a);
//    }
}
