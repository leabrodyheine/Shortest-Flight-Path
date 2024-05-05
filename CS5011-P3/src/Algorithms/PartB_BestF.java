package Algorithms;

import General.Node;
import General.Utility;
import java.util.*;
import java.util.stream.Collectors;

public class PartB_BestF {

    public static List<Node> BestF(Node start, Node goal, int planetSize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(Node::getHeuristic)
                        .thenComparingInt(Node::getAngle)
                        .thenComparingInt(Node::getD));
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();

        parentMap.put(start, null);
        frontier.add(start);

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
                if (!visited.contains(next)) {
                    frontier.add(next);
                    parentMap.put(next, current);
                }
            }
        }
        Utility.algorithmFails(visited.size());
        return null;
    }

    private static void printFrontier(PriorityQueue<Node> frontier) {
        Node[] frontierArray = frontier.toArray(new Node[0]);
        Arrays.sort(frontierArray,
                Comparator.comparingDouble(Node::getHeuristic)
                        .thenComparingInt(Node::getAngle)
                        .thenComparingInt(Node::getD));
        if (frontierArray.length != 0) {
            String result = Arrays.stream(frontierArray)
                    .map(node -> node.toString() + String.format("%.3f", node.getHeuristic()))
                    .collect(Collectors.joining(","));
            System.out.println("[" + result + "]");
        }
    }
}
