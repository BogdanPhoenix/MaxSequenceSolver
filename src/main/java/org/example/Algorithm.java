package org.example;

import lombok.NonNull;

import java.util.*;

public final class Algorithm {
    private Algorithm() {}

    public static String getLargestDigitalPuzzle(Collection<String> numbers) {
        Map<String, List<Puzzle>> puzzleMap = createPuzzleMap(numbers);
        Collection<Puzzle> longest = getLongestSequence(puzzleMap);

        return Puzzle.concatPuzzles(longest);
    }

    private static Map<String, List<Puzzle>> createPuzzleMap(@NonNull Collection<String> numbers) {
        Map<String, List<Puzzle>> puzzleMap = new HashMap<>();

        for(String number : numbers) {
            Puzzle puzzle = new Puzzle(number);

            if(puzzleMap.containsKey(puzzle.getStartNum())) {
                puzzleMap.get(puzzle.getStartNum()).add(puzzle);
            } else {
                List<Puzzle> puzzleList = new ArrayList<>();
                puzzleList.add(puzzle);
                puzzleMap.put(puzzle.getStartNum(), puzzleList);
            }
        }

        return puzzleMap;
    }

    private static Collection<Puzzle> getLongestSequence(Map<String, List<Puzzle>> puzzleMap) {
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
            Map<String, List<Puzzle>> puzzleMap,
            List<Puzzle> puzzles,
            Map<String, Collection<Puzzle>> cache,
            Set<String> visited
    ) {
        List<Puzzle> sequence = new LinkedList<>();

        for(Puzzle puzzle : puzzles) {
            if (cache.containsKey(puzzle.toString())) {
                return cache.get(puzzle.toString());
            }

            if (visited.contains(puzzle.toString())) {
                return List.of();
            }

            List<Puzzle> buffer = new LinkedList<>();
            visited.add(puzzle.toString());
            buffer.add(puzzle);

            String nextKey = puzzle.getEndNum();
            if (puzzleMap.containsKey(nextKey)) {
                List<Puzzle> nextPuzzle = puzzleMap.get(nextKey);
                buffer.addAll(createSequence(puzzleMap, nextPuzzle, cache, visited));
            }

            cache.put(puzzle.toString(), buffer);

            if(buffer.size() > sequence.size()) {
                sequence = buffer;
            }
        }

        return sequence;
    }
}
