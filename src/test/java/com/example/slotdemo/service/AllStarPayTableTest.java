package com.example.slotdemo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AllStarPayTableTest {

    @Test
    void A_5_400() {
        PayTable sut = new AllStarPayTable();

        Screen screen = new Screen(List.of(
                List.of("A", "1", "2"),
                List.of("A", "2", "5"),
                List.of("A", "1", "3"),
                List.of("A", "3", "4"),
                List.of("A", "1", "2")
        ));

        int actual = sut.getOdd(screen);

        Assertions.assertThat(actual).isEqualTo(400);
    }

    @Test
    void A_4_200() {
        PayTable sut = new AllStarPayTable();

        Screen screen = new Screen(List.of(
                List.of("A", "1", "2"),
                List.of("A", "2", "5"),
                List.of("A", "1", "3"),
                List.of("A", "3", "4"),
                List.of("2", "1", "2")
        ));

        int actual = sut.getOdd(screen);

        Assertions.assertThat(actual).isEqualTo(200);
    }
}
