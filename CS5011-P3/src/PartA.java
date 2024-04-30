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

        @Override
        public int compareTo(Node other) {
            if (this.d != other.d) {
                return Integer.compare(this.d, other.d); // Prioritize lower distance
            }
            return Integer.compare(this.angle, other.angle); // Then by angle
        }

        @Override
        public String toString() {
            return String.format("(%d:%d)", d, angle);
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

        // public List<Node> getSuccessors(int planetSize) {
        // List<Node> successors = new ArrayList<>();
        // int[] angleChanges = { -45, 45 };

        // for (int angleChange : angleChanges) {
        // if (this.d > 0) {
        // int newAngle = (this.angle + angleChange + 360) % 360;
        // double additionalCost = calculateAngularCost(this.d, angleChange);
        // successors.add(new Node(this.d, newAngle, this, this.cost + additionalCost));
        // }
        // }

        // int[] distanceChanges = { -1, 1 };
        // for (int distanceChange : distanceChanges) {
        // int newD = this.d + distanceChange;
        // if (newD > 0 && newD < planetSize) {
        // double additionalCost = calculateRadialCost(distanceChange);
        // successors.add(new Node(newD, this.angle, this, this.cost + additionalCost));
        // }
        // }
        // return successors;
        // }
    }

    private static double calculateAngularCost(int radius, int angleChange) {
        return Math.abs(angleChange) * (Math.PI * radius / 4) / 45;
    }

    private static double calculateRadialCost(int distanceChange) {
        return Math.abs(distanceChange);
    }

    public static List<Node> bfs(Node start, Node goal, int planetSize) {
        Queue<Node> frontier = new LinkedList<>();
        Set<Node> visited = new HashSet<>(); // Use a set to track visited nodes

        frontier.add(start);
        visited.add(start);

        while (!frontier.isEmpty()) {
            printFrontier(frontier);
            Node current = frontier.poll();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);
            if (current.equals(goal)) {
                return constructPath(current);
            }

            List<Node> successors = getSuccessors(current, planetSize);

            Collections.sort(successors);
            for (Node successor : getSuccessors(current, planetSize)) {
                if (!visited.contains(successor)) {
                    visited.add(successor);
                    frontier.add(successor);
                    System.out.println("Adding to frontier: " + successor);
                }
            }
        }

        System.out.println("fail");
        System.out.println(visited.size()); // Print the number of unique nodes processed
        return null;
    }

    private static List<Node> getSuccessors(Node node, int planetSize) {
        List<Node> successors = new ArrayList<>();
        int[] angleChanges = { -45, 45 };

        for (int angleChange : angleChanges) {
            int newAngle = (node.angle + angleChange + 360) % 360;
            if (node.d > 0) {
                successors.add(new Node(node.d, newAngle, node, node.cost + 1)); // Assume cost of 1 per move
            }
        }

        int[] distanceChanges = { -1, 1 };
        for (int distanceChange : distanceChanges) {
            int newD = node.d + distanceChange;
            if (newD > 0 && newD < planetSize) {
                successors.add(new Node(newD, node.angle, node, node.cost + 1));
            }
        }
        return successors;
    }

    private static void printFrontier(Collection<Node> frontier) {
        if (!frontier.isEmpty()) {
            String result = frontier.stream()
                    .map(Node::toString)
                    .collect(Collectors.joining(","));
            System.out.println("[" + result + "]");
        }
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

    public static void printPath(List<Node> path, int visitedCount) {
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
