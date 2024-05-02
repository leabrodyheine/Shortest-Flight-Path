package Algorithms;

import General.Node;
import java.util.*;
import java.util.stream.Collectors;

public class PartB_SMAStar {
    public static List<Node> smaStar(Node start, Node goal, int planetSize, int memorySize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(Node::getfCost)
                        .thenComparingInt(Node::getD)
                        .thenComparingInt(Node::getAngle));
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Double> costSoFar = new HashMap<>();

        frontier.add(start);
        parentMap.put(start, null);
        costSoFar.put(start, 0.0);

        while (!frontier.isEmpty()) {
            if (frontier.size() > memorySize) {
                removeWorstNode(frontier, parentMap, costSoFar, goal);
            }
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
                double newCost = costSoFar.getOrDefault(current, Double.POSITIVE_INFINITY) + next.getCost();
                if (!visited.contains(next) && (newCost < costSoFar.getOrDefault(next, Double.POSITIVE_INFINITY))) {
                    costSoFar.put(next, newCost);
                    next.setfCost(newCost + next.calculateHeuristic(goal));
                    parentMap.put(next, current);
                    frontier.add(next);
                }
            }
        }

        System.out.println("fail");
        System.out.println(visited.size());
        return null;
    }

    private static void removeWorstNode(PriorityQueue<Node> frontier, Map<Node, Node> parentMap,
            Map<Node, Double> costSoFar, Node goal) {
        if (frontier.isEmpty())
            return;
        Node worstNode = null;
        double worstCost = Double.NEGATIVE_INFINITY;

        for (Node node : frontier) {
            if (node.getfCost() > worstCost && isLeaf(node, frontier, parentMap)) {
                worstCost = node.getfCost();
                worstNode = node;
            }
        }
        if (worstNode != null) {
            frontier.remove(worstNode);
            costSoFar.remove(worstNode);
            Node parent = parentMap.get(worstNode);
            if (parent != null) {
                updateParentCost(parent, parentMap, costSoFar, frontier, goal);
            }
            System.out.println("Removed node due to memory limit: " + worstNode);
        }
    }

    private static boolean isLeaf(Node node, PriorityQueue<Node> frontier, Map<Node, Node> parentMap) {
        // A node is considered a leaf if it does not have any children in the frontier
        for (Node n : frontier) {
            if (parentMap.get(n) == node) {
                return false;
            }
        }
        return true;
    }

    private static void updateParentCost(Node parent, Map<Node, Node> parentMap, Map<Node, Double> costSoFar,
            PriorityQueue<Node> frontier, Node goal) {
        double minCost = Double.POSITIVE_INFINITY;
        boolean hasChildren = false;
        for (Node n : frontier) {
            if (parentMap.get(n) == parent) {
                hasChildren = true;
                double childCost = costSoFar.getOrDefault(n, Double.POSITIVE_INFINITY) + n.calculateHeuristic(goal);
                if (childCost < minCost) {
                    minCost = childCost;
                }
            }
        }
        if (!hasChildren) {
            minCost = Double.POSITIVE_INFINITY;
        }
        parent.setfCost(minCost);
        costSoFar.put(parent, minCost);
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
