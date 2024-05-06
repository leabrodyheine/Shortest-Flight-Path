package General;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a node in a graph, typically used in pathfinding or graph
 * traversal algorithms. Each node is characterized by its position, cost to
 * reach, heuristic estimate towards a goal, and various other attributes useful
 * in search algorithms.
 */
public class Node implements Comparable<Node> {
    int d; // Distance from the pole
    int angle; // Angle in degrees
    Node parent; // Parent node in the path
    double cost; // Cost to reach this node
    double heuristic; // Heuristic value of the node to the goal
    private double fCost; // f-cost: total cost of the node
    int depth; // Depth in the search tree
    boolean leaf; // Whether the node is currently a leaf in the search tree
    List<Node> forgotten; // Nodes that were pruned from the search frontier
    boolean visited;
    
    /**
     * Constructs a new Node with specified parameters, used in SMA* algorithm.
     *
     * @param d      Distance from the pole.
     * @param angle  Angle in degrees.
     * @param parent Parent node in the search path.
     * @param cost   Cost to reach this node.
     * @param goal   Goal node to calculate heuristic towards.
     * @param depth  Depth of this node in the search tree.
     */
    public Node(int d, int angle, Node parent, double cost, Node goal, int depth) {
        this.d = d;
        this.angle = angle;
        this.parent = parent;
        this.cost = cost;
        if (goal != null) {
            this.heuristic = calculateHeuristic(goal);
        } else {
            this.heuristic = 0;
        }
        this.fCost = this.cost + this.heuristic;
        this.depth = depth;
        this.leaf = false;
        this.forgotten = new ArrayList<Node>();
    }

    /**
     * Secondary constructor for Node without specifying depth, typically used
     * outside the SMA* algorithm.
     *
     * @param d      Distance from the pole.
     * @param angle  Angle in degrees.
     * @param parent Parent node.
     * @param cost   Cost to reach this node.
     * @param goal   Goal node to calculate heuristic towards.
     */
    public Node(int d, int angle, Node parent, double cost, Node goal) {
        this.d = d;
        this.angle = angle;
        this.parent = parent;
        this.cost = cost;
        if (goal != null) {
            this.heuristic = calculateHeuristic(goal);
        } else {
            this.heuristic = 0;
        }
        this.fCost = this.cost + this.heuristic;
        this.depth = 1;
        this.forgotten = new ArrayList<Node>();
    }

    /**
     * Calculates the heuristic estimate from this node to a specified goal node
     * using the Euclidean distance formula adapted for polar coordinates.
     *
     * @param goal The goal node to calculate the heuristic towards.
     * @return The heuristic estimate.
     */
    public double calculateHeuristic(Node goal) {
        double radCurrent = Math.toRadians(this.angle);
        double radGoal = Math.toRadians(goal.angle);
        return Math.sqrt(this.d * this.d + goal.d * goal.d
                - 2 * this.d * goal.d * Math.cos(radGoal - radCurrent));
    }

    /**
     * Generates successors for this node based on possible movements on a grid.
     *
     * @param planetSize The size of the planet, limiting the maximum allowable
     *                   distance.
     * @param goal       The goal node, used to calculate heuristics for successors.
     * @return A list of successor nodes.
     */
    public List<Node> getSuccessors(int planetSize, Node goal) {
        List<Node> successors = new ArrayList<>();
        int[] angleChanges = { -45, 45 };
        int[] distanceChanges = { -1, 1 };

        for (int angleChange : angleChanges) {
            int newAngle = (this.angle + angleChange + 360) % 360;
            if (this.d > 0 && this.d < planetSize) {
                double additionalCost = calculateAngularCost(this.d, angleChange);
                successors.add(new Node(this.d, newAngle, this, this.cost + additionalCost, goal, this.depth + 1));
            }
        }

        for (int distanceChange : distanceChanges) {
            int newD = this.d + distanceChange;
            if (newD > 0 && newD < planetSize) {
                double additionalCost = calculateRadialCost(distanceChange);
                successors.add(new Node(newD, this.angle, this, this.cost + additionalCost, goal, this.depth + 1));
            }
        }
        return successors;
    }

    /**
     * Calculates the angular movement cost based on the change in angle and the
     * current distance from the pole.
     *
     * @param d           The current distance from the pole.
     * @param angleChange The change in angle, in degrees.
     * @return The cost associated with the angular movement.
     */
    private double calculateAngularCost(int d, double angleChange) {
        double oneEighthCircumference = (2 * Math.PI * d) / 8;
        return oneEighthCircumference;
    }

    /**
     * Calculates the radial movement cost based on the change in distance from the
     * pole.
     *
     * @param distanceChange The change in distance from the current position.
     * @return The cost associated with the radial movement.
     */
    private double calculateRadialCost(int distanceChange) {
        return Math.abs(distanceChange);
    }

    /**
     * Calculates the distance from this node to another node, considering both
     * angular and radial differences.
     *
     * @param other The node to which the distance is calculated.
     * @return The calculated distance.
     */
    public double distance(Node other) {
        double angleChange = this.angle - other.getAngle();
        int radialChange = this.d - other.d;
        double radialCost = this.calculateRadialCost(radialChange);
        double angleCost = this.calculateAngularCost(radialChange, angleChange);
        return Math.max(radialCost, angleCost);
    }

    /**
     * Compares this node to another node to determine ordering.
     * Nodes are first compared by distance from the pole, then by angle.
     *
     * @param other The node to compare against.
     * @return -1, 0, or 1 as this node is less than, equal to, or greater than the
     *         specified node.
     */
    @Override
    public int compareTo(Node other) {
        if (this.d != other.d) {
            return Integer.compare(this.d, other.d);
        }
        return Integer.compare(this.angle, other.angle);
    }

    /**
     * Determines whether another object is equal to this node.
     * Two nodes are considered equal if they have the same distance and angle.
     *
     * @param obj The object to compare with this node.
     * @return true if the specified object is equal to this node; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        return d == other.d && angle == other.angle;
    }

    /**
     * Generates a hash code for this node.
     *
     * @return A hash code value for this node.
     */
    @Override
    public int hashCode() {
        return Objects.hash(d, angle);
    }

    /**
     * Returns a string representation of this node, typically used for debugging
     * purposes.
     *
     * @return A string representation of this node, showing its distance and angle.
     */
    @Override
    public String toString() {
        return String.format("(%d:%d)", d, angle);
    }

    /**
     * Getters and Setters for the Node Class
     */

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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
        return heuristic;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    public double getfCost() {
        return fCost;
    }

    public void setfCost(double fCost) {
        this.fCost = fCost;
    }

    public List<Node> getForgotten() {
        return forgotten;
    }

    public void setForgotten(List<Node> forgotten) {
        this.forgotten = forgotten;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}
