package General;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A utility class providing common functionalities such as printing the
 * frontier,
 * constructing paths, and handling failure cases for graph traversal
 * algorithms.
 */
public class Utility {

    /**
     * Logs a failure message and the number of visited nodes when an algorithm
     * cannot find a path.
     *
     * @param visited The number of nodes that were visited during the execution of
     *                the algorithm.
     */
    public static void algorithmFails(int visited) {
        System.out.println("fail");
        System.out.println(visited);
    }

    /**
     * Prints the current state of the frontier.
     *
     * @param frontier The collection of nodes currently in the frontier.
     */
    public static void printFrontier(Collection<Node> frontier) {
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

    /**
     * Constructs the path from the goal node back to the start node using the
     * parent map.
     *
     * @param goal      The goal node where the path ends.
     * @param parentMap A map of child nodes to their parent nodes as discovered by
     *                  the BFS.
     * @return A list of nodes representing the path from the start to the goal.
     */
    public static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
        List<Node> path = new ArrayList<>();
        for (Node current = goal; current != null; current = parentMap.get(current)) {
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Prints the path found by the BFS.
     *
     * @param path         The list of nodes constituting the path.
     * @param visitedCount The number of nodes visited during the search.
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
