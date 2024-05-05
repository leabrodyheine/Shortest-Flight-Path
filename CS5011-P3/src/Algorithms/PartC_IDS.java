package Algorithms;

import java.util.*;

import General.Node;
import General.Utility;

public class PartC_IDS {

    private static List<Node> depthLimitedSearch(Node current, Node goal, int depth, Map<Node, Node> parentMap,
            Set<Node> visited, int planetSize) {
        visited.add(current);

        if (current.equals(goal)) {
            List<Node> path = Utility.constructPath(current, parentMap);
            return path;
        }
        if (depth == 0) {
            return null;
        }

        List<Node> successors = current.getSuccessors(planetSize, goal);
        Collections.sort(successors);

        for (Node node : successors) {
            if (!visited.contains(node)) {
                parentMap.put(node, current);
                List<Node> result = depthLimitedSearch(node, goal, depth - 1, parentMap, visited, planetSize);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    // Iterative Deepening Search
    public static List<Node> iterativeDeepeningSearch(Node start, Node goal, int planetSize) {
        for (int depth = 0; depth <= planetSize; depth++) {
            Set<Node> visited = new HashSet<>();
            Map<Node, Node> parentMap = new HashMap<>();
            parentMap.put(start, null);

            List<Node> path = depthLimitedSearch(start, goal, depth, parentMap, visited, planetSize);
            if (path != null) {
                System.out.println(visited);
                Utility.printPath(path, visited.size());
                return path;
            }
            System.out.println(visited);
        }
        System.out.println("fail");
        return null;
    }

    private static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }

    // public static void printPath(List<Node> path, int visitedCount) {
    //     if (path == null || path.isEmpty()) {
    //         System.out.println("fail");
    //     } else {
    //         path.forEach(node -> System.out.print(node));
    //         Node lastNode = path.get(path.size() - 1);
    //         System.out.printf("\n%.3f\n%d\n", lastNode.getCost(), visitedCount);
    //     }
    // }
}