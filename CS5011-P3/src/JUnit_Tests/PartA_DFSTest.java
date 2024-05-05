package JUnit_Tests;

import General.Node;
import Algorithms.PartA_DFS;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class PartA_DFSTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(1, 180, null, 0, null);
        int planetSize = 4;
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

    @Test
    public void testGoalOfZero() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 0, null, 0, null);
        Node goal = new Node(0, 0, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(1:0)]\n"
                + "[(1:45),(1:315)]\n"
                + "[(1:90),(1:315)]\n"
                + "[(1:135),(1:315)]\n"
                + "[(1:180),(1:315)]\n"
                + "[(1:225),(1:315)]\n"
                + "[(1:270),(1:315)]\n"
                + "[(1:315)]\n"
                + "fail\n"
                + "8\n";

        PartA_DFS.dfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    } // expected here is weirdly long?

    @Test
    public void testStartOfZero() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(0, 0, null, 0, null);
        Node goal = new Node(2, 0, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(0:0)]\n"
                + "[(1:0)]\n"
                + "[(1:45),(1:315)]\n"
                + "[(1:90),(1:315)]\n"
                + "[(1:135),(1:315)]\n"
                + "[(1:180),(1:315)]\n"
                + "[(1:225),(1:315)]\n"
                + "[(1:270),(1:315)]\n"
                + "[(1:315)]\n"
                + "fail\n"
                + "9\n";

        PartA_DFS.dfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testEdgeOfTheGrid() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(3, 0, null, 0, null);
        Node goal = new Node(3, 45, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(3:0)]\n"
                + "fail\n"
                + "1\n";

        PartA_DFS.dfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    } // Is this correct?

    @Test
    public void testGoalOffGrid() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 270, null, 0, null);
        Node goal = new Node(3, 315, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(1:270)]\n"
                + "[(1:225),(1:315)]\n"
                + "[(1:180),(1:315)]\n"
                + "[(1:135),(1:315)]\n"
                + "[(1:90),(1:315)]\n"
                + "[(1:45),(1:315)]\n"
                + "[(1:0),(1:315)]\n"
                + "[(1:315)]\n"
                + "fail\n"
                + "8\n";

        PartA_DFS.dfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testStartOffGrid() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(4, 270, null, 0, null);
        Node goal = new Node(1, 315, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(4:270)]\n"
                + "fail\n"
                + "1\n";

        PartA_DFS.dfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
