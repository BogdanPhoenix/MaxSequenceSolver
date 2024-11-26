package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PuzzleTest {
    @Test
    void testCreateThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Puzzle(null));
    }

    @ParameterizedTest
    @MethodSource("testSourceToThrowCreatePuzzle")
    void testCreateThrowIllegalArgumentException(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Puzzle(input));
    }

    private static Stream<Arguments> testSourceToThrowCreatePuzzle() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("12"),
                Arguments.of("12d4")
        );
    }

    @ParameterizedTest
    @MethodSource("testSourceToSuccessCreatePuzzle")
    void testCreateSuccess(String input, String expectedString) {
        Puzzle test = new Puzzle(input);
        assertEquals(expectedString, test.toString());
    }

    private static Stream<Arguments> testSourceToSuccessCreatePuzzle() {
        return Stream.of(
                Arguments.of("248460", "248460"),
                Arguments.of("608017", "608017"),
                Arguments.of("962282", "962282"),
                Arguments.of("994725", "994725"),
                Arguments.of("177092", "177092"),
                Arguments.of("077092", "077092"),
                Arguments.of("7709", "7709")
        );
    }

    @ParameterizedTest
    @MethodSource("testSourceIsNextPuzzle")
    void testIsNextNumber(String input, String next, boolean expected) {
        Puzzle test = new Puzzle(input);
        Puzzle nextPuzzle = new Puzzle(next);
        assertEquals(test.isNext(nextPuzzle), expected);
    }

    private static Stream<Arguments> testSourceIsNextPuzzle() {
        return Stream.of(
                Arguments.of("248460", "608017", true),
                Arguments.of("248406", "068017", true),
                Arguments.of("608017", "962282", false)
        );
    }

    @Test
    void testConcatPuzzlesThrowIllegalArgumentException() {
        List<Puzzle> puzzles = List.of(new Puzzle("248460"), new Puzzle("177092"));
        assertThrows(IllegalArgumentException.class, () -> Puzzle.concatPuzzles(puzzles));
    }

    @ParameterizedTest
    @MethodSource("testSourceConcatPuzzle")
    void testConcatPuzzlesSuccess(Collection<String> input, String expected) {
        List<Puzzle> puzzles = input.stream()
                .map(Puzzle::new)
                .toList();

        String actual = Puzzle.concatPuzzles(puzzles);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testSourceConcatPuzzle() {
        return Stream.of(
                Arguments.of(List.of(), ""),
                Arguments.of(List.of("248460"), "248460"),
                Arguments.of(List.of("248460", "608017", "177092"), "24846080177092"),
                Arguments.of(List.of("4561", "617896", "9614251", "51425"), "4561789614251425"),
                Arguments.of(List.of("4687451", "5142698", "9800145", "4578421"), "4687451426980014578421")
        );
    }
}
