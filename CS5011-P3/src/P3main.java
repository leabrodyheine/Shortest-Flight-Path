import java.util.List;

import Algorithms.PartA_BFS;
import Algorithms.PartA_DFS;
import Algorithms.PartB_BestF;
import Algorithms.PartB_AStar;
import Algorithms.PartB_SMAStar;

import General.Node;

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

		int memorySize = args.length > 4 ? Integer.parseInt(args[4]) : Integer.parseInt(args[1]);

		// Parse start and goal from args
		String[] startParams = args[2].split(":");
		String[] goalParams = args[3].split(":");

		Node goalNode = new Node(Integer.parseInt(goalParams[0]), Integer.parseInt(goalParams[1]), null, 0, null);
		Node startNode = new Node(Integer.parseInt(startParams[0]), Integer.parseInt(startParams[1]), null, 0,
				goalNode);

		// Run the search algorithm
		runSearch(args[0], Integer.parseInt(args[1]), startNode, goalNode, memorySize);
	}

	private static void runSearch(String algo, int size, Node startNode, Node goalNode, int memorySize) {
		List<Node> path = null;

		switch (algo) {
			case "BFS":
				path = PartA_BFS.bfs(startNode, goalNode, size);
				break;
			case "DFS":
				path = PartA_DFS.dfs(startNode, goalNode, size);
				break;
			case "BestF":
				path = PartB_BestF.BestF(startNode, goalNode, size);
				break;
			case "AStar":
				path = PartB_AStar.AStar(startNode, goalNode, size);
				break;
			case "SMAStar":
				// path = PartB_SMAStar.smaStar(startNode, goalNode, size, memorySize);
				break;
			default:
				System.out.println("fail");
				break;
		}
	}
}