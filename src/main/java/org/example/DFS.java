package org.example;

import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The DFS (Depth-First Search) class is responsible for finding the largest digital puzzle path
 * from a collection of number strings by creating puzzles and exploring possible connections between them.
 */
public final class DFS {
    private static final Map<Puzzle, List<Puzzle>> PUZZLE_MAP = new HashMap<>();
    private static final Map<Puzzle, Deque<Puzzle>> CACHE = new HashMap<>();

    private DFS() {}

    /**
     * Processes a collection of string numbers to create puzzles, establishes relationships between them,
     * and finds the largest digital puzzle path.
     *
     * @param numbers a collection of string representations of numbers.
     * @return a collection of puzzles representing the longest path. If the input is null or empty, returns an empty list.
     */
    public static Collection<Puzzle> getLargestDigitalPuzzle(Collection<String> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return List.of();
        }

        CACHE.clear();
        List<Puzzle> puzzles = numbers.stream()
                .map(Puzzle::new)
                .toList();

        createPuzzleMap(puzzles);
        return findLongestPath();
    }

    /**
     * Creates a map that stores puzzles based on their starting number.
     *
     * @param puzzles a collection of Puzzle objects.
     */
    private static void createPuzzleMap(@NonNull Collection<Puzzle> puzzles) {
        PUZZLE_MAP.clear();

        Map<String, List<Puzzle>> startIndex = puzzles.stream()
                .collect(Collectors.groupingBy(
                        Puzzle::getStartNum,
                        Collectors.toList()
                ));

        puzzles.forEach(puzzle ->
                PUZZLE_MAP.put(
                        puzzle,
                        startIndex.getOrDefault(puzzle.getEndNum(), Collections.emptyList())
                )
        );
    }

    /**
     * Finds the longest path from the puzzle map using depth-first search (DFS).
     *
     * @return a deque of Puzzle objects representing the longest path found.
     */
    private static Deque<Puzzle> findLongestPath() {
        return PUZZLE_MAP.keySet().stream()
                .map(node -> dfsRecursion(node, new HashSet<>(), new ArrayDeque<>()))
                .max(Comparator.comparingInt(Deque::size))
                .orElseGet(LinkedList::new);
    }

    /**
     * Performs a depth-first search (DFS) recursion to find the longest puzzle path starting from a given key.
     *
     * @param node the starting number of the puzzle path.
     * @param visited a set of visited puzzles to avoid revisiting them.
     * @param path the current path being explored.
     * @return a deque representing the longest path found.
     */
    private static Deque<Puzzle> dfsRecursion(Puzzle node, Set<Puzzle> visited, Deque<Puzzle> path) {
        if (CACHE.containsKey(node)) {
            return mergeCachedPath(node, path);
        }

        visited.add(node);
        path.addLast(node);

        Deque<Puzzle> maxPath = new LinkedList<>(path);

        for (Puzzle neighbor : PUZZLE_MAP.getOrDefault(node, List.of())) {
            if (visited.contains(neighbor)) {
                continue;
            }

            Deque<Puzzle> candidatePath = dfsRecursion(neighbor, visited, path);
            if (candidatePath.size() > maxPath.size()) {
                maxPath = candidatePath;
            }
        }

        path.removeLast();
        visited.remove(node);

        cachePath(maxPath);
        return maxPath;
    }

    /**
     * Merges a cached path with the current path to form a longer path.
     *
     * @param node the starting number of the puzzle.
     * @param currentPath the current puzzle path being explored.
     * @return a merged path that combines the current path with the cached path.
     */
    private static Deque<Puzzle> mergeCachedPath(Puzzle node, Deque<Puzzle> currentPath) {
        Deque<Puzzle> cachedPath = CACHE.get(node);
        Set<Puzzle> visited = new HashSet<>(currentPath);
        Deque<Puzzle> mergedPath = new LinkedList<>(currentPath);

        Puzzle lastPuzzleInPath = mergedPath.getLast();

        for (Puzzle puzzle : cachedPath) {
            if (isAdd(visited, puzzle, lastPuzzleInPath)) {
                mergedPath.add(puzzle);
                visited.add(puzzle);
                lastPuzzleInPath = puzzle;
            }
        }

        return mergedPath;
    }

    /**
     * Determines whether a puzzle can be added to the current path based on visitation and sequence rules.
     *
     * @param visited a set of already visited puzzles.
     * @param currentPuzzle the puzzle being considered for addition.
     * @param lastPuzzle the last puzzle in the current path.
     * @return true if the puzzle can be added, false otherwise.
     */
    private static boolean isAdd(Set<Puzzle> visited, Puzzle currentPuzzle, Puzzle lastPuzzle) {
        return !visited.contains(currentPuzzle) && lastPuzzle.isNext(currentPuzzle);
    }

    /**
     * Caches the given puzzle path for future use to avoid redundant calculations.
     *
     * @param path the puzzle path to cache.
     */
    private static void cachePath(Deque<Puzzle> path) {
        if (path.isEmpty()) {
            return;
        }

        Puzzle firstElement = path.getFirst();
        CACHE.merge(firstElement, path, (existing, newPath) ->
                newPath.size() > existing.size() ? new LinkedList<>(newPath) : existing
        );
    }
}
