package Algorithms;
import java.util.*;
import java.util.stream.Collectors;

import General.Node;

public class PartC_IDS {
    private static List<Node> depthLimitedSearch(Node current, Node goal, int depth, int maxDepth, Map<Node, Node> parentMap, Set<Node> visited, int planetSize) {
        // Print the current frontier for debugging
        printFrontier(Arrays.asList(current));

        if (depth > maxDepth) return null; // Depth limit reached
        if (current.equals(goal)) { // Goal check
            return constructPath(goal, parentMap); // Construct and return the path
        }
        visited.add(current);

        List<Node> successors = current.getSuccessors(planetSize, goal);
        Collections.sort(successors); // Sort successors to maintain consistent order

        for (Node next : successors) {
            if (!visited.contains(next)) {
                parentMap.put(next, current);
                List<Node> result = depthLimitedSearch(next, goal, depth + 1, maxDepth, parentMap, visited, planetSize);
                if (result != null) return result; // Found a path
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

            List<Node> path = depthLimitedSearch(start, goal, 0, depth, parentMap, visited, planetSize);
            if (path != null) {
                printPath(path, visited.size()); // Print the path and stats if found
                return path;
            }
        }
        System.out.println("fail");
        return null; // No path found within the depth limit
    }

    private static void printFrontier(Collection<Node> frontier) {
        if (!frontier.isEmpty()) {
            String result = frontier.stream()
                    .map(Node::toString)
                    .collect(Collectors.joining(","));
            System.out.println("[" + result + "]");
        }
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

    public static void printPath(List<Node> path, int visitedCount) {
        if (path == null || path.isEmpty()) {
            System.out.println("fail");
        } else {
            for (Node node : path) {
                System.out.print(node + "");
            }
            Node lastNode = path.get(path.size() - 1);
            double cost = lastNode.getCost();
            System.out.println();
            System.out.printf("%.3f\n%d\n", cost, visitedCount);
        }
    }
}