package Algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import General.Node;
import General.Utility;

public class PartC_IDA {

    public static List<Node> ida_star(Node root, Node goal, int planetSize) {
        double bound = h(root, goal);
        List<Node> path = new ArrayList<>();
        path.add(root);

        while (true) {
            double t = search(path, 0, bound, goal, planetSize);
            if (t == Double.POSITIVE_INFINITY) {
                System.out.println("fail");
                return null; // No solution found
            } else if (t == -1) {
                return path; // Solution found
            }
            bound = t; // Set new cost boundary
        }
    }

    // Recursive search function
    private static double search(List<Node> path, double g, double bound, Node goal, int planetSize) {
        Node node = path.get(path.size() - 1);
        double f = g + h(node, goal);

        if (f > bound)
            return f;
        if (is_goal(node, goal)) {
            Utility.printPath(path, path.size()); // Additional path print for clarity
            return -1; // FOUND, use -1 to indicate success
        }
        double min = Double.POSITIVE_INFINITY;
        List<Node> successors = node.getSuccessors(planetSize, goal);
        Utility.printFrontier(new HashSet<>(successors));

        for (Node succ : successors) {
            if (!path.contains(succ)) {
                path.add(succ);
                double t = search(path, g + cost(node, succ), bound, goal, planetSize);
                if (t == -1)
                    return -1; // Pass success up the stack
                if (t < min)
                    min = t;
                path.remove(path.size() - 1); // Backtrack
            }
        }
        return min;
    }

    // Heuristic function for estimating the least cost from node to goal
    private static double h(Node node, Node goal) {
        return node.calculateHeuristic(goal);
    }

    // Step cost function between nodes
    private static double cost(Node node, Node succ) {
        return node.distance(succ);
    }

    // Goal test function
    private static boolean is_goal(Node node, Node goal) {
        return node.equals(goal);
    }
}
