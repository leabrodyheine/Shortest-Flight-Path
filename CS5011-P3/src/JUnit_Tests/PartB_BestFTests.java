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

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(0, 0, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:45)1.000,(1:135)1.000]\n"
                + "[(1:0)1.000,(1:135)1.000]\n"
                + "[(1:135)1.000,(1:315)1.000]\n"
                + "[(1:180)1.000,(1:315)1.000]\n"
                + "[(1:225)1.000,(1:315)1.000]\n"
                + "[(1:270)1.000,(1:315)1.000]\n"
                + "[(1:315)1.000]\n"
                + "fail\n"
                + "8\n";
        PartB_BestF.BestF(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testGoalOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(4, 90, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:45)3.368,(1:135)3.368]\n"
                + "[(1:135)3.368,(1:0)4.123]\n"
                + "[(1:0)4.123,(1:180)4.123]\n"
                + "[(1:180)4.123,(1:315)4.760]\n"
                + "[(1:225)4.760,(1:315)4.760]\n"
                + "[(1:315)4.760,(1:270)5.000]\n"
                + "[(1:270)5.000]\n"
                + "fail\n"
                + "8\n";

        PartB_BestF.BestF(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testStartOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(4, 90, null, 0, null);
        Node goal = new Node(1, 90, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(4:90)0.000]\n"
                + "fail\n"
                + "1\n";

        PartB_BestF.BestF(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
