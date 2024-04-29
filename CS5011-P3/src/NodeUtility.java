import java.util.*;

public class NodeUtility {
    public double calculateCost(Node current, Node next) {
        double r1 = current.d;
        double r2 = next.d;
        double theta1 = Math.toRadians(current.angle);
        double theta2 = Math.toRadians(next.angle);
        double angularDistance = theta2 - theta1;
        return Math.sqrt(r1 * r1 + r2 * r2 - 2 * r1 * r2 * Math.cos(angularDistance));
    }

    public List<Node> getSuccessors(Node current, int planetSize, int[] directions) {
        List<Node> successors = new ArrayList<>();
        for (int direction : directions) {
            int newD = current.d;
            int newAngle = (current.angle + direction) % 360;

            // Check valid move based on the direction and current node context
            switch (direction) {
                case 0: // North towards the pole, decrement d
                    if (newD > 0)
                        newD--;
                    break;
                case 180: // South away from the pole, increment d
                    if (newD < planetSize - 1)
                        newD++;
                    break;
                case 90: // East - Ensure not moving east at d=0
                case 270: // West - Ensure not moving west at d=0
                    if (newD == 0)
                        continue; // Prevent invalid lateral movements at poles
                    break;
            }

            // Ensure new position is within valid bounds and potentially other conditions
            if (newD >= 0 && newD < planetSize) {
                double initialCost = calculateCost(current, new Node(newD, newAngle, current, 0)); // Dummy node for
                                                                                                   // cost calculation
                Node newNode = new Node(newD, newAngle, current, current.cost + initialCost);
                successors.add(newNode);
            }
        }
        return successors;
    }

    public double heuristic(Node node, Node goal) {
        double dDiff = Math.abs(node.d - goal.d);
        double angleDiff = Math.min(Math.abs(node.angle - goal.angle), 360 - Math.abs(node.angle - goal.angle));
        return dDiff + (angleDiff / 360.0 * 2 * Math.PI * node.d);
    }

    public void printFrontier(Collection<Node> frontier) {
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

    public List<Node> constructPath(Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        while (goal != null) {
            path.addFirst(goal);
            goal = goal.parent;
        }
        return path;
    }

    public void printPath(List<Node> path) {
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
