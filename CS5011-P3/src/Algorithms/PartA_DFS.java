package Algorithms;

import java.util.*;
import General.Node;

public class PartA_DFS {

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
        System.out.println("fail");
        System.out.println(visited.size());
        return null;
    }

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

    private static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
        List<Node> path = new ArrayList<>();
        for (Node current = goal; current != null; current = parentMap.get(current)) {
            path.add(current);
        }
        Collections.reverse(path);
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
            System.out.printf("%.3f\n%d\n", cost, visitedCount);
        }
    }
}