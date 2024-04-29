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
            // for (int angleChange : angleChanges) {
            //     int newAngle = (this.angle + angleChange + 360) % 360; // Correctly handle angle wrapping
                // Check if the new state is within valid bounds before adding
                // if (this.d > 0 && this.d < planetSize - 1) { // Only allow angular changes if
                // within valid distance
                // // range
                // successors.add(new Node(this.d, newAngle, this, this.cost +
                // calculateAngularCost(angleChange)));
                // }
                for (int angleChange : angleChanges) {
                    int newAngle = (this.angle + angleChange + 360) % 360; // Correctly handle angle wrapping
                    successors.add(new Node(this.d, newAngle, this, this.cost + calculateAngularCost(angleChange)));
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

        @Override
        public String toString() {
            return String.format("(%d:%d)", d, angle);
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
        Map<Node, Boolean> visited = new HashMap<>(); // Use a map to store nodes and their visited status
        frontier.add(start);
        visited.put(start, true);

        System.out.println("[" + start + "]"); // Print the initial state with the start node

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (current.equals(goal)) {
                List<Node> path = constructPath(current);
                printPath(path);
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize);
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (Node next : successors) {
                if (visited.getOrDefault(next, false) == false) { // Check if not visited
                    visited.put(next, true);
                    frontier.add(next);
                    if (first) {
                        first = false;
                        sb.append("[");
                    } else {
                        sb.append(",");
                    }
                    sb.append(next);
                }
            }
            if (!first) { // Close the bracket if any node was added to the string
                sb.append("]");
                System.out.println(sb.toString());
            }
        }
        System.out.println("No path found.");
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
            for (Node node : path) {
                System.out.print(node + "");
            }
            Node lastNode = path.get(path.size() - 1);
            System.out.println();
            System.out.printf("%.3f\n%d\n", lastNode.cost, path.size());
        }
    }
}
