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
    public void testGoalOfZero() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 0, null, 0, null);
        Node goal = new Node(0, 0, null, 0, null);
        int planetSize = 5; // Use an appropriate size where no path exists due to setup
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
    } // expected here is weirdly long?

    @Test
    public void testEdgeOfTheGrid() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(3, 0, null, 0, null);
        Node goal = new Node(3, 45, null, 0, null);
        int planetSize = 2; // Ensuring the edge of the grid is handled
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
    } // this should fail?
}