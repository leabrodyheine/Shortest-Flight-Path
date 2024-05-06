package Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import Algorithms.PartB_SMAStar;
import General.Node;

public class PartB_SMAStarTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testBasicPathfinding() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(1:0)1.414]\n"
                + "[(1:45)10000.000,(1:315)10000.000]\n"
                + "fail\n"
                + "2\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testBasicPathfinding2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 4;
        String frontier_result = "[(1:0)1.414]\n"
                + "[(1:45)1.551,(1:315)2.633]\n"
                + "[(1:90)1.571,(1:315)2.633,(1:0)2.985]\n"
                + "(1:0)(1:45)(1:90)\n"
                + "1.571\n"
                + "3\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testGoalOfZero() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(0, 0, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(1:0)1.000]\n"
                + "[(1:45)10000.000,(1:315)10000.000]\n"
                + "fail\n"
                + "2\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(1, 90, null, 0, null);
        Node start = new Node(3, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(3:0)3.162]\n"
                + "fail\n"
                + "1\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds2() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(4, 90, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(1:0)4.123]\n"
                + "[(1:45)10000.000,(1:315)10000.000]\n"
                + "fail\n"
                + "2\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testPathOutOfBounds3() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(4, 90, null, 0, null);
        Node start = new Node(4, 0, null, 0, goal);
        int planetSize = 2;
        int memorySize = 2;
        String frontier_result = "[(4:0)5.657]\n"
                + "fail\n"
                + "1\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testMemoryConstraint() {
        System.setOut(new PrintStream(outContent));

        Node goal = new Node(2, 180, null, 0, null);
        Node start = new Node(1, 0, null, 0, goal);
        int planetSize = 3;
        int memorySize = 2;
        String frontier_result = "[(1:0)3.000]\n"
                + "[(2:0)10000.000,(1:315)10000.000]\n"
                + "fail\n"
                + "2\n";

        PartB_SMAStar.smaStar(start, goal, planetSize, memorySize);
        assertEquals(frontier_result, outContent.toString());
    }
}