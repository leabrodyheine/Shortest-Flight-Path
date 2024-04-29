import java.util.*;

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

            while (!frontier.isEmpty()) {
                Node current = frontier.poll();
                System.out.println("Frontier: " + frontier); // Add this line to print the state of the frontier
                if (current.equals(goal)) {
                    return util.constructPath(current);
                }
                explored.add(current);
                List<Node> successors = util.getSuccessors(current, planetSize, directions);
                System.out.println("Expanding: " + current + ", Successors: " + successors); // Add this to show node
                                                                                             // expansion
                for (Node next : successors) {
                    if (!explored.contains(next) && !frontier.contains(next)) {
                        frontier.add(next);
                    }
                }
            }
            return null;
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
