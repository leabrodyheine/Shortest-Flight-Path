package Algorithms;

import java.util.*;
import java.util.stream.Collectors;

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

            if (!visited.add(current)) {
                continue;
            }

            visited.add(current);

            if (current.equals(goal)) {
                List<Node> path = constructPath(current, parentMap);
                printPath(path, visited.size());
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize);
            Collections.sort(successors, Collections.reverseOrder()); // Ensure correct exploration order

            for (Node next : successors) {
                if (!visited.contains(next) && !frontier.contains(next)) {
                    frontier.push(next);
                    parentMap.put(next, current);
                }
            }
        }
        System.out.println("fail");
        System.out.println(visited.size()); // Print the number of unique nodes processed
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

    private static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current); // Retrieve the parent from the map
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
            System.out.printf("%.3f\n%d\n", cost, visitedCount);
        }
    }
}