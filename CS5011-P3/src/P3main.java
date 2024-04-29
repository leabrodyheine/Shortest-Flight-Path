import java.util.List;

public class P3main {

	public static void main(String[] args) {
		if (args.length < 4) {
			System.out.println("Usage: java P3main <DFS|BFS|AStar|BestF|SMAStar> <N> <ds:as> <dg:ag> [<param>]");
			System.exit(1);
		}
		System.out.println("World: Oedipus " + args[1]);
		System.out.println("Departure airport -- Start: " + args[2]);
		System.out.println("Destination airport -- Goal: " + args[3]);
		System.out.println("Search algorithm: " + args[0]);

		NodeUtility utility = new NodeUtility();
		SearchStrategy searchStrategy = getSearchStrategy(args[0], utility);
		if (searchStrategy == null) {
			System.out.println("Invalid algorithm specified.");
			return;
		}

		String[] startParams = args[2].split(":");
		String[] goalParams = args[3].split(":");
		Node startNode = new Node(Integer.parseInt(startParams[0]), Integer.parseInt(startParams[1]), null, 0);
		Node goalNode = new Node(Integer.parseInt(goalParams[0]), Integer.parseInt(goalParams[1]), null, 0);

		List<Node> path = searchStrategy.search(startNode, goalNode, Integer.parseInt(args[1]));
		if (path != null) {
			utility.printPath(path);
		} else {
			System.out.println("No path found.");
		}
	}

	private static SearchStrategy getSearchStrategy(String algo, NodeUtility util) {
		// int[] fullDirections = { 0, 45, 90, 135, 180, 225, 270, 315 };
		int[] restrictedDirections = { 0, 90, 180, 270 };

		switch (algo) {
			case "BFS":
				return new PartA.BFS(util, restrictedDirections);
			case "DFS":
				return new PartA.DFS(util, restrictedDirections);
			case "BestF":
				// return new PartB.BestFirstSearch(util, restrictedDirections);
			case "AStar":
				// return new AStarSearch(util);
			case "SMAStar":
				// return new SMAStarSearch(util);
			default:
				return null;
		}
	}
}