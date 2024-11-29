package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DataTest {
    @ParameterizedTest
    @MethodSource("testSourceGetLargestDigitalPuzzle")
    void testGetDataFromTxtIOException(String path, Class<Exception> expected) {
        assertThrows(expected, () -> Data.getDataFromTXT(path));
    }

    private static Stream<Arguments> testSourceGetLargestDigitalPuzzle() {
        return Stream.of(
                Arguments.of(null, NullPointerException.class),
                Arguments.of("", IllegalArgumentException.class),
                Arguments.of("  ", IllegalArgumentException.class),
                Arguments.of("test.json", IllegalArgumentException.class),
                Arguments.of("/home/test/test_number.txt", IOException.class)
        );
    }
}
