package org.example;

import lombok.NonNull;

import java.util.*;

public final class Algorithm {
    private static final Map<String, List<Puzzle>> puzzleMap = new HashMap<>();

    private Algorithm() {}

    public static String getLargestDigitalPuzzle(Collection<String> numbers) {
        createPuzzleMap(numbers);
        Collection<Puzzle> longest = getLongestSequence();

        return Puzzle.concatPuzzles(longest);
    }

    private static void createPuzzleMap(@NonNull Collection<String> numbers) {
        puzzleMap.clear();

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
    }

    private static Collection<Puzzle> getLongestSequence() {
        Collection<Puzzle> longest = new ArrayList<>();

        for (var entry : puzzleMap.entrySet()) {
            Collection<Puzzle> sequence = createSequence(entry.getValue(), new HashSet<>());
            if (sequence.size() > longest.size()) {
                longest = sequence;
            }
        }

        return longest;
    }


    private static Collection<Puzzle> createSequence(List<Puzzle> puzzles, Set<String> visited) {
        Collection<Puzzle> sequence = new LinkedList<>();

        for(Puzzle puzzle : puzzles) {
            Collection<Puzzle> buffer = createSequence(puzzle, visited);

            if(buffer.size() > sequence.size()) {
                sequence = buffer;
            }
        }

        return sequence;
    }

    private static Collection<Puzzle> createSequence(Puzzle puzzle, Set<String> visited) {
        if (visited.contains(puzzle.toString())) {
            return List.of();
        }

        List<Puzzle> sequence = new LinkedList<>();
        visited.add(puzzle.toString());
        sequence.add(puzzle);

        String nextKey = puzzle.getEndNum();
        if (puzzleMap.containsKey(nextKey)) {
            List<Puzzle> nextPuzzle = puzzleMap.get(nextKey);
            sequence.addAll(createSequence(nextPuzzle, visited));
        }

        visited.remove(puzzle.toString());
        return sequence;
    }
}
