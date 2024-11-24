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
        Collection<Puzzle> longest = new LinkedList<>();

        for(var entry : puzzleMap.entrySet()) {
            Map<String, Puzzle> mapCopy = new HashMap<>(puzzleMap);
            Collection<Puzzle> buffer = createSequence(mapCopy, entry.getValue());

            if(buffer.size() > longest.size()) {
                longest = buffer;
            }
        }

        return longest;
    }

    private static Collection<Puzzle> createSequence(Map<String, Puzzle> puzzleMap, Puzzle puzzle) {
        if(!puzzleMap.containsKey(puzzle.getEndNum())) {
            return List.of(puzzle);
        }

        Puzzle nextPuzzle = puzzleMap.get(puzzle.getEndNum());
        List<Puzzle> subSequence = new LinkedList<>();

        puzzleMap.remove(nextPuzzle.getStartNum());
        subSequence.add(puzzle);
        subSequence.addAll(createSequence(puzzleMap, nextPuzzle));

        return subSequence;
    }
}
