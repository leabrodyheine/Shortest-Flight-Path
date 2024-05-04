package JUnit_Tests;

import General.Node;
import Algorithms.PartA_BFS;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.Test;

public class PartA_BFSTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(3, 0, null, 0, null);
        Node goal = new Node(3, 90, null, 0, null);
        int planetSize = 4; // Setting the planet size for successor generation
        String frontier_result = "[(3:0)]\n"
                + "[(2:0),(3:45),(3:315)]\n"
                + "[(3:45),(3:315),(1:0),(2:45),(2:315)]\n"
                + "[(3:315),(1:0),(2:45),(2:315),(3:90)]\n"
                + "[(1:0),(2:45),(2:315),(3:90),(3:270)]\n"
                + "[(2:45),(2:315),(3:90),(3:270),(1:45),(1:315)]\n"
                + "[(2:315),(3:90),(3:270),(1:45),(1:315),(2:90)]\n"
                + "[(3:90),(3:270),(1:45),(1:315),(2:90),(2:270)]\n"
                + "(3:0)(3:45)(3:90)\n"
                + "4.712\n"
                + "8\n";

        PartA_BFS.bfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testNoPathAvailable() {
        // Setup scenario where there is no path
        Node start = new Node(0, 0, null, 0, null);
        Node goal = new Node(4, 180, null, 0, null);
        int planetSize = 5; // Use an appropriate size where no path exists due to setup

        List<Node> result = PartA_BFS.bfs(start, goal, planetSize);
        assertNull("Should return null when no path is available.", result);
    }

    @Test
    public void testEdgeOfTheGrid() {
        // Testing edge conditions
        Node start = new Node(4, 0, null, 0, null);
        Node goal = new Node(4, 315, null, 0, null);
        int planetSize = 5; // Ensuring the edge of the grid is handled

        List<Node> result = PartA_BFS.bfs(start, goal, planetSize);
        assertNotNull(result);
        assertTrue("The path should navigate along the edge to the goal.", result.contains(goal));
    }

    @Test
    public void testDirectPath() {
        // Test direct path from start to goal
        Node start = new Node(1, 0, null, 0, null);
        Node goal = new Node(1, 45, null, 0, null); // One step away
        int planetSize = 5; // Sufficient to reach from start to goal

        List<Node> result = PartA_BFS.bfs(start, goal, planetSize);
        assertNotNull(result);
        assertEquals("The goal should be the last node in the path.", goal, result.get(result.size() - 1));
    }
}