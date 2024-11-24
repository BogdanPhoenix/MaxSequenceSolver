package org.example;

import java.util.*;

public final class Algorithm {
    private Algorithm() {}

    public static String getLargestDigitalPuzzle(Collection<String> numbers) {
        Map<String, Puzzle> puzzleMap = new HashMap<>();

        for(String number : numbers) {
            Puzzle puzzle = new Puzzle(number);
            puzzleMap.put(puzzle.getStartNum(), puzzle);
        }

        Collection<Puzzle> longest = getLongestSequence(puzzleMap);

        return Puzzle.concatPuzzles(longest);
    }

    private static Collection<Puzzle> getLongestSequence(Map<String, Puzzle> puzzleMap) {
        Map<String, Collection<Puzzle>> cache = new HashMap<>();
        Collection<Puzzle> longest = new LinkedList<>();

        for (var puzzle : puzzleMap.values()) {
            Set<String> visited = new HashSet<>();
            Collection<Puzzle> sequence = createSequence(puzzleMap, puzzle, cache, visited);
            if (sequence.size() > longest.size()) {
                longest = sequence;
            }
        }

        return longest;
    }

    private static Collection<Puzzle> createSequence(
            Map<String, Puzzle> puzzleMap,
            Puzzle puzzle,
            Map<String, Collection<Puzzle>> cache,
            Set<String> visited
    ) {
        if (cache.containsKey(puzzle.getStartNum())) {
            return cache.get(puzzle.getStartNum());
        }

        if (visited.contains(puzzle.toString())) {
            return List.of();
        }

        visited.add(puzzle.toString());

        List<Puzzle> sequence = new LinkedList<>();
        sequence.add(puzzle);

        String nextKey = puzzle.getEndNum();
        if (puzzleMap.containsKey(nextKey)) {
            Puzzle nextPuzzle = puzzleMap.get(nextKey);
            sequence.addAll(createSequence(puzzleMap, nextPuzzle, cache, visited));
        }

        cache.put(puzzle.getStartNum(), sequence);
        return sequence;
    }
}
