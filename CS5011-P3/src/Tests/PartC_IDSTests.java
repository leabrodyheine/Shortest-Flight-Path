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
        String frontier_result = "(3:0)(3:45)(3:90)\n"
                + "4.712\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testBasicPathfinding2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(7, 90, null, 0, null);
        Node start = new Node(1, 225, null, 0, goal);
        int planetSize = 8;
        String frontier_result = "(1:225)(1:180)(1:135)(1:90)(2:90)(3:90)(4:90)(5:90)(6:90)(7:90)\n"
                + "8.356\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(3, 0, null, 0, goal);
        int planetSize = 2;
        String frontier_result = "fail\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testAdvancedPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(2, 90, null, 0, null);
        Node start = new Node(7, 0, null, 0, goal);
        int planetSize = 8;
        String frontier_result = "(7:0)(6:0)(5:0)(4:0)(3:0)(2:0)(2:45)(2:90)\n"
                + "8.142\n";

        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testAdvancedPathfinding2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 0, null, 0, null);
        Node start = new Node(3, 180, null, 0, goal);
        int planetSize = 4;
        String frontier_result = "(3:180)(2:180)(1:180)(1:135)(1:90)(1:45)(1:0)\n"
                + "5.142\n";
        PartC_IDS.iterativeDeepeningSearch(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
