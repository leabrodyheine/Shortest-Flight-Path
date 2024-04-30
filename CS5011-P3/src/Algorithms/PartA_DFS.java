package Algorithms;

import java.util.*;
import java.util.stream.Collectors;

import General.Node;

public class PartA_DFS {

    public static List<Node> dfs(Node start, Node goal, int planetSize) {
        Stack<Node> frontier = new Stack<>();
        Set<Node> visited = new HashSet<>();
        frontier.push(start);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);
            Node current = frontier.pop();
            if (!visited.add(current)) {
                continue;
            }

            if (current.equals(goal)) {
                return constructPath(current);
            }

            List<Node> successors = current.getSuccessors(planetSize);
            for (Node next : successors) {
                if (!visited.contains(next)) {
                    frontier.push(next);
                }
            }
        }
        System.out.println("fail");
        return null;
    }

    private static void printFrontier(Collection<Node> frontier) {
        if (!frontier.isEmpty()) {
            String result = frontier.stream()
                    .map(Node::toString)
                    .collect(Collectors.joining(","));
            System.out.println("[" + result + "]");
        }
    }

    private static List<Node> constructPath(Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.addFirst(current);
            Node parent = current.getParent(); // Corrected to call the instance method on an object
            current = parent; // Update current to its parent
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
            System.out.printf("%.3f\n%d\n", cost, path.size());
        }
    }
}