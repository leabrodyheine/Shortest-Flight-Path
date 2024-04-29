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
            int[] angleChanges = { -45, 45 }; // Allowed angle changes
            int[] distanceChanges = { -1, 1 }; // Allowed distance changes

            // Handle angular changes
            for (int angleChange : angleChanges) {
                int newAngle = (this.angle + angleChange) % 360;
                if (newAngle < 0)
                    newAngle += 360; // Correct negative angles
                if (this.d > 0 && this.d < planetSize - 1) { // Ensure valid distance
                    successors.add(new Node(this.d, newAngle, this, this.cost + calculateCost(this.d, this.d)));
                }
            }

            // Handle radial changes
            for (int distanceChange : distanceChanges) {
                int newD = this.d + distanceChange;
                if (newD >= 0 && newD < planetSize) { // Ensure new distance is within bounds
                    successors.add(new Node(newD, this.angle, this, this.cost + calculateCost(this.d, newD)));
                }
            }

            return successors;
        }

        private double calculateCost(int currentD, int newD) {
            // This might be a simplified placeholder for your actual cost calculation logic
            return currentD == newD ? (2 * Math.PI * currentD) / 360 : 1.0;
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
        printFrontier(frontier);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            if (current.equals(goal)) {
                List<Node> path = constructPath(current);
                printPath(path);
                return path;
            }
            explored.add(current);
            for (Node child : current.getSuccessors(planetSize)) {
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.add(child);
                }
            }
            printFrontier(frontier);
        }
        return null;
    }

    // public static List<Node> dfs(Node start, Node goal, int planetSize) {
    // Stack<Node> frontier = new Stack<>();
    // Set<Node> explored = new HashSet<>();
    // frontier.push(start);

    // while (!frontier.isEmpty()) {
    // Node current = frontier.pop();
    // if (current.equals(goal)) {
    // return constructPath(current);
    // }
    // explored.add(current);
    // for (Node child : current.getSuccessors(planetSize)) {
    // if (!explored.contains(child) && !frontier.contains(child)) {
    // frontier.push(child);
    // }
    // }
    // }
    // return null; // No path found
    // }

    private static void printFrontier(Collection<Node> frontier) {
        if (frontier.isEmpty())
            return;
        System.out.print("[");
        Iterator<Node> it = frontier.iterator();
        while (it.hasNext()) {
            Node node = it.next();
            System.out.print(String.format("(%d:%d)", node.d, node.angle));
            if (it.hasNext())
                System.out.print(",");
        }
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
            path.forEach(node -> System.out.print(String.format("(%d:%d)", node.d, node.angle)));
            Node lastNode = path.get(path.size() - 1);
            System.out.println();
            System.out.printf("%.3f\n%d\n", lastNode.cost, path.size());
        }
    }

}