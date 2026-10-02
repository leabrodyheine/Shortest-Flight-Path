# Pathfinding Algorithms for Planetary Navigation

A completed Java coursework project that compares search strategies for routing between airports in the fictional Oedipus planetary system. Each planet is represented as a two-dimensional circular grid, and the command-line program reports a route from a start airport to a goal airport.

## Algorithms

- Depth-first search (DFS)
- Breadth-first search (BFS)
- Best-first search
- A* search
- Simplified Memory-Bounded A* (SMA*)
- Iterative deepening search (IDS)

## Build and run

The project was developed with JDK 17.

```bash
cd "Shortest Flight Path/src"
javac Algorithms/*.java General/*.java Tests/BenchMarkScript.java P3main.java
java P3main BFS 5 2:45 1:180
```

The arguments are `<algorithm> <world-size> <start-distance:start-angle> <goal-distance:goal-angle> [memory-size]`. The optional memory size is used by SMA*.

Run the benchmark harness with:

```bash
java Tests.BenchMarkScript
```

Benchmark output and the accompanying analysis are included in `Shortest Flight Path/src/BenchMarkOutput.txt` and `CS5011-P3 Report.pdf`.

## Project status

This repository contains the completed implementation, tests, benchmarks, and report for the assignment.
