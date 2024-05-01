package Algorithms;

import General.Node;
import java.util.*;
import java.util.stream.Collectors;

public class PartB_AStar {
    public static List<Node> AStar(Node start, Node goal, int planetSize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingDouble(Node::getfCost));
        Map<Node, Node> parentMap = new HashMap<>();
        Map<Node, Double> costSoFar = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        frontier.add(start);
        parentMap.put(start, null);
        costSoFar.put(start, 0.0);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);
            Node current = frontier.poll();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            if (current.equals(goal)) {
                List<Node> path = constructPath(current, parentMap);
                printPath(path, visited.size());
                return path;
            }

            for (Node next : current.getSuccessors(planetSize, goal)) {
                double newCost = costSoFar.get(current) + next.getCost();
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    double priority = newCost + next.calculateHeuristic(goal);
                    next.setfCost(priority);
                    frontier.add(next);
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
            current = parentMap.get(current);
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
