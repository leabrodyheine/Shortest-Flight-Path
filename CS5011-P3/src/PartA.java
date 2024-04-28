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
                newAngle = (newAngle < 360) ? newAngle : newAngle - 360; // Normalize angle within 360 degrees

                if (newAngle % 45 != 0)
                    continue; // Ensure angle is one of the principal directions

                // Movement logic based on direction
                if (direction == 0 && newD > 0)
                    newD--; // North
                else if (direction == 180 && newD < planetSize - 1)
                    newD++; // South
                else if ((direction == 90 || direction == 270) && newD == 0)
                    continue; // Cannot move East/West at the pole

                if (isValidCoordinate(newD, newAngle, planetSize)) {
                    double newCost = this.cost + calculateCost(this.d, newD);
                    successors.add(new Node(newD, newAngle, this, newCost));
                }
            }
            return successors;
        }

        private boolean isValidCoordinate(int d, int angle, int planetSize) {
            return d >= 0 && d < planetSize && (angle % 45 == 0); // Check valid distance and angle
        }

        private double calculateCost(int currentD, int newD) {
            if (currentD != newD)
                return 1.0; // Cost for moving between parallels
            return 2 * Math.PI * currentD / 8; // Cost for moving along the same parallel
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
            if (current.equals(goal))
                return constructPath(current);
            explored.add(current);

            for (Node child : current.getSuccessors(planetSize)) {
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.add(child);
                }
            }
        }
        return null; // No path found
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
        List<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.add(0, current);
            current = current.parent;
        }
        return path;
    }

    public static void printPath(List<Node> path) {
        if (path == null) {
            System.out.println("fail");
            return;
        }
        path.forEach(node -> System.out.print(String.format("(%d:%d)", node.d, node.angle)));
        System.out.println();
    }
}
