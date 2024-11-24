package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DataTest {
    @Test
    void testGetDataFromTxtNullPointerException() {
        assertThrows(NullPointerException.class, () -> Data.getDataFromTXT(null));
    }

    @ParameterizedTest
    @MethodSource("testSourceGetLargestDigitalPuzzle")
    void testGetDataFromTxtIOException(String path) {
        assertThrows(IOException.class, () -> Data.getDataFromTXT(path));
    }

    private static Stream<Arguments> testSourceGetLargestDigitalPuzzle() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("  "),
                Arguments.of("test.json"),
                Arguments.of("/home/test/test_number.txt")
        );
    }
}
