import java.util.*;

public class PartA {

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
            int[] angleChanges = { -45, 45 }; // Allowed angular changes
            int[] distanceChanges = { -1, 1 }; // Allowed radial changes

            // Generate successors for angle changes
            for (int angleChange : angleChanges) {
                int newAngle = (this.angle + angleChange + 360) % 360; // Correctly handle angle wrapping
                // Check if the new state is within valid bounds before adding
                if (this.d > 0 && this.d < planetSize - 1) { // Only allow angular changes if within valid distance
                                                             // range
                    successors.add(new Node(this.d, newAngle, this, this.cost + calculateAngularCost(angleChange)));
                }
            }

            // Generate successors for distance changes
            for (int distanceChange : distanceChanges) {
                int newD = this.d + distanceChange;
                if (newD >= 0 && newD < planetSize) { // Check radial boundaries
                    successors.add(new Node(newD, this.angle, this, this.cost + calculateRadialCost(distanceChange)));
                }
            }
            return successors;
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

    private static double calculateAngularCost(int angleChange) {
        return Math.abs(angleChange) / 45.0;
    }

    private static double calculateRadialCost(int distanceChange) {
        return Math.abs(distanceChange);
    }

    public static List<Node> bfs(Node start, Node goal, int planetSize) {
        Queue<Node> frontier = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        frontier.add(start);
        visited.add(start);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            if (current.equals(goal)) {
                return constructPath(current);
            }

            for (Node next : current.getSuccessors(planetSize)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    frontier.add(next);
                }
            }
        }
        return null;
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

    public static void printPath(List<Node> path) {
        if (path == null || path.isEmpty()) {
            System.out.println("fail");
        } else {
            path.forEach(node -> System.out.print(String.format("(%d:%d)", node.d, node.angle)));
            Node lastNode = path.get(path.size() - 1);
            System.out.println();
            System.out.printf("%.3f\n%d\n", lastNode.cost, path.size());
        }
    }
}
