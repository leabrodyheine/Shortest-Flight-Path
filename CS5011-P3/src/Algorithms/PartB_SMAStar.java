package Algorithms;

import General.Node;
import java.util.*;
import java.util.stream.Collectors;

public class PartB_SMAStar {
    private static Set<Node> forgotten = new HashSet<>(); // Handling forgotten nodes

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
                shrinkFrontier(frontier, parentMap, goal, memorySize);
            }

            printFrontier(frontier);
            Node current = frontier.poll();

            if (visited.contains(current)) {
                continue;
            } else if (current.getfCost() >= 10000.0) {
                continue;
            }

            visited.add(current);

            if (current.equals(goal)) {
                List<Node> path = constructPath(current, parentMap);
                printPath(path, visited.size());
                return path;
            }

            expand(current, frontier, parentMap, goal, planetSize, visited);
        }
        System.out.println("fail");
        System.out.println(visited.size());
        return null;
    }

    private static void expand(Node current, PriorityQueue<Node> frontier, Map<Node, Node> parentMap, Node goal,
            int planetSize, Set<Node> visited) {
        List<Node> successors = current.getSuccessors(planetSize, goal);
        for (Node successor : successors) {
            double newCost = current.getCost() + current.distance(successor); // Correct calculation of the cumulative
                                                                              // cost
            double newFcost = newCost + successor.calculateHeuristic(goal);

            boolean shouldUpdate = false;
            if (!frontier.contains(successor) && !visited.contains(successor)) {
                shouldUpdate = true;
            } else if (frontier.contains(successor) && newFcost < successor.getfCost()) {
                frontier.remove(successor); // Must remove to update correctly
                shouldUpdate = true;
            }

            if (shouldUpdate) {
                successor.setCost(newCost);
                successor.setfCost(newFcost);
                frontier.add(successor);
                parentMap.put(successor, current);
            }
        }
    }

    private static void shrinkFrontier(PriorityQueue<Node> frontier, Map<Node, Node> parentMap, Node goal,
            int memorySize) {
        while (frontier.size() > memorySize) {
            Node worstNode = getWorstLeafNode(frontier, parentMap);
            if (worstNode != null) {
                frontier.remove(worstNode);
                forgotten.add(worstNode);
                Node parent = parentMap.get(worstNode);
                if (parent != null) {
                    updateParentCost(parent, frontier, goal, parentMap);
                    // Check if parent becomes a leaf and update accordingly
                    if (isLeaf(parent, frontier, parentMap)) {
                        parent.setfCost(Double.POSITIVE_INFINITY); // Mark parent as a leaf if it has no more children
                    }
                }
                System.out.println("Removed node due to memory limit: " + worstNode);
            }
        }
    }

    private static Node getWorstLeafNode(PriorityQueue<Node> frontier, Map<Node, Node> parentMap) {
        return frontier.stream()
                .filter(node -> isLeaf(node, frontier, parentMap))
                .max(Comparator.comparingDouble(Node::getfCost))
                .orElse(null);
    }

    private static boolean isLeaf(Node node, PriorityQueue<Node> frontier, Map<Node, Node> parentMap) {
        return frontier.stream().noneMatch(n -> parentMap.get(n) == node);
    }

    private static void updateParentCost(Node parent, PriorityQueue<Node> frontier, Node goal,
            Map<Node, Node> parentMap) {
        OptionalDouble minCost = frontier.stream()
                .filter(n -> parent.equals(parentMap.get(n)))
                .mapToDouble(Node::getfCost)
                .min();
        parent.setfCost(minCost.isPresent() ? minCost.getAsDouble() + parent.calculateHeuristic(goal)
                : Double.POSITIVE_INFINITY);
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
        Node current = goal;
        while (current != null) {
            path.add(current);
            current = parentMap.get(current); // Follow the chain of parents
        }
        Collections.reverse(path); // Reverse to start from the beginning
        return path;
    }

    public static void printPath(List<Node> path, int visitedCount) {
        if (path == null || path.isEmpty()) {
            System.out.println("fail");
        } else {
            Node lastNode = path.get(path.size() - 1);
            double cost = lastNode.getCost();
            for (Node node : path) {
                System.out.print(node + "");
                cost += node.getCost();
            }
            System.out.println();
            System.out.printf("%.3f\n%d\n", cost, visitedCount);
        }
    }
}
