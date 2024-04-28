import java.util.List;

/********************
 * Starter Code
 * 
 * This class contains some examples of required inputs and outputs
 * 
 * @author alice toniolo
 *
 *         run with
 *         java P3main <Algo> <N> <ds:as> <dg:ag>
 *         we assume <N> <ds:as> <dg:ag> are valid parameters, no need to check
 */

public class P3main {

	public static void main(String[] args) {
		if (args.length < 4) {
			System.out.println("usage: java P3main <DFS|BFS|AStar|BestF|SMAStar|...> <N> <ds:as> <dg:ag> [<param>]");
			System.exit(1);
		}
		// Print initial information
		System.out.println("World: Oedipus " + args[1]);
		System.out.println("Departure airport -- Start: " + args[2]);
		System.out.println("Destination airport -- Goal: " + args[3]);
		System.out.println("Search algorithm: " + args[0]);
		System.out.println();

		// Parse start and goal from args
		String[] startParams = args[2].split(":");
		String[] goalParams = args[3].split(":");
		PartA.Node startNode = new PartA.Node(Integer.parseInt(startParams[0]), Integer.parseInt(startParams[1]), null,
				0);
		PartA.Node goalNode = new PartA.Node(Integer.parseInt(goalParams[0]), Integer.parseInt(goalParams[1]), null, 0);

		// Run the search algorithm
		runSearch(args[0], Integer.parseInt(args[1]), startNode, goalNode);
	}

	private static void runSearch(String algo, int size, PartA.Node startNode, PartA.Node goalNode) {
		List<PartA.Node> path;
		boolean isFinalPath = true;

		switch (algo) {
			case "BFS":
				path = PartA.bfs(startNode, goalNode, size);
				// Calculate path cost and nodes visited
				// Print the path if found
				if (path != null) {
					PartA.printPath(path);
				} else {
					System.out.println("No path found.");
				}
				break;
			case "DFS":
				path = PartA.dfs(startNode, goalNode, size);
				// Calculate path cost and nodes visited
				// Print the path if found
				if (path != null) {
					PartA.printPath(path);
				} else {
					System.out.println("No path found.");
				}
				break;
			case "BestF": // Placeholder for future implementation
				// Implement Best-first search when PartB is ready
				break;
			case "AStar": // Placeholder for future implementation
				// Implement A* search when PartB is ready
				break;
			case "SMAStar": // Placeholder for future implementation
				// Implement SMA* search when PartB is ready
				break;
			default:
				System.out.println("Invalid algorithm specified.");
				break;
		}
	}
}
