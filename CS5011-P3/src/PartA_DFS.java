import java.util.*;

public class PartA_DFS {
    
    public static List<Node> dfs(Node start, Node goal, int planetSize) {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (!visited.add(current)) {  // Check if node is already visited
                continue;
            }

            if (current.equals(goal)) {
                return constructPath(current);
            }

            for (Node successor : current.getSuccessors(planetSize)) {
                if (!visited.contains(successor)) {
                    stack.push(successor);
                }
            }
        }
        return null;  // Return null if no path is found
    }

    private static List<Node> constructPath(Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.addFirst(current);
            current = current.parent;
        }
        return path;
    }
}

// Main class to run DFS might be similar to your BFS setup, adapting command line inputs as necessary.
