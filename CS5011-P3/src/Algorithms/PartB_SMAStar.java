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
        Map<Node, Integer> depthMap = new HashMap<>();

        frontier.add(start);
        parentMap.put(start, null);
        costSoFar.put(start, 0.0);
        depthMap.put(start, 1); // Assuming depth starts from 1

        while (!frontier.isEmpty()) {
            if (frontier.size() > memorySize) {
                removeWorstNode(frontier, parentMap, costSoFar, depthMap, goal);
            }
            Node current = frontier.poll();
            printFrontier(frontier);

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
                int nextDepth = depthMap.get(current) + 1;
                double newCost = costSoFar.get(current) + next.getCost();
                double fCost = newCost + next.calculateHeuristic(goal);

                if (nextDepth > memorySize) {
                    fCost = Double.POSITIVE_INFINITY; // Setting infinite cost if depth exceeds memory size
                }

                if (!visited.contains(next) || newCost < costSoFar.getOrDefault(next, Double.POSITIVE_INFINITY)) {
                    costSoFar.put(next, newCost);
                    depthMap.put(next, nextDepth);
                    next.setfCost(fCost);
                    frontier.add(next);
                    parentMap.put(next, current);
                }
            }
        }

        System.out.println("fail");
        System.out.println(visited.size());
        return null;
    }

    private static void removeWorstNode(PriorityQueue<Node> frontier, Map<Node, Node> parentMap,
            Map<Node, Double> costSoFar, Map<Node, Integer> depthMap, Node goal) {
        if (frontier.isEmpty())
            return;

        Node worstNode = null;
        double worstCost = Double.POSITIVE_INFINITY;

        // Finding the worst leaf node
        for (Node node : frontier) {
            if (node.getfCost() < worstCost && isLeaf(node, frontier, parentMap)) {
                worstCost = node.getfCost();
                worstNode = node;
            }
        }

        if (worstNode != null) {
            frontier.remove(worstNode);
            costSoFar.remove(worstNode);
            depthMap.remove(worstNode);
            Node parent = parentMap.remove(worstNode);

            // Update parent node's f-cost if needed
            if (parent != null && isLeaf(parent, frontier, parentMap)) {
                double parentNewCost = calculateParentNewCost(parent, parentMap, costSoFar, goal);
                parent.setfCost(parentNewCost);
                costSoFar.put(parent, parentNewCost);
            }
            System.out.println("Removed node due to memory limit: " + worstNode);
        }
    }

    private static double calculateParentNewCost(Node parent, Map<Node, Node> parentMap, Map<Node, Double> costSoFar, Node goal) {
        double minCost = Double.POSITIVE_INFINITY;
        for (Map.Entry<Node, Node> entry : parentMap.entrySet()) {
            if (entry.getValue() == parent) {
                Double cost = costSoFar.get(entry.getKey());
                if (cost != null && cost < minCost) {
                    minCost = cost;
                }
            }
        }
        return minCost == Double.POSITIVE_INFINITY ? minCost : minCost + parent.calculateHeuristic(goal);
    }

    private static boolean isLeaf(Node node, PriorityQueue<Node> frontier, Map<Node, Node> parentMap) {
        return parentMap.values().stream().noneMatch(n -> n == node);
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
