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

                // Normalize the angle to fit within the 8 principal directions
                if (newAngle % 45 != 0) {
                    continue; // If the angle is not one of the principal directions, skip it
                }

                // Check movement direction and calculate new coordinates
                if (direction == 0 && newD > 0) { // Moving North
                    newD -= 1; // Move towards the pole
                } else if (direction == 180 && newD < planetSize - 1) { // Moving South
                    newD += 1; // Move away from the pole
                } else if ((direction == 90 || direction == 270) && newD == 0) {
                    continue; // Can't move East or West when at the pole
                }

                // Other directions don't change 'd', only the angle
                if (isValidCoordinate(newD, newAngle, planetSize)) {
                    double newCost = this.cost + calculateCost(this.d, newD);
                    successors.add(new Node(newD, newAngle, this, newCost));
                }
            }

            // Sort successors based on tie-breaking strategy
            successors.sort(Comparator.comparingInt((Node n) -> n.d).thenComparingInt(n -> n.angle));
            return successors;
        }

        private boolean isValidCoordinate(int d, int angle, int planetSize) {
            // Check if d is within the bounds of the grid
            if (d < 0 || d >= planetSize) {
                return false;
            }
            // Check if the angle is one of the 8 principal directions
            if (!(angle == 0 || angle == 45 || angle == 90 || angle == 135 ||
                    angle == 180 || angle == 225 || angle == 270 || angle == 315)) {
                return false;
            }
            // Ensure the aircraft can't fly over the pole or beyond the last parallel
            if (d == 0 && angle != 0) {
                return false; // At the pole, the only valid angle is 0 (no movement allowed)
            }
            // If none of the invalid conditions are met, the coordinate is valid
            return true;
        }

        private double calculateCost(int currentD, int newD) {
            // Calculate the cost from currentD to newD based on the assignment spec
            if (currentD != newD) {
                return 1.0; // Cost is 1 if moving to a different parallel
            } else {
                return (2 * Math.PI * currentD) / 8; // Cost is the meridian length portion if moving along the same
                                                     // parallel
            }
        }

        // Method to compare nodes
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Node node = (Node) obj;
            return d == node.d &&
                    angle == node.angle;
        }

        @Override
        public int hashCode() {
            return Objects.hash(d, angle);
        }
    }

    public static List<Node> bfs(Node start, Node goal, int planetSize) {
        Queue<Node> frontier = new LinkedList<>();
        Set<Node> explored = new HashSet<>();
        int nodesVisited = 0;

        frontier.add(start);
        printFrontier(frontier); // Print the initial state of the frontier

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            nodesVisited++; // Increment nodes visited

            if (current.equals(goal)) {
                return constructPath(current); // Path cost and nodes visited are handled within constructPath
            }
            explored.add(current);
            for (Node child : current.getSuccessors(planetSize)) {
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.add(child);
                }
            }
            printFrontier(frontier); // Print the state of the frontier at each step
        }
        return null; // Return null or an empty list if no path is found
    }

    public static List<Node> dfs(Node start, Node goal, int planetSize) {
        Stack<Node> frontier = new Stack<>();
        Set<Node> explored = new HashSet<>();
        int nodesVisited = 0; // Counter for the number of nodes visited

        frontier.push(start);
        printFrontier(frontier); // Print the initial state of the frontier

        while (!frontier.isEmpty()) {
            Node current = frontier.pop();
            nodesVisited++; // Increment nodes visited

            if (current.equals(goal)) {
                return constructPath(current); // Path cost and nodes visited are handled within constructPath
            }
            explored.add(current);
            for (Node child : current.getSuccessors(planetSize)) {
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.push(child);
                }
            }
            printFrontier(frontier); // Print the state of the frontier at each step
        }
        return null; // Return null or an empty list if no path is found
    }

    private static void printFrontier(Collection<Node> frontier) {
        System.out.print("[");
        frontier.forEach(node -> System.out.print(String.format("(%d:%d), ", node.d, node.angle)));
        System.out.println("]");
    }

    private static List<Node> constructPath(Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        double totalCost = 0; // Total path cost
        int pathLength = 0; // Number of nodes in the path

        while (current != null) {
            path.addFirst(current);
            if (current.parent != null) {
                totalCost += current.cost; // Calculate total path cost
            }
            current = current.parent;
            pathLength++;
        }
        System.out.println("Path Cost: " + String.format("%.3f", totalCost));
        System.out.println("Nodes Visited: " + pathLength);
        return path;
    }

    public static void printPath(List<Node> path) {
        if (path == null || path.isEmpty()) {
            System.out.println("fail");
        } else {
            double totalCost = 0.0;
            for (Node node : path) {
                System.out.print(String.format("(%d:%d)", node.d, node.angle));
                if (node.parent != null) { // Ensure we do not add cost for the start node which has no parent
                    totalCost += node.cost; // Assuming node.cost is the cost to reach this node from its parent
                }
            }
            System.out.println();
            System.out.println(String.format("%.3f", totalCost)); // Print the total path cost calculated
            System.out.println(path.size()); // Print the number of nodes visited
        }
    }

}
