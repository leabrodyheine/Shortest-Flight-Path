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
            Utility.printFrontier(frontier);
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
}