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

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(0, 0, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:45)1.785,(1:135)1.785]\n"
                + "[(1:135)1.785,(1:0)3.356]\n"
                + "[(1:0)3.356,(1:180)3.356]\n"
                + "[(1:180)3.356,(1:315)5.712]\n"
                + "[(1:225)5.712,(1:315)5.712]\n"
                + "[(1:315)5.712,(1:270)8.854]\n"
                + "[(1:270)8.854]\n"
                + "fail\n"
                + "8\n";

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }

    @Test
    public void testGoalOutOfBounds() {
        System.setOut(new PrintStream(outContent));

        Node start = new Node(1, 90, null, 0, null);
        Node goal = new Node(4, 90, null, 0, null);
        int planetSize = 2;
        String frontier_result = "[(1:90)0.000]\n"
                + "[(1:45)4.153,(1:135)4.153]\n"
                + "[(1:135)4.153,(1:0)6.479]\n"
                + "[(1:0)6.479,(1:180)6.479]\n"
                + "[(1:180)6.479,(1:315)9.472]\n"
                + "[(1:225)9.472,(1:315)9.472]\n"
                + "[(1:315)9.472,(1:270)12.854]\n"
                + "[(1:270)12.854]\n"
                + "fail\n"
                + "8\n";

        PartB_AStar.AStar(start, goal, planetSize);
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

        PartB_AStar.AStar(start, goal, planetSize);
        assertEquals(frontier_result, outContent.toString());
    }
}
