package Algorithms;

import General.Node;
import java.util.*;
import java.util.stream.Collectors;

public class PartB_SMAStar {
    public static List<Node> smaStar(Node start, Node goal, int planetSize, int memorySize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(Node::getfCost)
                        .thenComparingInt(Node::getAngle)
                        .thenComparingInt(Node::getD));
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Double> costSoFar = new HashMap<>();

        frontier.add(start);
        parentMap.put(start, null);
        costSoFar.put(start, 0.0);

        while (!frontier.isEmpty() && frontier.size() <= memorySize) {
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

            List<Node> successors = current.getSuccessors(planetSize, goal);

            for (Node next : successors) {
                double newCost = costSoFar.get(current) + next.getCost();

                if (!visited.contains(next)) {
                    if (!frontier.contains(next) || newCost < costSoFar.get(next)) {
                        costSoFar.put(next, newCost);
                        parentMap.put(next, current);
                        if (frontier.contains(next)) {
                            frontier.remove(next); // Remove to update priority
                        }
                        if (frontier.size() >= memorySize) {
                            frontier.remove();
                        }
                        frontier.add(next);
                    }
                }
            }
        }
        if (frontier.size() >= memorySize) {
            Node toRemove = frontier.poll(); // or some logic to decide which node to remove
            System.out.println("Removing node due to memory limit: " + toRemove); // Debug
        }

        System.out.println("fail");
        System.out.println(visited.size());
        return null;
    }

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

    private static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
        List<Node> path = new ArrayList<>();
        while (goal != null) {
            path.add(goal);
            goal = parentMap.get(goal);
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
