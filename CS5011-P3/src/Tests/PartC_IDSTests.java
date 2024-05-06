package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartC_IDS;
import General.Node;

public class PartC_IDSTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(3, 90, null, 0, null);
        Node start = new Node(3, 0, null, 0, goal);
        int planetSize = 4;
        String frontier_result = "[(3:0)]\n"
                + "[(3:315), (3:45), (3:0), (2:0)]\n"
                + "[(1:0), (3:90), (2:315), (3:45), (2:45), (3:0), (2:0)]\n"
                + "(3:0)(3:45)(3:90)\n"
                + "4.712\n"
                + "7\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(3, 0, null, 0, goal);
        int planetSize = 2;
        String frontier_result = "[(3:0)]\n"
        + "[(3:0)]\n"
        + "[(3:0)]\n"
        + "fail\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    } //check if this is correct
}
