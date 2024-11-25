package org.example;

import lombok.NonNull;

import java.util.*;

public class AlgorithmDFS {
    private static Map<String, List<String>> puzzleMap = new HashMap<>();

    private AlgorithmDFS() {}

    public static String getLargestDigitalPuzzle(Collection<String> numbers) {
        createPuzzleMap(numbers);
        var result = findLongestPathWithLogs();
        return convertListToString(result);
    }

    private static void createPuzzleMap(@NonNull Collection<String> numbers) {
        puzzleMap.clear();

        for (String num : numbers) {
            String endKey = num.substring(num.length() - 2);
            puzzleMap.putIfAbsent(num, new ArrayList<>());
            for (String otherNum : numbers) {
                if (!num.equals(otherNum)) {
                    String otherStartKey = otherNum.substring(0, 2);
                    if (endKey.equals(otherStartKey)) {
                        puzzleMap.get(num).add(otherNum);
                    }
                }
            }
        }
    }

    private static List<String> findLongestPathWithLogs() {
        List<String> longestPath = new ArrayList<>();

        for (String node : puzzleMap.keySet()) {
            List<String> candidatePath = dfs(node, new HashSet<>(), new ArrayDeque<>());
            if (candidatePath.size() > longestPath.size()) {
                longestPath = candidatePath;
            }
        }

        return longestPath;
    }

    private static List<String> dfs(String node, Set<String> visited, Deque<String> path) {
        visited.add(node);
        path.addLast(node);

        List<String> maxPath = new ArrayList<>(path);
        for (String neighbor : puzzleMap.get(node)) {
            if (visited.contains(neighbor)) {
                continue;
            }

            List<String> candidatePath = dfs(neighbor, visited, path);
            if (candidatePath.size() > maxPath.size()) {
                maxPath = candidatePath;
            }
        }

        path.removeLast();
        visited.remove(node);
        return maxPath;
    }

    private static String convertListToString(List<String> list) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < list.size(); i++) {
            if(i == 0) {
                builder.append(list.get(i));
                continue;
            }

            builder.append(list.get(i).substring(2));
        }

        return builder.toString();
    }
}
