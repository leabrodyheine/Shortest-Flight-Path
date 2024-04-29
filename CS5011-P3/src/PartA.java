import java.util.*;

public class PartA {

    public static class BFS implements SearchStrategy {
        private NodeUtility util;

        public BFS(NodeUtility utility) {
            this.util = utility;
        }

        @Override
        public List<Node> search(Node start, Node goal, int planetSize) {
            Queue<Node> frontier = new LinkedList<>();
            Set<Node> explored = new HashSet<>();
            frontier.add(start);
            util.printFrontier(frontier);

            while (!frontier.isEmpty()) {
                Node current = frontier.poll();
                if (current.equals(goal)) {
                    return util.constructPath(current);
                }
                explored.add(current);
                for (Node child : util.getSuccessors(current, planetSize)) {
                    if (!explored.contains(child) && !frontier.contains(child)) {
                        frontier.add(child);
                    }
                }
                util.printFrontier(frontier);
            }
            return null;
        }
    }

    public static class DFS implements SearchStrategy {
        private NodeUtility util;

        public DFS(NodeUtility utility) {
            this.util = utility;
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
                for (Node child : util.getSuccessors(current, planetSize)) {
                    if (!explored.contains(child) && !frontier.contains(child)) {
                        frontier.push(child);
                    }
                }
            }
            return null;
        }
    }
}
