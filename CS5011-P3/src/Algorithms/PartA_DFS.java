package Algorithms;

import java.util.*;
import General.Node;
import General.Utility;
/**
 * This class implements the Depth-First Search (DFS) algorithm for graph
 * traversal.
 */
public class PartA_DFS {

    /**
     * Executes the Depth-First Search (DFS) from a start node to a goal node within
     * a given planet size.
     *
     * @param start      The starting node of the search.
     * @param goal       The target node to find.
     * @param planetSize The size of the planet, which influences the bounds of the
     *                   search area.
     * @return A list of nodes representing the path from the start to the goal if
     *         found, otherwise null.
     */
    public static List<Node> dfs(Node start, Node goal, int planetSize) {
        Stack<Node> frontier = new Stack<>();
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        frontier.push(start);
        parentMap.put(start, null);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);
            Node current = frontier.pop();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);
            if (current.equals(goal)) {
                List<Node> path = constructPath(current, parentMap);
                printPath(path, visited.size());
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize, goal);
            Collections.sort(successors, Comparator.comparingInt(Node::getD).thenComparingInt(Node::getAngle));

            for (int i = successors.size() - 1; i >= 0; i--) {
                Node next = successors.get(i);
                if (!visited.contains(next) && !frontier.contains(next)) {
                    frontier.push(next);
                    parentMap.put(next, current);
                }
            }
        }
        Utility.algorithmFails(visited);
        return null;
    }

    /**
     * Prints the current state of the frontier (nodes to be visited).
     *
     * @param frontier The stack containing the nodes currently in the frontier.
     */
    private static void printFrontier(Stack<Node> frontier) {
        if (!frontier.isEmpty()) {
            String result = "";
            for (int i = frontier.size() - 1; i >= 0; i--) {
                Node node = frontier.get(i);
                result += node.toString() + ",";

            }
            System.out.println("[" + result.substring(0, result.length() - 1) + "]");
        }
    }

    /**
     * Constructs the path from the goal node back to the start node using the
     * parent map.
     *
     * @param goal      The end node of the path.
     * @param parentMap A map storing each node and its parent node as discovered
     *                  during the search.
     * @return A list of nodes representing the path from the goal back to the
     *         start.
     */
    private static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        return path;
    }

    /**
     * Prints the path found by the DFS and the number of nodes visited during the
     * search.
     *
     * @param path         The list of nodes forming the path from start to goal.
     * @param visitedCount The count of nodes visited during the search.
     */
    public static void printPath(List<Node> path, int visitedCount) {
        if (path == null || path.isEmpty()) {
            System.out.println("fail");
        } else {
            path.forEach(node -> System.out.print(node));
            Node lastNode = path.get(path.size() - 1);
            System.out.printf("\n%.3f\n%d\n", lastNode.getCost(), visitedCount);
        }
    }
}