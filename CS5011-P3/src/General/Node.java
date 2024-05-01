package General;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {
    int d; // Distance from the pole
    int angle; // Angle in degrees
    Node parent; // Parent node in the path
    double cost; // Cost to reach this node
    double heuristic; // Heuristic value of the node to the goal

    // Unified constructor
    public Node(int d, int angle, Node parent, double cost, Node goal) {
        this.d = d;
        this.angle = angle;
        this.parent = parent;
        this.cost = cost;
        if (goal != null) {
            this.heuristic = calculateHeuristic(goal); // Only calculate if goal is provided
        } else {
            this.heuristic = 0; // Default to 0 if no goal is provided (e.g., for goal node itself)
        }
    }

    private double calculateHeuristic(Node goal) {
        double radCurrent = Math.toRadians(this.angle);
        double radGoal = Math.toRadians(goal.angle);
        return Math.sqrt(this.d * this.d + goal.d * goal.d
                - 2 * this.d * goal.d * Math.cos(radGoal - radCurrent));
    }

    public List<Node> getSuccessors(int planetSize, Node goal) {
        List<Node> successors = new ArrayList<>();
        int[] angleChanges = { -45, 45 };
        int[] distanceChanges = { -1, 1 };

        for (int angleChange : angleChanges) {
            int newAngle = (this.angle + angleChange + 360) % 360;
            if (this.d > 0) {
                double additionalCost = calculateAngularCost(this.d, angleChange);
                successors.add(new Node(this.d, newAngle, this, this.cost + additionalCost, goal));
            }
        }

        for (int distanceChange : distanceChanges) {
            int newD = this.d + distanceChange;
            if (newD > 0 && newD < planetSize) {
                double additionalCost = calculateRadialCost(distanceChange);
                successors.add(new Node(newD, this.angle, this, this.cost + additionalCost, goal));
            }
        }
        return successors;
    }

    private static double calculateAngularCost(int d, int angleChange) {
        double oneEighthCircumference = (2 * Math.PI * d) / 8;
        return oneEighthCircumference;
    }

    private static double calculateRadialCost(int distanceChange) {
        return Math.abs(distanceChange);
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

    public double getCost() {
        return cost;
    }

    public Node getParent() {
        return parent;
    }

    public int getD() {
        return d;
    }

    public int getAngle() {
        return angle;
    }

    public double getHeuristic() {
        return this.heuristic;
    }
}
