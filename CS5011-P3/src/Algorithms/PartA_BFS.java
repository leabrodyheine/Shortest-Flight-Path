package Algorithms;

import java.util.*;

import General.Node;

public class PartA_BFS {

    public static List<Node> bfs(Node start, Node goal, int planetSize) {
        Queue<Node> frontier = new ArrayDeque<>();
        Map<Node, Node> parentMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        frontier.add(start);
        parentMap.put(start, null);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);
            Node current = frontier.poll();

            visited.add(current);
            if (current.equals(goal)) {
                List<Node> path = constructPath(current, parentMap);
                printPath(path, visited.size());
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize, goal);

            Collections.sort(successors);

            for (Node next : successors) {
                if (!visited.contains(next) && !frontier.contains(next)) {
                    frontier.add(next);
                    parentMap.put(next, current);
                }
            }
        }

        System.out.println("fail");
        System.out.println(visited.size());
        return null;
    }

    private static void printFrontier(Collection<Node> frontier) {
        if (!frontier.isEmpty()) {
            StringBuilder result = new StringBuilder();
            for (Node node : frontier) {
                if (result.length() > 0)
                    result.append(",");
                result.append(node.toString());
            }
            System.out.println("[" + result + "]");
        }
    }

    private static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
        List<Node> path = new ArrayList<>();
        for (Node current = goal; current != null; current = parentMap.get(current)) {
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }
    
    public static void printPath(List<Node> path, int visitedCount) {
        if (path == null || path.isEmpty()) {
            System.out.println("fail");
        } else {
            path.forEach(node -> System.out.print(node));
            Node lastNode = path.get(path.size() - 1);
            System.out.printf("\n%.3f\n%d\n", lastNode.getCost(), visitedCount);
        }
    }
}