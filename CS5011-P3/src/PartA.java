import java.util.*;

public class PartA {

    public static class Node implements Comparable<Node> {
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

            // Generate successors for angle changes
            for (int angleChange : angleChanges) {
                int newAngle = (this.angle + angleChange + 360) % 360; // Correctly handle angle wrapping
                if (this.d > 0) { // Angular change only allowed if not at the pole
                    successors.add(new Node(this.d, newAngle, this, this.cost + 1));
                }
            }

            // Generate successors for distance changes
            int[] distanceChanges = { -1, 1 }; // Allowed radial changes
            for (int distanceChange : distanceChanges) {
                int newD = this.d + distanceChange;
                if (newD >= 0 && newD < planetSize) { // Check radial boundaries
                    successors.add(new Node(newD, this.angle, this, this.cost + 1));
                }
            }

            return successors;
        }

        @Override
        public int compareTo(Node other) {
            if (this.d != other.d) {
                return Integer.compare(this.d, other.d);
            }
            return Integer.compare(this.angle, other.angle);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            return d == other.d && angle == other.angle;
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
        Map<Node, Boolean> visited = new HashMap<>();
        frontier.add(start);
        visited.put(start, true);

        printFrontier(frontier);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (current.equals(goal)) {
                List<Node> path = constructPath(current);
                printPath(path);
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize);
            // Sort successors before adding to the queue
            Collections.sort(successors);

            for (Node next : successors) {
                if (!visited.getOrDefault(next, false)) {
                    visited.put(next, true);
                    frontier.add(next);
                }
            }
            printFrontier(frontier);
        }
        System.out.println("No path found.");
        return null;
    }

    private static void printFrontier(Collection<Node> frontier) {
        System.out.print("[");
        frontier.forEach(node -> System.out.print(node + ","));
        System.out.println("]");
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
