import java.util.Objects; // Import the Objects class for utility methods

public class Node {
    int d; // Distance from the pole
    int angle; // Angle in degrees
    Node parent; // Parent node in the path
    double cost; // Cost to reach this node
    double heuristicCost; // Heuristic cost for best-first search

    public Node(int d, int angle, Node parent, double cost) {
        this.d = d;
        this.angle = angle;
        this.parent = parent;
        this.cost = cost;
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
        return Objects.hash(d, angle); // Use Objects.hash() to generate hashCode
    }

    @Override
    public String toString() {
        return String.format("(%d:%d)", d, angle); // Ensure this matches the required format
    }

}
