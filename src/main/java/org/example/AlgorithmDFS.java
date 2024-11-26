package org.example;

import lombok.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AlgorithmDFS {
    private static final Map<Puzzle, List<Puzzle>> PUZZLES_MAP = new HashMap<>();

    private AlgorithmDFS() {}

    public static String getLargestDigitalPuzzle(Collection<String> numbers) {
        List<Puzzle> puzzles = numbers.stream()
                .map(Puzzle::new)
                .toList();

        createPuzzleMap(puzzles);
        var result = findLongestPathParallel();
        return Puzzle.concatPuzzles(result);
    }

    private static void createPuzzleMap(@NonNull Collection<Puzzle> puzzles) {
        PUZZLES_MAP.clear();

        Map<String, List<Puzzle>> startIndex = new HashMap<>();
        for (Puzzle puzzle : puzzles) {
            startIndex.computeIfAbsent(puzzle.getStartNum(), k -> new ArrayList<>()).add(puzzle);
        }

        for (Puzzle puzzle : puzzles) {
            List<Puzzle> matchingPuzzles = startIndex.getOrDefault(puzzle.getEndNum(), Collections.emptyList());
            PUZZLES_MAP.put(puzzle, new ArrayList<>(matchingPuzzles));
        }
    }

    private static List<Puzzle> findLongestPathParallel() {
        return PUZZLES_MAP.keySet().parallelStream()
                .map(node -> dfs(node, new HashSet<>(), new ArrayDeque<>()))
                .max(Comparator.comparingInt(List::size))
                .orElseGet(ArrayList::new);
    }

    private static List<Puzzle> dfs(Puzzle node, Set<Puzzle> visited, Deque<Puzzle> path) {
        visited.add(node);
        path.addLast(node);

        List<Puzzle> maxPath = new ArrayList<>(path);
        for (Puzzle neighbor : PUZZLES_MAP.get(node)) {
            if (visited.contains(neighbor)) {
                continue;
            }

            List<Puzzle> candidatePath = dfs(neighbor, visited, path);
            if (candidatePath.size() > maxPath.size()) {
                maxPath = candidatePath;
            }
        }

        path.removeLast();
        visited.remove(node);
        return maxPath;
    }
}
