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
        final Reels baseGameReels = new Reels(baseGameRawReels, random);
        final BaseGamePayTable baseGamePayTable = new BaseGamePayTable();
        final Reels freeGameReels = new Reels(freeGameRawReels, random);
        final FreeGamePayTable freeGamePayTable = new FreeGamePayTable();
        sut = new SlotScoreCalculator(
                new GameFlow(baseGameReels, baseGamePayTable), new GameFlow(freeGameReels, freeGamePayTable));
    }

    // 執行
    private void do_spin_base(int bet) throws WrongGameModeException {
        spinResult = sut.spinBase(bet);
    }

    private void do_spin_free() throws WrongGameModeException {
        spinResult = sut.spinFree();
    }

    // 驗證
    private void assert_win(int win) {
        Assertions.assertThat(spinResult.getWin()).isEqualTo(win);
    }

    private void assert_spinResult_screen(List<List<String>> rawScreen) {
        Assertions.assertThat(spinResult.getScreen()).isEqualTo(new Screen(rawScreen));
    }

    private void assert_sut_screen(List<List<String>> rawReels) {
        Assertions.assertThat(sut.getScreen()).isEqualTo(new Screen(rawReels));
    }

    @Test
    void test01_lose() throws WrongGameModeException {
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

        do_spin_base(10);

        assert_win(0);
        assert_spinResult_screen(List.of(
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("2", "A", "1")
        ));
    }

    @Test
    void test02_one_line() throws WrongGameModeException {
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

        do_spin_base(10);

        assert_win(100);
    }

    @Test
    void test03_two_line() throws WrongGameModeException {
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

        do_spin_base(10);

        assert_win(400);
    }

    @Test
    void test04_three_line() throws WrongGameModeException {
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

        do_spin_base(10);

        assert_win(1000);
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
    void test06_free_game_three_line() throws WrongGameModeException {
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

        do_spin_base(10);

        do_spin_free();

        assert_win(5000);
        assert_spinResult_screen(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        ));
    }

    @Test
    void test07_free_game_two_line() throws WrongGameModeException {
        assume_sut(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        do_spin_base(10);

        do_spin_free();

        assert_win(3000);
        assert_spinResult_screen(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        ));
    }

    @Test
    void test08_free_game_one_line() throws WrongGameModeException {
        assume_sut(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "2", "1")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        do_spin_base(10);

        do_spin_free();

        assert_win(1000);
        assert_spinResult_screen(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "2", "1")
        ));
    }

    @Test
    void test09_can_not_play_base_in_free_mode() throws WrongGameModeException {
        assume_sut(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "2", "1")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        do_spin_base(10);

        Assertions.assertThatThrownBy(
                () -> do_spin_base(10)
        ).hasMessageContaining("wrong mode : Free Game");
    }

    @Test
    void test10_free_mode_finish() throws WrongGameModeException {
        assume_sut(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "2", "1")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        do_spin_base(10);
        do_spin_free();
        do_spin_free();
        do_spin_free();

        Assertions.assertThatThrownBy(
                () -> do_spin_free()
        ).hasMessageContaining("wrong mode : Base Game");
    }

    @Test
    void test10_free_mode_get_screen() throws WrongGameModeException {
        assume_sut(List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        ), List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "2", "1")
        ));

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);

        do_spin_base(10);

        Assertions.assertThat(sut.getScreen()).isEqualTo(
                new Screen(List.of(
                        List.of("A", "A", "2"),
                        List.of("A", "A", "2"),
                        List.of("A", "2", "1")
                )));
    }

    @Test
    void test11_base_game_recovery() throws WrongGameModeException {
        List<List<String>> baseGameRawReels = List.of(
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2"),
                List.of("A", "1", "2")
        );
        List<List<String>> freeGameRawReels = List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2")
        );
        
        assume_sut(baseGameRawReels, freeGameRawReels);

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1, 1, 1, 1, 2);

        do_spin_base(10);

        Memento memento = sut.toMemento();

        assume_sut(baseGameRawReels, freeGameRawReels);

        sut.restore(memento);

        assert_sut_screen(List.of(
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("1", "2", "A"),
                List.of("2", "A", "1")
        ));
    }

    @Test
    void test12_free_game_recovery() throws WrongGameModeException {
        List<List<String>> baseGameRawReels = List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "A", "1")
        );
        List<List<String>> freeGameRawReels = List.of(
                List.of("A", "A", "2"),
                List.of("A", "A", "2"),
                List.of("A", "2", "1")
        );

        assume_sut(baseGameRawReels, freeGameRawReels);

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0, 0, 0, 0, 0, 1, 0);

        do_spin_base(10);

        do_spin_free();

        Memento memento = sut.toMemento();

        assume_sut(baseGameRawReels, freeGameRawReels);

        sut.restore(memento);

        assert_sut_screen(List.of(
                List.of("A", "2", "A"),
                List.of("A", "A", "2"),
                List.of("A", "2", "1")
        ));

        Assertions.assertThat(sut.getFreeGameCount()).isEqualTo(2);
    }
}
