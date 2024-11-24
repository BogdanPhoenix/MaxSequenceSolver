package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmTest {
    @ParameterizedTest
    @MethodSource("testSourceGetLargestDigitalPuzzle")
    void testGetLargestDigitalPuzzle(List<String> input, String expected) {
        String actual = Algorithm.getLargestDigitalPuzzle(input);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testSourceGetLargestDigitalPuzzle() {
        return Stream.of(
                Arguments.of(List.of(), ""),
                Arguments.of(List.of("608017", "248460", "962282", "994725", "177092"), "24846080177092"),
                Arguments.of(List.of("001748", "330541", "942517", "335401", "174800", "485500"), "942517480017485500")
        );
    }
}
