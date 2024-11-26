package org.example;

import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Abstract class representing a Depth-First Search (DFS) strategy for solving a puzzle-related problem.
 * This class maintains a mapping of puzzles and provides functionality to find the largest digital puzzle.
 */
public abstract class DFS {
    /**
     * A map that links each puzzle to a list of puzzles that can follow it, based on specific criteria.
     */
    protected final Map<Puzzle, List<Puzzle>> puzzlesMap = new HashMap<>();

    /**
     * Processes a collection of string numbers to create puzzles, establishes relationships between them,
     * and finds the largest digital puzzle path.
     *
     * @param numbers a collection of string representations of numbers.
     * @return a list of puzzles representing the longest path. If the input is null or empty, returns an empty list.
     */
    public List<Puzzle> getLargestDigitalPuzzle(Collection<String> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return List.of();
        }

        List<Puzzle> puzzles = numbers.stream()
                .map(Puzzle::new)
                .toList();

        createPuzzleMap(puzzles);
        return findLongestPath();
    }

    /**
     * Constructs a map of relationships between puzzles.
     * Each puzzle is mapped to a list of puzzles that start where it ends.
     *
     * @param puzzles a collection of puzzles to be mapped.
     */
    private void createPuzzleMap(@NonNull Collection<Puzzle> puzzles) {
        puzzlesMap.clear();

        Map<String, List<Puzzle>> startIndex = puzzles.stream()
                .collect(Collectors.groupingBy(
                        Puzzle::getStartNum,
                        Collectors.toList()
                ));

        puzzles.forEach(puzzle ->
                puzzlesMap.put(
                        puzzle,
                        startIndex.getOrDefault(puzzle.getEndNum(), Collections.emptyList())
                )
        );
    }

    /**
     * Abstract method to be implemented by subclasses to find the longest path in the puzzle map.
     *
     * @return a list of puzzles representing the longest path.
     */
    protected abstract List<Puzzle> findLongestPath();
}
