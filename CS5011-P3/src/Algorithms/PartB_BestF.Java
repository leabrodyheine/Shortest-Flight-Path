package Algorithms;

import General.Node;
import java.util.*;
import java.util.stream.Collectors;

public class PartB_BestF {

    public static List<Node> BestF(Node start, Node goal, int planetSize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingDouble(Node::getHeuristic));
        Set<Node> explored = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();

        parentMap.put(start, null);
        frontier.add(start);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);
            Node current = frontier.poll();

            if (explored.contains(current)) {
                continue;
            }

            explored.add(current);

            if (current.equals(goal)) {
                List<Node> path = constructPath(current, parentMap);
                printPath(path, explored.size());
                return path;
            }
            List<Node> successors = current.getSuccessors(planetSize, goal);

            for (Node next : successors) {
                if (!explored.contains(next) && !frontier.contains(next)) {
                    frontier.add(next);
                    parentMap.put(next, current);
                }
            }
        }
        System.out.println("fail");
        System.out.println(explored.size());
        return null;
    }
 
    private static void printFrontier(PriorityQueue<Node> frontier) {
        Node[] frontier_array = (Node[])frontier.toArray();
        if (frontier_array.length != 0) {
            String result = "";
            for (int i = frontier_array.length - 1; i >= 0; i--){
                Node node = frontier_array[i];
                result += node.toString() + node.getfCost() + ",";

            }
            System.out.println("[" + result.substring(0, result.length() - 1) + "]");
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
