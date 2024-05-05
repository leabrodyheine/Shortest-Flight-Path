package Algorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import General.Node;
import General.Utility;

/**
 * Implements the Iterative Deepening Search (IDS) algorithm, which combines the
 * strengths of both Breadth-First Search and Depth-First Search by repeatedly
 * executing a depth-limited search and increasing the depth limit with each
 * iteration until the goal is found or the depth limit exceeds the specified
 * maximum.
 */
public class PartC_IDS {
    /**
     * Performs a depth-limited search from a given node up to a specified depth.
     * 
     * @param current    The current node from which to explore.
     * @param goal       The goal node to be reached.
     * @param depth      The maximum depth limit for this search iteration.
     * @param parentMap  A map to track the path from each node to its parent.
     * @param visited    A set of nodes that have already been visited in this
     *                   search iteration.
     * @param planetSize The size of the planet, influencing node expansion rules.
     * @return A list of nodes representing the path from the current node to the
     *         goal if found within the depth limit; otherwise, null.
     */
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

    /**
     * Executes the Iterative Deepening Search algorithm using a starting node and a
     * goal node, iteratively deepening the depth of the search until the goal is
     * found or the depth exceeds the size of the planet.
     * 
     * @param start      The starting node of the search.
     * @param goal       The target goal node.
     * @param planetSize The size of the planet, used to cap the depth of search.
     * @return A list of nodes representing the path from the start to the goal if
     *         found; otherwise, null.
     */
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
}