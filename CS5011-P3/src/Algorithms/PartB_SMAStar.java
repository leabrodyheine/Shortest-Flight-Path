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
        Map<Node, Double> costSoFar = new HashMap<>();
        int visitedCount = 0;

        frontier.add(start);
        parentMap.put(start, null);
        costSoFar.put(start, 0.0);

        while (!frontier.isEmpty()) {
            visitedCount++;
            printFrontier(frontier);
            Node current = frontier.poll();

            if (current.getfCost() >= 10000.0 || current.getDepth() >= memorySize) {
                break;
            }

            if (current.equals(goal)) {
                List<Node> path = constructPath(current, parentMap);
                printPath(path, visitedCount);
                return path;
            }

            updateFrontier(frontier, current, goal, planetSize, memorySize, parentMap);
        }
        System.out.println("fail");
        System.out.println(visitedCount);
        return null;
    }

    private static void updateFrontier(PriorityQueue<Node> frontier, Node current, Node goal, int planetSize,
            int memorySize,
            Map<Node, Node> parentMap) {

        List<Node> successors;
        if (current.getForgotten().size() == 0) {
            successors = current.getSuccessors(planetSize, goal);
        } else {
            successors = current.getForgotten();
        }
        for (Node successor : successors) {

            if (current.getForgotten().contains(successor)) {
                current.getForgotten().remove(successor);
            } else {
                if (!successor.equals(goal) && successor.getDepth() >= memorySize) {
                    successor.setfCost(10000);
                }
            }
            successor.setLeaf(true);
            successor.getParent().setLeaf(false);
        }
        frontier.addAll(successors);

        if (frontier.size() > memorySize) {
            shrinkFrontier(frontier, parentMap, goal, memorySize);
        }
    }

    private static void shrinkFrontier(PriorityQueue<Node> frontier, Map<Node, Node> parentMap, Node goal,
            int memorySize) {
        while (frontier.size() > memorySize) {
            Node worstNode = getWorstLeafNode(frontier);
            if (worstNode != null) {
                frontier.remove(worstNode);
                worstNode.setLeaf(false);
                worstNode.getForgotten().add(worstNode.getParent());
                Node parent = worstNode.getParent();
                if (parent != null) {
                    parent.getForgotten().add(worstNode);
                    double minFCost = parent.getForgotten().stream().mapToDouble(Node::getfCost).min().orElse(10000);
                    parent.setfCost(minFCost);
                    if (!existsInFrontierWhereWorstParentInAncestors(worstNode, frontier)) {
                        parent.setLeaf(true);
                        frontier.add(parent);
                    }

                }
            }
        }
    }

    private static boolean existsInFrontierWhereWorstParentInAncestors(Node worstNode, PriorityQueue<Node> frontier) {
        for (Node node : frontier) {
            if (ancestors(node).contains(worstNode.getParent())) {
                return true;
            }
        }
        return false;
    }

    private static List<Node> ancestors(Node node) {
        List<Node> ancestors = new ArrayList<Node>();
        while (node.getParent() != null) {
            ancestors.add(node.getParent());
            node = node.getParent();
        }
        return ancestors;
    }

    private static Node getWorstLeafNode(PriorityQueue<Node> frontier) {
        return frontier.stream()
                .filter(node -> node.getLeaf())
                .max(Comparator.comparingDouble(Node::getfCost))
                .orElse(null);
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
            current = current.getParent(); // Follow the chain of parents
        }
        Collections.reverse(path); // Reverse to start from the beginning
        return path;
    }

    public static void printPath(List<Node> path, int visitedCount) {
        if (path == null || path.isEmpty()) {
            System.out.println("failed to find path");
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
