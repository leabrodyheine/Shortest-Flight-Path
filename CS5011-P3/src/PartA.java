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
            // Debugging: Output current state before generating successors
            System.out.println("Generating successors for Node: " + this);

            int[] angleChanges = { -45, 45 };
            int[] distanceChanges = { -1, 1 };

            // Generate successors
            for (int angleChange : angleChanges) {
                int newAngle = (this.angle + angleChange + 360) % 360;
                successors.add(new Node(this.d, newAngle, this, this.cost + calculateAngularCost(angleChange)));
            }

            for (int distanceChange : distanceChanges) {
                int newD = this.d + distanceChange;
                if (newD >= 0 && newD < planetSize) {
                    successors.add(new Node(newD, this.angle, this, this.cost + calculateRadialCost(distanceChange)));
                }
            }

            // Debugging: Output generated successors
            System.out.println("Successors generated: " + successors.size());
            for (Node s : successors) {
                System.out.println(" -> Successor: " + s);
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
        // Define cost for angular movement
        return Math.abs(angleChange) / 45.0; // Example: cost per 45-degree turn
    }

    private static double calculateRadialCost(int distanceChange) {
        // Define cost for radial movement
        return Math.abs(distanceChange); // Example: cost per radial step
    }

    public static List<Node> bfs(Node start, Node goal, int planetSize) {
        Queue<Node> frontier = new LinkedList<>();
        Map<Node, Double> costMap = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        frontier.add(start);
        costMap.put(start, 0.0);
        visited.add(start);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            if (current.equals(goal)) {
                return constructPath(current);
            }

            for (Node next : current.getSuccessors(planetSize)) {
                if (!visited.contains(next)) {
                    double newCost = current.cost
                            + (next.d != current.d ? calculateRadialCost(Math.abs(next.d - current.d))
                                    : calculateAngularCost(Math.abs(next.angle - current.angle)));
                    if (!costMap.containsKey(next) || newCost < costMap.get(next)) {
                        visited.add(next);
                        costMap.put(next, newCost);
                        next.cost = newCost;
                        frontier.add(next);
                    }
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
