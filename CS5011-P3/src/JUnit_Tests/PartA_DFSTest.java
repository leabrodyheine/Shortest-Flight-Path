package JUnit_Tests;

import General.Node;
import Algorithms.PartA_DFS;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class PartA_DFSTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(1, 180, null, 0, null);
        int planetSize = 4; // Setting the planet size for successor generation
        String frontier_result = "[(1:90)]\n"
                + "[(1:45),(1:135),(2:90)]\n"
                + "[(1:0),(2:45),(1:135),(2:90)]\n"
                + "[(1:315),(2:0),(2:45),(1:135),(2:90)]\n"
                + "[(1:270),(2:315),(2:0),(2:45),(1:135),(2:90)]\n"
                + "[(1:225),(2:270),(2:315),(2:0),(2:45),(1:135),(2:90)]\n"
                + "[(1:180),(2:225),(2:270),(2:315),(2:0),(2:45),(1:135),(2:90)]\n"
                + "(1:90)(1:45)(1:0)(1:315)(1:270)(1:225)(1:180)\n"
                + "4.712\n"
                + "7\n";

        PartA_DFS.dfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
