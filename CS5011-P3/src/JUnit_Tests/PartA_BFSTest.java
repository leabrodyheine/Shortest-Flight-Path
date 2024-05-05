package JUnit_Tests;

import General.Node;
import Algorithms.PartA_BFS;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class PartA_BFSTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(3, 0, null, 0, null);
        Node goal = new Node(3, 90, null, 0, null);
        int planetSize = 4;
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
        int planetSize = 2;
        String frontier_result = "[(1:0)]\n"
                + "[(1:45),(1:315)]\n"
                + "[(1:315),(1:90)]\n"
                + "[(1:90),(1:270)]\n"
                + "[(1:270),(1:135)]\n"
                + "[(1:135),(1:225)]\n"
                + "[(1:225),(1:180)]\n"
                + "[(1:180)]\n"
                + "fail\n"
                + "8\n";

        PartA_BFS.bfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testStartOfZero() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(0, 0, null, 0, null);
        Node goal = new Node(2, 0, null, 0, null);
        int planetSize = 5;
        String frontier_result = "[(0:0)]\n"
        + "[(1:0)]\n"
        + "[(1:45),(1:315),(2:0)]\n"
        + "[(1:315),(2:0),(1:90),(2:45)]\n"
        + "[(2:0),(1:90),(2:45),(1:270),(2:315)]\n"
        + "(0:0)(1:0)(2:0)\n"
        + "2.000\n"
        + "5\n";

        PartA_BFS.bfs(start, goal, planetSize);
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

        PartA_BFS.bfs(start, goal, planetSize);
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
                + "[(1:315),(1:180)]\n"
                + "[(1:180),(1:0)]\n"
                + "[(1:0),(1:135)]\n"
                + "[(1:135),(1:45)]\n"
                + "[(1:45),(1:90)]\n"
                + "[(1:90)]\n"
                + "fail\n"
                + "8\n";

        PartA_BFS.bfs(start, goal, planetSize);
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

        PartA_BFS.bfs(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}