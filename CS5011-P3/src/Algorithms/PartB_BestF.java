package Algorithms;

import General.Node;
import General.Utility;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implements the Best-First Search algorithm, an informed search that
 * prioritizes nodes based on a heuristic function. This class utilizes a
 * priority queue to manage the frontier, where nodes are ordered based on their
 * heuristic values towards the goal, which helps in exploring the most
 * promising paths first. Best-First Search is particularly useful in scenarios
 * where an approximate path to the goal is preferred quickly.
 */
public class PartB_BestF {
    /**
     * Executes the Best-First Search from a start node to a goal node within a
     * specified planet size.
     *
     * @param start      The starting node of the path.
     * @param goal       The target node to reach.
     * @param planetSize The size of the planet which may limit the search area.
     * @return A list of nodes representing the path from start to goal if one
     *         exists; otherwise, returns null if no path can be found.
     */
    public static List<Node> BestF(Node start, Node goal, int planetSize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(Node::getHeuristic)
                        .thenComparingInt(Node::getAngle)
                        .thenComparingInt(Node::getD));
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();

        parentMap.put(start, null);
        frontier.add(start);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);
            Node current = frontier.poll();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            if (current.equals(goal)) {
                List<Node> path = Utility.constructPath(current, parentMap);
                Utility.printPath(path, visited.size());
                return path;
            }
            List<Node> successors = current.getSuccessors(planetSize, goal);

            for (Node next : successors) {
                if (!visited.contains(next)) {
                    frontier.add(next);
                    parentMap.put(next, current);
                }
            }
        }
        Utility.algorithmFails(visited.size());
        return null;
    }

    /**
     * Prints the current state of the frontier, showing the nodes in the order they
     * will be explored, sorted by their heuristic values, and then by angle and
     * distance when heuristics are equal.
     *
     * @param frontier The priority queue representing the nodes currently in the
     *                 frontier of the search.
     */
    private static void printFrontier(PriorityQueue<Node> frontier) {
        Node[] frontierArray = frontier.toArray(new Node[0]);
        Arrays.sort(frontierArray,
                Comparator.comparingDouble(Node::getHeuristic)
                        .thenComparingInt(Node::getAngle)
                        .thenComparingInt(Node::getD));
        if (frontierArray.length != 0) {
            String result = Arrays.stream(frontierArray)
                    .map(node -> node.toString() + String.format("%.3f", node.getHeuristic()))
                    .collect(Collectors.joining(","));
            System.out.println("[" + result + "]");
        }
    }
}
