# Pathfinding Algorithms for Planetary Navigation

## Project Overview
This project implements several pathfinding algorithms to navigate a 2D grid 
representing a planet. The goal is to find efficient routes from a starting 
point to a destination under various constraints. The algorithms implemented 
include Breadth-First Search (BFS), Depth-First Search (DFS), A* Search, 
Best-First Search, Simplified Memory-Bounded A* (SMA*), and Iterative Deepening 
Search (IDS). Each algorithm is designed to solve the problem with specific 
advantages and trade-offs in terms of memory usage, runtime efficiency, and path 
optimality.

For Part C of the project, I selected the Iterative Deepening Search (IDS) algorithm. 
I chose IDS for its ability to combine the depth-first searching approach, 
which minimizes memory usage, with the breadth-first search’s ability to find 
the shortest path. This makes IDS particularly effective for scenarios where the 
depth of the solution is unknown or the state space is very large.


## How to Run the Benchmark Testing
Compile the Java files:
1. cd src
2. javac Algorithms/*.java General/*.java
3. javac Tests/BenchMarkScript.java
4. java Tests/BenchMarkScript.java
5. view the results in BenchMarkOutput.txt (optional)
6. From the IDE, run the MakeBoxPlots.py script to generate box plots for runtime 
and cost. This will display box plots illustrating the performance comparisons 
across the implemented algorithms.

## How to run Junit testing
Navigate to the testing folder, then you can run the tests from inside the IDE.