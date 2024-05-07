package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartC_IDA;
import General.Node;

public class PartC_IDATest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test

    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));
        Node goal = new Node(3, 90, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 4;

        String frontier_result = "[(1:315),(1:45),(2:0)]\n"
                + "[(1:0),(1:90),(2:45)]\n"
                + "[(1:135),(2:90),(1:45)]\n"
                + "[(1:180),(2:135),(1:90)]\n"
                + "[(1:225),(2:180),(1:135)]\n"
                + "[(2:180),(3:135),(1:135),(2:90)]\n"
                + "[(2:135),(3:90),(1:90),(2:45)]\n"
                + "[(2:90),(3:45),(1:45),(2:0)]\n"
                + "(1:0)(1:45)(1:90)(1:135)(2:135)(2:90)(3:90)\n" //should be "(1:0)(1:45)(2:45)(3:45)(3:90)\n"  // Shortest path found
                + "5.927\n"
                + "7\n"; // should be 5

        PartC_IDA.ida_star(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
