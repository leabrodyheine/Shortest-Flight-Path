package Algorithms;

import java.util.*;

import General.Node;
import General.Utility;

/**
 * Implements the Breadth-First Search (BFS) algorithm for navigating through a
 * graph.
 */
public class PartA_BFS {
    /**
     * Performs BFS to find the shortest path from the start node to the goal node.
     *
     * @param start      The starting node of the path.
     * @param goal       The goal node of the path.
     * @param planetSize The size of the planet, used to limit the search area.
     * @return A list of nodes representing the path from start to goal if one
     *         exists, or null if no path is found.
     */
    public static List<Node> bfs(Node start, Node goal, int planetSize) {
        Queue<Node> frontier = new ArrayDeque<>();
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        frontier.add(start);
        parentMap.put(start, null);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);
            Node current = frontier.poll();

            visited.add(current);
            if (current.equals(goal)) {
                List<Node> path = Utility.constructPath(current, parentMap);
                Utility.printPath(path, visited.size());
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize, goal);

            Collections.sort(successors);

            for (Node next : successors) {
                if (!visited.contains(next) && !frontier.contains(next)) {
                    frontier.add(next);
                    parentMap.put(next, current);
                }
            }
        }
        Utility.algorithmFails(visited);
        return null;
    }

    /**
     * Prints the current state of the frontier.
     *
     * @param frontier The collection of nodes currently in the frontier.
     */
    private static void printFrontier(Collection<Node> frontier) {
        if (!frontier.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for (Node node : frontier) {
                if (result.length() > 0)
                    result.append(",");
                result.append(node.toString());
            }
            System.out.println("[" + result + "]");
        }
    }

    // /**
    //  * Constructs the path from the goal node back to the start node using the
    //  * parent map.
    //  *
    //  * @param goal      The goal node where the path ends.
    //  * @param parentMap A map of child nodes to their parent nodes as discovered by
    //  *                  the BFS.
    //  * @return A list of nodes representing the path from the start to the goal.
    //  */
    // private static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
    //     List<Node> path = new ArrayList<>();
    //     for (Node current = goal; current != null; current = parentMap.get(current)) {
    //         path.add(current);
    //     }
    //     Collections.reverse(path);
    //     return path;
    // }

    // /**
    //  * Prints the path found by the BFS.
    //  *
    //  * @param path         The list of nodes constituting the path.
    //  * @param visitedCount The number of nodes visited during the search.
    //  */
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