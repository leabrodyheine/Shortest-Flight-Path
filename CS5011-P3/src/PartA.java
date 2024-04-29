import java.util.*;
import java.util.stream.Collectors;

public class PartA {

    public static class BFS implements SearchStrategy {
        private NodeUtility util;
        private int[] directions;

        public BFS(NodeUtility utility, int[] directions) {
            this.util = utility;
            this.directions = directions;
        }

        @Override
        public List<Node> search(Node start, Node goal, int planetSize) {
            Queue<Node> frontier = new LinkedList<>();
            frontier.add(start);
            Set<Node> explored = new HashSet<>();

            // Initial frontier state for debugging
            printFrontier(frontier);

            while (!frontier.isEmpty()) {
                Node current = frontier.poll();

                // Check goal before expanding
                if (current.equals(goal)) {
                    System.out.println(); // Ensures new line before final path print
                    return util.constructPath(current);
                }

                explored.add(current);
                List<Node> successors = util.getSuccessors(current, planetSize, directions);

                // Collect names for debugging before adding to frontier
                List<String> debugNodes = new ArrayList<>();
                for (Node next : successors) {
                    if (!explored.contains(next) && !frontier.contains(next)) {
                        frontier.add(next);
                        debugNodes.add(next.toString());
                    }
                }

                // Print newly added nodes to frontier for next loop
                if (!debugNodes.isEmpty()) {
                    System.out.print("[" + String.join(",", debugNodes) + "]");
                }
            }
            return null;
        }

        public void printFrontier(Collection<Node> frontier) {
            if (frontier.isEmpty()) {
                System.out.print("[]");
            } else {
                System.out.print("[" + frontier.stream().map(Node::toString).collect(Collectors.joining(",")) + "]");
            }
        }
    }

    public static class DFS implements SearchStrategy {
        private NodeUtility util;
        private int[] directions;

        public DFS(NodeUtility utility, int[] directions) {
            this.util = utility;
            this.directions = directions;
        }

        @Override
        public List<Node> search(Node start, Node goal, int planetSize) {
            Stack<Node> frontier = new Stack<>();
            Set<Node> explored = new HashSet<>();
            frontier.push(start);

            while (!frontier.isEmpty()) {
                Node current = frontier.pop();
                if (current.equals(goal)) {
                    return util.constructPath(current);
                }
                explored.add(current);
                for (Node child : util.getSuccessors(current, planetSize, directions)) {
                    if (!explored.contains(child) && !frontier.contains(child)) {
                        frontier.push(child);
                    }
                }
            }
            return null;
        }
    }
}
