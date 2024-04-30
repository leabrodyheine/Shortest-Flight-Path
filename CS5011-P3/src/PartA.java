import java.util.*;
import java.util.stream.Collectors;

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
            int[] angleChanges = { -45, 45 };

            for (int angleChange : angleChanges) {
                if (this.d > 0) {
                    int newAngle = (this.angle + angleChange + 360) % 360;
                    double additionalCost = calculateAngularCost(this.d, angleChange);
                    successors.add(new Node(this.d, newAngle, this, this.cost + additionalCost));
                }
            }

            int[] distanceChanges = { -1, 1 };
            for (int distanceChange : distanceChanges) {
                int newD = this.d + distanceChange;
                if (newD > 0 && newD < planetSize) {
                    double additionalCost = calculateRadialCost(distanceChange);
                    successors.add(new Node(newD, this.angle, this, this.cost + additionalCost));
                }
            }
            return successors;
        }

        // @Override
        // public int compareTo(Node other) {
        // if (Double.compare(this.cost, other.cost) != 0) {
        // return Double.compare(this.cost, other.cost);
        // }
        // if (this.d != other.d) {
        // return Integer.compare(this.d, other.d);
        // }
        // return Integer.compare(this.angle, other.angle);
        // }
        @Override
        public int compareTo(Node other) {
            return Double.compare(this.cost, other.cost);
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

    // private static double calculateAngularCost(int angleChange) {
    // return Math.abs(angleChange) / 45.0;
    // }
    private static double calculateAngularCost(int radius, int angleChange) {
        return Math.abs(angleChange) * (Math.PI * radius / 4) / 45;
    }

    private static double calculateRadialCost(int distanceChange) {
        return Math.abs(distanceChange);
    }

    // public static List<Node> bfs(Node start, Node goal, int planetSize) {
    // Queue<Node> frontier = new LinkedList<>();
    // Map<Node, Boolean> visited = new HashMap<>();
    // int visitedCount = 0; // Counter for visited nodes

    // frontier.add(start);
    // visited.put(start, true);
    // visitedCount++; // Count the start node as visited

    // printFrontier(frontier);

    // while (!frontier.isEmpty()) {
    // Node current = frontier.poll();

    // if (current.equals(goal)) {
    // List<Node> path = constructPath(current);
    // printPath(path);
    // return path;
    // }

    // List<Node> successors = current.getSuccessors(planetSize);
    // Collections.sort(successors);

    // for (Node next : successors) {
    // if (!visited.getOrDefault(next, false)) {
    // visited.put(next, true);
    // frontier.add(next);
    // visitedCount++; // Increment count for each unique visit
    // }
    // }
    // printFrontier(frontier);
    // }

    // System.out.println("fail");
    // System.out.println(visitedCount);
    // return null;
    // }
    public static List<Node> bfs(Node start, Node goal, int planetSize) {
        PriorityQueue<Node> frontier = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();

        frontier.add(start);
        parentMap.put(start, null); // Start node has no parent

        while (!frontier.isEmpty()) {
            printFrontier(frontier); // Move the print statement here to correctly display before polling
            Node current = frontier.poll();

            if (visited.contains(current)) {
                continue; // Skip processing if already visited
            }
            visited.add(current); // Mark the node as visited when it is actually processed

            if (current.equals(goal)) {
                List<Node> path = constructPath(current, parentMap);
                printPath(path, visited.size()); // Print visited count only here within path print
                return path;
            }

            List<Node> successors = current.getSuccessors(planetSize);
            for (Node next : successors) {
                if (!visited.contains(next) && !frontier.contains(next)) {
                    frontier.add(next);
                    parentMap.put(next, current); // Track the parent of each node
                }
            }
        }

        System.out.println("fail");
        System.out.println(visited.size()); // Ensure visited count is printed only once if failed
        return null;
    }

    private static void printFrontier(Collection<Node> frontier) {
        if (!frontier.isEmpty()) {
            String result = frontier.stream()
                    .map(Node::toString)
                    .collect(Collectors.joining(","));
            System.out.println("[" + result + "]");
        }
    }

    private static List<Node> constructPath(Node goal, Map<Node, Node> parentMap) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.addFirst(current);
            current = parentMap.get(current); // Retrieve the parent from the map
        }
        return path;
    }

    public static void printPath(List<Node> path, int visitedCount) {
        if (path == null || path.isEmpty()) {
            System.out.println("fail");
        } else {
            for (Node node : path) {
                System.out.print(node + "");
            }
            Node lastNode = path.get(path.size() - 1);
            System.out.println();
            System.out.printf("%.3f\n%d\n%d\n", lastNode.cost, path.size(), visitedCount);
        }
    }
}
