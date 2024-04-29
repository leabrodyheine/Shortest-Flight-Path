import java.util.*;

public class PartB {

    public static class BestFirstSearch implements SearchStrategy {
        private NodeUtility util;

        public BestFirstSearch(NodeUtility utility) {
            this.util = utility;
        }

        @Override
        public List<Node> search(Node start, Node goal, int planetSize) {
            PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingDouble(n -> n.heuristicCost));
            Map<Node, Double> costSoFar = new HashMap<>();
            start.heuristicCost = util.heuristic(start, goal);
            start.cost = 0;
            frontier.add(start);
            costSoFar.put(start, 0.0);

            while (!frontier.isEmpty()) {
                Node current = frontier.poll();
                if (current.equals(goal)) {
                    return util.constructPath(current);
                }

                for (Node next : util.getSuccessors(current, planetSize)) {
                    double newCost = costSoFar.get(current) + util.calculateCost(current, next);
                    if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                        costSoFar.put(next, newCost);
                        next.cost = newCost;
                        next.heuristicCost = newCost + util.heuristic(next, goal);
                        frontier.add(next);
                        next.parent = current;
                    }
                }
            }
            return null; // No path found
        }
    }
}
