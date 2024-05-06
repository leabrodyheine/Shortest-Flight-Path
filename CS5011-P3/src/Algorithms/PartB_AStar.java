package Algorithms;

import General.Node;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import General.Utility;

/**
 * Implements the A* (A-Star) search algorithm to find the most efficient path
 * from a start node to a goal node. A* is a best-first search algorithm that
 * uses costs along with heuristics to estimate the most promising path to the
 * goal. This class uses a priority queue to keep the nodes prioritized by the
 * estimated cost to reach the goal, combining the actual cost from the start
 * and a heuristic estimated cost to the goal.
 */
public class PartB_AStar {
    /**
     * Executes the A* search algorithm to find the shortest path from a start node
     * to a goal node within a specified planet size, considering both path cost and
     * heuristic estimates.
     *
     * @param start      The starting node of the path.
     * @param goal       The target node to reach.
     * @param planetSize The size of the planet which may limit the search area.
     * @return A list of nodes representing the shortest path from start to goal if
     *         one exists, otherwise returns null if no path can be found.
     */
    public static List<Node> AStar(Node start, Node goal, int planetSize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(Node::getfCost)
                        .thenComparingInt(Node::getD)
                        .thenComparingInt(Node::getAngle));
        Map<Node, Node> parentMap = new HashMap<>();

        int visitedCount = 0;
        start.setCost(0.0); // Ensure start cost is zero
        start.setfCost(start.calculateHeuristic(goal));
        frontier.add(start);
        parentMap.put(start, null);

        while (!frontier.isEmpty()) {
            visitedCount++;
            printFrontier(frontier);
            Node current = frontier.poll();

            if (current.getVisited()) {
                continue;
            }
            current.setVisited(true);

            if (current.equals(goal)) {
                List<Node> path = Utility.constructPath(current, parentMap);
                Utility.printPath(path, visitedCount);
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize, goal);
            for (Node next : successors) {
                double newCost = current.getCost() + next.distance(current);
                if (!next.getVisited() || newCost < next.getCost()) {
                    next.setCost(newCost);
                    double priority = newCost + next.calculateHeuristic(goal);
                    next.setfCost(priority);

                    if (!next.getVisited() || frontier.contains(next)) {
                        frontier.remove(next); // Make sure to remove the old node instance
                        frontier.add(next); // Add the updated node back to the frontier
                    }
                    parentMap.put(next, current);
                }
            }
        }
        Utility.algorithmFails(visitedCount);
        return null;
    }

    /**
     * Prints the current state of the frontier in the A* algorithm, where the
     * frontier is prioritized by f-cost, and for nodes with the same f-cost, by
     * angle and then distance.
     *
     * @param frontier The priority queue representing the frontier of the A*
     *                 search.
     */
    private static void printFrontier(PriorityQueue<Node> frontier) {
        Node[] frontierArray = frontier.toArray(new Node[0]);
        Arrays.sort(frontierArray,
                Comparator.comparingDouble(Node::getfCost)
                        .thenComparingInt(Node::getAngle)
                        .thenComparingInt(Node::getD));
        if (frontierArray.length != 0) {
            String result = Arrays.stream(frontierArray)
                    .map(node -> node.toString() + String.format("%.3f", node.getfCost()))
                    .collect(Collectors.joining(","));
            System.out.println("[" + result + "]");
        }
    }
}
