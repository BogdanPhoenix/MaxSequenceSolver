package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DFSTest {
    private static final DFS algorithm = new RecursionDFS();

    @ParameterizedTest
    @MethodSource("testSourceGetLargestDigitalPuzzle")
    void testGetLargestDigitalPuzzle(List<String> input, String expected) {
        List<Puzzle> puzzles = algorithm.getLargestDigitalPuzzle(input);
        String actual = Puzzle.concatPuzzles(puzzles);

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testSourceGetLargestDigitalPuzzle() {
        return Stream.of(
                Arguments.of(List.of(), ""),
                Arguments.of(List.of("608017", "248460", "962282", "994725", "177092"), "24846080177092"),
                Arguments.of(List.of("001748", "330541", "942517", "335401", "174800", "485500"), "942517480017485500"),
                Arguments.of(List.of("397853", "871196", "277603", "749226", "839595", "131852",
                                "409432", "810698", "456030", "529185", "758823", "265024", "051041", "699031", "737269", "139340",
                                "730977", "249786", "039931", "055669", "100107", "653178", "279773", "336550", "332847", "685485",
                                "423269", "193536", "890062", "377637", "595777", "412134", "322736", "546929", "616370", "767332",
                                "781184", "920944", "851005", "258850", "064083", "051202", "427711", "359855", "540928", "314284"),
                        "13185291851005566990314284"),
                Arguments.of(List.of("397853", "871196", "277603", "749226", "839595", "131852",
                                "409432", "810698", "456030", "529185", "758823", "265024", "051041", "699031", "737269", "139340",
                                "730977", "249786", "039931", "055669", "100107", "653178", "279773", "336550", "332847", "685485",
                                "423269", "193536", "890062", "377637", "595777", "412134", "322736", "546929", "616370", "767332",
                                "781184", "920944", "851005", "258850", "064083", "051202", "427711", "359855", "051041", "314284"),
                        "13185291851005566990314284"),
                Arguments.of(List.of("397853", "871196", "277603", "749226", "839595", "131852", "359432",
                                "810698", "453153", "529185", "758823", "265024", "051041", "699031", "737269", "139340",
                                "730977", "249786", "039931", "055669", "100107", "653178", "279773", "466550", "332847",
                                "685485", "693269", "193536", "890062", "377637", "595777", "502134", "322736", "546929",
                                "616370", "767332", "781184", "920944", "851005", "508850", "064083", "051202", "427711",
                                "359855", "540928", "314284"),
                        "131852918510055669326990314284")
        );
    }
}
