# Pathfinding Algorithms for Planetary Navigation

## Project Overview
This project focuses on implementing and evaluating AI search algorithms for a flight route planner across the constellation of planets called Oedipus. Each planet is represented as a 2D circular grid. The primary task is to compute the best route from a departure airport (S) to a destination airport (G) using various algorithms under different constraints.

Implemented algorithms include:
- Uninformed Search: Depth-First Search (DFS), Breadth-First Search (BFS)
- Informed Search: Best-First Search (BestF), A* Search (AStar), Simplified Memory-Bounded A* (SMA*)
- Advanced Search Options: Iterative Deepening Search (IDS) and other custom functionalities

## How to Compile and Run the Code
Ensure you have Java installed (JDK17, as used in the School Lab Machines). Navigate to the project directory and use the following commands:

### Compilation
```bash
cd src
javac Algorithms/*.java General/*.java
javac Tests/BenchmarkScript.java


java P3main <Algorithm> <N> <d_s:angle_s> <d_g:angle_g>
# Example: java P3main BFS 5 2:45 1:180


java Tests/BenchmarkScript.java
# Output in BenchmarkOutput.txt
