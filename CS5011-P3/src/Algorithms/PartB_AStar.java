package Algorithms;

import General.Node;
import java.util.*;
import java.util.stream.Collectors;

public class PartB_AStar {
    // public static List<Node> AStar(Node start, Node goal, int planetSize) {
    //     PriorityQueue<Node> frontier = new PriorityQueue<>(
    //             Comparator.comparingDouble(Node::getfCost)
    //                     .thenComparingInt(Node::getD)
    //                     .thenComparingInt(Node::getAngle));
    //     Map<Node, Node> parentMap = new HashMap<>();
    //     Map<Node, Double> costSoFar = new HashMap<>();
    //     Set<Node> visited = new HashSet<>();

    //     frontier.add(start);
    //     parentMap.put(start, null);
    //     costSoFar.put(start, 0.0);

    //     while (!frontier.isEmpty()) {
    //         printFrontier(frontier);
    //         Node current = frontier.poll(); // Automatically removes the best node

    //         if (visited.contains(current)) {
    //             continue;
    //         }

    //         visited.add(current);

    //         if (current.equals(goal)) {
    //             List<Node> path = constructPath(current, parentMap);
    //             printPath(path, visited.size());
    //             return path;
    //         } else {
    //             frontier.addAll(expand(current, frontier, visited, goal, planetSize));
    //         }
    //     }
    //     System.out.println("fail");
    //     System.out.println(visited.size());
    //     return null;
    // }

    // private static List<Node> expand(Node current, PriorityQueue<Node> frontier, Set<Node> visited, Node goal,
    //         int planetSize) {
    //     List<Node> nextStates = current.getSuccessors(planetSize, goal);
    //     List<Node> successors = new ArrayList<Node>();
    //     double cost = 0;
    //     for (Node state : nextStates) {
    //         if (!visited.contains(state) || !frontier.contains(state)) {
    //             successors.add(state);
    //             cost = current.getCost() + current.distance(state);
    //             state.setCost(cost);
    //         } else if (frontier.contains(state)) {
    //             cost = current.getCost() + current.distance(state);
    //             if (cost < state.getCost()) {
    //                 state.setCost(cost);
    //             }
    //         }
    //     }
    //     return successors;
    // }

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
            Node current = frontier.poll(); // Automatically removes the best node

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
