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
                visited.add(current);
                continue;
            }


            if (current.equals(goal)) {
                return constructPath(current);
            }

            List<Node> successors = current.getSuccessors(planetSize);

            Collections.sort(successors);

            for (Node next : successors) {
                if (!visited.contains(next)) {
                    frontier.push(next);
                }
            }
        }
        System.out.println("fail");
        System.out.println(visited.size()); // Print the number of unique nodes processed
        return null;
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