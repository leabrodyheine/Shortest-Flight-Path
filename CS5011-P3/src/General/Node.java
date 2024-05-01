package General;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {
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
        int[] distanceChanges = { -1, 1 };

        for (int angleChange : angleChanges) {
            int newAngle = (this.angle + angleChange + 360) % 360;
            if (this.d > 0) {
                double additionalCost = calculateAngularCost(this.d, angleChange);
                successors.add(new Node(this.d, newAngle, this, this.cost + additionalCost));
            }
        }

        for (int distanceChange : distanceChanges) {
            int newD = this.d + distanceChange;
            if (newD > 0 && newD < planetSize) {
                double additionalCost = calculateRadialCost(distanceChange);
                successors.add(new Node(newD, this.angle, this, this.cost + additionalCost));
            }
        }
        return successors;
    }

    private static double calculateAngularCost(int d, int angleChange) {
        // Calculate 1/8 of the circumference at the radius d
        double oneEighthCircumference = (2 * Math.PI * d) / 8;
    
        // Calculate the proportional distance for the given angleChange
        // as a fraction of the total 360 degrees
        double distanceForAngleChange = oneEighthCircumference * (Math.abs(angleChange) / 360.0);
    
        return distanceForAngleChange;
    }
    

    private static double calculateRadialCost(int distanceChange) {
        return Math.abs(distanceChange);
    }

    @Override
    public int compareTo(Node other) {
        if (Double.compare(this.cost, other.cost) != 0) {
            return Double.compare(this.cost, other.cost);
        }
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
}
