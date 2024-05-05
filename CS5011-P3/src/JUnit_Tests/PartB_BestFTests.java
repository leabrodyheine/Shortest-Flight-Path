package JUnit_Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartB_BestF;
import General.Node;

public class PartB_BestFTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(1, 180, null, 0, null);
        int planetSize = 4; // Setting the planet size for successor generation
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:135)0.765,(1:45)1.848,(2:90)2.236]\n"
                + "[(1:180)0.000,(2:135)1.474,(1:45)1.848,(2:90)2.236]\n"
                + "(1:90)(1:135)(1:180)\n"
                + "1.571\n"
                + "3\n";

        PartB_BestF.BestF(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testGoalOfZero() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(3, 90, null, 0, null);
        Node goal = new Node(0, 0, null, 0, null);
        int planetSize = 4;
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:135)0.765,(1:45)1.848,(2:90)2.236]\n"
                + "[(1:180)0.000,(2:135)1.474,(1:45)1.848,(2:90)2.236]\n"
                + "(1:90)(1:135)(1:180)\n"
                + "1.571\n"
                + "3\n";
        PartB_BestF.BestF(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(4, 90, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:135)0.765,(1:45)1.848,(2:90)2.236]\n"
                + "[(1:180)0.000,(2:135)1.474,(1:45)1.848,(2:90)2.236]\n"
                + "(1:90)(1:135)(1:180)\n"
                + "1.571\n"
                + "3\n";

        PartB_BestF.BestF(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
