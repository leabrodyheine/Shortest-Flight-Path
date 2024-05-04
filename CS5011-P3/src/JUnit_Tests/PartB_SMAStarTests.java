package JUnit_Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartB_AStar;
import Algorithms.PartB_SMAStar;
import General.Node;

public class PartB_SMAStarTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 0, null, 0, null);
        Node goal = new Node(1, 90, null, 0, null);
        int planetSize = 2; // Setting the planet size for successor generation
        int memorySize = 1;
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:135)1.551,(1:45)2.633,(2:90)3.236]\n"
                + "[(1:180)2.356,(1:45)2.633,(2:90)3.236,(2:135)4.044]\n"
                + "(1:90)(1:135)(1:180)\n"
                + "1.571\n"
                + "3\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }
}