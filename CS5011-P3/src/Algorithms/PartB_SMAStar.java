package Algorithms;

import General.Node;
import General.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Implements the Simplified Memory-Bounded A* (SMA*) search algorithm, which is
 * a variant of the A* search algorithm designed to handle limited memory. It
 * dynamically adjusts the search frontier to maintain the size within a
 * specified memory bound, using a heuristic to prioritize nodes and truncating
 * the least promising nodes when necessary.
 */
public class PartB_SMAStar {
    /**
     * Executes the SMA* search from a start node to a goal node, considering a
     * defined memory size limit.
     * 
     * @param start      The starting node of the search.
     * @param goal       The goal node to find.
     * @param planetSize The size of the planet, which may influence the maximum
     *                   search bounds.
     * @param memorySize The maximum size of the frontier, limiting the number of
     *                   nodes stored in memory at one time.
     * @return A list of nodes representing the path from the start to the goal if
     *         found; null if no path exists.
     */
    public static List<Node> smaStar(Node start, Node goal, int planetSize, int memorySize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(Node::getfCost)
                        .thenComparingInt(Node::getD)
                        .thenComparingInt(Node::getAngle));
        int visitedCount = 0;

        frontier.add(start);

        while (!frontier.isEmpty()) {
            visitedCount++;
            printFrontier(frontier);
            Node current = frontier.poll();
            current.setVisited(true);

            if (current.getfCost() >= 10000.0 || current.getDepth() >= memorySize) {
                break;
            }

            if (current.equals(goal)) {
                List<Node> path = constructPath(current);
                Utility.printPath(path, visitedCount);
                return path;
            }

            updateFrontier(frontier, current, goal, planetSize, memorySize);
        }
        Utility.algorithmFails(visitedCount);
        return null;
    }

    /**
     * Updates the frontier by adding successors of the current node, handling the
     * memory size constraint by potentially truncating less promising nodes.
     *
     * @param frontier   The priority queue used to store nodes during the search.
     * @param current    The current node being expanded.
     * @param goal       The goal node of the search.
     * @param planetSize The size of the planet influencing node expansions.
     * @param memorySize The maximum number of nodes allowed in the frontier.
     */
    private static void updateFrontier(PriorityQueue<Node> frontier, Node current, Node goal, int planetSize,
            int memorySize) {
        List<Node> successors = new ArrayList<>(
                current.getForgotten().size() == 0 ? current.getSuccessors(planetSize, goal) : current.getForgotten());

        // List<Node> sucessors = new ArrayList<>();
        for (Node successor : successors) {
            if (current.getForgotten().contains(successor)) {
                current.getForgotten().remove(successor);
            } else if (!successor.equals(goal) && successor.getDepth() >= memorySize) {
                successor.setfCost(10000);
            }
            successor.setLeaf(true);
            if (successor.getParent() != null) {
                successor.getParent().setLeaf(false);
            }
        }
        frontier.addAll(successors);

        if (frontier.size() > memorySize) {
            shrinkFrontier(frontier, goal, memorySize);
        }
    }

    /**
     * Reduces the size of the frontier when it exceeds the memory limit, removing
     * the least promising nodes.
     *
     * @param frontier   The priority queue of nodes.
     * @param goal       The goal node, used for recalculating heuristic values if
     *                   needed.
     * @param memorySize The maximum size of the frontier allowed.
     */
    private static void shrinkFrontier(PriorityQueue<Node> frontier, Node goal,
            int memorySize) {
        while (frontier.size() > memorySize) {
            Node worstNode = getWorstLeafNode(frontier);
            if (worstNode != null) {
                frontier.remove(worstNode);
                worstNode.setLeaf(false);
                worstNode.getForgotten().add(worstNode.getParent());
                Node parent = worstNode.getParent();
                if (parent != null) {
                    parent.getForgotten().add(worstNode);
                    double minFCost = parent.getForgotten().stream().mapToDouble(Node::getfCost).min().orElse(10000);
                    parent.setfCost(minFCost);
                    if (!existsInFrontierWhereWorstParentInAncestors(worstNode, frontier)) {
                        parent.setLeaf(true);
                        frontier.add(parent);
                    }

                }
            }
        }
    }

    /**
     * Checks if a node's parent is present in the frontier and if this parent is
     * the worst in terms of cost among its ancestors.
     *
     * @param worstNode The node considered the worst based on its f-cost.
     * @param frontier  The current frontier.
     * @return true if the worst parent is found among the ancestors of any node in
     *         the frontier, false otherwise.
     */
    private static boolean existsInFrontierWhereWorstParentInAncestors(Node worstNode, PriorityQueue<Node> frontier) {
        for (Node node : frontier) {
            if (ancestors(node).contains(worstNode.getParent())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Computes and returns the list of ancestor nodes for a given node up to the
     * root of the search tree.
     *
     * @param node The node whose ancestors are to be found.
     * @return A list of nodes representing the ancestors of the given node.
     */
    private static List<Node> ancestors(Node node) {
        List<Node> ancestors = new ArrayList<Node>();
        while (node.getParent() != null) {
            ancestors.add(node.getParent());
            node = node.getParent();
        }
        return ancestors;
    }

    /**
     * Identifies the node with the worst (highest) f-cost that is a leaf node in
     * the frontier.
     *
     * @param frontier The current frontier.
     * @return The node with the highest f-cost that does not have any children in
     *         the search tree.
     */
    private static Node getWorstLeafNode(PriorityQueue<Node> frontier) {
        return frontier.stream()
                .filter(node -> node.getLeaf())
                .max(Comparator.comparingDouble(Node::getfCost))
                .orElse(null);
    }

    /**
     * Prints the nodes currently in the frontier along with their f-costs, sorted
     * by f-cost, angle, and distance.
     *
     * @param frontier The priority queue containing the nodes.
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

    /**
     * Constructs the path from the goal node back to the start node using the
     * parent map.
     *
     * @param goal The goal node where the path ends.
     * @return A list of nodes representing the path from the goal to the start.
     */
    private static List<Node> constructPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;
        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}
