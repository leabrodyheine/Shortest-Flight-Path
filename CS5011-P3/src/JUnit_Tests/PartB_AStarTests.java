package JUnit_Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartB_AStar;
import General.Node;

public class PartB_AStarTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(1, 180, null, 0, null);
        int planetSize = 4; 
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:135)1.551,(1:45)2.633,(2:90)3.236]\n"
                + "[(1:180)2.356,(1:45)2.633,(2:90)3.236,(2:135)4.044]\n"
                + "(1:90)(1:135)(1:180)\n"
                + "1.571\n"
                + "3\n";

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testGoalOfZero() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(3, 90, null, 0, null);
        Node goal = new Node(0, 0, null, 0, null);
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

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(4, 90, null, 0, null);
        int planetSize = 2;
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

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
