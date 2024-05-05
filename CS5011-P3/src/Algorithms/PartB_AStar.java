package Algorithms;

import General.Node;
import java.util.*;
import java.util.stream.Collectors;
import General.Utility;

public class PartB_AStar {
    public static List<Node> AStar(Node start, Node goal, int planetSize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(Node::getfCost)
                        .thenComparingInt(Node::getD)
                        .thenComparingInt(Node::getAngle));
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
                List<Node> path = Utility.constructPath(current, parentMap);
                Utility.printPath(path, visited.size());
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize, goal);

            for (Node next : successors) {
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
}
