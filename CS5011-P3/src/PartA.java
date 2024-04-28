import java.util.*;

public class PartA {

    private static final int[] DIRECTIONS = { 0, 45, 90, 135, 180, 225, 270, 315 };

    public static class Node {
        int d; // Distance from the pole
        int angle; // Angle in degrees
        Node parent; // Parent node in the path
        double cost; // Cost to reach this node

        public Node(int d, int angle, Node parent, double cost) {
            this.d = d;
            this.angle = angle;
            this.parent = parent;
            this.cost = cost;
        }

        public List<Node> getSuccessors(int planetSize) {
            List<Node> successors = new ArrayList<>();

            for (int direction : DIRECTIONS) {
                int newD = this.d;
                int newAngle = (this.angle + direction) % 360;

                // Skip angles not on principal directions
                if (newAngle % 45 != 0) {
                    continue;
                }

                if (direction == 0 && newD > 0) { // North
                    newD -= 1;
                } else if (direction == 180 && newD < planetSize - 1) { // South
                    newD += 1;
                } else if ((direction == 90 || direction == 270) && newD == 0) { // East/West at pole
                    continue;
                }

                if (isValidCoordinate(newD, newAngle, planetSize)) {
                    double newCost = this.cost + calculateCost(this.d, newD);
                    successors.add(new Node(newD, newAngle, this, newCost));
                }
            }

            successors.sort(Comparator.comparingInt((Node n) -> n.d).thenComparingInt(n -> n.angle));
            return successors;
        }

        private boolean isValidCoordinate(int d, int angle, int planetSize) {
            if (d < 0 || d >= planetSize) {
                return false;
            }
            return angle % 45 == 0 && (d != 0 || angle == 0);
        }

        private double calculateCost(int currentD, int newD) {
            return currentD != newD ? 1.0 : (2 * Math.PI * currentD) / 8;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Node node = (Node) obj;
            return d == node.d && angle == node.angle;
        }

        @Override
        public int hashCode() {
            return Objects.hash(d, angle);
        }
    }

    public static List<Node> bfs(Node start, Node goal, int planetSize) {
        Queue<Node> frontier = new LinkedList<>();
        Set<Node> explored = new HashSet<>();
        frontier.add(start);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            if (current.equals(goal)) {
                return constructPath(current);
            }
            explored.add(current);
            for (Node child : current.getSuccessors(planetSize)) {
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.add(child);
                }
            }
        }
        return null;
    }

    public static List<Node> dfs(Node start, Node goal, int planetSize) {
        Stack<Node> frontier = new Stack<>();
        Set<Node> explored = new HashSet<>();
        frontier.push(start);

        while (!frontier.isEmpty()) {
            Node current = frontier.pop();
            if (current.equals(goal)) {
                return constructPath(current);
            }
            explored.add(current);
            for (Node child : current.getSuccessors(planetSize)) {
                if (!explored.contains(child)) {
                    frontier.push(child);
                }
            }
        }
        return null;
    }

    private static List<Node> constructPath(Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        double totalCost = 0;

        while (current != null) {
            path.addFirst(current);
            if (current.parent != null) {
                totalCost += current.cost;
            }
            current = current.parent;
        }
        return path;
    }

    public static void printPath(List<Node> path) {
        if (path == null || path.isEmpty()) {
            System.out.println("fail");
        } else {
            path.forEach(node -> System.out.print(String.format("(%d:%d)", node.d, node.angle)));
            System.out.println();
            double totalCost = path.get(path.size() - 1).cost;
            System.out.println(String.format("%.3f", totalCost));
            System.out.println(path.size());
        }
    }
}
