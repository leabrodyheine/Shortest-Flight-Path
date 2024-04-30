import java.util.*;
import java.util.stream.Collectors;

public class PartA_DFS {

    public static List<Node> dfs(Node start, Node goal, int planetSize) {
        Stack<Node> frontier = new Stack<>();
        Set<Node> visited = new HashSet<>();
        frontier.push(start);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);

            Node current = frontier.pop();
            if (!visited.add(current)) { // Check if node is already visited
                continue;
            }

            if (current.equals(goal)) {
                return constructPath(current);
            }

            for (Node successor : current.getSuccessors(planetSize)) {
                if (!visited.contains(successor)) {
                    frontier.push(successor);
                }
            }
        }
        return null; // Return null if no path is found
    }

    private static void printFrontier(Collection<Node> frontier) {
        if (!frontier.isEmpty()) {
            String result = frontier.stream()
                    .map(Node::toString)
                    .collect(Collectors.joining(","));
            System.out.println("[" + result + "]");
        }
    }

    private static List<Node> constructPath(Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.addFirst(current);
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
            System.out.println();
            System.out.printf("%.3f\n%d\n", lastNode.cost, path.size());
        }
    }
}