package org.example;

import java.util.*;

/**
 * A concrete implementation of the DFS class that uses an iterative approach to find the longest path in a puzzle map.
 * This implementation avoids recursion by using a stack-based strategy.
 */
public final class IterativeDFS extends DFS {
    /**
     * Finds the longest path in the puzzle map using an iterative DFS approach.
     * The method processes nodes in parallel to improve performance for large datasets.
     *
     * @return a list of puzzles representing the longest path found in the map.
     */
    protected List<Puzzle> findLongestPath() {
        List<Puzzle> globalLongestPath = new ArrayList<>();

        puzzlesMap.keySet().parallelStream().forEach(startNode -> {
            List<Puzzle> longestPath = dfsIterative(startNode);

            synchronized (IterativeDFS.class) {
                if (longestPath.size() > globalLongestPath.size()) {
                    globalLongestPath.clear();
                    globalLongestPath.addAll(longestPath);
                }
            }
        });

        return globalLongestPath;
    }

    /**
     * Performs an iterative depth-first search (DFS) to find the longest path starting from a given node.
     *
     * @param startNode the starting puzzle node for the DFS.
     * @return a list of puzzles representing the longest path found from the starting node.
     */
    private List<Puzzle> dfsIterative(Puzzle startNode) {
        Deque<List<Puzzle>> stack = new ArrayDeque<>();
        stack.push(List.of(startNode));
        List<Puzzle> longestPath = new ArrayList<>(List.of(startNode));

        while (!stack.isEmpty()) {
            List<Puzzle> currentPath = stack.pop();
            Puzzle lastNode = currentPath.getLast();

            if (currentPath.size() > longestPath.size()) {
                longestPath = new ArrayList<>(currentPath);
            }

            List<Puzzle> neighbors = puzzlesMap.getOrDefault(lastNode, List.of());

            for (Puzzle neighbor : neighbors) {
                if (currentPath.contains(neighbor)) {
                    continue;
                }

                List<Puzzle> newPath = new ArrayList<>(currentPath);
                newPath.add(neighbor);
                stack.push(newPath);
            }
        }

        return longestPath;
    }
}
