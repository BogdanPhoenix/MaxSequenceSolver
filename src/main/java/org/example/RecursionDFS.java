package org.example;

import java.util.*;

/**
 * A concrete implementation of the DFS class that uses recursion to find the longest path in the puzzle map.
 * This implementation employs depth-first search (DFS) to explore all possible paths.
 */
public final class RecursionDFS extends DFS {
    /**
     * Finds the longest path in the puzzle map using a parallelized recursive DFS approach.
     *
     * @return a list of puzzles representing the longest path. If no path exists, returns an empty list.
     */
    protected List<Puzzle> findLongestPath() {
        return puzzlesMap.keySet().parallelStream()
                .map(node -> dfsRecursion(node, new HashSet<>(), new ArrayDeque<>()))
                .max(Comparator.comparingInt(List::size))
                .orElseGet(ArrayList::new);
    }

    /**
     * Performs a recursive depth-first search (DFS) to explore all paths starting from the given node.
     *
     * @param node     the current puzzle node being explored.
     * @param visited  a set of visited nodes to prevent cycles.
     * @param path     a deque representing the current path being explored.
     * @return the longest path found starting from the given node.
     */
    private List<Puzzle> dfsRecursion(Puzzle node, Set<Puzzle> visited, Deque<Puzzle> path) {
        visited.add(node);
        path.addLast(node);

        List<Puzzle> maxPath = new ArrayList<>(path);
        for (Puzzle neighbor : puzzlesMap.get(node)) {
            if (visited.contains(neighbor)) {
                continue;
            }

            List<Puzzle> candidatePath = dfsRecursion(neighbor, visited, path);
            if (candidatePath.size() > maxPath.size()) {
                maxPath = candidatePath;
            }
        }

        path.removeLast();
        visited.remove(node);
        return maxPath;
    }
}
