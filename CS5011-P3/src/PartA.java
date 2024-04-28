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

                // Adjust movement based on direction
                switch (direction) {
                    case 0:
                        if (newD > 0)
                            newD--;
                        break; // North
                    case 180:
                        if (newD < planetSize - 1)
                            newD++;
                        break; // South
                    case 90:
                    case 270:
                        if (newD == 0)
                            continue; // Skip East/West at poles
                }

                if (isValidCoordinate(newD, newAngle, planetSize)) {
                    double newCost = this.cost + calculateCost(this.d, newD);
                    successors.add(new Node(newD, newAngle, this, newCost));
                }
            }
            return successors;
        }

        private boolean isValidCoordinate(int d, int angle, int planetSize) {
            return d >= 0 && d < planetSize;
        }

        private double calculateCost(int currentD, int newD) {
            if (currentD == newD)
                return 2 * Math.PI * currentD / 8;
            return 1.0;
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
        printFrontier(frontier); // Initial frontier

        while (!frontier.isEmpty()) {
            int levelSize = frontier.size(); // Number of nodes at the current level
            Queue<Node> currentLevelNodes = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                Node current = frontier.poll();
                if (current.equals(goal)) {
                    List<Node> finalPath = constructPath(current);
                    printPath(finalPath); // Print the final path once found
                    return finalPath;
                }
                explored.add(current);

                for (Node child : current.getSuccessors(planetSize)) {
                    if (!explored.contains(child) && !frontier.contains(child)) {
                        frontier.add(child);
                        currentLevelNodes.add(child); // Add to temp storage to print after all nodes at this level are
                                                      // processed
                    }
                }
            }

            if (!currentLevelNodes.isEmpty()) {
                printFrontier(currentLevelNodes); // Print all new frontier nodes together
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
                if (!explored.contains(child) && !frontier.contains(child)) {
                    frontier.push(child);
                }
            }
        }
        return null; // No path found
    }

    private static void printFrontier(Collection<Node> frontier) {
        if (frontier.isEmpty()) {
            return;
        }
        System.out.print("[");
        Iterator<Node> it = frontier.iterator();
        while (it.hasNext()) {
            Node node = it.next();
            System.out.print(String.format("(%d:%d)", node.d, node.angle));
            if (it.hasNext()) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    private static List<Node> constructPath(Node goal) {
        List<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null) {
            path.add(0, current); // Add to front to reverse the path
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
            System.out.printf("\n%.3f\n%d\n", lastNode.cost, path.size());
        }
    }

}
